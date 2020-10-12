package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts;

import org.bukkit.Material;

public class Sluda extends Artefact {

    // VALUE
    public static String artefactID = "sluda_a";
    public static Material material = Material.SCUTE;
    public static String name = "Слюда";
    public static String lore0 = "Восстанавливает 1 ед. здоровья раз в 3 секунды";

    public static int lore1_cost = 10000;
    // VALUE

    public Sluda(int count) {
        setAmount(count);
        initART(this);
    }

    public Sluda() {
        setAmount(1);
        initART(this);
    }
}
