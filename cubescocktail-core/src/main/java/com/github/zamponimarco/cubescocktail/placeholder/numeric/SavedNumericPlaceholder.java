package com.github.zamponimarco.cubescocktail.placeholder.numeric;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.savedplaceholder.SavedPlaceholder;
import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lSaved Placeholder", description = "gui.placeholder.double.saved-placeholder.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhiODUxODMwY2YwM2ZhMzBkZDNjMmIwYWVjNzg1Y2JmNmUyNzhlMzllYmExZTkyODllNzg4MDlmMzljM2VhIn19fQ==")
public class SavedNumericPlaceholder extends NumericPlaceholder {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=";

    @Serializable(headTexture = HEAD, fromList = "getPlaceholders", fromListMapper = "placeholdersMapper", description = "gui.placeholder.double.saved-placeholder.name")
    private String placeholderName;

    public SavedNumericPlaceholder(boolean target, String placeholderName) {
        super(target);
        this.placeholderName = placeholderName;
    }

    public SavedNumericPlaceholder() {
        this(TARGET_DEFAULT, "");
    }

    public SavedNumericPlaceholder(Map<String, Object> map) {
        super(map);
        this.placeholderName = (String) map.getOrDefault("placeholderName", "");
    }

    public static List<Object> getPlaceholders(ModelPath<?> path) {
        return CubesCocktail.getInstance().getSavedPlaceholderManager().getPlaceholders().stream().
                filter(savedPlaceholder -> savedPlaceholder.getPlaceholder() instanceof NumericPlaceholder).
                map(SavedPlaceholder::getName).collect(Collectors.toList());
    }

    public static Function<Object, ItemStack> placeholdersMapper() {
        return obj -> {
            String placeholder = (String) obj;
            return ItemUtils.getNamedItem(CubesCocktail.getInstance().getSavedPlaceholderManager().getByName(placeholder).getItem().
                    getWrapped().clone(), MessageUtils.color("&6&l" + placeholder), Lists.newArrayList());
        };
    }

    @Override
    public Double computePlaceholder(ActionTarget target, ActionSource source) {
        SavedPlaceholder placeholder = CubesCocktail.getInstance().getSavedPlaceholderManager().getByName(placeholderName);
        if (placeholder != null) {
            return ((NumericPlaceholder) placeholder.getPlaceholder()).computePlaceholder(target, source);
        }
        return 0.0;
    }

    @Override
    public String getName() {
        return String.format("%s %s", target ? "Target" : "Source", placeholderName);
    }

    @Override
    public NumericPlaceholder clone() {
        return new SavedNumericPlaceholder(target, placeholderName);
    }
}
