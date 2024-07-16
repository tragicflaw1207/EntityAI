package me.ruende.entityai.items.circle;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("UnreachableCode")
public class MagicCircleFactory {
    public ItemStack createCircle() {
        CustomStack stack = CustomStack.getInstance("magic_staff:magic_circle");
        if (stack == null) {
            return null;
        }
        return stack.getItemStack();
    }
}