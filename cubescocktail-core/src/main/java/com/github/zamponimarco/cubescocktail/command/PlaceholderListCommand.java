package com.github.zamponimarco.cubescocktail.command;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.gui.model.ModelCollectionInventoryHolder;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class PlaceholderListCommand extends AbstractCommand {

    @SneakyThrows
    @Override
    protected void execute(String[] arguments, CommandSender sender) {
        Player p = (Player) sender;
        p.openInventory(new ModelCollectionInventoryHolder<>(CubesCocktail.getInstance(),
                CubesCocktail.getInstance().getSavedPlaceholderManager(), "placeholders").getInventory());
    }

    @Override
    protected boolean isOnlyPlayer() {
        return true;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("cubescocktail.placeholder");
    }
}
