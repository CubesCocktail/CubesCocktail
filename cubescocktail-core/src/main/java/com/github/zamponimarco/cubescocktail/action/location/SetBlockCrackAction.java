package com.github.zamponimarco.cubescocktail.action.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.AlwaysTrueCondition;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Random;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lCrack Block Action", description = "gui.action.location.crack.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzlhNDZiMmFiMzJmMjE2ZTJkOTIyYzcyMzdiYTIzMTlmOTFiNzFmYTI0ZmU0NTFhZDJjYTgxNDIzZWEzYzgifX19")
public class SetBlockCrackAction extends PacketAction {

    private static final String CRACK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzlhNDZiMmFiMzJmMjE2ZTJkOTIyYzcyMzdiYTIzMTlmOTFiNzFmYTI0ZmU0NTFhZDJjYTgxNDIzZWEzYzgifX19";
    private static final String TICKS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlOGNmZjc1ZjdkNDMzMjYwYWYxZWNiMmY3NzNiNGJjMzgxZDk1MWRlNGUyZWI2NjE0MjM3NzlhNTkwZTcyYiJ9fX0=";

    @Serializable(headTexture = CRACK_HEAD, description = "gui.action.location.crack.stage",
            additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, maxValue = 9, scale = 1)
    private NumericValue destroyStage;
    @Serializable(headTexture = TICKS_HEAD, description = "gui.action.location.crack.ticks",
            additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, scale = 1)
    private NumericValue ticks;

    public SetBlockCrackAction() {
        this(TARGET_DEFAULT, new AlwaysTrueCondition(), new NumericValue(0), new NumericValue(100));
    }

    public SetBlockCrackAction(boolean target, Condition condition, NumericValue destroyStage, NumericValue ticks) {
        super(target, condition);
        this.destroyStage = destroyStage;
        this.ticks = ticks;
    }

    public SetBlockCrackAction(Map<String, Object> map) {
        super(map);
        this.destroyStage = (NumericValue) map.getOrDefault("destroyStage", new NumericValue(0));
        this.ticks = (NumericValue) map.getOrDefault("ticks", new NumericValue(100));
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        Location location = getLocation(target, source);

        if (location == null) {
            return ActionResult.FAILURE;
        }

        if (source.getCaster() instanceof Player &&
                !CubesCocktail.getInstance().getWorldGuardHook().
                        canBuild((Player) source.getCaster(), location)) {
            return ActionResult.FAILURE;
        }
        selectedPlayers(location, source).forEach(player -> sendSetBlockCrackPacket(player, location,
                destroyStage.getRealValue(target, source).intValue(), ticks.getRealValue(target, source).intValue()));
        return ActionResult.SUCCESS;
    }

    private void sendSetBlockCrackPacket(Player player, Location location, int stage, int ticks) {
        int randomEid = new Random().nextInt();
        CubesCocktail.getInstance().getProtocolWrapper().sendBlockBreakAnimationPacket(player, location, stage, randomEid);
        Bukkit.getScheduler().runTaskLater(CubesCocktail.getInstance(), () -> {
            CubesCocktail.getInstance().getProtocolWrapper().sendBlockBreakAnimationPacket(player, location, -1, randomEid);
        }, ticks);
    }

    @Override
    public String getName() {
        return "&6&lSet Block Destroy Stage: &c" + destroyStage.getName();
    }

    @Override
    public Action clone() {
        return new SetBlockCrackAction(target, condition.clone(), destroyStage.clone(), ticks.clone());
    }

}
