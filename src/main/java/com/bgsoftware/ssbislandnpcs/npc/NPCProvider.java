package com.bgsoftware.ssbislandnpcs.npc;

import com.bgsoftware.superiorskyblock.api.island.Island;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public interface NPCProvider {

    IslandNPC createNPC(Location location, Island island, EntityType entityType, String name);

}
