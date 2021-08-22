package com.github.zamponimarco.cubescocktail.action.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lTeleport", description = "gui.action.location.teleport.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWNiN2MyMWNjNDNkYzE3Njc4ZWU2ZjE2NTkxZmZhYWIxZjYzN2MzN2Y0ZjZiYmQ4Y2VhNDk3NDUxZDc2ZGI2ZCJ9fX0=")
public class TeleportAction extends LocationAction {

    public TeleportAction(boolean target) {
        super(target);
    }

    public TeleportAction() {
        this(TARGET_DEFAULT);
    }

    public TeleportAction(Map<String, Object> map) {
        super(map);
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, Map<String, Object> map) {
        LivingEntity entity = getEntity(target, source);

        if (entity == null) {
            return ActionResult.FAILURE;
        }

        Location location = getLocation(target, source).clone();

        entity.teleport(location);

        return ActionResult.SUCCESS;
    }

    public LivingEntity getEntity(ActionTarget target, ActionSource source) {
        if (this.target) {
            return source.getCaster();
        } else if (target instanceof EntityTarget) {
            return ((EntityTarget) target).getTarget();
        }
        return null;
    }

    @Override
    public Action clone() {
        return new TeleportAction(target);
    }

    @Override
    public String getName() {
        return "&6&lTeleport";
    }
}
