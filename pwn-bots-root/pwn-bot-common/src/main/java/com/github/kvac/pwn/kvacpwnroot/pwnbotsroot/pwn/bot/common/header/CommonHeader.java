package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.control.CommonControl;
import lombok.Getter;

public class CommonHeader {

    private CommonHeader() {
    }
    @Getter
    static final CommonControl CC = new CommonControl();

    @Getter
    static final TorHeader TOR_HEADER = new TorHeader();

}
