package me.kevin.mysqldeadloackexample.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_property")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Property {
    @Id
    @GeneratedValue
    @Column(name = "property_seq")
    private Integer seq;

    private String key;
    private String value;
}
