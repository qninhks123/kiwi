package me.qninh.kiwi.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_14_R1.MojangsonParser;
import net.minecraft.server.v1_14_R1.NBTTagCompound;

public class ItemUtil {

    public static net.minecraft.server.v1_14_R1.ItemStack getNMItemStack(ItemStack item) {
        return CraftItemStack.asNMSCopy(item);
    }

    public static NBTTagCompound getTag(ItemStack item) {
        return getNMItemStack(item).getTag();
    }

    public static void setTag(ItemStack item, NBTTagCompound compound) {
        net.minecraft.server.v1_14_R1.ItemStack nmsItem = getNMItemStack(item);
        nmsItem.setTag(compound);
        item.setItemMeta(CraftItemStack.getItemMeta(nmsItem));
    }

    public static ItemStack parser(String source) {
        try {
            NBTTagCompound compound = MojangsonParser.parse(source);
            net.minecraft.server.v1_14_R1.ItemStack nmsItem = net.minecraft.server.v1_14_R1.ItemStack.a(compound);
        
            return CraftItemStack.asBukkitCopy(nmsItem);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    } 
} 