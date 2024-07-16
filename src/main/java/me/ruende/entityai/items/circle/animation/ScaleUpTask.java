package me.ruende.entityai.items.circle.animation;

import org.bukkit.entity.ItemDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import org.bukkit.util.Transformation;
import me.ruende.entityai.EntityAI;

public class ScaleUpTask extends BukkitRunnable {
    private final ItemDisplay itemDisplay;
    private final int scaleUpDuration;
    private float currentScale;
    private int ticks = 0;

    public ScaleUpTask(ItemDisplay itemDisplay) {
        this.itemDisplay = itemDisplay;
        this.scaleUpDuration = MagicCircleAnimationConfig.SCALE_UP_DURATION;
        this.currentScale = MagicCircleAnimationConfig.INITIAL_SCALE;
    }

    @Override
    public void run() {
        if (itemDisplay.isDead() || ticks >= scaleUpDuration) {
            if (ticks >= scaleUpDuration) {
                new RotationAndGlowTask(itemDisplay).runTaskTimer(EntityAI.getInstance(), 0L, 1L);
            }
            this.cancel();
            return;
        }

        updateCurrentScale();
        itemDisplay.setTransformation(createTransformation());
        ticks++;
    }

    private void updateCurrentScale() {
        currentScale += (MagicCircleAnimationConfig.FINAL_SCALE - MagicCircleAnimationConfig.INITIAL_SCALE) / scaleUpDuration;
    }

    private Transformation createTransformation() {
        return new Transformation(
                new Vector3f(),
                new AxisAngle4f(),
                new Vector3f(currentScale, currentScale, currentScale),
                new AxisAngle4f()
        );
    }
}
