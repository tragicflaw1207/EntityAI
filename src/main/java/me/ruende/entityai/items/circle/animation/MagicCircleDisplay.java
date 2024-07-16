package me.ruende.entityai.items.circle.animation;

import me.ruende.entityai.EntityAI;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.EntityType;

public class MagicCircleDisplay {

    public void displayMagicCircle(Location location, ItemStack itemStack) {
        ItemDisplay itemDisplay = createItemDisplay(location, itemStack);
        new ScaleUpTask(itemDisplay).runTaskTimer(EntityAI.getInstance(), 0L, 1L);
    }

    private ItemDisplay createItemDisplay(Location location, ItemStack itemStack) {
        ItemDisplay itemDisplay = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
        itemDisplay.setItemStack(itemStack);
        itemDisplay.setRotation(0f, 90f);
        itemDisplay.setPersistent(false);
        return itemDisplay;
    }
}
