package com.github.zamponimarco.cubescocktail.cooldown;

import com.github.zamponimarco.cubescocktail.key.Key;
import lombok.*;

@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CooldownInfo {
    @EqualsAndHashCode.Include
    private Key key;
    private int remainingCooldown;
}
