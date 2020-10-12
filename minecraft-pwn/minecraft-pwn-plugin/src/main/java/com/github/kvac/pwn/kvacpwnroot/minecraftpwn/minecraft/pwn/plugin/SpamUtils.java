package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin;

public class SpamUtils {

    public static boolean spamContains(String forCheck) {
        String[] splited = forCheck.split(" ");
        for (int i = 0; i < splited.length; i++) {
            if (containsDomain(splited[i])) {
                return true;
            }
            String[] splitedIP = splited[i].split(":");
            for (int j = 0; j < splitedIP.length; j++) {
                if (validIP(splitedIP[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsDomain(String input) {
        for (String string : PluginEntryPoint.domains) {
            if (input.toLowerCase().contains(string.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean validIP(String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }
            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            if (ip.endsWith(".")) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
