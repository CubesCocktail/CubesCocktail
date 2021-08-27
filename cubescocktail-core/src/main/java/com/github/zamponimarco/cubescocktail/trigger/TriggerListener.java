package com.github.zamponimarco.cubescocktail.trigger;

import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface TriggerListener {

    void onTrigger(ActionArgument args);

    @NotNull
    Trigger getTrigger();

}
