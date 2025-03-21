package ru.yawlick.brownylib.common.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;

public final class ItemUtil {
    public static void name(ItemStack itemStack, String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
    }

    public static void lore(ItemStack itemStack, String lore) {
        ItemMeta meta = itemStack.getItemMeta();
        String[] lines = lore.split("\n");
        meta.setLore(List.of(lines));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_STORED_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
    }

    public static void writeNBT(ItemStack itemStack, String key, Object value) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(value instanceof String) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.STRING, (String) value);
        } else if(value instanceof Integer) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.INTEGER, (int) value);
        } else if(value instanceof Float) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.FLOAT, (float) value);
        } else if(value instanceof Long) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.LONG, (long) value);
        } else if(value instanceof Short) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.SHORT, (short) value);
        } else if(value instanceof Double) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.DOUBLE, (double) value);
        } else if(value instanceof PersistentDataContainer[]) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER_ARRAY, (PersistentDataContainer[]) value);
        } else if(value instanceof PersistentDataContainer) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER, (PersistentDataContainer) value);
        } else if(value instanceof UUID) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.STRING, ((UUID) value).toString());
        }
        itemStack.setItemMeta(meta);
    }

    public static <T> T readNBT(ItemStack itemStack, String key, T type) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(type instanceof String || type instanceof UUID) {
            return (T) data.get(NamespacedKey.fromString(key), PersistentDataType.STRING);
        } else if(type instanceof Integer) {
            return (T) data.get(NamespacedKey.fromString(key), PersistentDataType.INTEGER);
        } else if(type instanceof Float) {
            return (T) data.get(NamespacedKey.fromString(key), PersistentDataType.FLOAT);
        } else if(type instanceof Long) {
            return (T) data.get(NamespacedKey.fromString(key), PersistentDataType.LONG);
        } else if(type instanceof Short) {
            return (T) data.get(NamespacedKey.fromString(key), PersistentDataType.SHORT);
        } else if(type instanceof Double) {
            return (T) data.get(NamespacedKey.fromString(key), PersistentDataType.DOUBLE);
        } else if(type instanceof PersistentDataContainer[]) {
            return (T) data.get(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER_ARRAY);
        } else if(type instanceof PersistentDataContainer) {
            return (T) data.get(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER);
        }
        return null;
    }

    public static boolean hasNBT(ItemStack itemStack, String key, Object type) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(type instanceof String || type instanceof UUID) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.STRING);
        } else if(type instanceof Integer) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.INTEGER);
        } else if(type instanceof Float) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.FLOAT);
        } else if(type instanceof Long) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.LONG);
        } else if(type instanceof Short) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.SHORT);
        } else if(type instanceof Double) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.DOUBLE);
        } else if(type instanceof PersistentDataContainer[]) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER_ARRAY);
        } else if(type instanceof PersistentDataContainer) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER);
        }
        return false;
    }
}
