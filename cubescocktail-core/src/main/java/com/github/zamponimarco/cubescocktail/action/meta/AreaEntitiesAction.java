package com.github.zamponimarco.cubescocktail.action.meta;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.LocationSource;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.annotation.PossibleSources;
import com.github.zamponimarco.cubescocktail.annotation.PossibleTargets;
import com.github.zamponimarco.cubescocktail.area.Area;
import com.github.zamponimarco.cubescocktail.area.SphericArea;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.condition.bool.BooleanCondition;
import com.github.zamponimarco.cubescocktail.entity.sorter.EntitySorter;
import com.github.zamponimarco.cubescocktail.entity.sorter.ProximitySorter;
import com.github.zamponimarco.cubescocktail.placeholder.bool.IsSourcePlaceholder;
import com.github.zamponimarco.cubescocktail.source.SelectedEntitySource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.SelectedEntityTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@PossibleTargets("getPossibleTargets")
@PossibleSources("getPossibleSources")
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lApply actions to entities in Area", description = "gui.action.meta.wrapper.area-entities.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==")
public class AreaEntitiesAction extends WrapperAction {

    private static final boolean CAST_LOCATION_DEFAULT = true;
    private static final NumericValue COUNT_DEFAULT = new NumericValue(0);
    private static final EntitySorter SORTER_DEFAULT = new ProximitySorter();
    private static final Condition CONDITION_DEFAULT = new BooleanCondition(true, new IsSourcePlaceholder());

    private static final String ACTIONS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODIxNmVlNDA1OTNjMDk4MWVkMjhmNWJkNjc0ODc5NzgxYzQyNWNlMDg0MWI2ODc0ODFjNGY3MTE4YmI1YzNiMSJ9fX0=";
    private static final String SHAPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2IyYjVkNDhlNTc1Nzc1NjNhY2EzMTczNTUxOWNiNjIyMjE5YmMwNThiMWYzNDY0OGI2N2I4ZTcxYmMwZmEifX19";
    private static final String CONDITION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMyNzEwNTI3MTllZjY0MDc5ZWU4YzE0OTg5NTEyMzhhNzRkYWM0YzI3Yjk1NjQwZGI2ZmJkZGMyZDZiNWI2ZSJ9fX0=";
    private static final String CAST_LOCATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQzYjJlM2U5OTU0ZjgyMmI0M2ZlNWY5MTUwOTllMGE2Y2FhYTgxZjc5MTIyMmI1ODAzZDQxNDVhODUxNzAifX19";
    private static final String SORTER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE2ZDUxYzIyYzg5NTgyODVjMDBhYWFmOTNiNjIxYzE1YmUxMGFhMDQ4MzhhZmUxZDg5Y2Q5YzM2MDMxNDRkZiJ9fX0=";
    private static final String COUNT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";

    @Serializable(headTexture = ACTIONS_HEAD, description = "gui.action.meta.wrapper.area-entities.actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    private List<Action> actions;

    @Serializable(headTexture = SHAPE_HEAD, description = "gui.action.meta.wrapper.area-blocks.area", additionalDescription = {"gui.additional-tooltips.recreate"})
    private Area area;

    @Serializable(headTexture = CONDITION_HEAD, description = "gui.action.meta.wrapper.area-entities.condition")
    private Condition condition;

    @Serializable(headTexture = COUNT_HEAD, description = "gui.action.meta.wrapper.area-entities.count")
    @Serializable.Number(minValue = 0, scale = 1)
    private NumericValue count;

    @Serializable(headTexture = SORTER_HEAD, description = "gui.action.meta.wrapper.area-entities.sorter",
            additionalDescription = {"gui.additional-tooltips.recreate"})
    private EntitySorter sorter;

    @Serializable(headTexture = CAST_LOCATION_HEAD, description = "gui.action.meta.wrapper.area-entities.cast-from-location")
    @Serializable.Optional(defaultValue = "CAST_LOCATION_DEFAULT")
    private boolean castFromLocation;

    public AreaEntitiesAction() {
        this(TARGET_DEFAULT, Lists.newArrayList(), new SphericArea(), CONDITION_DEFAULT.clone(),
                CAST_LOCATION_DEFAULT, SORTER_DEFAULT.clone(), COUNT_DEFAULT.clone());
    }

    public AreaEntitiesAction(boolean target, List<Action> actions, Area area,
                              Condition condition, boolean castFromLocation, EntitySorter sorter, NumericValue count) {
        super(target);
        this.actions = actions;
        this.area = area;
        this.condition = condition;
        this.castFromLocation = castFromLocation;
        this.sorter = sorter;
        this.count = count;
    }

    public AreaEntitiesAction(Map<String, Object> map) {
        super(map);
        this.actions = (List<Action>) map.getOrDefault("actions", Lists.newArrayList());
        this.actions.removeIf(Objects::isNull);

        this.castFromLocation = (boolean) map.getOrDefault("castFromLocation", CAST_LOCATION_DEFAULT);
        this.sorter = (EntitySorter) map.getOrDefault("sorter", SORTER_DEFAULT.clone());
        this.count = (NumericValue) map.getOrDefault("count", COUNT_DEFAULT.clone());

        this.area = (Area) map.getOrDefault("area", new SphericArea());
        this.condition = (Condition) map.getOrDefault("condition", CONDITION_DEFAULT.clone());
    }

    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Sets.newHashSet(SelectedEntityTarget.class);
    }

    public Collection<Class<? extends Source>> getPossibleSources() {
        return Sets.newHashSet(SelectedEntitySource.class);
    }

    @Override
    public List<Action> getWrappedActions() {
        return actions;
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        Location l = getLocation(target, source);

        LivingEntity caster = source.getCaster();
        if (l != null) {
            Predicate<LivingEntity> select = e -> condition.checkCondition(new EntityTarget(e), source);
            World world = l.getWorld();
            if (world != null) {
                List<LivingEntity> livingEntities = area.entitiesInside(l, target, source).stream().
                        filter(select).collect(Collectors.toList());
                Stream<LivingEntity> entityStream = livingEntities.stream();
                int realCount = count.getRealValue(target, source).intValue();
                if (realCount > 0) {
                    sorter.sortCollection(livingEntities, target, source);
                    entityStream = entityStream.limit(realCount);
                }
                entityStream.forEach(entity -> actions.forEach(action -> action.execute(new EntityTarget(entity),
                        castFromLocation ? new LocationSource(l, caster, source.getSourceItem()) : source, args)));
            } else {
                return ActionResult.FAILURE;
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new AreaEntitiesAction(target, actions.stream().map(Action::clone).collect(Collectors.toList()), area.clone(),
                condition.clone(), castFromLocation, sorter.clone(), count.clone());
    }

    @Override
    public String getName() {
        return "&6&lArea: &c" + area.getName();
    }
}
