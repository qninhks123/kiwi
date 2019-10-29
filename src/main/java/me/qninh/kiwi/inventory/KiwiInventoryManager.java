package me.qninh.kiwi.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.Inventory;

public class KiwiInventoryManager {

    private static List<KiwiInventory> invs = new ArrayList<>();

    public static void register(KiwiInventory inv) {
        invs.add(inv);        
    }

    public static KiwiInventory fromInventory(Inventory inv) {
        for (int i=0; i<invs.size(); i+=1) {
            KiwiInventory kiwiInv = invs.get(i);

            if (kiwiInv.getInventory() == inv) {
                return kiwiInv;
            }
        }
        return null;
    }
}