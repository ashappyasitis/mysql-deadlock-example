package me.kevin.mysqldeadloackexample.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevin.mysqldeadloackexample.model.request.CreateCardRequest;

import javax.persistence.*;

@Entity
@Table(name = "t_card")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_seq")
    private Integer seq;

    @Column(name = "mem_seq")
    private Integer memSeq;

    @Column(name = "sale_seq")
    private Integer saleSeq;

    public Card(CreateCardRequest createCardRequest) {
        this.memSeq = createCardRequest.getMemSeq();
        this.saleSeq = createCardRequest.getSaleSeq();
    }

    public Card(Integer memSeq, Integer saleSeq) {
        this.memSeq = memSeq;
        this.saleSeq = saleSeq;
    }
}
