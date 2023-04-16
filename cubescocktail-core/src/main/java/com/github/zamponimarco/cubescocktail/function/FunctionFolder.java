package com.github.zamponimarco.cubescocktail.function;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class FunctionFolder extends AbstractFunction {

    private static final String SKILL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmJiMTI1NmViOWY2NjdjMDVmYjIxZTAyN2FhMWQ1MzU1OGJkYTc0ZTI0MGU0ZmE5ZTEzN2Q4NTFjNDE2ZmU5OCJ9fX0=";
    private static final String NAME_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmEzZmNlNTAzNmY3YWQ0ZjExMTExY2UzMThmOGYxYWVlODU5ZWY0OWRlMTI5M2YxMTYyY2EyZTJkZWEyODFkYiJ9fX0=";
    private static final String FOLDER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTYzMzBhNGEyMmZmNTU4NzFmYzhjNjE4ZTQyMWEzNzczM2FjMWRjYWI5YzhlMWE0YmI3M2FlNjQ1YTRhNGUifX19";
    protected static int folderCounter = 1;
    @Serializable(headTexture = SKILL_HEAD)
    private List<AbstractFunction> skills;

    @Serializable(headTexture = NAME_HEAD)
    private String displayName;

    public FunctionFolder() {
        super(nextAvailableName());
        this.skills = Lists.newArrayList();
        this.displayName = name;
    }

    public FunctionFolder(String name, List<AbstractFunction> skills, String displayName) {
        super(name);
        this.skills = skills;
        this.displayName = displayName;
        folderCounter++;
    }

    public FunctionFolder(Map<String, Object> map) {
        super(map);
        this.skills = (List<AbstractFunction>) map.getOrDefault("skills", Lists.newArrayList());
        this.displayName = (String) map.getOrDefault("displayName", name);
        folderCounter++;
    }

    protected static String nextAvailableName() {
        String name;
        do {
            name = "folder" + folderCounter;
            folderCounter++;
        } while (CubesCocktail.getInstance().getFunctionManager().getAbstractFunctionByName(name) != null);
        return name;
    }

    @Override
    public Function getByName(String name) {
        return skills.stream().map(skill -> skill.getByName(name)).filter(Objects::nonNull).findFirst().
                orElse(null);
    }

    @Override
    public List<Function> getExecutableFunctions() {
        return skills.stream().reduce(Lists.newArrayList(), (list, skill) -> {
            list.addAll(skill.getExecutableFunctions());
            return list;
        }, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });
    }

    @Override
    public ItemStack getGUIItem() {
        List<Component> lore = Lists.newArrayList(
                MessageUtils.color("&6&lContains: &c" + skills.size() + " item" + (skills.size() == 1 ? "" : "s")),
                MessageUtils.color("&6&lid: &c" + name)
        );
        lore.addAll(Libs.getLocale().getList("gui.function.folder-description"));
        return ItemUtils.getNamedItem(Libs.getVersionWrapper().skullFromValue(FOLDER_HEAD), MessageUtils.color(displayName), lore);
    }
}
