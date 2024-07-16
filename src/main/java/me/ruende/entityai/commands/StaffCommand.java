package me.ruende.entityai.commands;

import me.ruende.entityai.items.staff.PyromancerStaff;
import me.ruende.entityai.items.staff.Staff;
import me.ruende.entityai.items.staff.SummonerStaff;
import me.ruende.entityai.messages.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffCommand extends BukkitCommand {

    public StaffCommand(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.PLAYER_ONLY_COMMAND);
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Messages.HOW_TO_USE_STAFF_COMMAND);
            return false;
        }

        // 스태프를 누구에게 얼마만큼 어디에 지급할지 결정하는것은
        // 스태프 객체 그 자체에서는 관심도 없고, 할 수도 없다.
        // 그렇다면 관심이 있는 객체에서 처리해야 한다.
        // 스태프를 지급하는 커맨드는, 스태프 지급에 관심이 있다. 여기서 처리하자.

        // 여기서 어떤 스태프를 가져올지 결정한다.
        Staff staff = null;
        switch (args[0].toLowerCase()) {
            case "summoner" -> staff = new SummonerStaff();
            case "pyromancer" -> staff = new PyromancerStaff();
        }

        // 가져온 스태프를 누구에게 얼마만큼 어디에 지급할지 결정한다.
        if (staff != null) {
            player.getInventory().addItem(staff.getItemStack());
        }

        return true;
    }
}
