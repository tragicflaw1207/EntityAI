package me.ruende.entityai.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class TargetingHelper {
    private static final int MAX_DISTANCE = 30;
    private static final PotionEffect GLOW_EFFECT = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, false, false);

    public Block getTargetBlock(Player player) {
        Block targetBlock = player.getTargetBlockExact(MAX_DISTANCE);
        return (targetBlock != null && targetBlock.getType() != Material.AIR) ? targetBlock : null;
    }

    public LivingEntity getTargetEntity(Player player) {
        RayTraceResult result = player.getWorld().rayTraceEntities(player.getEyeLocation(), player.getLocation().getDirection(), MAX_DISTANCE, entity -> !entity.equals(player));
        Entity entity = (result != null) ? result.getHitEntity() : null;

        if (entity instanceof LivingEntity && hasLineOfSight(player, (LivingEntity) entity)) {
            return (LivingEntity) entity;
        }

        return null;
    }

    private boolean hasLineOfSight(Player player, LivingEntity entity) {
        Vector direction = entity.getEyeLocation().toVector().subtract(player.getEyeLocation().toVector()).normalize();
        RayTraceResult rayTraceResult = player.getWorld().rayTraceBlocks(player.getEyeLocation(), direction, player.getEyeLocation().distance(entity.getEyeLocation()));
        return rayTraceResult == null;
    }

    public void applyGlowEffect(LivingEntity entity) {
        entity.addPotionEffect(GLOW_EFFECT);
    }

    public void removeGlowEffect(LivingEntity entity) {
        entity.removePotionEffect(PotionEffectType.GLOWING);
    }
}
