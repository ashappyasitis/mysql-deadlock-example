package me.kevin.mysqldeadloackexample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kevin.mysqldeadloackexample.model.entity.Card;
import me.kevin.mysqldeadloackexample.model.entity.Property;
import me.kevin.mysqldeadloackexample.model.request.CreateCardRequest;
import me.kevin.mysqldeadloackexample.repository.CardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final PropertyService propertyService;

    public void save(CreateCardRequest createCardRequest) {
        Card save = cardRepository.save(new Card(createCardRequest));

        Property propertyOrNull = propertyService.getPropertyOrNull(createCardRequest.getPropertySeq());
        if (propertyOrNull != null && "yes".equals(propertyOrNull.getValue())) {
            try {
                Thread.sleep(10000);
                throw new RuntimeException("Rollback을 위한 Exception");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
