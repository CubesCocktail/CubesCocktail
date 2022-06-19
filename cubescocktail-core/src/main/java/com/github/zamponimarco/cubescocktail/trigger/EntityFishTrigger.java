package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.LocationSource;
import com.github.zamponimarco.cubescocktail.source.RayTraceSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.CasterTarget;
import com.github.zamponimarco.cubescocktail.trgt.LocationTarget;
import com.github.zamponimarco.cubescocktail.trgt.RayTraceTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Enumerable.Child
@Enumerable.Displayable(condition = "isCasterOnlyPlayer", name = "&c&lOn Entity Fish", description = "gui.trigger.interact.fish.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGFlYjIxYTI1ZTQ2ODA2Y2U4NTM3ZmJkNjY2ODI4MWNmMTc2Y2VhZmU5NWFmOTBlOTRhNWZkODQ5MjQ4NzgifX19")
public class EntityFishTrigger extends InteractionTrigger {

    private final static PlayerFishEvent.State STATE_DEFAULT = PlayerFishEvent.State.FISHING;
    private static final String STATE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGFlYjIxYTI1ZTQ2ODA2Y2U4NTM3ZmJkNjY2ODI4MWNmMTc2Y2VhZmU5NWFmOTBlOTRhNWZkODQ5MjQ4NzgifX19";

    @Serializable(headTexture = STATE_HEAD, description = "gui.trigger.interact.fish.state", stringValue = true)
    private PlayerFishEvent.State state;

    public EntityFishTrigger() {
        this(STATE_DEFAULT);
    }

    public EntityFishTrigger(PlayerFishEvent.State state) {
        this.state = state;
    }

    public EntityFishTrigger(Map<String, Object> map) {
        super(map);
        this.state = PlayerFishEvent.State.valueOf((String) map.getOrDefault("state", STATE_DEFAULT.name()));

        legacyTransition(map);
    }

    @Deprecated
    private void legacyTransition(Map<String, Object> map) {
        List<Action> onEntityActions = (List<Action>) map.get("onEntityActions");
        if (onEntityActions != null && !onEntityActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new CasterTarget(), onEntityActions));
        }

        List<Action> onHookActions = (List<Action>) map.get("onHookActions");
        if (onHookActions != null && !onHookActions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new LocationTarget(), onHookActions));
        }
    }

    @Override
    public String getName() {
        return String.format("&cEntity Fish &6&ltrigger: &c%s", state.name());
    }

    @Override
    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(CasterTarget.class, RayTraceTarget.class, LocationTarget.class);
    }

    @Override
    public Collection<Class<? extends Source>> getPossibleSources() {
        return Lists.newArrayList(CasterSource.class, RayTraceSource.class, LocationSource.class);
    }

    @Override
    public Trigger clone() {
        return new EntityFishTrigger(state);
    }
}
