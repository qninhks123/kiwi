package me.qninh.kiwi.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KiwiInventory {

    private Inventory inv;

    public KiwiInventory(String title, int size) {
        inv = Bukkit.createInventory(null, size, title);
    }

    public ItemStack getItem(int index) {
        return inv.getItem(index);
    }

    public ItemStack[] getContents() {
        return inv.getContents();
    }

    public void setItem(int index, ItemStack item) {
        inv.setItem(index, item);
    }

    public void setContents(ItemStack[] items) {
        inv.setContents(items);
    }

    public Inventory getInventory() {
        return inv;
    }

    public void open(Player player) {
        player.openInventory(inv);
        onOpen();
    }

    public void onOpen() {}

    public void onClick() {}

    public void onClose() {}

}