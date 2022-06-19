package com.github.zamponimarco.cubescocktail.cooldown;

import lombok.*;

@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CooldownInfo {
    @EqualsAndHashCode.Include
    private Object key;
    private int remainingCooldown;
}
