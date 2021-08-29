package com.github.zamponimarco.cubescocktail.action.meta;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.trgt.CasterTarget;
import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lChange Source and Target", description = "gui.action.meta.wrapper.source.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM2Mzc5NjFmODQ1MWE1M2I2N2QyNTMxMmQzNTBjNjIwZjMyYjVmNjA4YmQ2YWRlMDY2MzdiZTE3MTJmMzY0ZSJ9fX0")
public class ChangeSourceAction extends WrapperAction {
    private static final String ACTIONS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODIxNmVlNDA1OTNjMDk4MWVkMjhmNWJkNjc0ODc5NzgxYzQyNWNlMDg0MWI2ODc0ODFjNGY3MTE4YmI1YzNiMSJ9fX0=";

    @Serializable(headTexture = ACTIONS_HEAD, description = "gui.action.meta.wrapper.source.actions")
    @Serializable.Optional(defaultValue = "ACTIONS_DEFAULT")
    private List<ActionGroup> groups;

    public ChangeSourceAction() {
        this(TARGET_DEFAULT, Lists.newArrayList());
    }

    public ChangeSourceAction(boolean target, List<ActionGroup> groups) {
        super(target);
        this.groups = groups;
    }

    public ChangeSourceAction(Map<String, Object> map) {
        super(map);
        this.groups = (List<ActionGroup>) map.getOrDefault("groups", Lists.newArrayList());

        legacyTransition(map);
    }

    @Deprecated
    private void legacyTransition(Map<String, Object> map) {
        List<Action> actions = (List<Action>) map.get("actions");
        if (actions != null && !actions.isEmpty()) {
            groups.add(new ActionGroup(new CasterSource(), new CasterTarget(), actions));
        }
    }

    @Override
    public List<Action> getWrappedActions() {
        return groups.stream().map(ActionGroup::getWrappedActions).reduce(Lists.newArrayList(), (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        groups.forEach(group -> group.executeGroup(args.clone()));
        return ActionResult.SUCCESS;
    }

    @Override
    public String getName() {
        return "&6&lChange Source and Target";
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }

    @Override
    public Action clone() {
        return new ChangeSourceAction(TARGET_DEFAULT, groups.stream().map(ActionGroup::clone).collect(Collectors.toList()));
    }
}
