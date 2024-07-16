package me.ruende.entityai.entity.control;

import me.ruende.entityai.entity.summon.EntitySummoner;
import me.ruende.entityai.messages.Messages;
import me.ruende.entityai.utils.TargetingHelper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.List;

public class AttackControl {
    private final TargetingHelper targetingHelper;
    private final EntitySummoner entitySummoner;

    public AttackControl(TargetingHelper targetingHelper, EntitySummoner entitySummoner) {
        this.targetingHelper = targetingHelper;
        this.entitySummoner = entitySummoner;
    }

    public void attack(Player player) {
        LivingEntity targetEntity = targetingHelper.getTargetEntity(player);
        if (targetEntity == null || targetEntity instanceof Player || isSummonedEntity(targetEntity)) {
            player.sendMessage(Messages.INVALID_TARGET_ENTITY);
            return;
        }

        ControlState.getInstance().setAttack(true);

        List<LivingEntity> summonedEntities = entitySummoner.getSummonedEntities();
        for (LivingEntity entity : summonedEntities) {
            if (entity instanceof Mob mob) {
                mob.setTarget(targetEntity);
            }
        }
    }

    private boolean isSummonedEntity(LivingEntity entity) {
        return entitySummoner.getSummonedEntities().stream()
                .anyMatch(summonedEntity -> summonedEntity.getUniqueId().equals(entity.getUniqueId()));
    }
}
