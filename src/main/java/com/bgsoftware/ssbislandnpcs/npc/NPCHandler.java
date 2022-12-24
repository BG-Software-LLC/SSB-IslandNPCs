package com.bgsoftware.ssbislandnpcs.npc;

import com.bgsoftware.ssbislandnpcs.SSBIslandNPCs;
import com.bgsoftware.superiorskyblock.api.island.Island;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class NPCHandler {

    private final Map<UUID, IslandNPC> npcsByUUID = new HashMap<>();
    private final List<IslandNPC> npcs = new LinkedList<>();

    private final SSBIslandNPCs module;

    public NPCHandler(SSBIslandNPCs module) {
        this.module = module;
    }

    public Optional<IslandNPC> getNPC(Island island) {
        return Optional.ofNullable(this.npcsByUUID.get(island.getUniqueId()));
    }

    public IslandNPC createNPC(Island island, Location location) {
        IslandNPC islandNPC = module.getNPCProvider().createNPC(location, island, EntityType.PLAYER, "OmerBenGera");
        loadNPC(island, islandNPC);
        return islandNPC;
    }

    public void removeNPC(Island island) {
        IslandNPC islandNPC = this.npcsByUUID.remove(island.getUniqueId());
        if (islandNPC != null) {
            npcs.remove(islandNPC);
            islandNPC.remove();
        }
    }

    public void loadNPC(Island island, IslandNPC islandNPC) {
        this.npcsByUUID.put(island.getUniqueId(), islandNPC);
        this.npcs.add(islandNPC);
    }

    public List<IslandNPC> getNPCs() {
        return Collections.unmodifiableList(this.npcs);
    }

}
