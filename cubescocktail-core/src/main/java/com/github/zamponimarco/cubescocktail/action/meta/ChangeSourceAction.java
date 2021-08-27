package com.github.zamponimarco.cubescocktail.action.meta;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.EntitySource;
import com.github.zamponimarco.cubescocktail.action.source.LocationSource;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.*;
import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lChange Source", description = "gui.action.meta.wrapper.source.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM2Mzc5NjFmODQ1MWE1M2I2N2QyNTMxMmQzNTBjNjIwZjMyYjVmNjA4YmQ2YWRlMDY2MzdiZTE3MTJmMzY0ZSJ9fX0")
public class ChangeSourceAction extends WrapperAction {
    private static final String ACTIONS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODIxNmVlNDA1OTNjMDk4MWVkMjhmNWJkNjc0ODc5NzgxYzQyNWNlMDg0MWI2ODc0ODFjNGY3MTE4YmI1YzNiMSJ9fX0=";

    @Serializable(headTexture = ACTIONS_HEAD, description = "gui.action.meta.wrapper.source.actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    private List<Action> actions;

    public ChangeSourceAction() {
        this(TARGET_DEFAULT, Lists.newArrayList());
    }

    public ChangeSourceAction(boolean target, List<Action> actions) {
        super(target);
        this.actions = actions;
    }

    public ChangeSourceAction(Map<String, Object> map) {
        super(map);
        this.actions = (List<Action>) map.getOrDefault("actions", Lists.newArrayList());
    }

    @Override
    public List<Action> getWrappedActions() {
        return actions;
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        ActionSource newSource = null;
        if (target instanceof LocationTarget) {
            newSource = new LocationSource(target.getLocation().clone(), source.getCaster(), source.getSourceItem());
        } else if (target instanceof EntityTarget) {
            newSource = new EntitySource(((EntityTarget) target).getTarget(), source.getSourceItem());
        } else if (target instanceof ItemTarget) {
            newSource = new EntitySource(((ItemTarget) target).getOwner(), source.getSourceItem());
        } else if (target instanceof ProjectileTarget) {
            newSource = new LocationSource(target.getLocation(), source.getCaster(), source.getSourceItem());
        }
        ActionSource finalNewSource = newSource;
        actions.forEach(action -> action.execute(target, finalNewSource, args));
        return ActionResult.SUCCESS;
    }

    @Override
    public String getName() {
        return "&6&lChange source";
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }

    @Override
    public Action clone() {
        return new ChangeSourceAction(TARGET_DEFAULT, actions.stream().map(Action::clone).collect(Collectors.toList()));
    }
}
