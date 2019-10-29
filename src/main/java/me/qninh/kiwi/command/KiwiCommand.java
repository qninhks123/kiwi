package me.qninh.kiwi.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.qninh.kiwi.util.PlayerUtil;

public class KiwiCommand extends Command {

    private Label[] labels;

    private KiwiExecutor executor;

    private Method handler;

    private Plugin plugin;

    public KiwiCommand(String name, String description, Label[] labels, KiwiExecutor executor, Method handler) {
        super(name);
        this.description = description;
        this.labels = labels;
        this.executor = executor;
        this.handler = handler;
        this.usageMessage = "/" + name;

        for (Label label : labels) {
            usageMessage += " <" + label.name() + ">";
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        List<Object> params = new ArrayList<>();

        if (args.length > labels.length) {
            PlayerUtil.sendError((Player) sender, "Dữ liệu vào vượt quá mức quy định");
            return false;
        }   

        if (handler.getReturnType() != boolean.class) {
            throw new Error("command handler must return boolean not " + handler.getReturnType().getName().toLowerCase());
        }

        params.add(sender);

        for (int idx = 0; idx < labels.length; idx += 1) {
            Label label = labels[idx];

            if (idx > args.length - 1) {
                if (label.type() == LabelType.INT) {
                    params.add(0);
                } else {
                    params.add(null);
                }
                continue;
            }

            switch (label.type()) {
                case LIST:
                    List<String> list = Arrays.asList(label.list());
                    if (list.contains(args[idx])) {
                        params.add(args[idx]);
                    } else {
                        params.add(null);
                    }
                    break;
                case MATERIAL:
                    params.add(Material.matchMaterial(args[idx]));
                    break;
                case ENTITY:
                    params.add(EntityType.fromName(args[idx]));
                    break;
                case PLAYER:
                    params.add(plugin.getServer().getPlayer(args[idx]));
                    break;
                case TEXT:
                    params.add(args[idx]);
                case INT:
                    try {
                        params.add(Integer.valueOf(args[idx]));
                    } catch(NumberFormatException e) {
                        return false;
                    }
            }
        }
        try {
            boolean result = (boolean) handler.invoke(executor, params.toArray());
            if (!result) {
                PlayerUtil.sendError((Player) sender, "Phát hiện trục trặc");
            }
            return result;
            
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } catch(Error e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length > labels.length) return list;

        Label label = labels[args.length - 1];

        switch (label.type()) {
            case TEXT:
            case INT:
                break;
            case LIST:
                Arrays.stream(label.list())
                    .filter(text -> text.startsWith(args[args.length - 1]))
                    .forEach(text -> list.add(text));
                break;
            case MATERIAL:
                Arrays.stream(Material.values())
                    .map(type -> type.toString().toLowerCase())
                    .filter(type -> type.startsWith(args[args.length - 1]))
                    .forEach(type -> list.add(type));
                break;
            case PLAYER:
                plugin.getServer().getOnlinePlayers().forEach(player -> list.add(player.getName()));
            case ENTITY: 
                Arrays.stream(EntityType.values())
                    .map(type -> type.toString().toLowerCase())
                    .filter(type -> type.startsWith(args[args.length - 1]))
                    .forEach(type -> list.add(type));
                break;
        }

        return list;
    }

    /**
     * SYSTEM
     */
    public void b(Plugin plugin) {
        this.plugin = plugin;
    }
}