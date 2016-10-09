package net.emenbee.lib.item;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ItemBuilder implements Cloneable {

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    @Getter private ItemStack itemStack;
    @Getter private ItemMeta itemMeta;

    public ItemBuilder() {
        itemStack = new ItemStack(Material.AIR);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder setMaterialData(MaterialData data) {
        itemStack.setData(data);
        return this;
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... lines) {
        itemMeta.setLore(Arrays.asList(lines));
        return this;
    }

    public ItemBuilder setLore(List<String> lines) {
        itemMeta.setLore(lines);
        return this;
    }

    public ItemBuilder setLore(Supplier<List<String>> lines) {
        itemMeta.setLore(lines.get());
        return this;
    }

    public ItemBuilder withItemFlags(ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemBuilder clone() {
        try {
            ItemBuilder builder = (ItemBuilder) super.clone();
            if (this.itemStack != null) {
                builder.itemStack = this.itemStack.clone();
            }
            if (this.itemMeta != null) {
                builder.itemMeta = this.itemMeta.clone();
            }
            return builder;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
