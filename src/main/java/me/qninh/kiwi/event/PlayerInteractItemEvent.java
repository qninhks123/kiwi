package me.qninh.kiwi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractItemEvent extends PlayerInteractEvent implements ItemEvent {

    private ItemStack item;

    public PlayerInteractItemEvent(Player player, ItemStack item, Action action, EquipmentSlot hand) {
        super(player, action, hand);
        this.item = item;
    }

    @Override
    public ItemStack getItem() {
        return item;
    } 
}