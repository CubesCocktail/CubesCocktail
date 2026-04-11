package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;

import java.util.Map;

@Enumerable.Parent(classArray = {EntitySprintTrigger.class, EntitySneakTrigger.class, EntityJumpTrigger.class})
@Enumerable.Displayable(name = "&c&lMovement Triggers", description = "gui.trigger.movement.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3YmMyYzFmNzdiM2Y5NTZjNTViZjU0NjY1YjdkYTVkOTYxMTFjY2M3NmY3NTdiN2I5ZmNkNTNlMjgxZjQifX19")
public abstract class MovementTrigger extends Trigger {

    public MovementTrigger() {
    }

    public MovementTrigger(Map<String, Object> map) {
        super(map);
    }
}
