package com.github.zamponimarco.cubescocktail;

import com.github.jummes.libs.command.PluginCommandExecutor;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.database.factory.DatabaseFactory;
import com.github.jummes.libs.gui.FieldInventoryHolderFactory;
import com.github.zamponimarco.cubescocktail.action.Action;
import com.github.zamponimarco.cubescocktail.action.group.ActionGroup;
import com.github.zamponimarco.cubescocktail.action.meta.RandomAction;
import com.github.zamponimarco.cubescocktail.addon.AddonManager;
import com.github.zamponimarco.cubescocktail.ai.goal.GoalSelector;
import com.github.zamponimarco.cubescocktail.ai.target.TargetSelector;
import com.github.zamponimarco.cubescocktail.area.Area;
import com.github.zamponimarco.cubescocktail.command.AddonsListCommand;
import com.github.zamponimarco.cubescocktail.command.FunctionsListCommand;
import com.github.zamponimarco.cubescocktail.command.HelpCommand;
import com.github.zamponimarco.cubescocktail.command.PlaceholderListCommand;
import com.github.zamponimarco.cubescocktail.condition.Condition;
import com.github.zamponimarco.cubescocktail.cooldown.CooldownOptions;
import com.github.zamponimarco.cubescocktail.cooldown.bar.CooldownBar;
import com.github.zamponimarco.cubescocktail.database.CompressedYamlDatabase;
import com.github.zamponimarco.cubescocktail.entity.Entity;
import com.github.zamponimarco.cubescocktail.entity.sorter.EntitySorter;
import com.github.zamponimarco.cubescocktail.function.AbstractFunction;
import com.github.zamponimarco.cubescocktail.function.Function;
import com.github.zamponimarco.cubescocktail.gui.ActionCollectionInventoryHolder;
import com.github.zamponimarco.cubescocktail.gui.SavedSkillCollectionInventoryHolder;
import com.github.zamponimarco.cubescocktail.hook.VaultHook;
import com.github.zamponimarco.cubescocktail.hook.WorldGuardHook;
import com.github.zamponimarco.cubescocktail.listener.PlayerItemListener;
import com.github.zamponimarco.cubescocktail.listener.ProjectileListener;
import com.github.zamponimarco.cubescocktail.loot.DropTable;
import com.github.zamponimarco.cubescocktail.loot.drop.Drop;
import com.github.zamponimarco.cubescocktail.manager.CooldownManager;
import com.github.zamponimarco.cubescocktail.manager.FunctionManager;
import com.github.zamponimarco.cubescocktail.manager.SavedPlaceholderManager;
import com.github.zamponimarco.cubescocktail.manager.TimerManager;
import com.github.zamponimarco.cubescocktail.math.Vector;
import com.github.zamponimarco.cubescocktail.placeholder.Placeholder;
import com.github.zamponimarco.cubescocktail.savedplaceholder.SavedPlaceholder;
import com.github.zamponimarco.cubescocktail.slot.EquipmentSlot;
import com.github.zamponimarco.cubescocktail.slot.NumberedSlot;
import com.github.zamponimarco.cubescocktail.slot.Slot;
import com.github.zamponimarco.cubescocktail.source.Source;
import com.github.zamponimarco.cubescocktail.target.Target;
import com.github.zamponimarco.cubescocktail.trigger.*;
import com.github.zamponimarco.cubescocktail.value.*;
import com.github.zamponimarco.cubescocktail.wrapper.ProtocolWrapper;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class CubesCocktail extends JavaPlugin {

    private static final String CONFIG_VERSION = "0.1";
    private static final String PACKAGE_PREFIX = "com.github.zamponimarco.cubescocktail.wrapper.ProtocolWrapper_";

    static {
        Libs.registerSerializables();

        ConfigurationSerialization.registerClass(Source.class);
        ConfigurationSerialization.registerClass(Target.class);
        ConfigurationSerialization.registerClass(Trigger.class);
        ConfigurationSerialization.registerClass(ActionGroup.class);

        ConfigurationSerialization.registerClass(Action.class);
        ConfigurationSerialization.registerClass(RandomAction.RandomActionEntry.class);

        ConfigurationSerialization.registerClass(Entity.class);

        ConfigurationSerialization.registerClass(Function.class);

        ConfigurationSerialization.registerClass(Condition.class);

        ConfigurationSerialization.registerClass(Placeholder.class);

        ConfigurationSerialization.registerClass(Value.class);
        ConfigurationSerialization.registerClass(NumericValue.class);
        ConfigurationSerialization.registerClass(StringValue.class);
        ConfigurationSerialization.registerClass(MaterialValue.class);
        ConfigurationSerialization.registerClass(VectorValue.class);

        ConfigurationSerialization.registerClass(SavedPlaceholder.class);

        ConfigurationSerialization.registerClass(Vector.class);

        ConfigurationSerialization.registerClass(Area.class);

        ConfigurationSerialization.registerClass(EntitySorter.class);

        ConfigurationSerialization.registerClass(CooldownBar.class);
        ConfigurationSerialization.registerClass(CooldownOptions.class);

        ConfigurationSerialization.registerClass(DropTable.class);
        ConfigurationSerialization.registerClass(Drop.class);

        ConfigurationSerialization.registerClass(TargetSelector.class);
        ConfigurationSerialization.registerClass(GoalSelector.class);

        legacyTransition();

    }

    private FunctionManager functionManager;
    private SavedPlaceholderManager savedPlaceholderManager;
    private AddonManager addonManager;
    private CooldownManager cooldownManager;
    private TimerManager timerManager;

    private WorldGuardHook worldGuardHook;
    private VaultHook vaultHook;
    private PluginCommandExecutor commandExecutor;

    private ProtocolWrapper protocolWrapper;

    public static CubesCocktail getInstance() {
        return getPlugin(CubesCocktail.class);
    }

    @Deprecated
    private static void legacyTransition() {
        ConfigurationSerialization.registerClass(BlockBreakTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.BlockBreakSkill");
        ConfigurationSerialization.registerClass(BlockPlaceTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.BlockPlaceSkill");
        ConfigurationSerialization.registerClass(DamageEntityTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.DamageEntitySkill");
        ConfigurationSerialization.registerClass(EntityCrossbowLoadTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.EntityCrossbowLoadSkill");
        ConfigurationSerialization.registerClass(EntityEquipArmorTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.EntityEquipArmorSkill");
        ConfigurationSerialization.registerClass(EntityFishTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.EntityFishSkill");
        ConfigurationSerialization.registerClass(EntityItemConsumeTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.EntityItemConsumeSkill");
        ConfigurationSerialization.registerClass(EntityJumpTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.EntityJumpSkill");
        ConfigurationSerialization.registerClass(EntityShootProjectileTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.EntityShootProjectileSkill");
        ConfigurationSerialization.registerClass(EntitySneakTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.EntitySneakSkill");
        ConfigurationSerialization.registerClass(EntitySprintTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.EntitySprintSprint");
        ConfigurationSerialization.registerClass(HitEntityTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.HitEntitySkill");
        ConfigurationSerialization.registerClass(LeftClickTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.LeftClickSkill");
        ConfigurationSerialization.registerClass(RightClickTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.RightClickSkill");
        ConfigurationSerialization.registerClass(TimerTrigger.class,
                "com.github.zamponimarco.itemdrink.skill.TimerSkill");
        ConfigurationSerialization.registerClass(Slot.class,
                "com.github.zamponimarco.itemdrink.skill.Skill$Slot");
        ConfigurationSerialization.registerClass(EquipmentSlot.class,
                "com.github.zamponimarco.itemdrink.skill.Skill$EquipmentSlot");
        ConfigurationSerialization.registerClass(NumberedSlot.class,
                "com.github.zamponimarco.itemdrink.skill.Skill$NumberedSlot");
        ConfigurationSerialization.registerClass(CooldownOptions.class,
                "com.github.zamponimarco.itemdrink.skill.CooldownSkill$CooldownOptions");
        ConfigurationSerialization.registerClass(EntitySpawnTrigger.class);
    }

    @Override
    public void onEnable() {
        setUpConfig();
        setUpLibrary();
        setUpCommands();
        setUpData();
        setUpHooks();
        getServer().getPluginManager().registerEvents(new ProjectileListener(), CubesCocktail.getInstance());
        getServer().getPluginManager().registerEvents(new PlayerItemListener(), CubesCocktail.getInstance());
    }

    private void setUpConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            saveDefaultConfig();
        }

        if (!Objects.equals(getConfig().getString("version"), CONFIG_VERSION)) {
            getLogger().info("config.yml has changed. Old config is stored inside config-"
                    + getConfig().getString("version") + ".yml");
            File backupFolder = new File(getDataFolder(), "backup");
            if (!backupFolder.exists()) {
                backupFolder.mkdir();
            }
            File outputFile = new File(backupFolder, "config-" + getConfig().getString("version") + ".yml");
            FileUtil.copy(configFile, outputFile);
            configFile.delete();
            saveDefaultConfig();
        }

        Slot.additionalSlots.addAll(getConfig().getIntegerList("additional-slots"));
        Slot.slots.addAll(Slot.additionalSlots.stream().map(NumberedSlot::new).collect(Collectors.toList()));
    }

    @Override
    public void onDisable() {
        addonManager.unloadAddons();
    }

    private void setUpLibrary() {
        DatabaseFactory.getMap().put("comp", CompressedYamlDatabase.class);
        FieldInventoryHolderFactory.collectionGUIMap.put(Action.class, ActionCollectionInventoryHolder.class);
        FieldInventoryHolderFactory.collectionGUIMap.put(AbstractFunction.class, SavedSkillCollectionInventoryHolder.class);
        Libs.initializeLibrary(this);
        Libs.getLocale().registerLocaleFiles(this, Lists.newArrayList("en-US"), "en-US");
    }

    private void setUpData() {
        this.functionManager = new FunctionManager(AbstractFunction.class, "comp", this);
        this.savedPlaceholderManager = new SavedPlaceholderManager(SavedPlaceholder.class, "comp", this);
        this.cooldownManager = new CooldownManager();
        this.addonManager = new AddonManager();
        this.timerManager = new TimerManager();
    }

    private void setUpHooks() {
        worldGuardHook = new WorldGuardHook();
        vaultHook = new VaultHook();
        String serverVersion = getServer().getClass().getPackage().getName();
        String version = serverVersion.substring(serverVersion.lastIndexOf('.') + 1);
        try {
            protocolWrapper = (ProtocolWrapper) Class.forName(PACKAGE_PREFIX + version).getConstructor().newInstance();
        } catch (Exception e) {
            getLogger().severe("This plugin is not supported in your server version, please check the " +
                    "spigot page to find which versions are supported.");
            getPluginLoader().disablePlugin(this);
        }
    }

    private void setUpCommands() {
        commandExecutor = new PluginCommandExecutor("help", new HelpCommand());
        commandExecutor.registerCommand("functions", new FunctionsListCommand());
        commandExecutor.registerCommand("placeholders", new PlaceholderListCommand());
        commandExecutor.registerCommand("addons", new AddonsListCommand());
        getCommand("cc").setExecutor(commandExecutor);
    }
}
