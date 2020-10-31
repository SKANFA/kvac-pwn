package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.control;

import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.control.ControlEvent;
import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.control.ControlEvent.CONTROL_TYPE;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.CommonHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllAll {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void register() {
        CommonHeader.getCOMMAND_HANDLER().register();//COMMAND register
        CommonHeader.getTOR_HEADER().getTOR_HANDLER().register();//TOR register
    }

    public void init() {
        sendControlEvent(CONTROL_TYPE.INIT);
    }

    public void start() {
        sendControlEvent(CONTROL_TYPE.START);
    }

    /**
     *
     * @param controlTypeEvent
     */
    private void sendControlEvent(CONTROL_TYPE controlTypeEvent) {
        ControlEvent controlEvent = new ControlEvent();
        controlEvent.setType(controlTypeEvent);
        CommonHeader.getEVENT_HEADER().getCommandEventBus().post(controlEvent);//COMMAND
        CommonHeader.getEVENT_HEADER().getTorEventBus().post(controlEvent);//TOR
    }
}
