package com.bgsoftware.superiorskyblock.modules.islandnpcs;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import com.bgsoftware.superiorskyblock.api.modules.PluginModule;
import com.bgsoftware.superiorskyblock.modules.islandnpcs.listeners.IslandsListener;
import com.bgsoftware.superiorskyblock.modules.islandnpcs.nms.NMSAdapter;
import com.bgsoftware.superiorskyblock.modules.islandnpcs.npc.NPCHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SSBIslandNPCs extends PluginModule {

    private SuperiorSkyblock plugin;
    private final NPCHandler npcHandler = new NPCHandler(this);
    private final NMSAdapter nmsAdapter = loadNMSAdapter();

    public SSBIslandNPCs() {
        super("IslandNPCs", "Ome_R");
        if (nmsAdapter == null)
            throw new RuntimeException("Couldn't load NMS for this version.");
    }

    @Override
    public void onEnable(SuperiorSkyblock plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onReload(SuperiorSkyblock plugin) {

    }

    @Override
    public void onDisable(SuperiorSkyblock plugin) {

    }

    @Override
    public Listener[] getModuleListeners(SuperiorSkyblock plugin) {
        return new Listener[]{new IslandsListener(this)};
    }

    @Override
    public SuperiorCommand[] getSuperiorCommands(SuperiorSkyblock plugin) {
        return null;
    }

    @Override
    public SuperiorCommand[] getSuperiorAdminCommands(SuperiorSkyblock plugin) {
        return null;
    }

    public NPCHandler getNPCHandler() {
        return npcHandler;
    }

    public NMSAdapter getNMSAdapter() {
        return nmsAdapter;
    }

    public SuperiorSkyblock getPlugin() {
        return plugin;
    }

    public JavaPlugin getJavaPlugin() {
        return (JavaPlugin) plugin;
    }

    private static NMSAdapter loadNMSAdapter() {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            return (NMSAdapter) Class.forName(String.format("com.bgsoftware.superiorskyblock.modules." +
                    "islandnpcs.nms.%s.NMSAdapterImpl", version)).newInstance();
        } catch (Throwable error) {
            return null;
        }
    }

}
