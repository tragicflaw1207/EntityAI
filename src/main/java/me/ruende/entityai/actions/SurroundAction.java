package me.ruende.entityai.actions;

import me.ruende.entityai.entity.control.SurroundControl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * 포위 액션
 */
public class SurroundAction extends Action {

    private final SurroundControl control;

    public SurroundAction(SurroundControl control) {
        this.control = control;
    }

    @Override
    public void execute(@NotNull Player player) {
        // 단순 컨트롤이 아닌 다양한 컨트롤의 복합체가 액션의 작동으로 올 수 있으므로
        // 기능을 트리거 시키는 액션과, 기능의 세부적인 작동을 정의하는 컨트롤을 분리하여 재사용성을 높히는것이 적합하다.
        control.execute(player);

        // ex) otherControl.execute(player);
        // ex) anotherControl.execute(player);
    }
}
