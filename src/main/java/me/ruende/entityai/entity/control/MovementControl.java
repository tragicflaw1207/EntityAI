package me.ruende.entityai.entity.control;

import me.ruende.entityai.EntityAI;
import me.ruende.entityai.entity.summon.EntitySummoner;
import me.ruende.entityai.messages.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovementControl extends Control {

    private final EntitySummoner entitySummoner;
    private Location currentLocation;

    public MovementControl(EntitySummoner entitySummoner) {
        this.entitySummoner = entitySummoner;
    }

    @Override
    public void execute(@NotNull Player player) {
        targetingHelper.getTargetBlock(player)
                .filter(block -> block.getType() != Material.AIR) // 공기 블록 필터
                .ifPresentOrElse(target -> {
                    // 모든 필터를 통과했다면 실행
                    move(target);
                }, () -> {
                    // 타겟 블록이 없거나, 타겟 블록이 공기라면 실행
                    player.sendMessage(Messages.INVALID_TARGET_BLOCK);
                });
    }

    private void move(@NotNull Block target) {
        ControlState.getInstance().setMovement(true);
        currentLocation = target.getLocation().add(0, 1, 0);

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
