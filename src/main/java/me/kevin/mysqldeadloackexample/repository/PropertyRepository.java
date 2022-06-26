package me.kevin.mysqldeadloackexample.repository;

import me.kevin.mysqldeadloackexample.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    Property findBySeq(int seq);
}
