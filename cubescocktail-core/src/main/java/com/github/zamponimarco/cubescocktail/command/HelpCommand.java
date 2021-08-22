package com.github.zamponimarco.cubescocktail.command;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.util.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class HelpCommand extends AbstractCommand {
    @Override
    protected void execute(String[] strings, CommandSender sender) {
        sender.sendMessage(MessageUtils.color("        &c&lCubes&6&lCocktail &cHelp\n" +
                "&2/cc help &7Show help message.\n" +
                "&2/cc functions &7Open the skills GUI.\n" +
                "&2/cc placeholders &7Open the placeholder GUI\n" +
                "&2For further help: &7https://discord.gg/TzREkc9"));
    }

    @Override
    protected boolean isOnlyPlayer() {
        return false;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("cubescocktail.functions");
    }
}
