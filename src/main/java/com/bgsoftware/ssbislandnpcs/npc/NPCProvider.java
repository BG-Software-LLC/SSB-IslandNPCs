package com.bgsoftware.ssbislandnpcs.npc;

import com.bgsoftware.ssbislandnpcs.config.NPCMetadata;
import com.bgsoftware.superiorskyblock.api.island.Island;

public interface NPCProvider {

    IslandNPC createNPC(Island island, NPCMetadata metadata);

    void loadNPCs();

    void unloadNPCs();

}
