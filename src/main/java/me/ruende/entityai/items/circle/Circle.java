package me.ruende.entityai.items.circle;

import me.ruende.entityai.items.circle.animation.MagicCircleDisplay;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Circle {
    private final MagicCircleFactory factory;
    private final MagicCircleDisplay display;

    public Circle() {
        this.factory = new MagicCircleFactory();
        this.display = new MagicCircleDisplay();
    }

    public void createMagicCircle(Location location) {
        ItemStack itemStack = factory.createCircle();
        if (itemStack == null) {
            return;
        }
        display.displayMagicCircle(location, itemStack);
    }
}