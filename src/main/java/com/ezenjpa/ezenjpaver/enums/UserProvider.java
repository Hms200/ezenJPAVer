package com.ezenjpa.ezenjpaver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum UserProvider {
    GOOGLE("GOOGLE"),
    LOCAL("LOCAL");

    private String value;
}
