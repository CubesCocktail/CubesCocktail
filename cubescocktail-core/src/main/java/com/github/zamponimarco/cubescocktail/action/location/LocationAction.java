package com.github.zamponimarco.cubescocktail.action.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.Action;

import java.util.Map;

@Enumerable.Parent(classArray = {PacketAction.class, ParticleAction.class, SoundAction.class, SetBlockAction.class,
        TeleportAction.class, FakeBlockAction.class, ExplosionAction.class, SpawnEntityAction.class,
        SetBlockCrackAction.class, HighlightBlockAction.class, LightningAction.class})
@Enumerable.Displayable(name = "&9&lAction &6» &cLocation Targetable", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ5YjE0NjU2MTljNDJjN2NiMDZjZTFkMmNlNmViODRhZGQ2ZmM5YjIxYTE2ZGRhMjNmYWQyNDgwZTExZmYyIn19fQ==")
public abstract class LocationAction extends Action {

    public LocationAction(boolean target) {
        super(target);
    }

    public LocationAction(Map<String, Object> map) {
        super(map);
    }
}
