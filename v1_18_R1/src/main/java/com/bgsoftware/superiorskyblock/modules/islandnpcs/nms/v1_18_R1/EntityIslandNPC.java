package com.bgsoftware.superiorskyblock.modules.islandnpcs.nms.v1_18_R1;

import com.bgsoftware.superiorskyblock.modules.islandnpcs.npc.IslandNPC;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;

public final class EntityIslandNPC extends EntityPlayer implements IslandNPC {

    public EntityIslandNPC(MinecraftServer server, WorldServer world, GameProfile profile) {
        super(server, world, profile);
    }

}
