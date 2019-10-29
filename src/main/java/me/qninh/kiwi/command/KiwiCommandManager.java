package me.qninh.kiwi.command;

import java.lang.reflect.Method;


import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.plugin.Plugin;

public class KiwiCommandManager {
    public static void registerExecutor(Plugin plugin, KiwiExecutor executor) {
        Class<?> clazz = executor.getClass();

        for (Method method: clazz.getMethods()) {
            Command annotation = method.getAnnotation(Command.class);
            if (annotation != null) {
                KiwiCommand command = new KiwiCommand(annotation.name(), annotation.description(), annotation.labels(), executor, method);
                command.b(plugin);
                ((CraftServer) plugin.getServer()).getCommandMap().register(command.getName(), command);
            }
        }
    }
}