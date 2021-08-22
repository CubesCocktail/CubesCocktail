package com.github.zamponimarco.cubescocktail.placeholder.material;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.zamponimarco.cubescocktail.placeholder.Placeholder;
import org.bukkit.Material;

import java.util.Map;

@Enumerable.Parent(classArray = {BlockMaterialPlaceholder.class, MaterialFromStringPlaceholder.class})
public abstract class MaterialPlaceholder extends Placeholder<Material> {

    public MaterialPlaceholder(boolean target) {
        super(target);
    }

    public MaterialPlaceholder(Map<String, Object> map) {
        super(map);
    }

    public abstract MaterialPlaceholder clone();
}
