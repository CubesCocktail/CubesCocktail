package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;

import java.util.Map;

@Enumerable.Parent(classArray = {LeftClickTrigger.class, RightClickTrigger.class,
        BlockBreakTrigger.class, BlockPlaceTrigger.class, EntityFishTrigger.class, EntityItemConsumeTrigger.class,
        EntityInteractedTrigger.class})
@Enumerable.Displayable(name = "&c&lInteraction Triggers", description = "gui.trigger.interact.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYThlZDg2M2QxNDA1ZGQ4YWM0OGU4ZTU3MTlhYWRmYWRiYTM5Y2RjNjllZTY3MzM2NTU4ZmE4MTYwZTQ3NTk0OCJ9fX0=")
public abstract class InteractionTrigger extends Trigger {

    public InteractionTrigger() {
    }

    public InteractionTrigger(Map<String, Object> map) {
        super(map);
    }
}
