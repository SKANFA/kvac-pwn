package com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.command;

import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.Event;
import lombok.Getter;
import lombok.Setter;

public class CommandEvent extends Event {

    @Getter
    @Setter
    String command;

    @Getter
    @Setter
    String output;
}
