package me.qninh.kiwi;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.qninh.kiwi.command.KiwiCommandManager;
import me.qninh.kiwi.command.KiwiExecutor;
import me.qninh.kiwi.core.CoreListenner;
import me.qninh.kiwi.item.ItemManager;
import me.qninh.kiwi.item.KiwiItem;

public class KiwiPlugin extends JavaPlugin {

    public void onEnable() {
        registerItem(new TestItem());
        registerExecutor(new TestExecutor());
        registerListener(new CoreListenner(this));
    }
 
    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);   
    }

    public void regiterExecutor(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }

    public void registerExecutor(KiwiExecutor executor) {
        KiwiCommandManager.registerExecutor(this, executor);
    }

    public void registerItem(KiwiItem item) {
        ItemManager.register(this, item);
    }
}