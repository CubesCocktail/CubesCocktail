package com.github.zamponimarco.cubescocktail.placeholder.string;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.placeholder.Placeholder;
import com.github.zamponimarco.cubescocktail.placeholder.numeric.entity.HealthPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
@Setter
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lObject To String Placeholder", description = "gui.placeholder.string.to-string.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzcyMzcwNGE5ZDU5MTBiOWNkNTA1ZGM5OWM3NzliZjUwMzc5Y2I4NDc0NWNjNzE5ZTlmNzg0ZGQ4YyJ9fX0=")
public class ObjectToStringPlaceholder extends StringPlaceholder {

    private static final String PLACEHOLDER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzcyMzcwNGE5ZDU5MTBiOWNkNTA1ZGM5OWM3NzliZjUwMzc5Y2I4NDc0NWNjNzE5ZTlmNzg0ZGQ4YyJ9fX0=";

    @Serializable(headTexture = PLACEHOLDER_HEAD, description = "gui.placeholder.string.to-string.placeholder",
            additionalDescription = {"gui.additional-tooltips.recreate"})
    private Placeholder<?> placeholder;

    public ObjectToStringPlaceholder() {
        this(TARGET_DEFAULT, new HealthPlaceholder());
    }

    public ObjectToStringPlaceholder(boolean target, Placeholder<?> placeholder) {
        super(target);
        this.placeholder = placeholder;
    }

    public static ObjectToStringPlaceholder deserialize(Map<String, Object> map) {
        Placeholder<?> placeholder = (Placeholder<?>) map.get("placeholder");
        return new ObjectToStringPlaceholder(TARGET_DEFAULT, placeholder);
    }

    @Override
    public String computePlaceholder(ActionTarget target, ActionSource source) {
        return placeholder.computePlaceholder(target, source).toString();
    }

    @Override
    public String getName() {
        return String.format("&6&l(&c%s&6&l).str()&c", placeholder.getName());
    }

    @Override
    public StringPlaceholder clone() {
        return new ObjectToStringPlaceholder(TARGET_DEFAULT, placeholder.clone());
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }
}
