package com.github.zamponimarco.cubescocktail.action.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.hook.VaultHook;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;

@Enumerable.Child()
@Enumerable.Displayable(name = "&c&lMoney", description = "gui.action.entity.money.description", condition = "vaultEnabled", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWUyNWRiZTQ3NjY3ZDBjZTIzMWJhYTIyM2RlZTk1M2JiZmM5Njk2MDk3Mjc5ZDcyMzcwM2QyY2MzMzk3NjQ5ZSJ9fX0=")
public class MoneyAction extends EntityAction {

    private static final NumericValue MONEY_DEFAULT = new NumericValue(10);
    private static final boolean GIVE_DEFAULT = true;

    private static final String MONEY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWUyNWRiZTQ3NjY3ZDBjZTIzMWJhYTIyM2RlZTk1M2JiZmM5Njk2MDk3Mjc5ZDcyMzcwM2QyY2MzMzk3NjQ5ZSJ9fX0";
    private static final String GIVE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBhN2I5NGM0ZTU4MWI2OTkxNTlkNDg4NDZlYzA5MTM5MjUwNjIzN2M4OWE5N2M5MzI0OGEwZDhhYmM5MTZkNSJ9fX0";

    @Serializable(headTexture = MONEY_HEAD, description = "gui.action.entity.money.money")
    @Serializable.Optional(defaultValue = "MONEY_DEFAULT")
    private NumericValue money;
    @Serializable(headTexture = GIVE_HEAD, description = "gui.action.entity.money.give")
    @Serializable.Optional(defaultValue = "GIVE_DEFAULT")
    private boolean give;

    public MoneyAction() {
        this(TARGET_DEFAULT, MONEY_DEFAULT.clone(), GIVE_DEFAULT);
    }

    public MoneyAction(boolean target, NumericValue money, boolean give) {
        super(target);
        this.money = money;
        this.give = give;
    }

    public MoneyAction(Map<String, Object> map) {
        super(map);
        this.money = (NumericValue) map.getOrDefault("money", MONEY_DEFAULT);
        this.give = (boolean) map.getOrDefault("give", GIVE_DEFAULT);
    }

    public static boolean vaultEnabled(ModelPath path) {
        return CubesCocktail.getInstance().getVaultHook().isEnabled();
    }

    @Override
    public ActionResult execute(ActionTarget target, ActionSource source, ActionArgument args) {
        Entity entity = getEntity(target, source);
        if (entity == null) {
            return ActionResult.FAILURE;
        }

        if (!(entity instanceof Player)) {
            return ActionResult.FAILURE;
        }

        VaultHook vault = CubesCocktail.getInstance().getVaultHook();

        if (!vault.isEnabled()) {
            return ActionResult.FAILURE;
        }

        Player player = ((Player) entity).getPlayer();
        double realValue = money.getRealValue(target, source);
        if (give) {
            vault.giveMoney(player, realValue);
        } else {
            vault.takeMoney(player, realValue);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public Action clone() {
        return new MoneyAction(target, money.clone(), give);
    }

    @Override
    public String getName() {
        return String.format("&6&l%s Money: &c%s", give ? "Give" : "Take", money.getName());
    }
}
