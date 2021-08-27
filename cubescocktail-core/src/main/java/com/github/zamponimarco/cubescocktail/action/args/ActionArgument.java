package com.github.zamponimarco.cubescocktail.action.args;

import java.util.HashMap;
import java.util.Map;

public class ActionArgument implements Cloneable {

    private Map<ActionArgumentKey<?>, Object> map;

    public ActionArgument() {
        this.map = new HashMap<>();
    }

    public ActionArgument(Map<ActionArgumentKey<?>, Object> map) {
        this.map = new HashMap<>(map);
    }

    public <Z> void setArgument(ActionArgumentKey<Z> key, Z value) {
        map.put(key, value);
    }

    public <Z> Z getArgument(ActionArgumentKey<Z> key) {
        return (Z) map.get(key);
    }

    @Override
    public ActionArgument clone() {
        return new ActionArgument(map);
    }
}
