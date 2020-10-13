package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.threads;

import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.PluginEntryPoint;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Artefact;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Artefact.ArtefactType;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtefactInventTask extends BukkitRunnable {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Getter
    @Setter
    private boolean stopeed = false;
    @Getter
    @Setter
    private PluginEntryPoint main;

    public ArtefactInventTask(PluginEntryPoint plugin) {
        this.main = plugin;
    }

    @Override
    public void run() {
        do {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
            if (stopeed == true) {
                break;
            }

            Collection<? extends Player> players = this.main.getServer().getOnlinePlayers();

            for (Player player : players) {
                /*
                                 * // RESPAWN if (player.isDead()) { try { ((CraftPlayer)
                                 * player).getHandle().playerConnection .a(new
                                 * PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN)); } catch
                                 * (Exception e) { e.printStackTrace(); } } // RESPAWN
                 */
                // ART
                PlayerInventory inventory = player.getInventory();

                try {

                    ItemStack[] itemstacks = inventory.getStorageContents();
                    if (itemstacks.length > 0) {
                        for (int i = 0; i < itemstacks.length; i++) {
                            if (itemstacks[i] != null) {
                                ItemStack itemStack = itemstacks[i];
                                ArtefactType typeART = Artefact.recognizeArtedact(itemStack);
                                if (typeART != null) {
                                    double count = (double) itemStack.getAmount();
                                    double currentHealth = player.getHealth();

                                    if (typeART.equals(ArtefactType.SLUDA)) {
                                        if ((currentHealth + count) <= 20) {
                                            player.setHealth(currentHealth + count);
                                        } else if ((currentHealth + count) >= 20) {
                                            player.setHealth(20);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        } while (isStopeed() == false);
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        stopeed = true;
    }
}
