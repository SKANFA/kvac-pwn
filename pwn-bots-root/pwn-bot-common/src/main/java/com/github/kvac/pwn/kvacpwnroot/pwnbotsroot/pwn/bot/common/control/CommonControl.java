package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.control;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.CommonHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonControl {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void init() {
        //INIT CMD
        //INIT CMD

        //INIT TOR
        CommonHeader.getTOR_HEADER().init();
        //INIT TOR
        //INIT CMD
        //INIT CMD
        //INIT CMD
        //INIT CMD
    }

    public void start() {
        CommonHeader.getTOR_HEADER().start();

    }

    public void listen() {
        logger.info("listen");
        CommonHeader.getTOR_HEADER().listen();
    }

}
