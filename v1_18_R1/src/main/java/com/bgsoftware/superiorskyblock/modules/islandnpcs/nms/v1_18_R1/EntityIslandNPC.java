package com.bgsoftware.superiorskyblock.modules.islandnpcs.nms.v1_18_R1;

import com.bgsoftware.superiorskyblock.modules.islandnpcs.npc.IslandNPC;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class EntityIslandNPC extends EntityPlayer implements IslandNPC {

    public EntityIslandNPC(MinecraftServer server, WorldServer world, GameProfile profile) {
        super(server, world, profile);
    }

    @Override
    public void showToPlayer(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(this);
        entityPlayer.b.a(packetPlayOutNamedEntitySpawn);
    }

    @Override
    public void hideFromPlayer(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(this.getEntityId());
        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, this);
        entityPlayer.b.a(packetPlayOutEntityDestroy);
        entityPlayer.b.a(packetPlayOutPlayerInfo);
    }

    @Override
    public void setLocation(Location location) {
        this.b(location.getX(), location.getY(), location.getZ());
    }

    private int getEntityId() {
        return this.ae();
    }

}
