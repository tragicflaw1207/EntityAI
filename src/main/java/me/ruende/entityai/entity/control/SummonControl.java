package me.ruende.entityai.entity.control;

import me.ruende.entityai.EntityAI;
import me.ruende.entityai.entity.summon.EntitySummoner;
import me.ruende.entityai.items.circle.Circle;
import me.ruende.entityai.messages.Messages;
import me.ruende.entityai.sound.SoundPlayer;
import me.ruende.entityai.utils.TargetingHelper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SummonControl {
    private final EntitySummoner entitySummoner;
    private final TargetingHelper blockTargetingHelper;
    private final Circle circle;
    private final SoundPlayer soundPlayer;

    public SummonControl(EntitySummoner entitySummoner, TargetingHelper blockTargetingHelper, Circle circle, SoundPlayer soundPlayer) {
        this.entitySummoner = entitySummoner;
        this.blockTargetingHelper = blockTargetingHelper;
        this.circle = circle;
        this.soundPlayer = soundPlayer;
    }

    public void summon(Player player) {
        Block targetBlock = blockTargetingHelper.getTargetBlock(player);
        if (targetBlock == null) {
            player.sendMessage(Messages.INVALID_TARGET_BLOCK);
            return;
        }

        Location summonLocation = targetBlock.getLocation().add(0.5, 1, 0.5);
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
    }
}
