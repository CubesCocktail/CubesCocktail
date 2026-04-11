package com.github.zamponimarco.cubescocktail.placeholder.block;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.constant.Textures;
import com.github.zamponimarco.cubescocktail.math.Vector;
import com.github.zamponimarco.cubescocktail.value.VectorValue;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.Map;

@Enumerable.Child
@Getter
@Setter
public class LocationBlockPlaceholder extends BlockPlaceholder {


    @Serializable(headTexture = Textures.PLACEHOLDER_HEAD, description = "gui.placeholder.string.to-string.placeholder",
            additionalDescription = {"gui.additional-tooltips.value"})
    private VectorValue vector;

    public LocationBlockPlaceholder() {
        this(TARGET_DEFAULT, new VectorValue());
    }

    public LocationBlockPlaceholder(boolean target, VectorValue vector) {
        super(target);
        this.vector = vector;
    }

    public LocationBlockPlaceholder(Map<String, Object> map) {
        super(map);
        try {
            this.vector = (VectorValue) map.getOrDefault("vector", new VectorValue());
        } catch (ClassCastException e) {
            this.vector = new VectorValue((Vector) map.getOrDefault("vector", new Vector()));
        }
    }

    @Override
    public Block computePlaceholder(ActionTarget target, ActionSource source) {
        Location l;
        if (this.target) {
            l = target.getLocation();
        } else {
            l = source.getLocation();
        }

        return l.clone().add(vector.getRealValue(target, source).computeVector(target, source)).getBlock();
    }

    @Override
    public String getName() {
        return String.format("&cBlock &6&l(&c%s&6&l/&c%s&6&l)&c", target ? "Target" : "Source", vector.toString());
    }

    @Override
    public BlockPlaceholder clone() {
        return new LocationBlockPlaceholder(target, vector.clone());
    }
}
