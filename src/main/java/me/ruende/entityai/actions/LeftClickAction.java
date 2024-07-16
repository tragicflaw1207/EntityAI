package me.ruende.entityai.actions;

import me.ruende.entityai.entity.control.AttackControl;
import org.bukkit.entity.Player;

public class LeftClickAction implements ClickAction {
    private final AttackControl attackControl;

    public LeftClickAction(AttackControl attackControl) {
        this.attackControl = attackControl;
    }

    @Override
    public void execute(Player player) {
        attackControl.attack(player);
    }
}