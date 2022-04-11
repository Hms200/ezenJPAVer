package com.ezenjpa.ezenjpaver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum Events {
    NOTHING("없음"),
    DISCOUN("할인"),
    MD_PICK("추천"),
    EVENT("이벤트");

    private final String event;

    Events(String event) {
        this.event = event;
    }


}
