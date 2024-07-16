package me.ruende.entityai.items.staff;

import me.ruende.entityai.actions.Action;
import me.ruende.entityai.actions.FireballAction;
import me.ruende.entityai.actions.FirewallAction;
import me.ruende.entityai.items.providor.OraxenProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Staff 추상클래스를 상속받아 다양한 스태프를 구현한다.
 */
public class PyromancerStaff extends Staff {

    public PyromancerStaff() {
        // 스태프의 아이디는 스태프마다 고유하다. 그렇다면 굳이 외부에서 주입받을 필요는 없다.
        // ItemProvider 또한 외부에서 주입받을 필요는 없다. 초기 설정에 따라 기본값을 제공한다.
        // 나중에 기능이 변경된다면 그 때 변경하면 된다.
        super("magic_staff:pyromancer_staff", "pyromancer_staff", new OraxenProvider());
    }

    @Override
    protected void runLeftAction(@NotNull Player player) {
        // 왼쪽 클릭에 파이어볼 액션을 바인드 한다.
        final Action action = new FireballAction();
        action.execute(player);
    }

    @Override
    protected void runRightAction(@NotNull Player player) {
        // 오른쪽 클릭에 파이어월 액션을 바인드 한다.
        final Action action = new FirewallAction();
        action.execute(player);
    }

    @Override
    protected void runShiftLeftAction(@NotNull Player player) {
        // 아무런 액션도 바인드하지 않는다.
    }

    @Override
    protected void runShiftRightAction(@NotNull Player player) {
        // 아무런 액션도 바인드하지 않는다.
    }

}
