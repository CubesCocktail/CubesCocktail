package com.github.zamponimarco.cubescocktail.wrapper;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ProtocolWrapper_v1_16_R2 implements ProtocolWrapper {

    @Override
    public void sendBlockBreakAnimationPacket(Player p, Location l, int crack, int randomEid) {
        Packet packet = new PacketPlayOutBlockBreakAnimation(randomEid,
                new BlockPosition(l.getBlockX(), l.getBlockY(), l.getBlockZ()), crack);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void sendDestroyTeamPacket(Player p, String team) {
        Packet packet = new PacketPlayOutScoreboardTeam(new ScoreboardTeam(new Scoreboard(), team), 1);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void sendCreateTeamPacket(Player p, ChatColor color, UUID id, String team) {
        ScoreboardTeam scTeam = new ScoreboardTeam(new Scoreboard(), team);
        scTeam.getPlayerNameSet().add(id.toString());
        scTeam.setColor(EnumChatFormat.values()[color.ordinal()]);
        Packet packet = new PacketPlayOutScoreboardTeam(scTeam, 0);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void sendDestroyEntityPacket(Player p, int eid) {
        Packet packet = new PacketPlayOutEntityDestroy(eid);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void sendEntityMetadataPacket(Player p, int eid, byte tags) {
        DataWatcher watcher = new DataWatcher(null);
        watcher.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), tags);
        Packet packet = new PacketPlayOutEntityMetadata(eid, watcher, true);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void sendSpawnEntityPacket(Player p, Location l, int eid, UUID id) {
        Packet packet = new PacketPlayOutSpawnEntity(eid, id, l.getX(), l.getY(), l.getZ(), 0, 0,
                EntityTypes.SHULKER, 0, new Vec3D(0, 0, 0));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}
