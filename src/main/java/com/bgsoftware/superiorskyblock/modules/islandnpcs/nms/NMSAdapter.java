package com.bgsoftware.superiorskyblock.modules.islandnpcs.nms;

import com.bgsoftware.superiorskyblock.modules.islandnpcs.npc.IslandNPC;
import org.bukkit.World;

public interface NMSAdapter {

    IslandNPC createNPCEntity(World bukkitWorld);

}
