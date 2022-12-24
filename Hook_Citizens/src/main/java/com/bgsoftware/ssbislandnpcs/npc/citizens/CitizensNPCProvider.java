package com.bgsoftware.ssbislandnpcs.npc.citizens;

import com.bgsoftware.common.reflection.ReflectMethod;
import com.bgsoftware.ssbislandnpcs.SSBIslandNPCs;
import com.bgsoftware.ssbislandnpcs.npc.IslandNPC;
import com.bgsoftware.ssbislandnpcs.npc.NPCProvider;
import com.bgsoftware.superiorskyblock.api.island.Island;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CitizensNPCProvider implements NPCProvider {

    private static final ReflectMethod<Integer> GENERATE_UNIQUE_ID = new ReflectMethod<>(CitizensNPCRegistry.class, "generateUniqueId");

    private final SSBIslandNPCs module;
    private boolean loadedData = false;

    public CitizensNPCProvider(SSBIslandNPCs module) {
        this.module = module;
        Bukkit.getPluginManager().registerEvents(new ListenerImpl(), module.getPlugin());
        Bukkit.getScheduler().runTaskLater(module.getPlugin(), () -> {
            if (!loadedData)
                loadNPCs();
        }, 5L);
    }

    @Override
    public IslandNPC createNPC(Location location, Island island, EntityType entityType, String name) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        NPC npc = npcRegistry.createNPC(entityType, island.getUniqueId(), GENERATE_UNIQUE_ID.invoke(npcRegistry), name);
        npc.spawn(location);
        return new CitizensIslandNPC(npc);
    }

    private void loadNPCs() {
        module.getPlugin().getGrid().getIslands().forEach(island -> {
            NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(island.getUniqueId());

            if (npc == null)
                return;

            npc.spawn(island.getCenter(module.getPlugin().getSettings().getWorlds().getDefaultWorld()));
            module.getNPCHandler().loadNPC(island, new CitizensIslandNPC(npc));
        });
    }

    private class ListenerImpl implements Listener {

        @EventHandler
        public void onEnable(CitizensEnableEvent event) {
            loadedData = true;
            loadNPCs();
        }

    }

}
