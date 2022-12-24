package com.bgsoftware.ssbislandnpcs.config;

import com.bgsoftware.common.config.CommentedConfiguration;
import com.bgsoftware.ssbislandnpcs.SSBIslandNPCs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class SettingsManager {

    public final Map<String, NPCMetadata> schematics;

    public SettingsManager(SSBIslandNPCs module) {
        File file = new File(module.getModuleFolder(), "config.yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            module.saveResource("config.yml");
        }

        CommentedConfiguration cfg = CommentedConfiguration.loadConfiguration(file);

        try {
            cfg.syncWithConfig(file, module.getResource("config.yml"), "schematics");
        } catch (IOException error) {
            throw new RuntimeException(error);
        }

        this.schematics = new HashMap<>();

        Optional.ofNullable(cfg.getConfigurationSection("schematics")).ifPresent(schematicsSection -> {
            for (String schematicName : schematicsSection.getKeys(false)) {
                Optional.ofNullable(schematicsSection.getConfigurationSection(schematicName)).ifPresent(schematicSection -> {
                    try {
                        this.schematics.put(schematicName.toLowerCase(Locale.ENGLISH), new NPCMetadata(module, schematicSection));
                    } catch (Exception error) {
                        throw new RuntimeException("An error occurred while parsing schematic: " + schematicName, error);
                    }
                });
            }
        });

    }

}
