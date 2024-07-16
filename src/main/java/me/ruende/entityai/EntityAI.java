package me.ruende.entityai;

import me.ruende.entityai.commands.CommandRegistry;
import me.ruende.entityai.handler.StaffUseHandler;
import me.ruende.entityai.items.staff.Staff;
import me.ruende.entityai.messages.LogMessages;
import org.bukkit.plugin.java.JavaPlugin;

public final class EntityAI extends JavaPlugin {
    private static EntityAI instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info(LogMessages.ENABLE_PLUGIN);

        new CommandRegistry().registerCommands(new Staff());
        new StaffUseHandler();
    }

    @Override
    public void onDisable() {
        getLogger().info(LogMessages.DISABLE_PLUGIN);
        instance = null;
    }

    public static EntityAI getInstance() {
        return instance;
    }
}
