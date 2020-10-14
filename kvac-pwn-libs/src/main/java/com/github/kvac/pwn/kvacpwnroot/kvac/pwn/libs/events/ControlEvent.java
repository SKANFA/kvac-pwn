package com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events;

import lombok.Getter;
import lombok.Setter;

public class ControlEvent extends Event {

    public enum CONTROL_TYPE {
        INIT, START, STOP
    }
    @Getter
    @Setter
    private CONTROL_TYPE type;
}
