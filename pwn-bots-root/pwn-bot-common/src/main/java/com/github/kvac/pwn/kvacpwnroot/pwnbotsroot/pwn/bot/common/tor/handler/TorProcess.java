package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.handler;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.CommonHeader;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.header.TorHeader;
import com.subgraph.orchid.TorClient;

public class TorProcess {

    public void init() {
        TorHeader.setTorClient(new TorClient());
        TorHeader.getTorClient().addInitializationListener(CommonHeader.getTOR_HEADER().getTOR_HANDLER());
        TorHeader.getTorClient().enableSocksListener(9000);
    }

    public void start() {
        TorHeader.getTorClient().start();
    }

}
