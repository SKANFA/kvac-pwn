package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.commands.handler.CommandHandler;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.control.ControllAll;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.header.TorHeader;
import lombok.Getter;

public class CommonHeader {

    private CommonHeader() {
    }
    @Getter
    static final ControllAll CONTROLL_ALL = new ControllAll();

    @Getter
    static final CommandHandler COMMAND_HANDLER = new CommandHandler();

    @Getter
    static final TorHeader TOR_HEADER = new TorHeader();
    @Getter
    static final EventHeader EVENT_HEADER = new EventHeader();

    public static boolean debug = true;
}
