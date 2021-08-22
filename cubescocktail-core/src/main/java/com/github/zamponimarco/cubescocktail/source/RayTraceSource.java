package com.github.zamponimarco.cubescocktail.source;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.source.LocationSource;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "sourceEnabled", name = "&c&lRay Cast Source", description = "gui.source.raycast.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0=")
public class RayTraceSource extends Source {

    private static final String DISTANCE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0=";

    @Serializable(headTexture = DISTANCE_HEAD, description = "gui.source.raycast.distance")
    private double rayCastMaxDistance;

    public RayTraceSource() {
        this.rayCastMaxDistance = 30;
    }

    public RayTraceSource(double rayCastMaxDistance) {
        this.rayCastMaxDistance = rayCastMaxDistance;
    }

    public RayTraceSource(Map<String, Object> map) {
        super(map);
        this.rayCastMaxDistance = (double) map.getOrDefault("rayCastMaxDistance", 30);
    }

    @Override
    public ActionSource getSource(Map<String, Object> args) {
        LivingEntity e = (LivingEntity) args.get("caster");
        RayTraceResult result = e.rayTraceBlocks(rayCastMaxDistance);
        if (result != null) {
            Location l = result.getHitPosition().toLocation(e.getWorld());
            return new LocationSource(l, e, null);
        }
        return new LocationSource(e.getEyeLocation(), e, null);
    }

    public static boolean sourceEnabled(ModelPath path) {
        return getPossibleSources(path).contains(RayTraceSource.class);
    }

    @Override
    public Source clone() {
        return new RayTraceSource(rayCastMaxDistance);
    }

    @Override
    public String getName() {
        return "Ray Trace Source";
    }
}
