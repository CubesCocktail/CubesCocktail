package com.github.zamponimarco.cubescocktail.action.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Map;

@Enumerable.Child
@Setter
@Enumerable.Displayable(name = "&c&lExplosion", description = "gui.action.location.explosion.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FmNTk3NzZmMmYwMzQxMmM3YjU5NDdhNjNhMGNmMjgzZDUxZmU2NWFjNmRmN2YyZjg4MmUwODM0NDU2NWU5In19fQ==")
public class ExplosionAction extends LocationAction {

    private static final NumericValue POWER_DEFAULT = new NumericValue(3);
    private static final boolean FIRE_DEFAULT = false;
    private static final boolean BLOCKS_DEFAULT = false;

    private static final String POWER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FmNTk3NzZmMmYwMzQxMmM3YjU5NDdhNjNhMGNmMjgzZDUxZmU2NWFjNmRmN2YyZjg4MmUwODM0NDU2NWU5In19fQ==";
    private static final String FIRE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDA4MGJiZWZjYTg3ZGMwZjM2NTM2YjY1MDg0MjVjZmM0Yjk1YmE2ZThmNWU2YTQ2ZmY5ZTljYjQ4OGE5ZWQifX19";
    private static final String BLOCKS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODNmYTBmYzA5OTZjZjc3MmQxZGJjMDUyYWEyNWIxMWRhYmFlOTc3ODIwYWY2NjNlZjAyMmQzY2UxZGI2MTEyMiJ9fX0=";

    @Serializable(headTexture = POWER_HEAD, description = "gui.action.location.explosion.power")
    @Serializable.Optional(defaultValue = "POWER_DEFAULT")
    private NumericValue power;
    @Serializable(headTexture = FIRE_HEAD, description = "gui.action.location.explosion.fire")
    @Serializable.Optional(defaultValue = "FIRE_DEFAULT")
    private boolean setFire;
    @Serializable(headTexture = BLOCKS_HEAD, description = "gui.action.location.explosion.break-blocks")
    @Serializable.Optional(defaultValue = "BLOCKS_DEFAULT")
    private boolean breakBlocks;

    public ExplosionAction() {
        this(TARGET_DEFAULT, POWER_DEFAULT.clone(), FIRE_DEFAULT, BLOCKS_DEFAULT);
    }

    public ExplosionAction(boolean target, NumericValue power, boolean setFire, boolean breakBlocks) {
        super(target);
        this.power = power;
        this.setFire = setFire;
        this.breakBlocks = breakBlocks;
    }

    public ExplosionAction(Map<String, Object> map) {
        super(map);
        this.power = (NumericValue) map.getOrDefault("power", POWER_DEFAULT.clone());
        this.setFire = (boolean) map.getOrDefault("setFire", FIRE_DEFAULT);
        this.breakBlocks = (boolean) map.getOrDefault("breakBlocks", BLOCKS_DEFAULT);
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        Location l = getLocation(target, source);
        if (l != null && l.getWorld() != null) {
            l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), power.getRealValue(target, source).floatValue(),
                    setFire, breakBlocks);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAILURE;
    }

    @Override
    public Action clone() {
        return new ExplosionAction(target, power.clone(), setFire, breakBlocks);
    }

    @Override
    public String getName() {
        return String.format("&6&lExplosion with power: %s", power.getName());
    }
}
