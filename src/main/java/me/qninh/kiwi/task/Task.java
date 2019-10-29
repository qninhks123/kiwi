package me.qninh.kiwi.task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Task {

    private int id;

    private int currentTime = 0;

    private BukkitScheduler scheduler;

    public Task() {}

    public Task(int id) {
        this.id = id;
    }

    public void repeat(Plugin plugin) {
        repeat(plugin, 0, 0, 0);
    }

    public void repeat(Plugin plugin, int period, int times) {
        repeat(plugin, 0, period, times);
    }

    public void repeat(Plugin plugin, int delay, int period, int times) {
        Task self = this;
        scheduler = plugin.getServer().getScheduler();
        
        id = scheduler.scheduleSyncRepeatingTask(plugin, new java.lang.Runnable(){
            @Override
            public void run() {
                if (times == 0 || currentTime < times) {
                    currentTime += 1;
                    self.run();
                    onTick();
                } else {
                    cancel();
                }
            }
        }, (long) delay, (long) period);

        onStart();        
    }

    public void cancel() {
        scheduler.cancelTask(id);
        onCancel();
    }

    public int getId() {
        return id;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public boolean isRunning() {
        return scheduler.isQueued(id) || scheduler.isCurrentlyRunning(id);
    }

    public void run() {}

    public void onStart() {} 

    public void onTick() {}

    public void onCancel() {}
}