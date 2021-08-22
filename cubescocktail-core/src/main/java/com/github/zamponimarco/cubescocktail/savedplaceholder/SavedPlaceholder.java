package com.github.zamponimarco.cubescocktail.savedplaceholder;

import com.github.jummes.libs.annotation.CustomClickable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.gui.PluginInventoryHolder;
import com.github.jummes.libs.gui.model.ModelObjectInventoryHolder;
import com.github.jummes.libs.gui.model.RemoveConfirmationInventoryHolder;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.database.NamedModel;
import com.github.zamponimarco.cubescocktail.placeholder.Placeholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.MaxHealthPlaceholder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Map;

@Getter
@CustomClickable(customCollectionClickConsumer = "getCustomClickConsumer")
public class SavedPlaceholder extends NamedModel {

    private static final String PLACEHOLDER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzcyMzcwNGE5ZDU5MTBiOWNkNTA1ZGM5OWM3NzliZjUwMzc5Y2I4NDc0NWNjNzE5ZTlmNzg0ZGQ4YyJ9fX0=";

    private static int counter = 1;

    @Serializable(headTexture = PLACEHOLDER_HEAD, description = "gui.saved-placeholder.placeholder")
    private Placeholder placeholder;
    @Serializable
    private ItemStackWrapper item;

    public SavedPlaceholder() {
        super(nextAvailableName());
        this.placeholder = new MaxHealthPlaceholder();
        this.item = new ItemStackWrapper();
    }

    public SavedPlaceholder(String name, Placeholder placeholder, ItemStackWrapper item) {
        super(name);
        this.placeholder = placeholder;
        this.item = item;
        counter++;
    }

    public SavedPlaceholder(Map<String, Object> map) {
        super(map);
        this.placeholder = (Placeholder) map.get("placeholder");
        this.item = (ItemStackWrapper) map.getOrDefault("item", new ItemStackWrapper());
    }

    private static String nextAvailableName() {
        String name;
        do {
            name = "placeholder" + counter;
            counter++;
        } while (CubesCocktail.getInstance().getSavedPlaceholderManager().getByName(name) != null);
        return name;
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(item.getWrapped(), MessageUtils.color("&6&lName: &c" + name),
                Libs.getLocale().getList("gui.saved-placeholder.description"));
    }


    public void getCustomClickConsumer(JavaPlugin plugin, PluginInventoryHolder parent,
                                       ModelPath<? extends Model> path, Field field, InventoryClickEvent e) {
        if (e.getClick().equals(ClickType.LEFT)) {
            path.addModel(this);
            if (!e.getCursor().getType().equals(Material.AIR)) {
                ItemStack newItem = e.getCursor().clone();
                this.item = new ItemStackWrapper(newItem, true);
                path.saveModel();
                e.getWhoClicked().getInventory().addItem(newItem);
                e.getCursor().setAmount(0);
                path.popModel();
                e.getWhoClicked().openInventory(parent.getInventory());
                return;
            }
            e.getWhoClicked().openInventory(new ModelObjectInventoryHolder(plugin, parent, path).getInventory());
        } else if (e.getClick().equals(ClickType.RIGHT)) {
            e.getWhoClicked().openInventory(new RemoveConfirmationInventoryHolder(plugin, parent, path, this,
                    field).getInventory());
        }
    }

    @Override
    protected boolean isAlreadyPresent(String name) {
        return CubesCocktail.getInstance().getSavedPlaceholderManager().getByName(name) != null;
    }

}
