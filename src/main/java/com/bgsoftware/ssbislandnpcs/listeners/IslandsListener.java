package com.bgsoftware.ssbislandnpcs.listeners;

import com.bgsoftware.ssbislandnpcs.SSBIslandNPCs;
import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandEnterProtectedEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandSchematicPasteEvent;
import org.bukkit.Bukkit;
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

        module.getNPCHandler().createNPC(event.getIsland(), event.getLocation().clone().add(0.5, 1, 0.5));
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

}
