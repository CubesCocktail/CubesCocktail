package com.github.zamponimarco.cubescocktail.value;

import com.github.jummes.libs.annotation.CustomClickable;
import com.github.jummes.libs.annotation.GUINameable;
import com.github.jummes.libs.gui.PluginInventoryHolder;
import com.github.jummes.libs.gui.model.ModelObjectInventoryHolder;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.math.Vector;
import com.github.zamponimarco.cubescocktail.placeholder.vector.DirectionPlaceholder;
import com.github.zamponimarco.cubescocktail.placeholder.vector.VectorPlaceholder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@GUINameable(GUIName = "getName")
@CustomClickable(customFieldClickConsumer = "getCustomClickConsumer")
@Getter
@Setter
public class VectorValue extends Value<Vector, VectorPlaceholder> {

    public VectorValue() {
        this(OBJECT_VALUE_DEFAULT, new Vector(), new DirectionPlaceholder());
    }

    public VectorValue(Vector value) {
        this(OBJECT_VALUE_DEFAULT, value, new DirectionPlaceholder());
    }

    public VectorValue(boolean objectValue, Vector value, VectorPlaceholder placeholderValue) {
        super(objectValue, value, placeholderValue);
    }

    public VectorValue(Map<String, Object> map) {
        super(map);
        this.value = new Vector();
        this.placeholderValue = new DirectionPlaceholder();
        if (this.objectValue) {
            this.value = (Vector) map.get("value");
        } else {
            this.placeholderValue = (VectorPlaceholder) map.get("placeholderValue");
        }
    }

    @Override
    public VectorValue clone() {
        return new VectorValue(objectValue, value.clone(), placeholderValue.clone());
    }

    @SneakyThrows
    public PluginInventoryHolder getCustomClickConsumer(JavaPlugin plugin, PluginInventoryHolder parent,
                                                        ModelPath<? extends Model> path, Field field,
                                                        InventoryClickEvent e) {
        Map<ClickType, Supplier<PluginInventoryHolder>> map = new HashMap<>();
        map.putIfAbsent(ClickType.LEFT, () -> {
            if (objectValue) {
                path.addModel(value);
            } else {
                path.addModel(placeholderValue);
            }
            return new ModelObjectInventoryHolder(plugin, parent, path);
        });
        return super.getCustomClickConsumer(plugin, parent, path, field, e, map);
    }

}
