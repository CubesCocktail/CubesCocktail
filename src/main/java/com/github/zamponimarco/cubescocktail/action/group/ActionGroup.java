package com.github.zamponimarco.cubescocktail.action.group;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.CasterTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class ActionGroup implements Model, Cloneable {


    @Serializable(headTexture = Textures.SOURCE_HEAD, description = "gui.group.source")
    private Source source;
    @Serializable(headTexture = Textures.TARGET_HEAD, description = "gui.group.target")
    private Target target;
    @Serializable(headTexture = Textures.ACTIONS_HEAD_3F4D5A4A, description = "gui.group.actions")
    private List<Action> actions;

    public ActionGroup() {
        this.source = new CasterSource();
        this.target = new CasterTarget();
        this.actions = Lists.newArrayList();
    }

    public ActionGroup(Source source, Target target, List<Action> actions) {
        this.source = source;
        this.target = target;
        this.actions = actions;
    }

    public ActionGroup(Map<String, Object> map) {
        source = (Source) map.getOrDefault("source", new CasterSource());
        target = (Target) map.getOrDefault("target", new CasterTarget());
        actions = (List<Action>) map.getOrDefault("actions", Lists.newArrayList());
    }

    public List<Action> getWrappedActions() {
        return actions;
    }

    public void executeGroup(ActionArgument args) {
        actions.forEach(action -> action.execute(target.getTarget(args), source.getSource(args), args));
    }

    @Override
    public ActionGroup clone() {
        return new ActionGroup(source.clone(), target.clone(),
                actions.stream().map(Action::clone).collect(Collectors.toList()));
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(ItemUtils.skullFromValue(Textures.ACTIONS_HEAD_3F4D5A4A),
                MessageUtils.color(String.format("&6&lGroup of %d actions", actions.size())),
                Lists.newArrayList(
                        MessageUtils.color("&6&lSource: &c" + source.getName()),
                        MessageUtils.color("&6&lTarget: &c" + target.getName())
                ));
    }
}