package me.kevin.mysqldeadloackexample.repository;

import me.kevin.mysqldeadloackexample.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
