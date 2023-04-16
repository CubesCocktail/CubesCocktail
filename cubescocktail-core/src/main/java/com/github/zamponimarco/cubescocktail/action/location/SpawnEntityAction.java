package com.github.zamponimarco.cubescocktail.action.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.entity.Entity;
import com.github.zamponimarco.cubescocktail.entity.GenericEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lSpawn entity", description = "gui.action.location.spawn.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==")
@Getter
@Setter
public class SpawnEntityAction extends LocationAction {
    private static final String ENTITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNjOGFhM2ZkZTI5NWZhOWY5YzI3ZjczNGJkYmFiMTFkMzNhMmU0M2U4NTVhY2NkNzQ2NTM1MjM3NzQxM2IifX19";

    @Serializable(headTexture = ENTITY_HEAD, description = "gui.action.location.spawn.entity", additionalDescription = {"gui.additional-tooltips.recreate"})
    private Entity entity;

    public SpawnEntityAction() {
        this(TARGET_DEFAULT, new GenericEntity());
    }

    public SpawnEntityAction(boolean target, Entity entity) {
        super(target);
        this.entity = entity;
    }

    public SpawnEntityAction(Map<String, Object> map) {
        super(map);
        this.entity = (Entity) map.getOrDefault("entity", new GenericEntity());
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        entity.spawnEntity(getLocation(target, source), target, source);
        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new SpawnEntityAction(target, entity.clone());
    }

    @Override
    public String getName() {
        return "&6&lSpawn: &c" + MessageUtils.capitalize(entity.getType().name());
    }
}
