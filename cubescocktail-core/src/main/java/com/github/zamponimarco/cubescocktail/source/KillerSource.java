package com.github.zamponimarco.cubescocktail.source;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.source.EntitySource;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "sourceEnabled", name = "&c&lKiller Source", description = "gui.source.damager.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBkZmM4YTM1NjNiZjk5NmY1YzFiNzRiMGIwMTViMmNjZWIyZDA0Zjk0YmJjZGFmYjIyOTlkOGE1OTc5ZmFjMSJ9fX0=")
public class KillerSource extends Source {

    public KillerSource() {
    }

    public KillerSource(Map<String, Object> map) {
        super(map);
    }

    @Override
    public ActionSource getSource(Map<String, Object> args) {
        LivingEntity killer = (LivingEntity) args.get("killer");
        return new EntitySource(killer, null);
    }

    public static boolean sourceEnabled(ModelPath path) {
        return getPossibleSources(path).contains(KillerSource.class);
    }

    @Override
    public Source clone() {
        return new KillerSource();
    }

    @Override
    public String getName() {
        return "Killer Source";
    }
}
