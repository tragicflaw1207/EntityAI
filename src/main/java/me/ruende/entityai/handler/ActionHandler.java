package me.ruende.entityai.handler;

import me.ruende.entityai.actions.*;
import me.ruende.entityai.entity.control.*;
import me.ruende.entityai.entity.summon.EntitySummoner;
import me.ruende.entityai.items.circle.Circle;
import me.ruende.entityai.sound.SoundPlayer;
import me.ruende.entityai.utils.AnimationHelper;
import me.ruende.entityai.utils.HighlightHelper;
import me.ruende.entityai.utils.TargetingHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ActionHandler {
    private final ClickAction rightClickAction;
    private final ClickAction leftClickAction;
    private final ClickAction shiftRightClickAction;
    private final ClickAction shiftLeftClickAction;

    public ActionHandler() {
        TargetingHelper targetingHelper = new TargetingHelper();
        EntitySummoner entitySummoner = new EntitySummoner();
        Circle circle = new Circle();
        SoundPlayer soundPlayer = new SoundPlayer();

        HighlightHelper highlightHelper = new HighlightHelper(targetingHelper);
        new AnimationHelper(highlightHelper.getDisplayBlocks());

        MovementControl movementControl = new MovementControl(entitySummoner, targetingHelper);
        AttackControl attackControl = new AttackControl(targetingHelper, entitySummoner);
        SummonControl summonControl = new SummonControl(entitySummoner, targetingHelper, circle, soundPlayer);
        SurroundControl surroundControl = new SurroundControl(targetingHelper, entitySummoner);

        this.rightClickAction = new RightClickAction(movementControl);
        this.leftClickAction = new LeftClickAction(attackControl);
        this.shiftRightClickAction = new ShiftRightClickAction(summonControl);
        this.shiftLeftClickAction = new ShiftLeftClickAction(surroundControl);
    }

    public void handleInteraction(PlayerInteractEvent event, Player player) {
        ClickAction actionToExecute = getActionToExecute(event.getAction(), player.isSneaking());
        if (actionToExecute != null) {
            actionToExecute.execute(player);
        }
    }

    private ClickAction getActionToExecute(Action action, boolean isSneaking) {
        return switch (action) {
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> isSneaking ? shiftRightClickAction : rightClickAction;
            case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> isSneaking ? shiftLeftClickAction : leftClickAction;
            default -> null;
        };
    }
}
