package com.github.zamponimarco.cubescocktail.key;

import com.github.jummes.libs.model.Model;

public interface Key extends Model, Cloneable {

    Key clone();

    String getName();
}
