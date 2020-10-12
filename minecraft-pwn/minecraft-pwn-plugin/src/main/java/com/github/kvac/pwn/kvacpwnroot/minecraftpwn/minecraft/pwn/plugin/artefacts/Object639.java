package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts;

import org.bukkit.Material;

public class Object639 extends Artefact {

    // VALUE
    public static String artefactID = "Object639_a";
    public static String name = "Object639";
    public static String lore0 = "Для вывода ядов";
    public static Material material = Material.FERMENTED_SPIDER_EYE;
    public static int lore1_cost = 20000;
    // VALUE

    public Object639(int count) {
        setAmount(count);
        initART(this);
    }

    public Object639() {
        setAmount(1);
        initART(this);
    }
}
