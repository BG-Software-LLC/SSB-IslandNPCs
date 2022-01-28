package com.bgsoftware.superiorskyblock.modules.islandnpcs.nms.v1_18_R1;

import com.bgsoftware.superiorskyblock.modules.islandnpcs.nms.NMSAdapter;
import com.bgsoftware.superiorskyblock.modules.islandnpcs.nms.v1_18_R1.npc.EntityIslandNPC;
import com.bgsoftware.superiorskyblock.modules.islandnpcs.npc.IslandNPC;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;

import java.util.UUID;

public final class NMSAdapterImpl implements NMSAdapter {

    @Override
    public IslandNPC createNPCEntity(World bukkitWorld) {
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer worldServer = ((CraftWorld) bukkitWorld).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "IslandNPC");
        return new EntityIslandNPC(server, worldServer, gameProfile);
    }

}
