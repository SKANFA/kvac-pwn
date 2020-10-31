package com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.commands;

import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.Permissions;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Artefact.ArtefactType;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Object639;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Pillow;
import com.github.kvac.pwn.kvacpwnroot.minecraftpwn.minecraft.pwn.plugin.artefacts.Sluda;
import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandAddART implements CommandExecutor {

    Logger logger = LoggerFactory.getLogger(getClass());

    enum commands {
        GIVE, NULLED
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] cmdArgs) {
        StringBuilder errorBuilder = new StringBuilder();
        try {
            CommandSender playerSender = sender;

            if (cmdArgs.length == 0) {
                return false;
            }
            if (cmdArgs.length == 1) {//give ?
                Collection<? extends Player> players = sender.getServer().getOnlinePlayers();
                ArrayList<Player> pList = new ArrayList<>(players);
                ArrayList<String> names = new ArrayList<>();
                pList.forEach(player -> {
                    names.add(player.getName());
                });
                sender.sendMessage("change player from list:" + names.toString());
                return false;
            }
            if (cmdArgs.length == 2) {
                sender.sendMessage("2");
            }
            if (cmdArgs.length == 3) {
                sender.sendMessage("3");

            }

            commands cmd = null;
            try {
                cmd = commands.valueOf(cmdArgs[0]);
            } catch (Exception e) {
                playerSender.sendMessage("Доступные действия:");
                for (commands cmds : commands.values()) {
                    if (cmds.toString().equals(commands.NULLED.toString())) {
                        continue;
                    }
                    playerSender.sendMessage(cmds.toString());
                }
                cmd = commands.NULLED;
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
            } catch (NumberFormatException e) {
                e.getClass();
            }

            for (String cmdArg : cmdArgs) {
                playerSender.sendMessage(cmdArg);
            }
            playerSender.sendMessage("cmd:" + cmd + " кому:" + playerTo + " что:" + addWhat + " сколько:" + count);

            switch (addWhat) {
                case SLUDA:
                    playerTo.getInventory().addItem(new Sluda(count));
                    break;
                case OBJECT639:
                    playerTo.getInventory().addItem(new Object639(count));
                    break;
                case PILLOW:
                    playerTo.getInventory().addItem(new Pillow(count));
                    break;
                default:
                    break;
            }
            playerTo.sendMessage(playerSender.getName() + " выдал вам " + addWhat + " в кол-ве:" + count);
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        }
        sender.sendMessage(errorBuilder.toString());
        return true;
    }
}
