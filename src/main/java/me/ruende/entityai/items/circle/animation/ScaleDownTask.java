package me.ruende.entityai.items.circle.animation;

import org.bukkit.entity.ItemDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import org.bukkit.util.Transformation;

public class ScaleDownTask extends BukkitRunnable {
    private final ItemDisplay itemDisplay;
    private final int scaleDownDuration;
    private float currentScale;
    private int ticks = 0;

    public ScaleDownTask(ItemDisplay itemDisplay) {
        this.itemDisplay = itemDisplay;
        this.scaleDownDuration = MagicCircleAnimationConfig.SCALE_DOWN_DURATION;
        this.currentScale = MagicCircleAnimationConfig.FINAL_SCALE;
    }

    @Override
    public void run() {
        if (itemDisplay.isDead() || ticks >= scaleDownDuration) {
            if (ticks >= scaleDownDuration) {
                itemDisplay.remove();
            }
            this.cancel();
            return;
        }

        updateCurrentScale();
        itemDisplay.setTransformation(createTransformation());
        ticks++;
    }

    private void updateCurrentScale() {
        currentScale -= (MagicCircleAnimationConfig.FINAL_SCALE - MagicCircleAnimationConfig.INITIAL_SCALE) / scaleDownDuration;
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
