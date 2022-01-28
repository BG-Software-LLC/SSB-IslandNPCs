package com.bgsoftware.superiorskyblock.modules.islandnpcs.listeners;

import com.bgsoftware.superiorskyblock.api.events.IslandEnterProtectedEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandSchematicPasteEvent;
import com.bgsoftware.superiorskyblock.modules.islandnpcs.SSBIslandNPCs;
import com.bgsoftware.superiorskyblock.modules.islandnpcs.npc.IslandNPC;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

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

        module.getNPCHandler().removeNPC(event.getIsland());

        IslandNPC islandNPC = module.getNPCHandler().createNPC(event.getIsland());
        islandNPC.setLocation(event.getLocation());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIslandEnterProtected(IslandEnterProtectedEvent event) {
        module.getNPCHandler().getNPC(event.getIsland()).ifPresent(islandNPC ->
                islandNPC.showToPlayer(event.getPlayer().asPlayer()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIslandLeaveProtected(IslandEnterProtectedEvent event) {
        module.getNPCHandler().getNPC(event.getIsland()).ifPresent(islandNPC ->
                islandNPC.hideFromPlayer(event.getPlayer().asPlayer()));
    }

}
