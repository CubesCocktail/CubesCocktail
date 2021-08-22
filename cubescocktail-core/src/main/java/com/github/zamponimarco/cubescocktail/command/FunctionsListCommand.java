package com.github.zamponimarco.cubescocktail.command;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.gui.SavedSkillCollectionInventoryHolder;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class FunctionsListCommand extends AbstractCommand {

    @SneakyThrows
    @Override
    protected void execute(String[] arguments, CommandSender sender) {
        Player p = (Player) sender;
        p.openInventory(new SavedSkillCollectionInventoryHolder(CubesCocktail.getInstance(), null,
                new ModelPath<>(CubesCocktail.getInstance().getFunctionManager(), null), CubesCocktail.getInstance().
                getFunctionManager().getClass().getDeclaredField("functions"), 1, o -> true).getInventory());
    }

    @Override
    protected boolean isOnlyPlayer() {
        return true;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("cubescocktail.skill");
    }
}
