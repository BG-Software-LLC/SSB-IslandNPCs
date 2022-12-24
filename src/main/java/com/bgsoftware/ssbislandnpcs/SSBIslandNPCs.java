package com.bgsoftware.ssbislandnpcs;

import com.bgsoftware.ssbislandnpcs.listeners.IslandsListener;
import com.bgsoftware.ssbislandnpcs.npc.IslandNPC;
import com.bgsoftware.ssbislandnpcs.npc.NPCHandler;
import com.bgsoftware.ssbislandnpcs.npc.NPCProvider;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import com.bgsoftware.superiorskyblock.api.modules.ModuleLoadTime;
import com.bgsoftware.superiorskyblock.api.modules.PluginModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;

public final class SSBIslandNPCs extends PluginModule {

    private SuperiorSkyblock plugin;
    private final NPCHandler npcHandler = new NPCHandler(this);

    private NPCProvider npcProvider;

    public SSBIslandNPCs() {
        super("IslandNPCs", "Ome_R");
    }

    @Override
    public void onEnable(SuperiorSkyblock plugin) {
        this.plugin = plugin;

        this.npcProvider = loadNMSProvider();

        if (this.npcProvider == null)
            throw new IllegalStateException("Cannot find a suitable NPC provider.");
    }

    @Override
    public void onReload(SuperiorSkyblock plugin) {

    }

    @Override
    public void onDisable(SuperiorSkyblock plugin) {
        // We want to despawn all npcs.
        this.npcHandler.getNPCs().forEach(IslandNPC::despawn);
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

    @Override
    public ModuleLoadTime getLoadTime() {
        return ModuleLoadTime.AFTER_HANDLERS_LOADING;
    }

    public NPCHandler getNPCHandler() {
        return npcHandler;
    }

    public NPCProvider getNPCProvider() {
        return npcProvider;
    }

    public SuperiorSkyblock getPlugin() {
        return plugin;
    }

    public JavaPlugin getJavaPlugin() {
        return (JavaPlugin) plugin;
    }

    private NPCProvider loadNMSProvider() {
        try {
            if (Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
                return createNPCProvider("com.bgsoftware.ssbislandnpcs.npc.citizens.CitizensNPCProvider");
            }
        } catch (Throwable ignored) {
        }

        return null;
    }

    private NPCProvider createNPCProvider(String clazzName) throws Exception {
        Class<?> clazz = Class.forName(clazzName);

        try {
            Constructor<?> constructor = clazz.getConstructor(SSBIslandNPCs.class);
            return (NPCProvider) constructor.newInstance(this);
        } catch (NoSuchMethodException ignored) {
        }

        return (NPCProvider) clazz.newInstance();
    }

}
