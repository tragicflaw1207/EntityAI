package me.ruende.entityai;

import me.ruende.entityai.commands.StaffCommand;
import me.ruende.entityai.handler.StaffHandler;
import me.ruende.entityai.items.staff.PyromancerStaff;
import me.ruende.entityai.items.staff.SummonerStaff;
import me.ruende.entityai.messages.LogMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class EntityAI extends JavaPlugin {
    private static EntityAI instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info(LogMessages.ENABLE_PLUGIN);

        // ---------- 리스너 등록
        final PluginManager pm = Bukkit.getPluginManager();

        // 서머너 스태프 작동 핸들러 추가
        pm.registerEvents(new StaffHandler(new SummonerStaff()), this);

        // 파이로맨서 스태프 작동 핸들러 추가
        pm.registerEvents(new StaffHandler(new PyromancerStaff()), this);

        // 같은 방식으로 스태프를 쉽게 확장할 수 있다.


        // ---------- 커맨드 추가
        final CommandMap cm = Bukkit.getCommandMap();

        // 스태프 지급 커맨드 추가
        cm.register("staff", new StaffCommand("staff"));

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
