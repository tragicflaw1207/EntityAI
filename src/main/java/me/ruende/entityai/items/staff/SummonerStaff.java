package me.ruende.entityai.items.staff;

import com.google.common.collect.Maps;
import me.ruende.entityai.actions.*;
import me.ruende.entityai.entity.control.AttackControl;
import me.ruende.entityai.entity.control.MovementControl;
import me.ruende.entityai.entity.control.SummonControl;
import me.ruende.entityai.entity.control.SurroundControl;
import me.ruende.entityai.entity.summon.EntitySummoner;
import me.ruende.entityai.items.circle.Circle;
import me.ruende.entityai.items.providor.ItemsAdderProvider;
import me.ruende.entityai.sound.SoundPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

/**
 * Staff 추상클래스를 상속받아 다양한 스태프를 구현한다.
 */
public class SummonerStaff extends Staff {

    // 소환한 몬스터 목록은 플레이어마다 존재해야 한다.
    private static final Map<UUID, EntitySummoner> entitySummonerHolder = Maps.newConcurrentMap();

    // 해당 클래스도 충분히 리팩토링 할 수 있을것이다.
    private final Circle circle;
    private final SoundPlayer soundPlayer;

    public SummonerStaff() {
        // 스태프의 아이디는 스태프마다 고유하다. 그렇다면 굳이 외부에서 주입받을 필요는 없다.
        // ItemProvider 또한 외부에서 주입받을 필요는 없다. 초기 설정에 따라 기본값을 제공한다.
        // 나중에 기능이 변경된다면 그 때 변경하면 된다.
        super("magic_staff:magic_staff", "magic_staff", new ItemsAdderProvider());

        // 작동에 필요한 필드를 초기화 한다.
        this.circle = new Circle();
        this.soundPlayer = new SoundPlayer();
    }

    @Override
    protected void runLeftAction(@NotNull Player player) {
        // 왼쪽 클릭 액션에 소환 액션을 바인드 한다.
        final EntitySummoner entitySummoner = entitySummonerHolder.computeIfAbsent(
                player.getUniqueId(), k -> new EntitySummoner());
        final SummonControl control = new SummonControl(entitySummoner, circle, soundPlayer);
        final Action action = new SummonAction(control);
        action.execute(player);
    }

    @Override
    protected void runRightAction(@NotNull Player player) {
        // 오른쪽 클릭에 이동 액션을 바인드 한다.
        final EntitySummoner entitySummoner = entitySummonerHolder.computeIfAbsent(
                player.getUniqueId(), k -> new EntitySummoner());
        final MovementControl control = new MovementControl(entitySummoner);
        final Action action = new MovementAction(control);
        action.execute(player);
    }

    @Override
    protected void runShiftLeftAction(@NotNull Player player) {
        // 쉬프트-왼쪽 클릭에 공격 액션을 바인드 한다.
        final EntitySummoner entitySummoner = entitySummonerHolder.computeIfAbsent(
                player.getUniqueId(), k -> new EntitySummoner());
        final AttackControl control = new AttackControl(entitySummoner);
        final Action action = new AttackAction(control);
        action.execute(player);
    }

    @Override
    protected void runShiftRightAction(@NotNull Player player) {
        // 쉬프트-오른쪽 클릭에 포위 액션을 바인드 한다.
        final EntitySummoner entitySummoner = entitySummonerHolder.computeIfAbsent(
                player.getUniqueId(), k -> new EntitySummoner());
        final SurroundControl control = new SurroundControl(entitySummoner);
        final Action action = new SurroundAction(control);
        action.execute(player);
    }

}
