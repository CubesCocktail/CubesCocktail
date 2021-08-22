package com.github.zamponimarco.cubescocktail.action.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lBlock Entity Event", description = "gui.action.entity.block-event.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
public class BlockEventAction extends EntityAction {

    private static final String BLOCKED_EVENT_DEFAULT = "toolbar-slot-change";
    private static final NumericValue TICKS_DEFAULT = new NumericValue(100);

    private static final String BLOCK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ";
    private static final String TICKS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlOGNmZjc1ZjdkNDMzMjYwYWYxZWNiMmY3NzNiNGJjMzgxZDk1MWRlNGUyZWI2NjE0MjM3NzlhNTkwZTcyYiJ9fX0=";

    private static final List<String> EVENTS_LIST = Lists.newArrayList("toolbar-slot-change");

    /**
     * The string that represents the event to block
     */
    @Serializable(headTexture = BLOCK_HEAD, fromList = "getEventsList", description = "gui.action.entity.block-event.blocked-event")
    private String blockedEvent;

    /**
     * The value that represents the number of ticks in which the
     * event for the target entity will be blocked.
     */
    @Serializable(headTexture = TICKS_HEAD, description = "gui.action.entity.block-event.ticks", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, scale = 1)
    private NumericValue ticks;

    public BlockEventAction(boolean target, String blockedEvent, NumericValue ticks) {
        super(target);
        this.blockedEvent = blockedEvent;
        this.ticks = ticks;
    }

    public BlockEventAction(Map<String, Object> map) {
        super(map);
        this.blockedEvent = (String) map.getOrDefault("blockedEvent", BLOCKED_EVENT_DEFAULT);
        this.ticks = (NumericValue) map.getOrDefault("ticks", TICKS_DEFAULT.clone());
    }

    public BlockEventAction() {
        this(TARGET_DEFAULT, BLOCKED_EVENT_DEFAULT, TICKS_DEFAULT.clone());
    }

    public static List<String> getEventsList(ModelPath path) {
        return EVENTS_LIST;
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, Map<String, Object> map) {
        LivingEntity e = getEntity(target, source);

        if (e == null) {
            return ActionResult.FAILURE;
        }

        e.setMetadata(blockedEvent, new FixedMetadataValue(CubesCocktail.getInstance(), true));

        Bukkit.getScheduler().runTaskLater(CubesCocktail.getInstance(), () ->
                        e.removeMetadata(blockedEvent, CubesCocktail.getInstance()),
                ticks.getRealValue(target, source).intValue());
        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new BlockEventAction(target, blockedEvent, ticks.clone());
    }

    @Override
    public String getName() {
        return String.format("&6&lBlock Event: &c%s", blockedEvent);
    }
}
