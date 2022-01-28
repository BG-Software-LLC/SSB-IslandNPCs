package com.bgsoftware.superiorskyblock.modules.islandnpcs.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IslandNPC {

    void showToPlayer(Player player);

    void hideFromPlayer(Player player);

    void setLocation(Location location);

}
