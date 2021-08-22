package com.github.zamponimarco.cubescocktail.manager;

import com.github.jummes.libs.model.ModelManager;
import com.github.zamponimarco.cubescocktail.function.AbstractFunction;
import com.github.zamponimarco.cubescocktail.function.Function;
import com.github.zamponimarco.cubescocktail.function.FunctionFolder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

@Getter
public class FunctionManager extends ModelManager<AbstractFunction> {

    private final List<AbstractFunction> functions;

    public FunctionManager(Class<AbstractFunction> classObject, String databaseType, JavaPlugin plugin) {
        super(classObject, databaseType, plugin, ImmutableMap.of("name", "function"));
        this.functions = database.loadObjects();
    }

    public List<Function> getExecutableSkills() {
        return functions.stream().reduce(Lists.newArrayList(), (list, skill) -> {
            list.addAll(skill.getExecutableFunctions());
            return list;
        }, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });
    }

    public AbstractFunction getAbstractFunctionByName(String name) {
        Function skill = getFunctionByName(name);
        if (skill != null) {
            return skill;
        }
        return getFolderByName(name);
    }

    public FunctionFolder getFolderByName(String name) {
        return (FunctionFolder) functions.stream().filter(abstractSavedSkill -> abstractSavedSkill instanceof FunctionFolder &&
                abstractSavedSkill.getName().equals(name)).findFirst().orElse(null);
    }

    public Function getFunctionByName(String name) {
        return functions.stream().map(abstractSkill -> abstractSkill.getByName(name)).filter(Objects::nonNull).findFirst().
                orElse(null);
    }

    public AbstractFunction getTopFunctionByName(String name) {
        return functions.stream().filter(skill -> skill.getByName(name) != null).findFirst().orElse(null);
    }

    public void addFunction(AbstractFunction skill) {
        functions.add(skill);
        saveModel(skill);
    }
}
