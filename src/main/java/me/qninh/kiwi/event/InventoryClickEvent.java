package me.qninh.kiwi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

public class InventoryClickEvent extends PlayerEvent implements InventoryEvent {

    private Inventory inv;

    private ClickType clickType;

    private InventoryAction action;

    public InventoryClickEvent(Player player, Inventory inv, ClickType clickType, InventoryAction action) {
        super(player);
        this.inv = inv;
        this.clickType = clickType;
        this.action = action;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public InventoryAction getAction() {
        return action;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}