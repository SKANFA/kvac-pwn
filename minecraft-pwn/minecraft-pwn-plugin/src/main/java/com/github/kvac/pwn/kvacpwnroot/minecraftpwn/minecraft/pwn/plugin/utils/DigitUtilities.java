package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.utils;

import java.security.SecureRandom;

public class DigitUtilities {

    private DigitUtilities() {
    }

    public static double randDouble(double min, double max) {
        SecureRandom r = new SecureRandom();
        return min + (max - min) * r.nextDouble();
    }
}
