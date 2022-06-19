package com.github.zamponimarco.cubescocktail.action.location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.github.zamponimarco.cubescocktail.CubesCocktail;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.args.ActionArgument;
import com.github.zamponimarco.cubescocktail.action.source.ActionSource;
import com.github.zamponimarco.cubescocktail.action.targeter.ActionTarget;
import com.github.zamponimarco.cubescocktail.condition.AlwaysTrueCondition;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.value.NumericValue;
import com.google.common.collect.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lHighlight Block", description = "gui.action.location.highlight.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZjNmVjM2I3NTM1NGI0OTIyMmE4OWM2NjNjNGFjYWQ1MjY0ZmI5NzdjYWUyNmYwYjU0ODNhNTk5YzQ2NCJ9fX0=")
public class HighlightBlockAction extends PacketAction {

    private static final String COLOR_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmVjNTk2M2UxZjc4ZjJmMDU5NDNmNGRkMzIyMjQ2NjEzNzRjMjIwZWNmZGUxZTU0NzU0ZjVlZTFlNTU1ZGQifX19";
    private static final String TICKS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlOGNmZjc1ZjdkNDMzMjYwYWYxZWNiMmY3NzNiNGJjMzgxZDk1MWRlNGUyZWI2NjE0MjM3NzlhNTkwZTcyYiJ9fX0=";

    @Serializable(headTexture = COLOR_HEAD, description = "gui.action.location.highlight.color", fromList = "getColors",
            fromListMapper = "colorsMapper")
    private ChatColor color;
    @Serializable(headTexture = TICKS_HEAD, description = "gui.action.location.highlight.ticks",
            additionalDescription = {"gui.additional-tooltips.value"})
    @Serializable.Number(minValue = 0, scale = 1)
    private NumericValue ticks;

    public HighlightBlockAction() {
        this(TARGET_DEFAULT, new AlwaysTrueCondition(), ChatColor.WHITE, new NumericValue(100));
    }

    public HighlightBlockAction(boolean target, Condition condition, ChatColor color, NumericValue ticks) {
        super(target, condition);
        this.color = color;
        this.ticks = ticks;
    }

    public HighlightBlockAction(Map<String, Object> map) {
        super(map);
        this.color = ChatColor.values()[(int) map.getOrDefault("color", 0)];
        this.ticks = (NumericValue) map.getOrDefault("ticks", new NumericValue(100));
    }

    public static List<Object> getColors(ModelPath<?> path) {
        return Arrays.stream(ChatColor.values()).filter(ChatColor::isColor).collect(Collectors.toList());
    }

    public static Function<Object, ItemStack> colorsMapper() {
        return obj -> ItemUtils.getNamedItem(Libs.getVersionWrapper().skullFromValue(COLOR_HEAD), MessageUtils.color(((ChatColor) obj).name()),
                Lists.newArrayList());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("ticks", ticks);
        map.put("color", color.ordinal());
        return map;
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

        selectedPlayers(location, source).forEach(player ->
                sendHighlightBlockPacket(player, location, color, ticks.getRealValue(target, source).intValue()));
        return ActionResult.SUCCESS;
    }

    private void sendHighlightBlockPacket(Player player, Location location, ChatColor color, int ticks) {
        int eid = new Random().nextInt();
        UUID id = UUID.randomUUID();
        String team = RandomStringUtils.random(16);

        Libs.getProtocolWrapper().sendSpawnEntityPacket(player, location.getBlock().getLocation().
                clone().add(.5, 0, .5), eid, id);
        Libs.getProtocolWrapper().sendEntityMetadataPacket(player, eid, (byte) 0x60);
        Libs.getProtocolWrapper().sendCreateTeamPacket(player, color, id, team);

        Bukkit.getScheduler().runTaskLater(CubesCocktail.getInstance(), () -> {
            Libs.getProtocolWrapper().sendDestroyEntityPacket(player, eid);
            Libs.getProtocolWrapper().sendDestroyTeamPacket(player, team);
        }, ticks);
    }

    @Override
    public String getName() {
        return "&c&lHighlight block";
    }

    @Override
    public Action clone() {
        return new HighlightBlockAction(target, condition.clone(), color, ticks.clone());
    }
}
