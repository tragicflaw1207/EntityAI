package me.ruende.entityai.utils;

import me.ruende.entityai.messages.LogMessages;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Mob;

import java.lang.reflect.Field;
import java.util.Set;

public class AIHelper {
    private static final Field GOALS_FIELD;

    static {
        try {
            GOALS_FIELD = PathfinderGoalSelector.class.getDeclaredField("d");
            GOALS_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(LogMessages.FAILED_INITIALIZE_FIELD, e);
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void clearPathfinderGoals(Mob mob) {
        try {
            EntityInsentient nmsEntity = (EntityInsentient) ((CraftLivingEntity) mob).getHandle();
            clearGoals(getGoalSelector(nmsEntity, "bO"));
            clearGoals(getGoalSelector(nmsEntity, "bP"));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private static PathfinderGoalSelector getGoalSelector(EntityInsentient nmsEntity, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = EntityInsentient.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (PathfinderGoalSelector) field.get(nmsEntity);
    }

    private static void clearGoals(PathfinderGoalSelector selector) throws IllegalAccessException {
        ((Set<?>) GOALS_FIELD.get(selector)).clear();
    }
}
