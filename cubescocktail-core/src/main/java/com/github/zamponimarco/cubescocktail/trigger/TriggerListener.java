package com.github.zamponimarco.cubescocktail.trigger;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface TriggerListener {

    void onTrigger(Map<String, Object> args);

    @NotNull
    Trigger getTrigger();

}
