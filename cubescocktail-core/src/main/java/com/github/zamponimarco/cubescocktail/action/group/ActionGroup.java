package com.github.zamponimarco.cubescocktail.action.group;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.source.CasterSource;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.trgt.CasterTarget;
import com.github.zamponimarco.cubescocktail.trgt.Target;
import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActionGroup implements Model, Cloneable {

    private static final String SOURCE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3OWRmMzlkNzVmYzBjZDFhNTMxMGViOGE1ODYxZDI1NDg4OGVkM2Y2ZDllMjVjMTNkNTFkZmUzYzFjODc5OSJ9fX0=";
    private static final String TARGET_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc4N2I3YWZiNWE1OTk1Mzk3NWJiYTI0NzM3NDliNjAxZDU0ZDZmOTNjZWFjN2EwMmFjNjlhYWU3ZjliOCJ9fX0=";
    private static final String ACTIONS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0ZDVhNGFiYjY0ZGIxMWI0NTcxZTc0N2M1OGU0MDMwMThmNjQ5YzE4MTZjNjUwOWY5YTNmN2E3ODIxYjQ4ZSJ9fX0=";

    @Serializable(headTexture = SOURCE_HEAD, description = "gui.group.source")
    private Source source;
    @Serializable(headTexture = TARGET_HEAD, description = "gui.group.target")
    private Target target;
    @Serializable(headTexture = ACTIONS_HEAD, description = "gui.group.actions")
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

    public void executeGroup(Map<String, Object> args) {
        actions.forEach(action -> action.execute(target.getTarget(args), source.getSource(args), args));
    }

    @Override
    public ActionGroup clone() {
        return new ActionGroup(source.clone(), target.clone(),
                actions.stream().map(Action::clone).collect(Collectors.toList()));
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue(ACTIONS_HEAD),
                MessageUtils.color(String.format("&6&lGroup of %d actions", actions.size())),
                Lists.newArrayList(
                   MessageUtils.color("&6&lSource: &c" + source.getName()),
                   MessageUtils.color("&6&lTarget: &c" + target.getName())
                ));
    }
}