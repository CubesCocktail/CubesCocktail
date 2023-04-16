package com.github.zamponimarco.cubescocktail.gui;

import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.gui.PluginInventoryHolder;
import com.github.jummes.libs.gui.model.ModelObjectInventoryHolder;
import com.github.jummes.libs.gui.model.RemoveConfirmationInventoryHolder;
import com.github.jummes.libs.gui.model.create.ModelCreateInventoryHolder;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.jummes.libs.util.ReflectUtils;
import com.github.zamponimarco.cubescocktail.function.AbstractFunction;
import com.github.zamponimarco.cubescocktail.function.Function;
import com.github.zamponimarco.cubescocktail.function.FunctionFolder;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FunctionCollectionInventoryHolder extends SelectableCollectionInventoryHolder<AbstractFunction> {

    public FunctionCollectionInventoryHolder(JavaPlugin plugin, PluginInventoryHolder parent, ModelPath<AbstractFunction> path,
                                             Field field, int page, Predicate<AbstractFunction> filter) {
        super(plugin, parent, path, field, page, filter);
    }

    @Override
    public ItemStack getGlintedItem(AbstractFunction item) {
        List<Component> lore = Libs.getLocale().getList("gui.saved-skill.description");
        lore.set(0, MessageUtils.color("&6&lApply actions &eto all selected skills:"));
        return ItemUtils.getNamedItem(Libs.getVersionWrapper().skullFromValue(SELECTED_HEAD),
                MessageUtils.color("&6&lName: &c" + item.getName()), lore);
    }

    @Override
    protected Consumer<InventoryClickEvent> getAddConsumer() {
        return e -> e.getWhoClicked().openInventory(new ModelCreateInventoryHolder(plugin, this, path, field,
                Function.class, true).getInventory());
    }

    @SneakyThrows
    @Override
    protected void defaultClickConsumer(AbstractFunction model, InventoryClickEvent e) {
        Collection<AbstractFunction> skills = ((Collection<AbstractFunction>) ReflectUtils.readField(field,
                path.getLast() != null ? path.getLast() : path.getModelManager()));
        if (e.getClick().equals(ClickType.LEFT)) {
            if (!e.getCursor().getType().equals(Material.AIR) && model instanceof Function) {
                setSkillGUIItem(model, e);
            } else {
                openSkillGUI(model, e);
            }
        } else if (e.getClick().equals(ClickType.RIGHT)) {
            deleteSkills(model, e);
        } else if (e.getClick().equals(ClickType.MIDDLE)) {
            cloneSkills(model, skills, e);
        } else if (e.getClick().equals(ClickType.NUMBER_KEY)) {
            if (e.getHotbarButton() == 8) {
                if (model instanceof FunctionFolder && !selected.contains(model)) {
                    unwrapSkills((FunctionFolder) model, skills, e);
                } else {
                    wrapSkills(model, e, skills);
                }
            }
        }

        if (e.getClick().equals(ClickType.DROP)) {
            selectModel(model, e);
        } else if (e.getClick().equals(ClickType.CONTROL_DROP)) {
            selectAllModels(e, skills);
        } else {
            unselectAllModels();
        }
    }

    private void setSkillGUIItem(AbstractFunction model, InventoryClickEvent e) {
        ItemStack newItem = e.getCursor().clone();
        ((Function) model).setItem(new ItemStackWrapper(newItem, true));
        path.addModel(model);
        path.saveModel();
        path.popModel();
        e.getWhoClicked().getInventory().addItem(newItem);
        e.getCursor().setAmount(0);
        e.getWhoClicked().openInventory(getInventory());
    }

    private void wrapSkills(AbstractFunction model, InventoryClickEvent e, Collection<AbstractFunction> items) {
        List<AbstractFunction> toRemove = selected.contains(model) ? selected : Lists.newArrayList(model);
        FunctionFolder batch = new FunctionFolder();
        batch.getSkills().addAll(toRemove);
        toRemove.forEach(skill -> path.deleteRoot(skill));
        items.removeAll(toRemove);
        items.add(batch);
        path.addModel(batch);
        path.saveModel();
        path.popModel();
        e.getWhoClicked().openInventory(getInventory());
    }

    private void unwrapSkills(FunctionFolder model, Collection<AbstractFunction> items, InventoryClickEvent e) {
        model.getSkills().forEach(item -> {
            items.add(item);
            path.addModel(item);
            path.saveModel();
            path.popModel();
        });
        path.deleteRoot(model);
        items.remove(model);
        e.getWhoClicked().openInventory(getInventory());
    }

    private void openSkillGUI(AbstractFunction model, InventoryClickEvent e) {
        path.addModel(model);
        e.getWhoClicked().openInventory(new ModelObjectInventoryHolder(plugin, this, path).getInventory());
    }

    private void deleteSkills(AbstractFunction model, InventoryClickEvent e) {
        if (selected.contains(model)) {
            e.getWhoClicked().openInventory(new RemoveConfirmationInventoryHolder(plugin, this, path,
                    new ArrayList<>(selected), field).getInventory());
        } else {
            e.getWhoClicked().openInventory(new RemoveConfirmationInventoryHolder(plugin, this, path, model,
                    field).getInventory());
        }
    }

    private void cloneSkills(AbstractFunction model, Collection<AbstractFunction> skills, InventoryClickEvent e) {
        if (selected.contains(model)) {
            selected.stream().filter(skill -> skill instanceof Function).forEach(skill -> {
                Function clonedItem = ((Function) skill).clone();
                skills.add(clonedItem);
                path.addModel(clonedItem);
                path.saveModel();
                path.popModel();
            });
        } else {
            if (model instanceof Function) {
                Function clonedItem = ((Function) model).clone();
                skills.add(clonedItem);
                path.addModel(clonedItem);
                path.saveModel();
                path.popModel();
            }

        }
        e.getWhoClicked().openInventory(getInventory());
    }
}
