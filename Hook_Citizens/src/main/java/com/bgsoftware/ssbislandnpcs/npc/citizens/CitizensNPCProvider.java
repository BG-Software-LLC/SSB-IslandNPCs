package com.bgsoftware.ssbislandnpcs.npc.citizens;

import com.bgsoftware.ssbislandnpcs.SSBIslandNPCs;
import com.bgsoftware.ssbislandnpcs.config.NPCMetadata;
import com.bgsoftware.ssbislandnpcs.config.OnClickAction;
import com.bgsoftware.ssbislandnpcs.npc.IslandNPC;
import com.bgsoftware.ssbislandnpcs.npc.NPCProvider;
import com.bgsoftware.superiorskyblock.api.island.Island;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.HologramTrait;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CitizensNPCProvider implements NPCProvider {

    private static final Pattern OWNER_PLACEHOLDER_PATTERN = Pattern.compile("%owner%");
    private static final Pattern PLAYER_PLACEHOLDER_PATTERN = Pattern.compile("%player%");
    private static final String NPC_METADATA_KEY = "SSBIslandsNPC_Metadata";

    private final SSBIslandNPCs module;
    private boolean canLoadData = false;

    private final NPCDataStore dataStore;
    private final NPCRegistry npcRegistry;

    public CitizensNPCProvider(SSBIslandNPCs module) {
        this.module = module;
        Bukkit.getPluginManager().registerEvents(new ListenerImpl(), module.getPlugin());
        Bukkit.getScheduler().runTaskLater(module.getPlugin(), () -> {
            if (!canLoadData) {
                canLoadData = true;
                loadNPCs();
            }
        }, 5L);

        // We want our NPCs to not be saved into disk.
        this.dataStore = new MemoryNPCDataStore();
        this.npcRegistry = CitizensAPI.createNamedNPCRegistry("SSBIslandsNPC", this.dataStore);
    }


    @Override
    public IslandNPC createNPC(Island island, NPCMetadata metadata) {
        String npcName = metadata.npcName;
        Matcher ownerPlaceholderMatcher = OWNER_PLACEHOLDER_PATTERN.matcher(npcName);
        if (ownerPlaceholderMatcher.find())
            npcName = ownerPlaceholderMatcher.replaceAll(island.getOwner().getName());

        NPC npc = this.npcRegistry.createNPC(metadata.npcType, island.getUniqueId(),
                this.dataStore.createUniqueNPCId(this.npcRegistry), npcName);

        npc.data().set(NPC_METADATA_KEY, metadata);

        if (!metadata.displayName.isEmpty()) {
            npc.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, false);
            HologramTrait trait = npc.getOrAddTrait(HologramTrait.class);
            metadata.displayName.forEach(trait::addLine);
        }

        if (metadata.lookAtNearby) {
            LookClose lookClose = npc.getOrAddTrait(LookClose.class);
            lookClose.toggle();
        }

        Location islandLocation = island.getCenter(module.getPlugin().getSettings().getWorlds().getDefaultWorld());
        npc.spawn(metadata.spawnOffset.applyToLocation(islandLocation));

        return new CitizensIslandNPC(npc);
    }

    @Override
    public void loadNPCs() {
        if (!canLoadData)
            return;

        // We don't actually load the data for islands unless their chunk is loaded.
        module.getPlugin().getGrid().getIslands().forEach(island -> {
            NPC existingNPC = this.npcRegistry.getByUniqueId(island.getUniqueId());
            if (existingNPC != null)
                existingNPC.destroy();

            NPCMetadata npcMetadata = module.getSettings().schematics.get(island.getSchematicName().toLowerCase(Locale.ENGLISH));

            if (npcMetadata == null)
                return;

            Location spawnLocation = npcMetadata.spawnOffset.applyToLocation(island.getCenter(
                    module.getPlugin().getSettings().getWorlds().getDefaultWorld()));

            if (!spawnLocation.getWorld().isChunkLoaded(spawnLocation.getBlockX() >> 4, spawnLocation.getBlockZ() >> 4))
                return;

            // Only spawn the NPC if the chunk is loaded.
            module.getNPCHandler().createNPC(island, npcMetadata);
        });
    }

    @Override
    public void unloadNPCs() {
        this.npcRegistry.deregisterAll();
    }

    private class ListenerImpl implements Listener {

        @EventHandler
        public void onEnable(CitizensEnableEvent event) {
            canLoadData = true;
            loadNPCs();
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onNPCClick(NPCLeftClickEvent event) {
            NPCMetadata metadata = event.getNPC().data().get(NPC_METADATA_KEY);
            if (metadata != null && metadata.onLeftClickAction != null)
                handleNPCClick(metadata.onLeftClickAction, event.getClicker());
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onNPCClick(NPCRightClickEvent event) {
            NPCMetadata metadata = event.getNPC().data().get(NPC_METADATA_KEY);
            if (metadata != null && metadata.onRightClickAction != null)
                handleNPCClick(metadata.onRightClickAction, event.getClicker());
        }

        private void handleNPCClick(OnClickAction clickAction, Player clicker) {
            switch (clickAction.action) {
                case COMMAND:
                    List<String> commandsToExecute = (List<String>) clickAction.actionData;
                    if (!commandsToExecute.isEmpty())
                        commandsToExecute.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                PLAYER_PLACEHOLDER_PATTERN.matcher(command).replaceAll(clicker.getName())));
                    break;
            }
        }

    }

}
