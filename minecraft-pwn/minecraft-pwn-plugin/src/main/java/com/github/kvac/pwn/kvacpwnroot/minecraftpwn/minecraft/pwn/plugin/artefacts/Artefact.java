package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Artefact extends ItemStack {

    public static enum ArtefactType {
        SLUDA, OBJECT639, PILLOW
    }

    @Getter
    @Setter
    private String artefactID = "ZERO";

    @Getter
    @Setter
    private Material material = Material.STONE;

    @Getter
    @Setter
    private List<String> loree = new ArrayList<String>();

    @Getter
    @Setter
    private String lore0 = "Artefact_DEFAULT";

    public static int lore1_cost = 10000;

    protected static void initART(Artefact artefact) {
        try {
            artefact.setArtefactID((String) artefact.getClass().getField("artefactID").get(artefact));

            artefact.setType((Material) artefact.getClass().getField("material").get(artefact));
            if (!artefact.hasItemMeta()) {
                ItemMeta m = new ItemStack(artefact.getType()).getItemMeta();
                // NAME
                m.setDisplayName(ChatColor.GREEN + (String) artefact.getClass().getField("name").get(artefact));
                // NAME

                // LORE
                // LORE--description
                artefact.getLoree().add((String) artefact.getClass().getField("lore0").get(artefact));
                // LORE--description
                // LORE--cost
                artefact.getLoree()
                        .add(Integer.toString((int) artefact.getClass().getField("lore1_cost").get(artefact)));
                // LORE--cost

                m.setLore(artefact.getLoree());
                // LORE

                // TEST
                // TEST
                // save meta
                artefact.setItemMeta(m);
            }
            //
            //
            //
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        System.out.println(artefact.hasItemMeta() + ":" + artefact.getItemMeta());
    }

    public static ArtefactType recognizeArtedact(ItemStack itemStack) {
        ArtefactType type = null;
        if (itemStack != null) {
            try {
                ItemMeta meta = itemStack.getItemMeta();
                if (meta != null) {
                    List<String> lore = meta.getLore();
                    if (lore != null && lore.size() > 0) {
                        // SLUDA
                        if (lore.get(0).equals(Sluda.lore0)) {
                            return ArtefactType.SLUDA;
                        } else if (lore.get(0).equals(Pillow.lore0)) {
                            return ArtefactType.PILLOW;
                        }
                        // SLUDA
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return type;
    }

}
