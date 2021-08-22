package com.github.zamponimarco.cubescocktail.condition.bool;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.GUINameable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@GUINameable(GUIName = "getName")
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lOr Condition", description = "gui.condition.or.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNhYzM0MTg1YmNlZTYyNmRkOWJjOTY2YmU2NDk4NDM4ZmJmYTc1NDFjODYyYWM3MTZmZmVmOWZkMTg1In19fQ==")
public class OrCondition extends TrueFalseCondition {

    private static final List<Condition> CONDITIONS_DEFAULT = Lists.newArrayList();

    private static final String CONDITIONS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNhYzM0MTg1YmNlZTYyNmRkOWJjOTY2YmU2NDk4NDM4ZmJmYTc1NDFjODYyYWM3MTZmZmVmOWZkMTg1In19fQ==";

    @Serializable(headTexture = CONDITIONS_HEAD, description = "gui.condition.or.conditions")
    @Serializable.Optional(defaultValue = "CONDITIONS_DEFAULT")
    private List<Condition> conditions;

    public OrCondition() {
        this(NEGATE_DEFAULT, Lists.newArrayList());
    }

    public OrCondition(boolean negate, List<Condition> conditions) {
        super(negate);
        this.conditions = conditions;
    }

    public OrCondition(Map<String, Object> map) {
        super(map);
        this.conditions = (List<Condition>) map.getOrDefault("conditions", Lists.newArrayList());
    }

    @Override
    public boolean testCondition(ActionTarget target, ActionSource source) {
        return conditions.stream().anyMatch(condition -> condition.checkCondition(target, source));
    }

    @Override
    public String getName() {
        String[] s = conditions.stream().map(condition -> "&6&l(" + condition.getName() + "&6&l)").toArray(String[]::new);
        return String.join(" &6&lor&c ", s);
    }

    @Override
    public Condition clone() {
        return new OrCondition(negate, conditions.stream().map(Condition::clone).collect(Collectors.toList()));
    }
}