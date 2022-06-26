package me.kevin.mysqldeadloackexample.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kevin.mysqldeadloackexample.model.request.CreateCardRequest;
import me.kevin.mysqldeadloackexample.service.CardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.kevin.mysqldeadloackexample.util.Utils.toJson;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class CardRestController {
    private final CardService cardService;

    @PostMapping("/cards")
    public String createCard(@RequestBody CreateCardRequest createCardRequest) {
        log.info("PST /api/v1/cards param: {}", toJson(createCardRequest));

        cardService.save(createCardRequest);

        return "카드생성 완료";
    }


}
