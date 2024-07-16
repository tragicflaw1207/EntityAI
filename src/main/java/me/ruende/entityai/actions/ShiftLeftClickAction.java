package me.ruende.entityai.actions;

import me.ruende.entityai.entity.control.SurroundControl;
import org.bukkit.entity.Player;

public class ShiftLeftClickAction implements ClickAction {
    private final SurroundControl surroundControl;

    public ShiftLeftClickAction(SurroundControl surroundControl) {
        this.surroundControl = surroundControl;
    }

    @Override
    public void execute(Player player) {
        surroundControl.surround(player);
    }
}
