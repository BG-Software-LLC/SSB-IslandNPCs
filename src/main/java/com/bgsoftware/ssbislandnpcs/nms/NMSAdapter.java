package com.bgsoftware.ssbislandnpcs.nms;

import com.bgsoftware.ssbislandnpcs.npc.IslandNPC;
import org.bukkit.Location;

public interface NMSAdapter {

    IslandNPC createNPCEntity(Location location);

}
