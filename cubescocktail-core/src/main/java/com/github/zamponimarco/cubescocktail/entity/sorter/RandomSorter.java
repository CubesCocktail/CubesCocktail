package com.github.zamponimarco.cubescocktail.entity.sorter;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&6&lRandom Sorter", description = "gui.entity.sorter.random.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM5ODg2YTkxNDgxOTM2NDg1OTgzNzAyNGM0YmNmYTg1M2Q4NmJkODJiZTU0MTdkNjhjMDU3Yjg0MzMifX19")
@Getter
@Setter
public class RandomSorter extends EntitySorter {

    public RandomSorter() {
    }

    public RandomSorter(Map<String, Object> map) {
        super(map);
    }

    @Override
    public void sortCollection(List<LivingEntity> list, ActionTarget target, ActionSource source) {
        Collections.shuffle(list);
    }

    @Override
    public EntitySorter clone() {
        return new RandomSorter();
    }
}
