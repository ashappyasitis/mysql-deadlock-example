# mysql-deadlock-example

## 주요 Error Message 정리
> org.springframework.dao.<span style="color:#11b986">**CannotAcquireLockException**</span>
> 
> org.hibernate.exception.<span style="color:#11b986">**LockAcquisitionException**</span>
> 
> com.mysql.cj.jdbc.exceptions.<span style="color:#11b986">**MySQLTransactionRollbackException**</span>
> 
> <span style="color:#11b986">**Deadlock**</span> found when trying to get lock

## Error 의 근거

MySQL, Insertion, Deadlock
출처: [innodb-locks-set](https://dev.mysql.com/doc/refman/8.0/en/innodb-locks-set.html)

## 가설에 대한 요약
스토리를 요약해 보면
1. session 1은 row에 exclusive lock을 획득한다.
2. session 2, 3은 duplicate-key error가 발생하고 이에 따라서 row에 shared lock을 각각 요청한다.
3. 이때 session 1이 rollback을 하게 되면 exclusive lock이 해제되고 이에 따라 session 2, 3의 shared lock이 요청 대기에서 승인 상태로 변경된다.
4. **Deadlock:** 이 상황에서 session 2, 3은 서로 shared lock을 획득한 상황이기 때문에 exclusive lock을 획득할 수 없게 된다.


# 동작 가이드

## Docker로 MySQL 띄우기
### MySQL docker image download
> $ <span style="color:#11b986">**docker**</span> pull mysql:latest

### Docker container 생성 및 실행
> $ <span style="color:#11b986">**docker**</span> run --name mysql_53306 -p <span style="color:#FF9514">**53306**</span>:**3306** -e MYSQL_ROOT_PASSWORD=53306 -e TZ=Asia/Seoul --restart=always -d mysql:latest
 
### Docker container 실행 확인
> $ <span style="color:#11b986">**docker**</span> ps


## Schema 생성
```sql
create schema s_deadlock_test collate utf8_general_ci;
```

### Table 생성
```sql
create table s_deadlock_test.t_card
(
    card_seq int auto_increment,
    mem_seq  int not null,
    sale_seq int not null,
    constraint t_card_pk
        primary key (card_seq)
);

create unique index t_card_mem_seq_sale_seq_uindex
    on s_deadlock_test.t_card (mem_seq, sale_seq);
    
# 나중에 태스트를 위한 
create table s_deadlock_test.t_property
(
    property_seq int auto_increment
        primary key,
    `key`        varchar(255) not null,
    value        varchar(255) not null
);
    
```

### Data 삽입
```sql
insert into s_deadlock_test.t_property (`key`, value) VALUES ('thread_sleep', 'yes');
insert into s_deadlock_test.t_property (`key`, value) VALUES ('thread_sleep', 'no');
```

## 테스트 Process
![테스트 프로세스](https://velog.velcdn.com/images/ashappyasikonw/post/a43e4105-2ddc-4c13-8342-fc1856b8784d/image.png)

1. 프로젝트를 실행 시킨다. (http://localhost:20000)
2. 순서1: 카드 추가 시도 - 10 초후 롤백
   POST /api/v1/cards 호출 ({propertySeq:1, memSeq: 1, saleSeq: 1}) -  propertySeq:1 인 경우 - 10 초 후 rollback 예정
3. 순서2: 카드 추가 시도
   POST /api/v1/cards 호출 ({propertySeq:2, memSeq: 1, saleSeq: 1})
4. 순서3: 카드 추가 시도
   POST /api/v1/cards 호출 ({propertySeq:2, memSeq: 1, saleSeq: 1})
5. Exception Error Message 확인

```bash

# 순서1: 카드 추가 시도 - 10 초후 롤백
curl --location --request POST 'http://localhost:20000/api/v1/cards' \
--header 'Content-Type: application/json' \
--data-raw '{
    "propertySeq": 1,
    "memSeq": 1,
    "saleSeq": 1
}'

# 순서2: 카드 추가 시도
curl --location --request POST 'http://localhost:20000/api/v1/cards' \
--header 'Content-Type: application/json' \
--data-raw '{
    "propertySeq": 2,
    "memSeq": 1,
    "saleSeq": 1
}'

# 순서3: 카드 추가 시도
curl --location --request POST 'http://localhost:20000/api/v1/cards' \
--header 'Content-Type: application/json' \
--data-raw '{
    "propertySeq": 2,
    "memSeq": 1,
    "saleSeq": 1
}'
```

### Error Message 확인
```log
...
Caused by: org.hibernate.exception.LockAcquisitionException: could not execute statement\
...
Caused by: com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction\
...
```

# 검증완료