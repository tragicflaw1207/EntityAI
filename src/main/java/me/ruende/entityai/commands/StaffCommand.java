package me.ruende.entityai.commands;

import me.ruende.entityai.items.staff.Staff;
import me.ruende.entityai.messages.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffCommand extends BukkitCommand {

    private final Staff staff;

    public StaffCommand(@NotNull String name, Staff staff) {
        super(name);
        this.staff = staff;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.PLAYER_ONLY_COMMAND);
            return false;
        }

        if (args.length != 0) {
            player.sendMessage(Messages.HOW_TO_USE_STAFF_COMMAND);
            return false;
        }

        staff.giveStaff(player);
        return true;
    }
}
