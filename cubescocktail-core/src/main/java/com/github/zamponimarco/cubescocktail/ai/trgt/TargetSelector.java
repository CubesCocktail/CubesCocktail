package com.github.zamponimarco.cubescocktail.ai.trgt;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Parent(classArray = {NearestAttackableTargetSelector.class, ClearTargetSelector.class})
public abstract class TargetSelector implements Model {
    public TargetSelector() {
    }

    public TargetSelector(Map<String, Object> map) {

    }

    public abstract void applyToEntity(Mob e, ActionSource source, ActionTarget target);

    public abstract String getName();

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getVersionWrapper().skullFromValue(getClass().
                getAnnotation(Enumerable.Displayable.class).headTexture()), MessageUtils.color(getName()), Libs.getLocale().
                getList("gui.additional-tooltips.delete"));
    }
}
