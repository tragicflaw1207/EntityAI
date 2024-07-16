package me.ruende.entityai.entity.control;

import me.ruende.entityai.EntityAI;
import me.ruende.entityai.entity.summon.EntitySummoner;
import me.ruende.entityai.messages.Messages;
import me.ruende.entityai.utils.TargetingHelper;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SurroundControl {
    private final TargetingHelper targetingHelper;
    private final EntitySummoner entitySummoner;
    private LivingEntity currentTarget;

    public SurroundControl(TargetingHelper targetingHelper, EntitySummoner entitySummoner) {
        this.targetingHelper = targetingHelper;
        this.entitySummoner = entitySummoner;
    }

    public void surround(Player player) {
        LivingEntity targetEntity = targetingHelper.getTargetEntity(player);
        if (targetEntity == null || targetEntity instanceof Player || isSummonedEntity(targetEntity)) {
            player.sendMessage(Messages.INVALID_TARGET_ENTITY);
            return;
        }

        ControlState.getInstance().setSurround(true);

        currentTarget = targetEntity;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (currentTarget == null || currentTarget.isDead() || !currentTarget.isValid() || !ControlState.getInstance().isSurround()) {
                    this.cancel();
                    return;
                }

                var summonedEntities = entitySummoner.getSummonedEntities().stream()
                        .filter(entity -> entity.isValid() && !entity.isDead())
                        .toList();
                int numEntities = summonedEntities.size();

                for (int i = 0; i < numEntities; i++) {
                    LivingEntity summonedEntity = summonedEntities.get(i);
                    if (summonedEntity instanceof Mob mob) {
                        Location surroundLocation = calculateSurroundingLocation(currentTarget.getLocation(), i, numEntities);
                        moveToTargetAndFace(mob, surroundLocation, currentTarget);
                    }
                }
                entitySummoner.getSummonedEntities().removeIf(entity -> !entity.isValid() || entity.isDead());
            }
        }.runTaskTimer(EntityAI.getInstance(), 0L, 1L);
    }

    private boolean isSummonedEntity(LivingEntity entity) {
        return entitySummoner.getSummonedEntities().stream()
                .anyMatch(summonedEntity -> summonedEntity.getUniqueId().equals(entity.getUniqueId()));
    }

    private Location calculateSurroundingLocation(Location center, int index, int total) {
        double angle = 2 * Math.PI * index / total;
        double RADIUS = 5.0; // 포위 반경
        double x = center.getX() + RADIUS * Math.cos(angle);
        double z = center.getZ() + RADIUS * Math.sin(angle);
        return new Location(center.getWorld(), x, center.getY(), z);
    }

    private void moveToTargetAndFace(Mob mob, Location targetLocation, LivingEntity targetEntity) {
        mob.setTarget(null);
        mob.getPathfinder().moveTo(targetLocation);
        mob.lookAt(targetEntity);
    }
}
