/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header;

import com.google.common.eventbus.EventBus;
import lombok.Getter;

/**
 *
 * @author jdcs_dev
 */
public class EventHeader {

    @Getter
    public final EventBus torEventBus = new EventBus();
    @Getter
    public final EventBus commandEventBus = new EventBus();

}
