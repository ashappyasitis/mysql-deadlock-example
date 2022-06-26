package me.kevin.mysqldeadloackexample.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCardRequest {
    private Integer propertySeq;
    private Integer memSeq;
    private Integer saleSeq;
}
