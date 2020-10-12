package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin;

import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Sluda;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Object639;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Pillow;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Artefact.ArtefactType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddART implements CommandExecutor {

    enum commands {
        give, nulled
    }

    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] cmdArgs) {
        StringBuilder errorBuilder = new StringBuilder();
        try {
            CommandSender playerSender = sender;

            if (cmdArgs.length == 0) {
                return false;
            }

            commands cmd = null;
            try {
                cmd = commands.valueOf(cmdArgs[0]);
            } catch (Exception e) {
                playerSender.sendMessage("Доступные действия:");
                for (commands cmds : commands.values()) {
                    if (cmds.toString().equals(commands.nulled.toString())) {
                        continue;
                    }
                    playerSender.sendMessage(cmds.toString());
                }
                cmd = commands.nulled;
                return false;
            }

            if (!playerSender.hasPermission(Permissions.STALKER_ARTEFACT_GIVE)) {
                playerSender.sendMessage("You do not have permission '" + Permissions.STALKER_ARTEFACT_GIVE + "'");
                return false;
            }
            Player playerTo = Bukkit.getPlayer(cmdArgs[1]);
            if (playerTo == null) {
                playerSender.sendMessage(ChatColor.RED + "Нет такого игрока:" + cmdArgs[1]);
                return false;
            }
            ArtefactType addWhat;
            try {
                addWhat = ArtefactType.valueOf(cmdArgs[2]);
            } catch (Exception e) {
                playerSender.sendMessage("Доступные артефакты:");
                for (ArtefactType types : ArtefactType.values()) {
                    playerSender.sendMessage(types.toString());
                }
                return false;
            }

            int count = 1;
            try {
                count = Integer.parseInt(cmdArgs[3]);
            } catch (Exception e) {
            }

            for (int i = 0; i < cmdArgs.length; i++) {
                playerSender.sendMessage(cmdArgs[i]);
            }
            playerSender.sendMessage("cmd:" + cmd + " кому:" + playerTo + " что:" + addWhat + " сколько:" + count);

            if (addWhat.equals(ArtefactType.SLUDA)) {
                playerTo.getInventory().addItem(new Sluda(count));
                playerTo.sendMessage(playerSender.getName() + " выдал вам " + addWhat + " в кол-ве:" + count);
            } else if (addWhat.equals(ArtefactType.OBJECT639)) {
                playerTo.getInventory().addItem(new Object639(count));
                playerTo.sendMessage(playerSender.getName() + " выдал вам " + addWhat + " в кол-ве:" + count);
            } else if (addWhat.equals(ArtefactType.PILLOW)) {
                playerTo.getInventory().addItem(new Pillow(count));
                playerTo.sendMessage(playerSender.getName() + " выдал вам " + addWhat + " в кол-ве:" + count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        sender.sendMessage(errorBuilder.toString());
        return true;
    }
}
