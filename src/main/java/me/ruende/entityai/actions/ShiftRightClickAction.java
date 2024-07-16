package me.ruende.entityai.actions;

import me.ruende.entityai.entity.control.SummonControl;
import org.bukkit.entity.Player;

public class ShiftRightClickAction implements ClickAction {
    private final SummonControl summonControl;

    public ShiftRightClickAction(SummonControl summonControl) {
        this.summonControl = summonControl;
    }

    @Override
    public void execute(Player player) {
        summonControl.summon(player);
    }
}
