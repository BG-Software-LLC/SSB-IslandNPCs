package com.bgsoftware.superiorskyblock.modules.islandnpcs.nms;

import com.bgsoftware.superiorskyblock.modules.islandnpcs.npc.IslandNPC;
import org.bukkit.Location;

public interface NMSAdapter {

    IslandNPC createNPCEntity(Location location);

}
