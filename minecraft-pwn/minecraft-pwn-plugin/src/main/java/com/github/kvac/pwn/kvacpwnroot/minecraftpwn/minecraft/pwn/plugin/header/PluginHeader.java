package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.header;

import java.io.File;
import java.util.ArrayList;
import lombok.Getter;

public class PluginHeader {

    private PluginHeader() {
    }
    // WEAPON
    @Getter
    static char wws = ' ';
    @Getter
    static final String WEAPON_LORE = Character.toString(PluginHeader.getWws());

    @Getter
    static final ArrayList<String> domains = new ArrayList<>();

    public static boolean fakeOnline = false;

    public static boolean random = true;
    public static int fake = 140;
    public static int min = 40;
    public static int max = 20000;

    public static File pingFile = new File("PING_LOG");
}
