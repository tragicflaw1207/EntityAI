package me.ruende.entityai.entity.summon;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import me.ruende.entityai.messages.LogMessages;
import me.ruende.entityai.utils.AIHelper;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("resource")
public class EntitySummoner {
    private final List<ActiveMob> summonedEntities = new ArrayList<>();

    public LivingEntity summonEntity(String entityType, Location summonLocation) {
        MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(entityType).orElse(null);

        if (mythicMob == null) {
            System.out.println(LogMessages.ENTITY_TYPE_NOT_FOUND + entityType);
            return null;
        }

        ActiveMob activeMob = mythicMob.spawn(BukkitAdapter.adapt(summonLocation), 1);
        if (activeMob == null) {
            System.out.println(LogMessages.FAILED_TO_SPAWN_ENTITY + entityType);
            return null;
        }

        summonedEntities.add(activeMob);
        LivingEntity summonedEntity = (LivingEntity) activeMob.getEntity().getBukkitEntity();
        if (summonedEntity instanceof Mob summonedMob) {
            AIHelper.clearPathfinderGoals(summonedMob);
        }
        return summonedEntity;
    }

    public List<LivingEntity> getSummonedEntities() {
        return summonedEntities.stream()
                .map(activeMob -> (LivingEntity) activeMob.getEntity().getBukkitEntity())
                .collect(Collectors.toList());
    }
}
