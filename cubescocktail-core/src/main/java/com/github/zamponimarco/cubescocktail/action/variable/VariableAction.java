package com.github.zamponimarco.cubescocktail.action.variable;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.source.EntitySource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.action.targeter.EntityTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Map;

@Enumerable.Parent(classArray = {SetNumericVariableAction.class, SetStringVariableAction.class, SetVectorVariableAction.class})
@Enumerable.Displayable(name = "&9&lSet variable &6» &cEntity/Item Targetable", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjVjNGQyNGFmZmRkNDgxMDI2MjAzNjE1MjdkMjE1NmUxOGMyMjNiYWU1MTg5YWM0Mzk4MTU2NDNmM2NmZjlkIn19fQ")
public abstract class VariableAction extends Action {
    protected static final String NAME_DEFAULT = "var";
    protected static final boolean PERSISTENT_DEFAULT = false;

    protected static final String NAME_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=";
    protected static final String VALUE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";
    private static final String PERSISTENT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTViMzRjNmNlZTY4NGQ3MTcxNGIzYTFjNzExNTExY2JkNjkyNDQ3ODIwYmM5YTExMzYyOGMxZDM1ODA0ODI1ZSJ9fX0=";

    @Serializable(headTexture = NAME_HEAD, description = "gui.action.variable.name")
    @Serializable.Optional(defaultValue = "NAME_DEFAULT")
    protected String name;

    @Serializable(displayItem = "getPersistentItem", description = "gui.action.variable.persistent")
    @Serializable.Optional(defaultValue = "PERSISTENT_DEFAULT")
    protected boolean persistent;

    public VariableAction(boolean target, String name, boolean persistent) {
        super(target);
        this.name = name;
        this.persistent = persistent;
    }

    public VariableAction(Map<String, Object> map) {
        super(map);
        this.name = (String) map.getOrDefault("name", NAME_DEFAULT);
        this.persistent = (boolean) map.getOrDefault("persistent", PERSISTENT_DEFAULT);
    }

    protected Metadatable getMetadatable(ActionTarget target, ActionSource source) {
        if (this.target) {
            if (target instanceof EntityTarget) {
                return ((EntityTarget) target).getTarget();
            }
        }
        if (source instanceof EntitySource) {
            return source.getCaster();
        }
        return null;
    }

    protected PersistentDataContainer getDataContainer(ActionTarget target, ActionSource source) {
        if (this.target) {
            if (target instanceof EntityTarget) {
                return ((EntityTarget) target).getTarget().getPersistentDataContainer();
            }
        }
        if (source instanceof EntitySource) {
            return source.getCaster().getPersistentDataContainer();
        }
        return null;
    }

    public ItemStack getPersistentItem() {
        return Libs.getVersionWrapper().skullFromValue(PERSISTENT_HEAD);
    }
}
