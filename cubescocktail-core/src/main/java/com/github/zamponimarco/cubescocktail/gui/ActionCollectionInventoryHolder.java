package com.github.zamponimarco.cubescocktail.gui;

import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.gui.PluginInventoryHolder;
import com.github.jummes.libs.gui.model.ModelObjectInventoryHolder;
import com.github.jummes.libs.gui.model.RemoveConfirmationInventoryHolder;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.meta.FunctionAction;
import com.github.zamponimarco.cubescocktail.action.meta.WrapperAction;
import com.github.zamponimarco.cubescocktail.function.Function;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ActionCollectionInventoryHolder extends SelectableCollectionInventoryHolder<Action> {

    private static final boolean TARGET_DEFAULT = true;

    public ActionCollectionInventoryHolder(JavaPlugin plugin, PluginInventoryHolder parent, ModelPath<Action> path,
                                           Field field, int page, Predicate<Action> filter) {
        super(plugin, parent, path, field, page, filter);
    }

    public ItemStack getGlintedItem(Action action) {
        List<Component> lore = Libs.getLocale().getList("gui.action.description");
        lore.set(0, MessageUtils.color("&6&lApply actions &eto all selected actions:"));
        lore.add(3 + WrapperAction.WRAPPERS_MAP.size(), MessageUtils.color("&6&l- [9] &eto wrap in a new skill"));
        return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue(SELECTED_HEAD), MessageUtils.color(action.getName()), lore);
    }

    @SneakyThrows
    protected void defaultClickConsumer(Action model, InventoryClickEvent e) {
        Collection<Action> actions = ((Collection<Action>) FieldUtils.readField(field,
                path.getLast() != null ? path.getLast() : path.getModelManager(), true));
        if (e.getClick().equals(ClickType.LEFT)) {
            openActionGUI(model, e);
        } else if (e.getClick().equals(ClickType.RIGHT)) {
            deleteActions(model, e);
        } else if (e.getClick().equals(ClickType.MIDDLE)) {
            cloneActions(model, e, actions);
        } else if (e.getClick().equals(ClickType.NUMBER_KEY) && WrapperAction.WRAPPERS_MAP.containsKey(model.getClass())
                && e.getHotbarButton() == WrapperAction.WRAPPERS_MAP.get(model.getClass())) {
            unwrapActions(model, e, actions);
        } else if (e.getClick().equals(ClickType.NUMBER_KEY) && WrapperAction.WRAPPERS_MAP.containsValue(e.getHotbarButton())) {
            wrapActions(e, model, actions);
        } else if (e.getClick().equals(ClickType.NUMBER_KEY) && e.getHotbarButton() == 8) {
            if (!selected.isEmpty()) {
                wrapIntoNewSavedSkill(model, e, actions);
            } else if (model instanceof FunctionAction) {
                openSkillGUI((FunctionAction) model, e);
            }
        }
        if (e.getClick().equals(ClickType.DROP)) {
            selectModel(model, e);
        } else if (e.getClick().equals(ClickType.CONTROL_DROP)) {
            selectAllModels(e, actions);
        } else {
            unselectAllModels();
        }
    }

    private void wrapIntoNewSavedSkill(Action model, InventoryClickEvent e, Collection<Action> actions) {
        if (selected.contains(model)) {
            Function newSkill = new Function();
            actions.removeAll(selected);
            newSkill.getActions().addAll(selected);
            CubesCocktail.getInstance().getFunctionManager().getFunctions().add(newSkill);
            CubesCocktail.getInstance().getFunctionManager().saveModel(newSkill);
            actions.add(new FunctionAction(TARGET_DEFAULT, newSkill.getName()));
            path.saveModel();
            e.getWhoClicked().openInventory(getInventory());
        }
    }

    private void openSkillGUI(FunctionAction model, InventoryClickEvent e) {
        Function skill = CubesCocktail.getInstance().getFunctionManager().getFunctionByName(model.getFunctionName());
        if (skill != null) {
            e.getWhoClicked().openInventory(new ModelObjectInventoryHolder(CubesCocktail.getInstance(),
                    this, new ModelPath<>(CubesCocktail.getInstance().getFunctionManager(), skill)).
                    getInventory());
        }
    }

    private void openActionGUI(Action model, InventoryClickEvent e) {
        path.addModel(model);
        e.getWhoClicked().openInventory(new ModelObjectInventoryHolder(plugin, this, path).getInventory());
    }

    private void deleteActions(Action model, InventoryClickEvent e) {
        if (selected.contains(model)) {
            e.getWhoClicked().openInventory(new RemoveConfirmationInventoryHolder(plugin, this, path,
                    new ArrayList<>(selected), field).getInventory());
        } else {
            e.getWhoClicked().openInventory(new RemoveConfirmationInventoryHolder(plugin, this, path, model,
                    field).getInventory());
        }
    }

    private void cloneActions(Action model, InventoryClickEvent e, Collection<Action> actions) {
        if (selected.contains(model)) {
            actions.addAll(selected.stream().map(Action::clone).collect(Collectors.toList()));
        } else {
            actions.add(model.clone());
        }
        path.saveModel();
        e.getWhoClicked().openInventory(getInventory());
    }

    private void unwrapActions(Action model, InventoryClickEvent e, Collection<Action> actions) {
        actions.remove(model);
        path.deleteRoot(model);
        actions.addAll(((WrapperAction) model).getWrappedActions());
        path.saveModel();
        e.getWhoClicked().openInventory(getInventory());
    }

    @SneakyThrows
    private void wrapActions(InventoryClickEvent e, Action model, Collection<Action> superActions) {
        WrapperAction wrapperAction = WrapperAction.WRAPPERS_MAP.inverse().get(e.getHotbarButton()).newInstance();
        superActions.add(wrapperAction);
        if (selected.contains(model)) {
            superActions.removeAll(selected);
            wrapperAction.getWrappedActions().addAll(selected);
        } else {
            superActions.remove(model);
            wrapperAction.getWrappedActions().add(model);
        }
        path.saveModel();
        e.getWhoClicked().openInventory(getInventory());
    }
}
