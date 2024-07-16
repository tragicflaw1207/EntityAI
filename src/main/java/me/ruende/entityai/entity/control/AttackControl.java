package me.ruende.entityai.entity.control;

import me.ruende.entityai.entity.summon.EntitySummoner;
import me.ruende.entityai.messages.Messages;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AttackControl extends Control {

    private final EntitySummoner entitySummoner;

    public AttackControl(EntitySummoner entitySummoner) {
        this.entitySummoner = entitySummoner;
    }

    @Override
    public void execute(@NotNull Player player) {
        targetingHelper.getTargetEntity(player) // 타겟 엔티티 검색 후 반환
                .filter(entity -> !(entity instanceof Player)) // 타겟 엔티티가 플레이어가 아닌지 필터
                .filter(entity -> entitySummoner.getSummonedEntities() // 타겟 엔티티가 소환된 엔티티가 아닌지 필터
                        .stream()
                        .anyMatch(summonedEntity ->
                                summonedEntity.getUniqueId().equals(entity.getUniqueId()))
                )
                .ifPresentOrElse(target -> { // 모든 필터를 통과한 타겟이 존재한다면 로직 수행
                    ControlState.getInstance().setAttack(true);

                    List<LivingEntity> summonedEntities = entitySummoner.getSummonedEntities();
                    for (LivingEntity entity : summonedEntities) {
                        if (entity instanceof Mob mob) {
                            mob.setTarget(target);
                        }
                    }
                }, () -> {
                    // 타겟 엔티티가 없거나, 플레이어거나, 소환된 엔티티일 때
                    // 즉 필터를 통과하지 못했다면 실행
                    player.sendMessage(Messages.INVALID_TARGET_ENTITY);
                });

    }

}
