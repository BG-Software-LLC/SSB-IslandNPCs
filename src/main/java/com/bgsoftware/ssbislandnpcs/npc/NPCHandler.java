package com.bgsoftware.ssbislandnpcs.npc;

import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.ssbislandnpcs.SSBIslandNPCs;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class NPCHandler {

    private final Map<UUID, IslandNPC> cachedNPCs = new HashMap<>();

    private SSBIslandNPCs module;

    public NPCHandler(SSBIslandNPCs module) {
        this.module = module;
    }

    public Optional<IslandNPC> getNPC(Island island) {
        return Optional.ofNullable(this.cachedNPCs.get(island.getUniqueId()));
    }

    public IslandNPC createNPC(Island island, Location location) {
        IslandNPC islandNPC = module.getNMSAdapter().createNPCEntity(location);
        this.cachedNPCs.put(island.getUniqueId(), islandNPC);
        return islandNPC;
    }

    public void removeNPC(Island island) {
        this.cachedNPCs.remove(island.getUniqueId());
    }

}
