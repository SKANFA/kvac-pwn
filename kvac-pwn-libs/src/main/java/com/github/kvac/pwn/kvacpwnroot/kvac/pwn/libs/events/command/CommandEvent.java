/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.command;

import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.Event;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jdcs_dev
 */
public class CommandEvent extends Event {

    @Getter
    @Setter
    String command;

    @Getter
    @Setter
    String output;
}
