package me.qninh.kiwi.event;

import org.bukkit.entity.Player;

public class PlayerEvent extends Event {

    private Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}