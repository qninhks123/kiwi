package me.qninh.kiwi.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.plugin.Plugin;

import me.qninh.kiwi.util.Util;

public class ItemManager {

    private static Map<String, Class<? extends KiwiItem>> items = new HashMap<>();

    public static void register(Plugin plugin, KiwiItem item) {
        Class<? extends KiwiItem> clazz = item.getClass();
        items.put(plugin.getName().toLowerCase() + ":" + Util.toSnakeCase(clazz.getSimpleName()), clazz);
    }

    public static String getId(KiwiItem item) {
        return getId(item.getClass());
    }

    public static String getId(Class<? extends KiwiItem> clazz) {
        for (Entry<String, Class<? extends KiwiItem>> entry: items.entrySet()) {
            if (entry.getValue() == clazz) return entry.getKey();
        }
        return null;
    }

    public static Class<? extends KiwiItem> fromId(String id) {
        return items.get(id);
    }
}
