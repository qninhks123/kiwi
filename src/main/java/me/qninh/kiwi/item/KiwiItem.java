package me.qninh.kiwi.item;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.qninh.kiwi.task.Task;
import me.qninh.kiwi.task.TaskExecutor;
import me.qninh.kiwi.util.ItemUtil;
import me.qninh.kiwi.util.ReflectUtil;
import me.qninh.kiwi.Kiwi;
import me.qninh.kiwi.document.Document;
import me.qninh.kiwi.event.PlayerInteractItemEvent;
import net.minecraft.server.v1_14_R1.NBTTagCompound;

public class KiwiItem extends Document implements TaskExecutor {

  private ItemStack item = null;

  public KiwiItem() {
    NBTTagCompound compound = new NBTTagCompound();
    NBTTagCompound meta = new NBTTagCompound();

    item = new ItemStack(Material.AIR);

    meta.setString(Kiwi.ITEM_KEY, getId());
    compound.set(Kiwi.META_KEY, meta);

    setNBTData(compound);

    onCreate();

    onEnable();
    save();
  }

  /**
   * SYSTEM
   */

  public void a(ItemStack var0) {
    item = var0;
    setNBTData(ItemUtil.getTag(item));
    fetch();
    onEnable();
  }

  /**
   * CONVERTER
   */

  public static KiwiItem fromItem(ItemStack item) {

    if (item == null)
      return null;

    NBTTagCompound compound = ItemUtil.getTag(item);

    if (compound == null)
      return null;
    if (!compound.hasKey(Kiwi.META_KEY))
      return null;

    String id = compound.getCompound(Kiwi.META_KEY).getString(Kiwi.ITEM_KEY);
    Class<? extends KiwiItem> clazz = ItemManager.fromId(id);

    try {
      Constructor<? extends KiwiItem> constructor = clazz.getConstructor();
      KiwiItem instance = constructor.newInstance();

      instance.a(item);

      return instance;
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      throw new Error(e.getCause().getMessage());
    }
  }

  public ItemStack toItem() {
    return item;
  }

  /**
   * TASK
   */

  @Override
  public Task getTask(String name) {
    Document tasks = getDocument(Kiwi.META_KEY).getDocument(Kiwi.TASK_KEY);
    KiwiItem self = this;
    Method method = ReflectUtil.getMethod(getClass(), name);

    if (tasks != null && tasks.hasKey(method.getName())) {
      int id = tasks.getInt(method.getName());
      Task task = new Task(id);

      if (task.isRunning())
        return task;
    }

    method.setAccessible(true);
    return new Task() {

      @Override
      public void run() {
        try {
          method.invoke(self);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onStart() {
        Document tasks = getDocument(Kiwi.META_KEY).getDocument(Kiwi.TASK_KEY);

        if (tasks == null)
          tasks = new Document();

        if (tasks.hasKey(Kiwi.TASK_KEY)) {
          throw new Error("This task is running !!");
        } else {
          tasks.setInt(method.getName(), this.getId());
          save(false);
        }
      }

      @Override
      public void onTick() {
        save();
      }

      @Override
      public void onCancel() {
        Document tasks = getDocument(Kiwi.META_KEY).getDocument(Kiwi.TASK_KEY);

        tasks.removeKey(method.getName());
        save(false);
      }
    };
  }

  /**
   * UTILITY
   */

  public String getId() {
    return ItemManager.getId(this);
  }

  public void setItemMeta(ItemMeta meta) {
    item.setItemMeta(meta);
  }

  public void setType(Material type) {
    item.setType(type);
  }

  @Override
  public Document clone() {
    return fromItem(item.clone());
  }

  /**
   * EVENT
   */

  public void onCreate() {
  }

  public void onEnable() {
  }

  public void onUpdate() {
  }

  public void onInteract(PlayerInteractItemEvent event) {
  }

  @Override
  public final void onSave() {
    ItemUtil.setTag(item, getNBTData());
  }
}
