package com.ezenjpa.ezenjpaver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ImgCat {

    // img cat - goods, reviews, thumb, detail
    GOODS("goods"),
    REVIEWS("reviews"),
    THUMB("thumb"),
    DETAIL("detail");

    private final String imgCat;
}
