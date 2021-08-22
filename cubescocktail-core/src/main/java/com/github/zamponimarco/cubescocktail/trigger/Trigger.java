package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.cooldown.CooldownOptions;
import com.github.zamponimarco.cubescocktail.slot.Slot;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Enumerable.Parent(classArray = {CombatTrigger.class, InteractionTrigger.class, MovementTrigger.class})
public abstract class Trigger implements Model, Cloneable {

    @Getter
    private static final List<TriggerListener> listeners = Lists.newArrayList();

    private static final String BLOCK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU0ZDljNDg4YzNmYmRlNTQ1NGUzODYxOWY5Y2M1YjViYThjNmMwMTg2ZjhhYTFkYTYwOTAwZmNiYzNlYTYifX19";

    @Deprecated
    protected List<ActionGroup> groups;

    @Deprecated
    protected CooldownOptions cooldownOptions;

    @Deprecated
    protected List<Slot> allowedSlots;

    @Deprecated
    protected boolean consumable;

    @Deprecated
    protected int cooldown;

    public Trigger() {
    }

    public Trigger(Map<String, Object> map) {
        generalLegacyTransition(map);
    }

    @Deprecated
    private void generalLegacyTransition(Map<String, Object> map) {
        try {
            this.allowedSlots = (List<Slot>) map.getOrDefault("allowedSlots", Lists.newArrayList());
            this.consumable = (boolean) map.getOrDefault("consumable", false);
            this.cooldownOptions = (CooldownOptions) map.getOrDefault("cooldownOptions", new CooldownOptions());
            this.groups = (List<ActionGroup>) map.getOrDefault("groups", Lists.newArrayList());
            this.cooldown = (int) map.getOrDefault("cooldown", 0);
        } catch (Exception ignored) {
        }
    }

    public void registerListener(TriggerListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(TriggerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public ItemStack getGUIItem() {
        List<Component> lore = getCustomLore();
        lore.addAll(Libs.getLocale().getList("gui.additional-tooltips.delete"));
        return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue(getClass().getAnnotation(Enumerable.Displayable.class).headTexture()),
                MessageUtils.color(getName()), lore);
    }

    public List<Component> getCustomLore() {
        return new ArrayList<>();
    }

    public abstract String getName();

    public abstract Collection<Class<? extends Target>> getPossibleTargets();

    public abstract Collection<Class<? extends Source>> getPossibleSources();

    @Override
    public abstract Trigger clone();

}
