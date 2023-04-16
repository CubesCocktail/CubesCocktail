package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.RayTraceSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.CasterTarget;
import com.github.zamponimarco.cubescocktail.trgt.ItemTarget;
import com.github.zamponimarco.cubescocktail.trgt.RayTraceTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "isCasterOnlyPlayer", name = "&c&lOn entity change armor", description = "gui.trigger.combat.armor.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNjZTgwMjQ2NzRiYTk0OGYzMWM0NjNjYmY5NzFiNDk0NmE1NGUzMjMxNDVmYWY0NTQ3YTYyNDJkY2Y2YTFjOCJ9fX0=")
@Getter
@Setter
public class EntityEquipArmorTrigger extends CombatTrigger {

    private static final String EQUIP_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNjZTgwMjQ2NzRiYTk0OGYzMWM0NjNjYmY5NzFiNDk0NmE1NGUzMjMxNDVmYWY0NTQ3YTYyNDJkY2Y2YTFjOCJ9fX0=";

    @Serializable(headTexture = EQUIP_HEAD, description = "gui.trigger.combat.armor.equip")
    private boolean onEquip;

    public EntityEquipArmorTrigger() {
        this(true);
    }

    public EntityEquipArmorTrigger(boolean onEquip) {
        this.onEquip = onEquip;
    }

    public EntityEquipArmorTrigger(Map<String, Object> map) {
        super(map);
        this.onEquip = (boolean) map.getOrDefault("onEquip", true);
    }

    @Override
    public String getName() {
        return "&cEquip item &6&ltrigger";
    }

    @Override
    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(CasterTarget.class, RayTraceTarget.class, ItemTarget.class);
    }

    @Override
    public Collection<Class<? extends Source>> getPossibleSources() {
        return Lists.newArrayList(CasterSource.class, RayTraceSource.class);
    }

    @Override
    public Trigger clone() {
        return new EntityEquipArmorTrigger(onEquip);
    }
}
