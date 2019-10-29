package me.qninh.kiwi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import me.qninh.kiwi.document.Tag;
import me.qninh.kiwi.event.PlayerInteractItemEvent;
import me.qninh.kiwi.item.KiwiItem;

public class TestItem extends KiwiItem {

    @Tag private int size;
    @Tag private String name;
    @Tag private ItemStack[] items;

    @Override
    public void onCreate() {
        setType(Material.CHEST);
        name = "Backpack";
        size = 9;
        items = new ItemStack[0];
    }

    @Override
    public void onInteract(PlayerInteractItemEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
        }
    }

}
