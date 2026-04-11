package com.github.zamponimarco.cubescocktail.action.meta;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lCancel the Event", description = "gui.action.meta.cancel-event.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZkMjQwMDAwMmFkOWZiYmJkMDA2Njk0MWViNWIxYTM4NGFiOWIwZTQ4YTE3OGVlOTZlNGQxMjlhNTIwODY1NCJ9fX0=")
public class CancelEventAction extends MetaAction {

    public CancelEventAction() {
        this(TARGET_DEFAULT);
    }

    public CancelEventAction(boolean target) {
        super(target);
    }

    public CancelEventAction(Map<String, Object> map) {
        super(map);
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        args.setArgument(ActionArgumentKey.CANCELLED, true);
        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new CancelEventAction();
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }

    @Override
    public String getName() {
        return "&6&lCancel Event";
    }
}
