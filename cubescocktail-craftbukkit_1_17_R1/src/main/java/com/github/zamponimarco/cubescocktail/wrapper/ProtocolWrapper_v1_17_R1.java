package com.github.zamponimarco.cubescocktail.wrapper;

import net.minecraft.EnumChatFormat;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardTeam;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ProtocolWrapper_v1_17_R1 implements ProtocolWrapper {

    @Override
    public void sendBlockBreakAnimationPacket(Player p, Location l, int crack, int randomEid) {
        Packet packet = new PacketPlayOutBlockBreakAnimation(randomEid,
                new BlockPosition(l.getBlockX(), l.getBlockY(), l.getBlockZ()), crack);
        ((CraftPlayer) p).getHandle().b.sendPacket(packet);
    }

    @Override
    public void sendDestroyTeamPacket(Player p, String team) {
        Packet packet = PacketPlayOutScoreboardTeam.a(new ScoreboardTeam(new Scoreboard(), team));
        ((CraftPlayer) p).getHandle().b.sendPacket(packet);
    }

    @Override
    public void sendCreateTeamPacket(Player p, ChatColor color, UUID id, String team) {
        ScoreboardTeam scTeam = new ScoreboardTeam(new Scoreboard(), team);
        scTeam.getPlayerNameSet().add(id.toString());
        scTeam.setColor(EnumChatFormat.values()[color.ordinal()]);
        PacketPlayOutScoreboardTeam packet = PacketPlayOutScoreboardTeam.a(scTeam, true);
        ((CraftPlayer) p).getHandle().b.sendPacket(packet);
    }

    @Override
    public void sendDestroyEntityPacket(Player p, int eid) {
        Packet packet = new PacketPlayOutEntityDestroy(eid);
        ((CraftPlayer) p).getHandle().b.sendPacket(packet);
    }

    @Override
    public void sendEntityMetadataPacket(Player p, int eid, byte tags) {
        DataWatcher watcher = new DataWatcher(null);
        watcher.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), tags);
        Packet packet = new PacketPlayOutEntityMetadata(eid, watcher, true);
        ((CraftPlayer) p).getHandle().b.sendPacket(packet);
    }

    @Override
    public void sendSpawnEntityPacket(Player p, Location l, int eid, UUID id) {
        Packet packet = new PacketPlayOutSpawnEntity(eid, id, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch(),
                EntityTypes.ay, 0, new Vec3D(0, 0, 0));
        ((CraftPlayer) p).getHandle().b.sendPacket(packet);
    }
}
