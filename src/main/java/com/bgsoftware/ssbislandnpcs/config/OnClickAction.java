package com.bgsoftware.ssbislandnpcs.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;

import java.util.Locale;

public class OnClickAction {

    public final ClickType clickType;
    public final Action action;
    public final Object actionData;

    public OnClickAction(ClickType clickType, ConfigurationSection section) throws InvalidConfigurationException {
        this.clickType = clickType;

        String action = section.getString("action");
        try {
            this.action = Action.valueOf(action.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException error) {
            throw new InvalidConfigurationException("Invalid action for on-click: " + action);
        }

        switch (this.action) {
            case COMMAND:
                if (!section.isList("commands"))
                    throw new InvalidConfigurationException("on-click is missing commands section");

                this.actionData = section.getStringList("commands");
                break;
            default:
                throw new InvalidConfigurationException("Invalid action for on-click: " + action);
        }

    }

    public enum Action {

        COMMAND

    }

    public enum ClickType {

        LEFT,
        RIGHT

    }

}
