package com.github.zamponimarco.cubescocktail.command;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class AddonsListCommand extends AbstractCommand {

    @Override
    protected void execute(String[] strings, CommandSender commandSender) {
        commandSender.sendMessage(MessageUtils.color("        &c&lCubes&6&lCocktail &cloaded addons:"));
        CubesCocktail.getInstance().getAddonManager().getLoadedAddons().forEach(addon -> {
            commandSender.sendMessage(MessageUtils.color(String.format("&6&l- &c%s v%s", addon.getDescription().getName(),
                    addon.getDescription().getVersion())));
        });
    }

    @Override
    protected boolean isOnlyPlayer() {
        return false;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("cubescocktail.adons");
    }
}
