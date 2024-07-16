package me.ruende.entityai.entity.control;

import me.ruende.entityai.EntityAI;
import me.ruende.entityai.entity.summon.EntitySummoner;
import me.ruende.entityai.messages.Messages;
import me.ruende.entityai.utils.TargetingHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class MovementControl {
    private final EntitySummoner entitySummoner;
    private final TargetingHelper targetingHelper;
    private Location currentLocation;

    public MovementControl(EntitySummoner entitySummoner, TargetingHelper targetingHelper) {
        this.entitySummoner = entitySummoner;
        this.targetingHelper = targetingHelper;
    }

    public void move(Player player) {
        Block targetBlock = targetingHelper.getTargetBlock(player);
        if (targetBlock == null || targetBlock.getType() == Material.AIR) {
            player.sendMessage(Messages.INVALID_TARGET_BLOCK);
            return;
        }

        ControlState.getInstance().setMovement(true);

        currentLocation = targetBlock.getLocation().add(0, 1, 0);

        List<LivingEntity> summonedEntities = entitySummoner.getSummonedEntities();
        if (summonedEntities.isEmpty()) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!ControlState.getInstance().isMovement()) {
                    this.cancel();
                    return;
                }

                for (LivingEntity entity : summonedEntities) {
                    if (entity instanceof Mob mob && !entity.isDead()) {
                        moveToTarget(mob, currentLocation);
                    }
                }
            }
        }.runTaskTimer(EntityAI.getInstance(), 0L, 1L);
    }

    private void moveToTarget(Mob mob, Location targetLocation) {
        if (mob.getLocation().distance(targetLocation) > 1.5) {
            mob.setTarget(null);
            mob.getPathfinder().moveTo(targetLocation);
        } else {
            ControlState.getInstance().setMovement(false);
        }
    }
}
