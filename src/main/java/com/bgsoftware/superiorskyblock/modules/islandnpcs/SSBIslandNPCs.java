package com.bgsoftware.superiorskyblock.modules.islandnpcs;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import com.bgsoftware.superiorskyblock.api.modules.PluginModule;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SSBIslandNPCs extends PluginModule {

    private JavaPlugin plugin;

    public SSBIslandNPCs() {
        super("IslandNPCs", "Ome_R");
    }

    @Override
    public void onEnable(SuperiorSkyblock plugin) {
        this.plugin = (JavaPlugin) plugin;
    }

    @Override
    public void onReload(SuperiorSkyblock plugin) {

    }

    @Override
    public void onDisable(SuperiorSkyblock plugin) {

    }

    @Override
    public Listener[] getModuleListeners(SuperiorSkyblock plugin) {
        return null;
    }

    @Override
    public SuperiorCommand[] getSuperiorCommands(SuperiorSkyblock plugin) {
        return null;
    }

    @Override
    public SuperiorCommand[] getSuperiorAdminCommands(SuperiorSkyblock plugin) {
        return null;
    }

    public JavaPlugin getJavaPlugin() {
        return plugin;
    }
}
