package com.github.zamponimarco.cubescocktail.action.meta;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.LocationTarget;
import com.github.zamponimarco.cubescocktail.annotation.PossibleSources;
import com.github.zamponimarco.cubescocktail.annotation.PossibleTargets;
import com.github.zamponimarco.cubescocktail.area.Area;
import com.github.zamponimarco.cubescocktail.area.SphericArea;
import com.github.zamponimarco.cubescocktail.condition.AlwaysTrueCondition;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.source.SelectedBlockSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.SelectedBlockTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
@PossibleTargets("getPossibleTargets")
@PossibleSources("getPossibleSources")
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lApply actions to blocks in Area", description = "gui.action.meta.wrapper.area-blocks.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZjZDQxNGIwNWE1MzJjNjA5YzJhYTQ4ZDZjMDYyYzI5MmQ1MzNkZmFmNGQ3MzJhYmU5YWY1NzQxNTg5ZSJ9fX0=")
public class AreaBlocksAction extends WrapperAction {

    private static final String ACTIONS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODIxNmVlNDA1OTNjMDk4MWVkMjhmNWJkNjc0ODc5NzgxYzQyNWNlMDg0MWI2ODc0ODFjNGY3MTE4YmI1YzNiMSJ9fX0=";
    private static final String SHAPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2IyYjVkNDhlNTc1Nzc1NjNhY2EzMTczNTUxOWNiNjIyMjE5YmMwNThiMWYzNDY0OGI2N2I4ZTcxYmMwZmEifX19";
    private static final String CONDITION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMyNzEwNTI3MTllZjY0MDc5ZWU4YzE0OTg5NTEyMzhhNzRkYWM0YzI3Yjk1NjQwZGI2ZmJkZGMyZDZiNWI2ZSJ9fX0=";


    @Serializable(headTexture = ACTIONS_HEAD, description = "gui.action.meta.wrapper.area-blocks.actions")
    private List<Action> actions;
    @Serializable(headTexture = SHAPE_HEAD, description = "gui.action.meta.wrapper.area-blocks.area",
            additionalDescription = {"gui.additional-tooltips.recreate"})
    private Area area;
    @Serializable(headTexture = CONDITION_HEAD, description = "gui.action.meta.wrapper.area-blocks.condition",
            additionalDescription = {"gui.additional-tooltips.recreate"})
    private Condition condition;

    public AreaBlocksAction() {
        this(TARGET_DEFAULT, Lists.newArrayList(), new SphericArea(), new AlwaysTrueCondition());
    }

    public AreaBlocksAction(boolean target, List<Action> actions, Area area, Condition condition) {
        super(target);
        this.actions = actions;
        this.area = area;
        this.condition = condition;
    }

    public AreaBlocksAction(Map<String, Object> map) {
        super(map);
        this.actions = (List<Action>) map.get("actions");
        this.actions.removeIf(Objects::isNull);
        this.area = (Area) map.get("area");
        this.condition = (Condition) map.getOrDefault("condition", new AlwaysTrueCondition());
    }

    public Collection<Class<? extends Target>> getPossibleTargets() {
        return Sets.newHashSet(SelectedBlockTarget.class);
    }

    public Collection<Class<? extends Source>> getPossibleSources() {
        return Sets.newHashSet(SelectedBlockSource.class);
    }

    @Override
    public List<Action> getWrappedActions() {
        return actions;
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        Predicate<Location> select = loc -> condition.checkCondition(new LocationTarget(loc), source);
        area.getBlocks(getLocation(target, source), target, source).stream().filter(select).forEach(block -> actions.
                forEach(action -> action.execute(new LocationTarget(block), source, args)));
        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new AreaBlocksAction(target, actions.stream().map(Action::clone).collect(Collectors.toList()),
                area.clone(), condition.clone());
    }

    @Override
    public String getName() {
        return "&6&lBlocks in area: &c" + area.getName();
    }
}
