package com.bgsoftware.ssbislandnpcs.npc.citizens;

import com.bgsoftware.ssbislandnpcs.npc.IslandNPC;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CitizensIslandNPC implements IslandNPC {

    private final NPC handle;

    public CitizensIslandNPC(NPC handle) {
        this.handle = handle;
    }

    @Override
    public void showToPlayer(Player player) {
        // Do nothing, Citizens should handle it.
    }

    @Override
    public void hideFromPlayer(Player player) {
        // Do nothing, Citizens should handle it.
    }

    @Override
    public void setLocation(Location location) {
        this.handle.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @Override
    public Location getLocation() {
        return this.handle.getStoredLocation();
    }

    @Override
    public void remove() {
        this.handle.destroy();
    }

    @Override
    public void despawn() {
        if (this.handle.isSpawned())
            this.handle.despawn(DespawnReason.REMOVAL);
    }
}
