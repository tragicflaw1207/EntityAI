package me.ruende.entityai.items.staff;

import dev.lone.itemsadder.api.CustomStack;
import me.ruende.entityai.messages.Messages;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("UnreachableCode")
public class Staff {
    public void giveStaff(Player player) {
        CustomStack stack = CustomStack.getInstance("magic_staff:magic_staff");
        if (stack == null) {
            player.sendMessage(Messages.ITEM_NOT_FOUND);
            return;
        }
        ItemStack itemStack = stack.getItemStack();

        player.getInventory().addItem(itemStack);
        player.sendMessage(Messages.GET_MAGIC_STAFF);
    }

    public boolean isStaff(ItemStack item) {
        CustomStack stack = CustomStack.byItemStack(item);
        if (stack == null) {
            return false;
        }
        return stack.getId().equals("magic_staff");
    }
}
