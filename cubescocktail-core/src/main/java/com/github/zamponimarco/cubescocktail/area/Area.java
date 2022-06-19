package com.github.zamponimarco.cubescocktail.area;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.value.VectorValue;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Enumerable.Parent(classArray = {SphericArea.class, CylindricArea.class, IntersectionArea.class, UnionArea.class,
        DifferenceArea.class, CubicArea.class})
public abstract class Area implements Model, Cloneable {

    protected static final VectorValue DEFAULT_TRANSLATION = new VectorValue();

    protected static final String SHAPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2IyYjVkNDhlNTc1Nzc1NjNhY2EzMTczNTUxOWNiNjIyMjE5YmMwNThiMWYzNDY0OGI2N2I4ZTcxYmMwZmEifX19";

    @Serializable(displayItem = "getTranslationItem", description = "gui.area.center",
            additionalDescription = {"gui.additional-tooltips.value"})
    protected VectorValue centerTranslation;

    public Area(VectorValue centerTranslation) {
        this.centerTranslation = centerTranslation;
    }

    public Area(Map<String, Object> map) {
        this.centerTranslation = (VectorValue) map.getOrDefault("centerTranslation", DEFAULT_TRANSLATION.clone());
    }

    protected Location finalLocation(Location center, ActionTarget target, ActionSource source) {
        return center.clone().add(centerTranslation.getRealValue(target, source).computeVector(target, source));
    }

    public abstract List<Location> getBlocks(Location center, ActionTarget target, ActionSource source);

    public abstract Collection<LivingEntity> entitiesInside(Location center, ActionTarget target, ActionSource source);

    public abstract String getName();

    public abstract Area clone();

    protected static double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    protected static double lengthSq(double x, double z) {
        return (x * x) + (z * z);
    }

    public ItemStack getTranslationItem() {
        return Libs.getVersionWrapper().
                skullFromValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmYzUyMjY0ZDhhZDllNjU0ZjQxNWJlZjAxYTIzOTQ3ZWRiY2NjY2Y2NDkzNzMyODliZWE0ZDE0OTU0MWY3MCJ9fX0=");
    }

    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getVersionWrapper().skullFromValue(getClass().getAnnotation(Enumerable.Displayable.class).
                headTexture()), MessageUtils.color(getName()), Libs.getLocale().getList("gui.area.description"));
    }
}
