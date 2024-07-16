package me.ruende.entityai.handler;

import me.ruende.entityai.EntityAI;
import me.ruende.entityai.items.staff.Staff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class StaffUseHandler implements Listener {
    private final Staff staff;
    private final ActionHandler actionHandler;

    public StaffUseHandler() {
        this.staff = new Staff();
        this.actionHandler = new ActionHandler();
        EntityAI.getInstance().getServer().getPluginManager().registerEvents(this, EntityAI.getInstance());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!staff.isStaff(item)) {
            return;
        }

        actionHandler.handleInteraction(event, player);
    }
}
