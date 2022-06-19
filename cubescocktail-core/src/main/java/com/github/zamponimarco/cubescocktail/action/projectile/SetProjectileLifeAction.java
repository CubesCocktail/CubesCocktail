package com.github.zamponimarco.cubescocktail.action.projectile;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ProjectileTarget;
import com.github.zamponimarco.cubescocktail.projectile.AbstractProjectile;
import com.github.zamponimarco.cubescocktail.value.NumericValue;

import java.util.Map;

@Enumerable.Child()
@Enumerable.Displayable(name = "&c&lSet Projectile Life", description = "gui.action.projectile.life.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZmZGQ0YjEzZDU0ZjZjOTFkZDVmYTc2NWVjOTNkZDk0NThiMTlmOGFhMzRlZWI1YzgwZjQ1NWIxMTlmMjc4In19fQ")
public class SetProjectileLifeAction extends ProjectileAction {

    private static final String LIFE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZmZGQ0YjEzZDU0ZjZjOTFkZDVmYTc2NWVjOTNkZDk0NThiMTlmOGFhMzRlZWI1YzgwZjQ1NWIxMTlmMjc4In19fQ";

    @Serializable(headTexture = LIFE_HEAD, description = "gui.action.projectile.life.life")
    private NumericValue life;

    public SetProjectileLifeAction() {
        this(TARGET_DEFAULT, new NumericValue());
    }

    public SetProjectileLifeAction(boolean target, NumericValue life) {
        super(target);
        this.life = life;
    }

    public SetProjectileLifeAction(Map<String, Object> map) {
        super(map);
        this.life = (NumericValue) map.getOrDefault("life", new NumericValue());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        if (!(target instanceof ProjectileTarget)) {
            return ActionResult.FAILURE;
        }

        AbstractProjectile projectile = ((ProjectileTarget) target).getProjectile();
        projectile.setProjectileLife(life.getRealValue(target, source).intValue());
        return ActionResult.SUCCESS;
    }

    @Override
    public String getName() {
        return "&6&lSet Projectile Life: &c" + life.getName();
    }

    @Override
    public Action clone() {
        return new SetProjectileLifeAction(target, life.clone());
    }
}
