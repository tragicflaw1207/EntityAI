package me.ruende.entityai.items.circle.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import me.ruende.entityai.EntityAI;

public class BrightGlowTask extends BukkitRunnable {
    private final ItemDisplay itemDisplay;
    private final Location location;
    private final Particle.DustOptions dustOptions;
    private final int brightGlowDuration;
    private int ticks = 0;

    public BrightGlowTask(ItemDisplay itemDisplay) {
        this.itemDisplay = itemDisplay;
        this.location = itemDisplay.getLocation();
        this.brightGlowDuration = MagicCircleAnimationConfig.BRIGHT_GLOW_DURATION;
        this.dustOptions = new Particle.DustOptions(org.bukkit.Color.fromRGB(255, 255, 255), 1);
    }

    @Override
    public void run() {
        if (itemDisplay.isDead() || ticks >= brightGlowDuration) {
            if (ticks >= brightGlowDuration) {
                new ScaleDownTask(itemDisplay).runTaskTimer(EntityAI.getInstance(), 0L, 1L);
            }
            this.cancel();
            return;
        }

        location.getWorld().spawnParticle(Particle.REDSTONE, location, 20, 0.5, 0.5, 0.5, dustOptions);
        ticks++;
    }
}