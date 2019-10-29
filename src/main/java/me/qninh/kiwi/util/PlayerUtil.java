package me.qninh.kiwi.util;

import org.bukkit.entity.Player;

public class PlayerUtil {

    public static void sendError(Player player, String msg) {
        player.sendMessage("§4§l[Lỗi] §c" + msg);
    }
}