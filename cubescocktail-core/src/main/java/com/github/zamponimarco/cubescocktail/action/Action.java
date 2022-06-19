package com.github.zamponimarco.cubescocktail.action;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.entity.EntityAction;
import com.github.zamponimarco.cubescocktail.action.item.ItemAction;
import com.github.zamponimarco.cubescocktail.action.location.LocationAction;
import com.github.zamponimarco.cubescocktail.action.meta.MetaAction;
import com.github.zamponimarco.cubescocktail.action.projectile.ProjectileAction;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.source.EntitySource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.action.variable.VariableAction;
import com.github.zamponimarco.cubescocktail.function.Function;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;

@Enumerable.Parent(classArray = {EntityAction.class, LocationAction.class, MetaAction.class, VariableAction.class,
        ItemAction.class, ProjectileAction.class})
public abstract class Action implements Model, Cloneable {

    protected static final boolean TARGET_DEFAULT = true;

    private static final String TARGET_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0==";

    @Serializable(displayItem = "targetItem", description = "gui.action.target")
    @Serializable.Optional(defaultValue = "TARGET_DEFAULT")
    protected boolean target;

    public Action(boolean target) {
        this.target = target;
    }

    public Action(Map<String, Object> map) {
        this.target = (boolean) map.getOrDefault("target", TARGET_DEFAULT);
    }

    public abstract ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args);

    public abstract String getName();

    protected Location getLocation(ActionTarget target, ActionSource source) {
        return getLocation(target, source, false);
    }

    protected Location getLocation(ActionTarget target, ActionSource source, boolean eyes) {
        if (this.target) {
            if (eyes && target instanceof EntityTarget) {
                return ((EntityTarget) target).getTarget().getEyeLocation();
            }
            return target.getLocation();
        }
        if (eyes && source instanceof EntitySource) {
            return source.getCaster().getEyeLocation();
        }
        return source.getLocation();
    }

    public ItemStack targetItem() {
        return Libs.getVersionWrapper().skullFromValue(TARGET_HEAD);
    }

    public void changeSkillName(String oldName, String newName) {
    }

    public Set<Function> getUsedSavedSkills() {
        return Sets.newHashSet();
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getVersionWrapper().skullFromValue(getClass().getAnnotation(Enumerable.Displayable.class).
                headTexture()), MessageUtils.color(getName()), Libs.getLocale().getList("gui.action.description"));
    }

    @Override
    public abstract Action clone();

    public enum ActionResult {
        SUCCESS,
        FAILURE
    }
}
