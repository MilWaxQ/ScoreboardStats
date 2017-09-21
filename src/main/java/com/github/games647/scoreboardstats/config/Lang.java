package com.github.games647.scoreboardstats.config;

import com.github.games647.scoreboardstats.ScoreboardStats;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Separate the code and the messages.
 */
public class Lang {

    private static final Lang INSTANCE = new Lang();

    /**
     * Get a localized string.
     *
     * @param key the localization key
     * @return the localized string
     */
    public static String get(String key) {
        //Static method wrapper
        return INSTANCE.getFormatted(key);
    }

    /**
     * Get a localized string.
     *
     * @param key the localization key
     * @param arguments arguments for formatting strings
     * @return localized
     */
    public static String get(String key, Object... arguments) {
        //Static method wrapper
        return INSTANCE.getFormatted(key, arguments);
    }

    private final ResourceBundle messages;

    /**
     * Initialize localization manager
     */
    public Lang() {
        ScoreboardStats plugin = JavaPlugin.getPlugin(ScoreboardStats.class);
        ClassLoader classLoader = plugin.getClass().getClassLoader();

        messages = ResourceBundle.getBundle("messages", Locale.getDefault(), classLoader);
    }

    private String getFormatted(String key, Object... arguments) {
        if (messages.containsKey(key)) {
            String result = messages.getString(key);
            if (arguments.length != 0) {
                //If there are arguments use message format to replace
                //properly cache the formatting instance
                result = MessageFormat.format(result, arguments);
            }

            return result;
        }

        //fail silently for now
        return "";
    }
}
