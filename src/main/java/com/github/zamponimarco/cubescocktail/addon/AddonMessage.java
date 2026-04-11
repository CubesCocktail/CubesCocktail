package com.github.zamponimarco.cubescocktail.addon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AddonMessage<T> {

    private String command;
    private List<Object> arguments;

}
