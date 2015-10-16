package com.sainttx.itembuilder;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Created by Matthew on 22/02/2015.
 */
public class ItemBuilder {

    private Material material;
    private int amount;
    private short durability;
    private List<String> lore;
    private String displayName;
    private Map<Enchantment, Integer> enchantments;
    private Collection<ItemFlag> flags;

    /**
     * Creates an empty stack of AIR
     */
    public ItemBuilder() {
        this(Material.AIR);
    }

    /**
     * Create an ItemBuilder with a material as the base
     *
     * @param material The base material
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    /**
     * Create an ItemBuilder with a base material and amount
     *
     * @param material The base material
     * @param amount   The amount in the ItemStack
     */
    public ItemBuilder(Material material, int amount) {
        this(material, amount, (short) 0);
    }

    /**
     * Create an ItemBuilder with a base material, amount and durabiltiy
     *
     * @param material   The base material
     * @param amount     The amount in the ItemStack
     * @param durability The base durability
     */
    public ItemBuilder(Material material, int amount, short durability) {
        Validate.notNull(material, "Material can't be null");

        this.material = material;
        this.amount = amount;
        this.durability = durability;
    }

    /**
     * Set the material of the item
     *
     * @param material the new material
     * @return this builder instance
     */
    public ItemBuilder setMaterial(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("material cannot be null");
        }
        this.material = material;
        return this;
    }

    /**
     * Sets the new amount in the result item
     *
     * @param amount the new amount
     * @return this builder instance
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Set the display name of the item
     *
     * @param displayName the new display name of the item
     * @return this builder instance
     */
    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : ChatColor.translateAlternateColorCodes('&', displayName);
        return this;
    }

    /**
     * Sets the durability of the item
     *
     * @param durability the new durability of the item
     * @return this builder instance
     */
    public ItemBuilder setDurability(short durability) {
        this.durability = durability;
        return this;
    }

    /**
     * Add lore to the item
     *
     * @param lore The lore to add
     * @return this builder instance
     */
    public ItemBuilder setLore(List<String> lore) {
        if (lore == null) {
            this.lore = null;
            return this;
        }

        if (this.lore == null) {
            this.lore = new ArrayList<>();
        }

        this.lore.clear();
        for (String piece : lore) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', piece));
        }

        return this;
    }

    /**
     * Adds a string to the current lore
     *
     * @param lore a string of lore to append
     * @return this builder instance
     */
    public ItemBuilder appendToLore(String lore) {
        if (lore != null) {
            if (this.lore == null) {
                this.lore = new ArrayList<>();
            }

            this.lore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        return this;
    }

    /**
     * Adds a list of strings to the current lore
     *
     * @param strings strings to add to the lore
     * @return this builder instance
     */
    public ItemBuilder appendToLore(Collection<String> strings) {
        if (strings != null) {
            if (this.lore == null) {
                this.lore = new ArrayList<String>();
            }

            for (String string : strings) {
                this.lore.add(ChatColor.translateAlternateColorCodes('&', string));
            }
        }

        return this;
    }

    /**
     * Add an enchantment to the item
     *
     * @param enchantment The enchantment
     * @param level       The level of enchantment
     * @return this builder instance
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, Integer level) {
        if (enchantment != null) {
            if (enchantments == null) {
                enchantments = new HashMap<>();
            }

            enchantments.put(enchantment, level);
        }
        return this;
    }

    /**
     * Adds an ItemFlag to the item
     *
     * @param flag the flag
     * @return this builder instance
     */
    public ItemBuilder addItemFlag(ItemFlag flag) {
        if (flag != null) {
            if (flags == null) {
                flags = new ArrayList<>();
            }

            flags.add(flag);
        }
        return this;
    }

    /**
     * Builds and returns the resulting ItemStack
     *
     * @return The ItemStack created
     */
    public ItemStack buildItemStack() {
        ItemStack item = new ItemStack(material, amount, durability);
        ItemMeta meta = item.getItemMeta();

        // Set the lore
        if (lore != null) {
            meta.setLore(lore);
        }

        // Set the display name
        if (displayName != null) {
            meta.setDisplayName(displayName);
        }

        // Add enchantments unsafely
        if (enchantments != null && !enchantments.isEmpty()) {
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
        }

        if (flags != null && !flags.isEmpty()) {
            meta.addItemFlags(flags.toArray(new ItemFlag[flags.size()]));
        }

        item.setItemMeta(meta);
        return item;
    }
}
