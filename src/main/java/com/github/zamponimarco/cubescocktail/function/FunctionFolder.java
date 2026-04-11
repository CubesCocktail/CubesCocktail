package com.github.zamponimarco.cubescocktail.function;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.constant.Textures;
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

    protected static int folderCounter = 1;
    @Serializable(headTexture = Textures.SKILL_HEAD)
    private List<AbstractFunction> skills;

    @Serializable(headTexture = Textures.NAME_HEAD_FA3FCE50)
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
        return ItemUtils.getNamedItem(ItemUtils.skullFromValue(Textures.FOLDER_HEAD), MessageUtils.color(displayName), lore);
    }
}
