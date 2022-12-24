package com.bgsoftware.ssbislandnpcs.nms.v1_18_R1;

import com.bgsoftware.ssbislandnpcs.nms.NMSAdapter;
import com.bgsoftware.ssbislandnpcs.nms.v1_18_R1.npc.EntityIslandNPC;
import com.bgsoftware.ssbislandnpcs.npc.IslandNPC;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;

import java.util.UUID;

public final class NMSAdapterImpl implements NMSAdapter {

    @Override
    public IslandNPC createNPCEntity(Location location) {
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "IslandNPC");
        EntityIslandNPC islandNPC = new EntityIslandNPC(server, worldServer, gameProfile);
        islandNPC.setLocation(location);
        return islandNPC;
    }

}
