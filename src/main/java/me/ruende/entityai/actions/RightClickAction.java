package me.ruende.entityai.actions;

import me.ruende.entityai.entity.control.MovementControl;
import org.bukkit.entity.Player;

public class RightClickAction implements ClickAction {
    private final MovementControl movementControl;

    public RightClickAction(MovementControl movementControl) {
        this.movementControl = movementControl;
    }

    @Override
    public void execute(Player player) {
        movementControl.move(player);
    }
}