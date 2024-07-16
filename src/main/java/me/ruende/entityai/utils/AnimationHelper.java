package me.ruende.entityai.utils;

import me.ruende.entityai.EntityAI;
import me.ruende.entityai.items.staff.Staff;
import org.bukkit.Bukkit;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AnimationHelper {
    private int tickCounter;
    private final List<BlockDisplay> displayBlocks;

    public AnimationHelper(List<BlockDisplay> displayBlocks) {
        this.displayBlocks = displayBlocks;
        this.tickCounter = 0;
        startAnimationTask();
    }

    public void animateHighlight(float offset) {
        for (BlockDisplay displayBlock : displayBlocks) {
            if (displayBlock != null && !displayBlock.isDead()) {
                org.bukkit.Location loc = displayBlock.getLocation();
                loc.setY(loc.getY() + offset);
                displayBlock.teleport(loc);
            }
        }
    }

    private void startAnimationTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                tickCounter++;
                float offset = (float) Math.sin(tickCounter * 0.1) * 0.008f;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (new Staff().isStaff(item)) {
                        animateHighlight(offset);
                    }
                }
            }
        }.runTaskTimer(EntityAI.getInstance(), 0L, 1L);
    }
}
