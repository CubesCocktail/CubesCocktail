package com.github.zamponimarco.cubescocktail.timer;

import com.github.zamponimarco.cubescocktail.key.Key;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TimerInfo {
    @EqualsAndHashCode.Include
    private Key key;
    private int task;
}