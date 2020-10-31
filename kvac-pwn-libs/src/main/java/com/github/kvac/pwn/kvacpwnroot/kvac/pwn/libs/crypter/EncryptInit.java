package com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.crypter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptInit {

    enum Actions {
        ENCRYPT, DECRYPT
    }

    @Getter
    @Setter
    Actions action;

    @Getter
    @Setter
    File fromFile;
    @Getter
    @Setter
    File toFile;

    @Getter
    Crypter crypter = new Crypter();

    Logger logger = LoggerFactory.getLogger(getClass());
    @Getter
    @Setter
    int bufferSize = 1024;

    public static void main(String[] args) {
        EncryptInit encryptInit = new EncryptInit();
        try {
            if (args.length != 3) {
                String errorMsg = Arrays.toString(Actions.values()) + " From TO";
                encryptInit.logger.error(errorMsg);
                System.exit(3);
            }
            Actions action = Actions.valueOf(args[0]);
            File fromFile = new File(args[1]);
            File toFile = new File(args[2]);

            encryptInit.setAction(action);
            encryptInit.setFromFile(fromFile);
            encryptInit.setToFile(toFile);
            encryptInit.start();

        } catch (IOException e) {
            encryptInit.logger.error("", e);
        }
    }

    private void start() throws IOException {
        try (InputStream inputStream = new FileInputStream(fromFile)) {
            try (OutputStream outputStream = new FileOutputStream(toFile, true)) {
                byte[] buf = new byte[bufferSize];

                int length;
                while ((length = inputStream.read(buf)) != -1) {
                    if (action.equals(Actions.DECRYPT)) {
                        byte[] crypted = crypter.decrypt(buf);
                        outputStream.write(crypted, 0, length);
                    } else if (action.equals(Actions.ENCRYPT)) {
                        byte[] crypted = crypter.encrypt(buf);
                        outputStream.write(crypted, 0, length);
                    }
                    outputStream.flush();
                }
            }
        }
    }
}
