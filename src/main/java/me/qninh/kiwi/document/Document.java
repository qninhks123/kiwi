package me.qninh.kiwi.document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.inventory.ItemStack;

import me.qninh.kiwi.util.ItemUtil;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagInt;
import net.minecraft.server.v1_14_R1.NBTTagList;
import net.minecraft.server.v1_14_R1.NBTTagString;

public class Document implements Cloneable {

    private NBTTagCompound compound;

    public Document() {
        compound = new NBTTagCompound(); 
    }

    public Document(NBTTagCompound compound) {
        this.compound = compound;
    }

    public NBTTagCompound getNBTData() {
        return compound;
    }

    public void setNBTData(NBTTagCompound compound) {
        this.compound = compound;
    }

    public void save() {
        save(true);
    }

    public void save(boolean all) {
        if (all) {
            Class<? extends Document> clazz = getClass();
            Field[] fields = (Field[]) ArrayUtils.addAll(clazz.getFields(), clazz.getDeclaredFields());

            try {
                for (Field field : fields) {
                    Tag data = field.getAnnotation(Tag.class);
                    String key = field.getName();

                    field.setAccessible(true);

                    if (data != null) {
                        if (data.name().isEmpty()) {
                            set(key, field.get(this));
                        } else {
                            set(data.name(), field.get(this));
                        }
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        onSave();
    }

    public void fetch() {
        Class<? extends Document> clazz = getClass();
        Field[] fields = (Field[]) ArrayUtils.addAll(clazz.getFields(), clazz.getDeclaredFields());

        if (compound.isEmpty()) return;

        for (Field field : fields) {
            Tag data = field.getAnnotation(Tag.class);
            Class<?> type = field.getType();
            String key = field.getName();

            field.setAccessible(true);

            if (data != null) {
                try {
                    if (data.name().isEmpty()) {
                        field.set(this, get(key, type));
                    } else {
                        field.set(this, get(data.name(), type));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onSave() {}

    /**
     * GLOBAL
     */
    public Object get(String key, Class<?> clazz) {
        Document target = this;
        String[] path = key.split("\\.");
        String key0 = key;

        if (path.length != 1) {
            for (int i = 0; i < path.length - 1; i += 1) {
                target = target.getDocument(path[i]);
                if (target == null) return null;
            }

            key0 = path[path.length - 1];
        }

        if (clazz == int.class) return target.getInt(key0);
        if (clazz == float.class) return target.getFloat(key0);
        if (clazz == boolean.class) return target.getBoolean(key0);
        if (clazz == String.class) return target.getString(key0);
        if (clazz == int[].class) return target.getInts(key0);
        if (clazz == String[].class) return target.getString(key0);
        if (ItemStack.class.isAssignableFrom(clazz)) return target.getItemStack(key0);
        if (ItemStack[].class.isAssignableFrom(clazz)) return target.getItemStacks(key0);
        return null;
    }

    public void set(String key, Object value) {
        if (value != null) {
            Class<?> clazz = value.getClass();
            Document target = this;
            String[] path = key.split("\\.");
            String key0 = key;

            if (path.length != 1) {
                for (int i = 0; i < path.length - 1; i += 1) {
                    target = target.getDocument(path[i]);
                }

                key0 = path[path.length - 1];
            }

            if (clazz == int.class) target.setInt(key0, (int) value); 
            if (clazz == float.class) target.setFloat(key0, (float) value);
            if (clazz == boolean.class) target.setBoolean(key0, (boolean) value);
            if (clazz == String.class) target.setString(key0, (String) value);
            if (clazz == int[].class) target.setInts(key0, (int[]) value);
            if (clazz == String[].class) target.setStrings(key0, (String[]) value);
            if (ItemStack.class.isAssignableFrom(clazz)) target.setItemStack(key0, (ItemStack) value);
            if (ItemStack[].class.isAssignableFrom(clazz)) target.setItemStacks(key0, (ItemStack[]) value);
        }
    }

    /**
     * INT
     */

    public int getInt(String key) {
        return compound.getInt(key);
    }

    public int[] getInts(String key) {
        return compound.getIntArray(key);
    }

    public void setInt(String key, int value) {
        compound.setInt(key, value);
        onSave();
    }

    public void setInts(String key, int[] value) {
        NBTTagList list = new NBTTagList();
        for (int v : value)
            list.add(new NBTTagInt(v));
        compound.set(key, list);
        onSave();
    }

    /**
     * FLOAT
     */

    public float getFloat(String key) {
        return compound.getFloat(key);
    }

    public void setFloat(String key, float value) {
        compound.setFloat(key, value);
        onSave();
    }

    /**
     * BOOLEAN
     */

    public boolean getBoolean(String key) {
        return compound.getBoolean(key);
    }

    public void setBoolean(String key, boolean value) {
        compound.setBoolean(key, value);
        onSave();
    }

    /**
     * STRING
     */

    public String getString(String key) {
        return compound.getString(key);
    }

    public String[] getStrings(String key) {
        List<String> value = new ArrayList<>();
        NBTTagList list = (NBTTagList) compound.get(key);

        list.forEach(v -> value.add(((NBTTagString) v).asString()));

        return value.toArray(new String[0]);
    }

    public void setString(String key, String value) {
        compound.setString(key, value);
        onSave();
    }

    public void setStrings(String key, String[] value) {
        NBTTagList list = new NBTTagList();

        for (String v : value) {
            list.add(new NBTTagString(v));
        }

        compound.set(key, list);
        onSave();
    }

    /**
     * DOCUMENT
     */

    public Document getDocument(String key) {
        NBTTagCompound value = compound.getCompound(key);
        Document self = this;

        if (value == null) value = new NBTTagCompound();

        Document document = new Document(value) {

            @Override
            public void onSave() {
                self.setDocument(key, this);
            }
        };

        document.fetch();

        return document;
    }

    public Document[] getDocuments(String key) {
        List<Document> value = new ArrayList<>();
        NBTTagList list = (NBTTagList) compound.get(key);

        list.forEach(v -> {
            Document document = new Document((NBTTagCompound) v);

            document.fetch();
            value.add(document);
        });

        return value.toArray(new Document[0]);
    }

    public void setDocument(String key, Document value) {
        compound.set(key, value.getNBTData());
        onSave();
    }

    public void setDocuments(String key, Document[] value) {
        NBTTagList list = new NBTTagList();

        for (Document v : value) {
            list.add(v.getNBTData());
        }

        compound.set(key, list);
        onSave();
    }

    /**
     * ITEMSTACK
     */

    public ItemStack getItemStack(String key) {
        return ItemUtil.parser(compound.getString(key));
    }

    public ItemStack[] getItemStacks(String key) {
        List<ItemStack> list = new ArrayList<>();
        String[] strs = getStrings(key);

        for (String str: strs) list.add(ItemUtil.parser(str));

        return list.toArray(new ItemStack[0]);
    }

    public void setItemStack(String key, ItemStack value) {
        setString(key, ItemUtil.getTag(value).toString());
        onSave();
    }

    public void setItemStacks(String key, ItemStack[] value) {
        NBTTagList list = new NBTTagList();

        for (ItemStack v: value) list.add(new NBTTagString(ItemUtil.getTag(v).toString()));

        compound.set(key, list);
        onSave();
    }   

    public boolean hasKey(String key) {
        return compound.hasKey(key);
    }

    public void removeKey(String key) {
        compound.remove(key);
        onSave();
    } 

    public boolean isEmpty() {
        return compound.isEmpty();
    }

    public Document clone() {
        return new Document(compound.clone());
    }
}