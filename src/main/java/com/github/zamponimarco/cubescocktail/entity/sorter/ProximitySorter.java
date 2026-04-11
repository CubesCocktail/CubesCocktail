package com.github.zamponimarco.cubescocktail.entity.sorter;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&6&lProximity Sorter", description = "gui.entity.sorter.proximity.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE1Mzc2MjIxNGFkOTljMmM1OWYzMGI0YTJhYTI5YTU4NTE2NjNhZDdkZmE4NTZlOGRhNzI1MjFhYWJhNjc4In19fQ==")
@Getter
@Setter
public class ProximitySorter extends EntitySorter {

    protected static final boolean TARGET_DEFAULT = true;
    protected static final boolean INVERSE_DEFAULT = false;


    @Serializable(headTexture = Textures.TARGET_HEAD, description = "gui.entity.sorter.proximity.target")
    @Serializable.Optional(defaultValue = "TARGET_DEFAULT")
    private boolean target;
    @Serializable(headTexture = Textures.TARGET_HEAD, description = "gui.entity.sorter.proximity.inverse")
    @Serializable.Optional(defaultValue = "INVERSE_DEFAULT")
    private boolean inverse;

    public ProximitySorter() {
        this(TARGET_DEFAULT, INVERSE_DEFAULT);
    }

    public ProximitySorter(Map<String, Object> map) {
        super(map);
        this.target = (boolean) map.getOrDefault("target", TARGET_DEFAULT);
        this.inverse = (boolean) map.getOrDefault("inverse", INVERSE_DEFAULT);
    }

    public ProximitySorter(boolean target, boolean inverse) {
        this.target = target;
        this.inverse = inverse;
    }

    @Override
    public void sortCollection(List<LivingEntity> list, ActionTarget target, ActionSource source) {
        list.sort(getComparator(target, source));
    }

    public Comparator<Entity> getComparator(ActionTarget target, ActionSource source) {
        return (e1, e2) -> {
            Location l = getLocation(target, source);
            int d = Double.compare(e1.getLocation().distance(l), e2.getLocation().distance(l));
            return this.inverse ? d * -1 : d;
        };
    }

    private Location getLocation(ActionTarget target, ActionSource source) {
        if (this.target) {
            return target.getLocation();
        }
        return source.getLocation();
    }

    @Override
    public EntitySorter clone() {
        return new ProximitySorter(target, inverse);
    }
}
