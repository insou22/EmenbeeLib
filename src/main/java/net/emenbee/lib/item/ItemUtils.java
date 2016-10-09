package net.emenbee.lib.item;

import net.emenbee.lib.except.UtilInstantiationException;
import net.emenbee.lib.reflection.ConstructorAccessor;
import net.emenbee.lib.reflection.MethodAccessor;
import net.emenbee.lib.reflection.Reflection;
import org.bukkit.inventory.ItemStack;

public final class ItemUtils {

    private static final Class<?> NMS_ITEMSTACK = Reflection.getNMSClass("ItemStack");
    private static final Class<?> CRAFT_ITEM_STACK = Reflection.getOBCClass("CraftItemStack");
    private static final MethodAccessor<?> AS_NMS_COPY = Reflection.getMethod(CRAFT_ITEM_STACK, "asNMSCopy", ItemStack.class);
    private static final MethodAccessor<?> AS_CRAFT_MIRROR = Reflection.getMethod(CRAFT_ITEM_STACK, "asCraftMirror", NMS_ITEMSTACK);

    private static final Class<?> NBT_BASE = Reflection.getNMSClass("NBTBase");
    private static final Class<?> NBT_COMPOUND = Reflection.getNMSClass("NBTTagCompound");
    private static final Class<?> NBT_LIST = Reflection.getNMSClass("NBTTagList");

    private static final ConstructorAccessor<?> NBT_COMPOUND_NEW = Reflection.getConstructor(NBT_COMPOUND);
    private static final ConstructorAccessor<?> NBT_LIST_NEW = Reflection.getConstructor(NBT_LIST);

    private static final MethodAccessor<Boolean> HAS_TAG = Reflection.getMethod(NMS_ITEMSTACK, "hasTag");
    private static final MethodAccessor<?> SET_TAG = Reflection.getMethod(NMS_ITEMSTACK, "setTag", NBT_COMPOUND);
    private static final MethodAccessor<?> GET_TAG = Reflection.getMethod(NMS_ITEMSTACK, "getTag");

    private static final MethodAccessor<?> NBT_COMPOUND_SET = Reflection.getMethod(NBT_COMPOUND, "set", String.class, NBT_BASE);

    private ItemUtils() {
        throw new UtilInstantiationException();
    }

    public static ItemStack glow(ItemStack item) {
        Object nmsStack = AS_NMS_COPY.call(null, item);

        Object tag;
        if (!HAS_TAG.call(nmsStack)) {
            tag = NBT_COMPOUND_NEW.create();
            SET_TAG.call(nmsStack, tag);
        } else {
            tag = GET_TAG.call(nmsStack);
        }

        Object fakeEnchant = NBT_LIST_NEW.create();
        NBT_COMPOUND_SET.call(tag, "ench", fakeEnchant);
        SET_TAG.call(nmsStack, tag);

        return (ItemStack) AS_CRAFT_MIRROR.call(null, nmsStack);
    }

}
