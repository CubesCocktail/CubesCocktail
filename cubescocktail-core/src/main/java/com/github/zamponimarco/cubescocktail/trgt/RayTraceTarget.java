package com.github.zamponimarco.cubescocktail.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgumentKey;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.LocationTarget;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(condition = "targetEnabled", name = "&c&lRay Cast Target", description = "gui.target.raycast.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0=")
public class RayTraceTarget extends Target {

    private static final String DISTANCE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0=";

    @Serializable(headTexture = DISTANCE_HEAD, description = "gui.target.raycast.distance")
    private double rayCastMaxDistance;

    public RayTraceTarget() {
        this.rayCastMaxDistance = 30;
    }

    public RayTraceTarget(double rayCastMaxDistance) {
        this.rayCastMaxDistance = rayCastMaxDistance;
    }

    public RayTraceTarget(Map<String, Object> map) {
        super(map);
        this.rayCastMaxDistance = (double) map.getOrDefault("rayCastMaxDistance", 30);
    }

    public static boolean targetEnabled(ModelPath<?> path) {
        return getPossibleTargets(path).contains(RayTraceTarget.class);
    }

    @Override
    public ActionTarget getTarget(ActionArgument args) {
        LivingEntity e = args.getArgument(ActionArgumentKey.CASTER);
        RayTraceResult result = e.rayTraceBlocks(rayCastMaxDistance);
        if (result != null) {
            Location l = result.getHitPosition().toLocation(e.getWorld());
            return new LocationTarget(l);
        }
        return new LocationTarget(e.getEyeLocation());
    }

    @Override
    public Target clone() {
        return new RayTraceTarget(rayCastMaxDistance);
    }

    @Override
    public String getName() {
        return "Ray Cast Target";
    }
}
