package com.bgsoftware.ssbislandnpcs.config;

import com.bgsoftware.ssbislandnpcs.SSBIslandNPCs;
import com.bgsoftware.superiorskyblock.api.wrappers.BlockOffset;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.EntityType;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class NPCMetadata {

    public final BlockOffset spawnOffset;
    public final EntityType npcType;
    public final String npcName;
    public final List<String> displayName;
    public final boolean lookAtNearby;
    public final OnClickAction onLeftClickAction;
    public final OnClickAction onRightClickAction;

    public NPCMetadata(SSBIslandNPCs module, ConfigurationSection section) throws InvalidConfigurationException {
        try {
            String[] spawnOffset = section.getString("spawn-offset", "0, 0, 0").split(", ");
            this.spawnOffset = module.getPlugin().getFactory().createBlockOffset(Integer.parseInt(spawnOffset[0]),
                    Integer.parseInt(spawnOffset[1]), Integer.parseInt(spawnOffset[2]));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException error) {
            throw new InvalidConfigurationException("Spawn offset is not valid");
        }

        String npcType = section.getString("type");

        if (npcType == null)
            throw new InvalidConfigurationException("Missing npc type");

        try {
            this.npcType = EntityType.valueOf(npcType.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException error) {
            throw new InvalidConfigurationException("Invalid NPC type: " + npcType);
        }

        this.npcName = section.getString("name", "");

        this.displayName = new LinkedList<>();
        if (section.contains("display-name")) {
            if (section.isString("display-name"))
                this.displayName.add(section.getString("display-name"));
            else if (section.isList("display-name"))
                this.displayName.addAll(section.getStringList("display-name"));
            else
                throw new InvalidConfigurationException("Display name is invalid");
        }

        this.lookAtNearby = section.getBoolean("look-at-nearby", false);

        ConfigurationSection onLeftClickSection = section.getConfigurationSection("on-left-click");
        if (onLeftClickSection == null) {
            this.onLeftClickAction = null;
        } else {
            this.onLeftClickAction = new OnClickAction(OnClickAction.ClickType.LEFT, onLeftClickSection);
        }
        ConfigurationSection onRightClickSection = section.getConfigurationSection("on-right-click");
        if (onRightClickSection == null) {
            this.onRightClickAction = null;
        } else {
            this.onRightClickAction = new OnClickAction(OnClickAction.ClickType.RIGHT, onRightClickSection);
        }
    }

}
