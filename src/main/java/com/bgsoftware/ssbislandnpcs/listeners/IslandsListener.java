package com.bgsoftware.ssbislandnpcs.listeners;

import com.bgsoftware.ssbislandnpcs.SSBIslandNPCs;
import com.bgsoftware.ssbislandnpcs.config.NPCMetadata;
import com.bgsoftware.ssbislandnpcs.npc.IslandNPC;
import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandEnterProtectedEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandSchematicPasteEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.Locale;

public final class IslandsListener implements Listener {

    private final SSBIslandNPCs module;

    public IslandsListener(SSBIslandNPCs module) {
        this.module = module;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIslandSchematicPaste(IslandSchematicPasteEvent event) {
        World schematicWorld = event.getLocation().getWorld();
        if (schematicWorld == null)
            return;

        if (module.getPlugin().getSettings().getWorlds().getDefaultWorld() != schematicWorld.getEnvironment())
            return;

        NPCMetadata npcMetadata = module.getSettings().schematics.get(event.getSchematic().toLowerCase(Locale.ENGLISH));

        if (npcMetadata == null)
            return;

        module.getNPCHandler().removeNPC(event.getIsland());

        module.getNPCHandler().createNPC(event.getIsland(), npcMetadata);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIslandEnterProtected(IslandEnterProtectedEvent event) {
        module.getNPCHandler().getNPC(event.getIsland()).ifPresent(islandNPC -> {
            Bukkit.getScheduler().runTaskLater(module.getJavaPlugin(),
                    () -> islandNPC.showToPlayer(event.getPlayer().asPlayer()), 2L);
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIslandLeaveProtected(IslandEnterProtectedEvent event) {
        module.getNPCHandler().getNPC(event.getIsland()).ifPresent(islandNPC ->
                islandNPC.hideFromPlayer(event.getPlayer().asPlayer()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIslandDisband(IslandDisbandEvent event) {
        module.getNPCHandler().removeNPC(event.getIsland());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIslandChunkLoad(ChunkLoadEvent event) {
        Island island = module.getPlugin().getGrid().getIslandAt(event.getChunk());

        if (island == null)
            return;

        IslandNPC existingNPC = module.getNPCHandler().getNPC(island).orElse(null);

        if (existingNPC != null)
            return;

        NPCMetadata npcMetadata = module.getSettings().schematics.get(island.getSchematicName().toLowerCase(Locale.ENGLISH));

        if (npcMetadata == null)
            return;

        module.getNPCHandler().createNPC(island, npcMetadata);
    }

}
