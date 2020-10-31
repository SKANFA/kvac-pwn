package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.init;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.CommonHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Init {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        Init init = new Init();
        try {
            init.init();
            init.start();
        } catch (Exception e) {
            init.logger.error("", e);
        }
    }

    //##############################INIT########################################
    //##########################################################################
    public void init() {
        CommonHeader.getCONTROLL_ALL().register();
        CommonHeader.getCONTROLL_ALL().init();
        //ADD CC HOST
    }

    //##############################START#######################################
    //##########################################################################
    public void start() {
        CommonHeader.getCONTROLL_ALL().start();
    }
}
