package me.qninh.kiwi.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import me.qninh.kiwi.util.Util;

public class KiwiShapedRecipe implements KiwiRecipe {

    private ShapedRecipe recipe;

    private Plugin plugin;

    public KiwiShapedRecipe(Plugin plugin) {
        this(plugin, new ItemStack(Material.AIR));
        this.plugin = plugin;
    }

    public KiwiShapedRecipe(Plugin plugin, ItemStack item) {
        NamespacedKey key = new NamespacedKey(plugin, Util.toSnakeCase(getClass().getName()));
        recipe = new ShapedRecipe(key, item);
    }

    public void shape(String... shape) {
        recipe.shape(shape);
    }

    public void setIngredient(char key, Material type) {
        recipe.setIngredient(key, type);
    }

    public void setIngredient(char key, ItemStack item) {
        recipe.setIngredient(key, new RecipeChoice() {

            @Override
            public boolean test(ItemStack item0) {
                return item0.isSimilar(item);
            }

            @Override
            public ItemStack getItemStack() {
                return item;
            }

            @Override
            public RecipeChoice clone() {
                try {
                    return (RecipeChoice) super.clone();
                } catch (CloneNotSupportedException e) {
                    throw new Error(e);
                }
            }
        });
    } 

    @Override
    public ShapedRecipe getRecipe() {
        return recipe;
    }

    /**
     * SYSTEM
     */
    public void a(ShapedRecipe var0) {
        recipe = var0;
    }
}