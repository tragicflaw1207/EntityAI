package me.ruende.entityai.entity.control;

import me.ruende.entityai.EntityAI;
import me.ruende.entityai.entity.summon.EntitySummoner;
import me.ruende.entityai.items.circle.Circle;
import me.ruende.entityai.messages.Messages;
import me.ruende.entityai.sound.SoundPlayer;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class SummonControl extends Control {

    private final EntitySummoner entitySummoner;
    private final Circle circle;
    private final SoundPlayer soundPlayer;

    public SummonControl(EntitySummoner entitySummoner, Circle circle, SoundPlayer soundPlayer) {
        this.entitySummoner = entitySummoner;
        this.circle = circle;
        this.soundPlayer = soundPlayer;
    }

    @Override
    public void execute(@NotNull Player player) {
        targetingHelper.getTargetBlock(player).ifPresentOrElse(target -> {
            Location summonLocation = target.getLocation().add(0.5, 1, 0.5);
            circle.createMagicCircle(summonLocation);

            new BukkitRunnable() {
                @Override
                public void run() {
                    LivingEntity summonedEntity = entitySummoner.summonEntity("SkeletalKnight", summonLocation);
                    if (summonedEntity != null) {
                        soundPlayer.playSummonSound(summonLocation);
                    }
                }
            }.runTaskLater(EntityAI.getInstance(), 80L);
        }, () -> {
            player.sendMessage(Messages.INVALID_TARGET_BLOCK);
        });
    }

}
