package com.github.zamponimarco.cubescocktail.action.meta;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.function.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Enumerable.Child
@Getter
@Setter
@Enumerable.Displayable(name = "&c&lExecute a saved skill", description = "gui.action.meta.skill.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM1NGM4YmE3NDIwNzlhZWQ1YWNmYmYwN2M0MjhiNDA2YmMwOTJkYjhhYmM2ZjE3ZjcwNTkwOTliMDQ5NTliZCJ9fX0=")
public class FunctionAction extends MetaAction {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=";

    @Serializable(headTexture = HEAD, fromList = "getSkills", fromListMapper = "skillsMapper", description = "gui.action.meta.skill.name")
    private String functionName;

    public FunctionAction() {
        this(TARGET_DEFAULT, "");
    }

    public FunctionAction(boolean target, String functionName) {
        super(target);
        this.functionName = functionName;
    }

    public FunctionAction(Map<String, Object> map) {
        super(map);
        String oldSkillName = (String) map.get("skillName");
        if (oldSkillName != null) {
            this.functionName = oldSkillName;
        } else {
            this.functionName = (String) map.getOrDefault("functionName", "");
        }
    }

    public static List<Object> getSkills(ModelPath<?> path) {
        return CubesCocktail.getInstance().getFunctionManager().getExecutableSkills().stream().
                map(Function::getName).collect(Collectors.toList());
    }

    public static java.util.function.Function<Object, ItemStack> skillsMapper() {
        return obj -> {
            String skill = (String) obj;
            return ItemUtils.getNamedItem(CubesCocktail.getInstance().getFunctionManager().getFunctionByName(skill).getItem().
                    getWrapped().clone(), MessageUtils.color("&6&l" + skill), Lists.newArrayList());
        };
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        Function skill = CubesCocktail.getInstance().getFunctionManager().getFunctionByName(functionName);
        if (skill != null) {
            skill.getActions().forEach(action -> action.execute(target, source, args));
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAILURE;
    }

    @Override
    public Action clone() {
        return new FunctionAction(TARGET_DEFAULT, functionName);
    }

    @Override
    public ItemStack targetItem() {
        return null;
    }

    @Override
    public String getName() {
        return "&6&lSkill name: &c" + functionName;
    }

    @Override
    public ItemStack getGUIItem() {
        ItemStack item = super.getGUIItem();
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();
        lore.add(3 + WrapperAction.WRAPPERS_MAP.size(), MessageUtils.color("&6&l- [9] &eto open the function GUI"));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void changeSkillName(String oldName, String newName) {
        if (this.functionName.equals(oldName)) {
            this.functionName = newName;
        }
    }

    @Override
    public Set<Function> getUsedSavedSkills() {
        return Sets.newHashSet(CubesCocktail.getInstance().getFunctionManager().getFunctionByName(functionName));
    }
}
