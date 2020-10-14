package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.handlers.ShellHandler;
import lombok.Getter;

public class ShellHeader {

    @Getter
    public final ShellHandler SHELL_HANDLER = new ShellHandler();

}
