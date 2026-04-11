package com.github.zamponimarco.cubescocktail.action.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Map;

@Enumerable.Displayable(name = "&c&lDamage", description = "gui.action.entity.damage.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVlMTE4ZWRkYWVlMGRmYjJjYmMyYzNkNTljMTNhNDFhN2Q2OGNjZTk0NWU0MjE2N2FhMWRjYjhkMDY3MDUxNyJ9fX0=")
@Enumerable.Child
@Getter
@Setter
public class DamageAction extends EntityAction {

    private static final boolean ABSOLUTE_DEFAULT = false;
    private static final NumericValue AMOUNT_DEFAULT = new NumericValue(1);


    @Serializable(headTexture = Textures.AMOUNT_HEAD, description = "gui.action.entity.damage.amount", additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0)
    @Serializable.Optional(defaultValue = "AMOUNT_DEFAULT")
    private NumericValue amount;
    @Serializable(headTexture = Textures.AMOUNT_HEAD, description = "gui.action.entity.damage.absolute")
    @Serializable.Optional(defaultValue = "ABSOLUTE_DEFAULT")
    private boolean absolute;

    public DamageAction() {
        this(TARGET_DEFAULT, AMOUNT_DEFAULT.clone(), ABSOLUTE_DEFAULT);
    }

    public DamageAction(boolean target, NumericValue amount, boolean absolute) {
        super(target);
        this.amount = amount;
        this.absolute = absolute;
    }

    public DamageAction(Map<String, Object> map) {
        super(map);
        this.absolute = (boolean) map.getOrDefault("absolute", ABSOLUTE_DEFAULT);
        this.amount = (NumericValue) map.getOrDefault("amount", AMOUNT_DEFAULT.clone());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        LivingEntity e = getEntity(target, source);

        if (e == null) {
            return ActionResult.FAILURE;
        }

        e.setMetadata("siattack", new FixedMetadataValue(CubesCocktail.getInstance(), true));
        if (!absolute) {
            e.damage(amount.getRealValue(target, source), source.getCaster());
        } else {
            e.setHealth(Math.max(e.getHealth() - amount.getRealValue(target, source), 0));
            e.damage(0, source.getCaster());
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new DamageAction(target, amount.clone(), absolute);
    }

    @Override
    public String getName() {
        return "&6&lDamage: &c" + amount.getName();
    }

}
