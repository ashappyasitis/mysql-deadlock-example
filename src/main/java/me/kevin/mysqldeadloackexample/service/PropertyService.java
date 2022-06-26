package me.kevin.mysqldeadloackexample.service;

import lombok.RequiredArgsConstructor;
import me.kevin.mysqldeadloackexample.model.entity.Property;
import me.kevin.mysqldeadloackexample.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public Property getPropertyOrNull(int seq) {
        return propertyRepository.findBySeq(seq);
    }
}
