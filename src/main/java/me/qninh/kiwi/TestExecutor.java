package me.qninh.kiwi;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.qninh.kiwi.command.Command;
import me.qninh.kiwi.command.KiwiExecutor;

public class TestExecutor implements KiwiExecutor {

    @Command(name = "test")
    public boolean test(CommandSender sender) {
        Player player = (Player) sender;
        ItemStack item = new TestItem().toItem();
        player.getInventory().addItem(item);

        return true;
    }
}