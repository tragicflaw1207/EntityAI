package me.ruende.entityai.commands;

import me.ruende.entityai.items.staff.Staff;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

public class CommandRegistry {
    private final CommandMap commandMap;

    public CommandRegistry() {
        this.commandMap = Bukkit.getCommandMap();
    }

    public void registerCommands(Staff staff) {
        commandMap.register("staff", new StaffCommand("staff", staff));
    }
}
