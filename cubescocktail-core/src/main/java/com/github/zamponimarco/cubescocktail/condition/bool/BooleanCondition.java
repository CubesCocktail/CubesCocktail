package com.github.zamponimarco.cubescocktail.condition.bool;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.GUINameable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.placeholder.bool.BooleanPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.bool.OnGroundPlaceholder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@GUINameable(GUIName = "getName")
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lBoolean Condition", description = "gui.condition.boolean.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdiNjJkMjc1ZDg3YzA5Y2UxMGFjYmNjZjM0YzRiYTBiNWYxMzVkNjQzZGM1MzdkYTFmMWRmMzU1YTIyNWU4MiJ9fX0=")
@Getter
@Setter
public class BooleanCondition extends TrueFalseCondition {

    private static final String PLACEHOLDER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmYzOGQ2MTIwM2U0ZTZhMmM5MjQ4MDMwNjA5ZWZiNTc3YjRmZTllZjc4NmNhNDBkYjk0M2Y1M2Y0ODBhZTY4OCJ9fX0=";

    @Serializable(headTexture = PLACEHOLDER_HEAD, description = "gui.condition.boolean.value",
            additionalDescription = {"gui.additional-tooltips.value"})
    private BooleanPlaceholder value;

    public BooleanCondition() {
        this(NEGATE_DEFAULT, new OnGroundPlaceholder());
    }

    public BooleanCondition(boolean negate, BooleanPlaceholder value) {
        super(negate);
        this.value = value;
    }

    public BooleanCondition(Map<String, Object> map) {
        super(map);
        this.value = (BooleanPlaceholder) map.get("value");
    }

    @Override
    public boolean testCondition(ActionTarget target, ActionSource source) {
        return value.computePlaceholder(target, source);
    }

    @Override
    public String getName() {
        return String.format("%s" + value.getName(), negate ? "&6&lNot &c" : "&c");
    }

    @Override
    public Condition clone() {
        return new BooleanCondition(negate, value.clone());
    }
}
