package com.bgsoftware.superiorskyblock.modules.islandnpcs.nms.v1_18_R1.npc;

import com.bgsoftware.superiorskyblock.modules.islandnpcs.npc.IslandNPC;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
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
        NPCNetworkManager networkManager = new NPCNetworkManager(EnumProtocolDirection.b);
        this.b = new NPCPlayerConnection(server, networkManager, this);
    }

    @Override
    public void showToPlayer(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(this);
        entityPlayer.b.a(createAddToTabPacket());
        entityPlayer.b.a(packetPlayOutNamedEntitySpawn);
        entityPlayer.b.a(createRemoveFromTabPacket());
    }

    @Override
    public void hideFromPlayer(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(this.getEntityId());
        entityPlayer.b.a(packetPlayOutEntityDestroy);
    }

    @Override
    public void setLocation(Location location) {
        this.getBukkitEntity().teleport(location);
    }

    private int getEntityId() {
        return this.ae();
    }

    private Packet<?> createAddToTabPacket() {
        return new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, this);
    }

    private Packet<?> createRemoveFromTabPacket() {
        return new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, this);
    }

}
