package com.bgsoftware.superiorskyblock.modules.islandnpcs.npc;

import com.bgsoftware.superiorskyblock.api.island.Island;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class NPCHandler {

    private final Map<UUID, IslandNPC> cachedNPCs = new HashMap<>();

    public NPCHandler() {

    }

    public Optional<IslandNPC> getNPC(Island island) {
        return Optional.ofNullable(this.cachedNPCs.get(island.getUniqueId()));
    }

    public IslandNPC createNPC(Island island) {
        // TODO: Create IslandNPC
        return null;
    }

    public void removeNPC(Island island) {
        this.cachedNPCs.remove(island.getUniqueId());
    }

}
