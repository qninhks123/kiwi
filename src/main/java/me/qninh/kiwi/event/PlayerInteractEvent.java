package me.qninh.kiwi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEvent extends PlayerEvent {

    private Action action;

    private EquipmentSlot hand;

    public PlayerInteractEvent(Player player, Action action, EquipmentSlot hand) {
        super(player);
        this.action = action;
        this.hand = hand;
    }

    public Action getAction() {
        return action;
    }

    public EquipmentSlot getSlot() {
        return hand;
    }
}