package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class CartListDTO {

    private Long cartListIdx;

    @Builder
    public CartListDTO(Long cartListIdx) {
        this.cartListIdx = cartListIdx;
    }
}
