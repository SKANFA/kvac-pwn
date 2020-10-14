package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.commands.header;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.commands.handler.CommandHandler;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.control.ControllAll;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.EventHeader;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.handler.TorHandler;
import lombok.Getter;

public class CommonHeader {

    private CommonHeader() {
    }
    @Getter
    static final ControllAll CONTROLL_ALL = new ControllAll();

    @Getter
    static final CommandHandler COMMAND_HANDLER = new CommandHandler();
    @Getter
    static final TorHandler TOR_HANDLER = new TorHandler();

    @Getter
    static final EventHeader EVENT_HEADER = new EventHeader();
}
