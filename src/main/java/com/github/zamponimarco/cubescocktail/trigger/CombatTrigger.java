package com.github.zamponimarco.cubescocktail.trigger;

import com.github.jummes.libs.annotation.Enumerable;

import java.util.Map;

@Enumerable.Displayable(name = "&c&lCombat Skills", description = "gui.trigger.combat.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDliNDdhZTk5M2FkM2RlZWExZTFiZmM0Yzk2ZjVlYzgwZDJjMDYxNjNlYzczMjhmMzAxMGYxMzgzMDlmNGIifX19")
@Enumerable.Parent(classArray = {DamageEntityTrigger.class, HitEntityTrigger.class, EntityShootProjectileTrigger.class,
        EntityCrossbowLoadTrigger.class, EntityEquipArmorTrigger.class, EntityDeathTrigger.class})
public abstract class CombatTrigger extends Trigger {

    public CombatTrigger() {
    }

    public CombatTrigger(Map<String, Object> map) {
        super(map);
    }
}
