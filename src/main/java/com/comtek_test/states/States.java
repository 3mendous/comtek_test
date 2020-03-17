package com.comtek_test.states;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum States {
    WAITING ("waiting"),
    PROCESSING ("processing"),
    SENDING ("sending");

    private String name;
}
