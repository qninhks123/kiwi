package me.qninh.kiwi.core;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import me.qninh.kiwi.event.PlayerInteractItemEvent;
import me.qninh.kiwi.inventory.KiwiInventory;
import me.qninh.kiwi.inventory.KiwiInventoryManager;
import me.qninh.kiwi.item.KiwiItem;

public class CoreListenner implements Listener {

  private Plugin plugin;

  public CoreListenner(Plugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Action action = event.getAction();
    EquipmentSlot hand = event.getHand();

    KiwiItem item0 = KiwiItem.fromItem(player.getInventory().getItemInMainHand());

    if (hand == EquipmentSlot.HAND && item0 != null) {
      PlayerInteractItemEvent e = new PlayerInteractItemEvent(player, item0.toItem(), action, hand);
      item0.onInteract(e);
      event.setCancelled(e.isCanceled());
      item0.onUpdate();
    }

    if (item0 != null)
      return;

    KiwiItem item1 = KiwiItem.fromItem(player.getInventory().getItemInOffHand());

    if (hand == EquipmentSlot.OFF_HAND && item1 != null) {
      PlayerInteractItemEvent e = new PlayerInteractItemEvent(player, item1.toItem(), action, hand);
      item1.onInteract(e);
      event.setCancelled(e.isCanceled());
      item1.onUpdate();
    }
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
  }

  @EventHandler
  public void onClose(InventoryCloseEvent event) {
    Inventory inv = event.getInventory();
    KiwiInventory kiwiInv = KiwiInventoryManager.fromInventory(inv);
    Player player = (Player) event.getViewers().get(0);

    if (kiwiInv != null) {
      kiwiInv.onClose();
    }
  }
}
