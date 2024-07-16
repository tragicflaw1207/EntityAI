package me.ruende.entityai.utils;

import me.ruende.entityai.EntityAI;
import me.ruende.entityai.items.staff.SummonerStaff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HighlightHelper {
    private final List<BlockDisplay> displayBlocks = new ArrayList<>();
    private Location lastLocation;
    private LivingEntity lastEntity;
    private final TargetingHelper targetingHelper;

    public HighlightHelper(TargetingHelper targetingHelper) {
        this.targetingHelper = targetingHelper;
        startHighlightTask();
    }

    public void updateTargetHighlight(Player player) {
        Optional<LivingEntity> targetEntity = targetingHelper.getTargetEntity(player);
        Optional<Block> targetBlock = targetingHelper.getTargetBlock(player);

        if (targetEntity.isPresent()) {
            updateHighlight(targetEntity.get(), null);
        } else if (targetBlock.isPresent()) {
            Location targetLocation = targetBlock.get().getLocation().add(0, 1, 0);
            updateHighlight(null, targetLocation);
        } else {
            clearHighlight();
        }
    }

    private void updateHighlight(LivingEntity targetEntity, Location targetLocation) {
        if (targetEntity != null && !Objects.equals(targetEntity, lastEntity)) {
            clearHighlight();
            targetingHelper.applyGlowEffect(targetEntity);
            lastEntity = targetEntity;
            lastLocation = null;
        } else if (targetLocation != null && !Objects.equals(targetLocation, lastLocation)) {
            clearHighlight();
            showBlockHighlight(targetLocation);
            lastLocation = targetLocation;
            lastEntity = null;
        }
    }

    private void showBlockHighlight(Location location) {
        clearHighlight();
        createBlockDisplays(location);
    }

    private void createBlockDisplays(Location location) {
        clearHighlight();
        Vector3f[] offsets = {
                new Vector3f(0.45f, 0.2f, 0.45f),
                new Vector3f(0.35f, 0.3f, 0.35f),
                new Vector3f(0.45f, 0.35f, 0.45f),
                new Vector3f(0.4f, 0.25f, 0.4f)
        };
        Vector3f[] scales = {
                new Vector3f(0.1f, 0.1f, 0.1f),
                new Vector3f(0.3f, 0.05f, 0.3f),
                new Vector3f(0.1f, 0.35f, 0.1f),
                new Vector3f(0.2f, 0.05f, 0.2f)
        };

        for (int i = 0; i < offsets.length; i++) {
            createBlockDisplay(location, offsets[i], scales[i]);
        }
    }

    private void createBlockDisplay(Location location, Vector3f offset, Vector3f scale) {
        Location displayLocation = location.clone().add(offset.x, offset.y, offset.z);
        BlockDisplay blockDisplay = (BlockDisplay) location.getWorld().spawnEntity(displayLocation, EntityType.BLOCK_DISPLAY);

        blockDisplay.setBlock(Material.YELLOW_CONCRETE.createBlockData());
        Transformation transformation = new Transformation(new Vector3f(), new AxisAngle4f(), scale, new AxisAngle4f());
        blockDisplay.setTransformation(transformation);
        blockDisplay.setPersistent(false);

        displayBlocks.add(blockDisplay);
    }

    public void clearHighlight() {
        if (lastEntity != null) {
            targetingHelper.removeGlowEffect(lastEntity);
            lastEntity = null;
        }
        for (BlockDisplay displayBlock : displayBlocks) {
            if (displayBlock != null && !displayBlock.isDead()) {
                displayBlock.remove();
            }
        }
        displayBlocks.clear();
        lastLocation = null;
    }

    private void startHighlightTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (new SummonerStaff().isStaff(item)) {
                        updateTargetHighlight(player);
                    } else {
                        clearHighlight();
                    }
                }
            }
        }.runTaskTimer(EntityAI.getInstance(), 0L, 1L);
    }

    public List<BlockDisplay> getDisplayBlocks() {
        return displayBlocks;
    }
}
