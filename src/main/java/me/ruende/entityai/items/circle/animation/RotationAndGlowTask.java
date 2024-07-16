package me.ruende.entityai.items.circle.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import org.bukkit.util.Transformation;
import me.ruende.entityai.EntityAI;

public class RotationAndGlowTask extends BukkitRunnable {
    private final ItemDisplay itemDisplay;
    private final Location location;
    private final Particle.DustOptions dustOptions;
    private final int rotationDuration;
    private final float rotationPerTick;
    private final Vector3f scale;
    private final AxisAngle4f rotationAxis;
    private int ticks = 0;

    public RotationAndGlowTask(ItemDisplay itemDisplay) {
        this.itemDisplay = itemDisplay;
        this.location = itemDisplay.getLocation();
        this.rotationDuration = MagicCircleAnimationConfig.ROTATION_DURATION;
        this.rotationPerTick = (float) (2 * Math.PI * MagicCircleAnimationConfig.ROTATIONS / rotationDuration);
        this.scale = new Vector3f(MagicCircleAnimationConfig.FINAL_SCALE, MagicCircleAnimationConfig.FINAL_SCALE, MagicCircleAnimationConfig.FINAL_SCALE);
        this.rotationAxis = new AxisAngle4f();
        this.dustOptions = new Particle.DustOptions(org.bukkit.Color.fromRGB(255, (int) (255 * ((float) ticks / rotationDuration)), (int) (255 * ((float) ticks / rotationDuration))), 1);
    }

    @Override
    public void run() {
        if (itemDisplay.isDead() || ticks >= rotationDuration) {
            if (ticks >= rotationDuration) {
                new BrightGlowTask(itemDisplay).runTaskTimer(EntityAI.getInstance(), 0L, 1L);
            }
            this.cancel();
            return;
        }

        itemDisplay.setTransformation(createTransformation());
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.5, 0.5, 0.5, dustOptions);
        ticks++;
    }

    private Transformation createTransformation() {
        float rotationAngle = rotationPerTick * ticks;
        return new Transformation(
                new Vector3f(),
                new AxisAngle4f(rotationAngle, rotationAxis.x, rotationAxis.y, rotationAxis.z),
                scale,
                new AxisAngle4f()
        );
    }
}
