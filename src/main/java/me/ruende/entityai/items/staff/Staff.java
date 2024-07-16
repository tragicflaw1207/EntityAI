package me.ruende.entityai.items.staff;

import me.ruende.entityai.actions.ActionType;
import me.ruende.entityai.items.providor.ItemProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 스태프를 관리하는 추상 클래스
 * <p>스태프 아이템을 생성하고, 특정 아이템이 스태프 아이템인지 확인한다.
 * <p>스태프 아이템을 플레이어에게 지급하는 로직은, 스태프 객체는 몰라도 문제 없다.
 * <p>스태프의 동작은 각 스태프마다 고유하다. 스태프에 종속되도 문제 없을것이다.
 */
public abstract class Staff {

    // 자식 클래스에서 사용해야 하는 필드라면, 접근 제어자를 protected로 설정하자.
    // 절대 빈 값이 오는것이 허용되지 않는 변수는 가급적 어노테이션을 이용하여 명시하도록 하자.
    protected final @NotNull String namespacedId;

    // 마찬가지로 어노테이션을 사용하여 개발자의 혼동을 줄이려고 노력하자.
    protected final @NotNull String id;

    // 스태프 객체는 아이템을 생성하는 방식을 몰라도 되기 때문에 추상화를 진행해보자.
    // 아이템을 여러 공급자를 통해 제작하도록 확장할 수 있다.
    // 이는 Dependency Injection 방식과 이를 응용한 Strategy Pattern 방식으로 재사용성을 높일 수 있다.
    // 자식 클래스에서 접근 할 필요가 없는 필드이다. private 접근 제어자로 보호하자, 변경이 필요하다면 setter를 통해 변경할 수 있다.
    private @NotNull ItemProvider provider;

    // 기본적으로 생성자를 통해 필드를 초기화한다.
    // 추후 협업을 진행한다고 가정했을 때, 해당 클래스를 상속받아 스태프를 확장할 수 있으므로 접근 제어자는 public 으로 설정한다.
    // 만약 위와 같은 상황을 고려할 필요가 없다면, 반드시 접근 제어자를 조정하여 다른 개발자가 의도에 맞게 사용할 수 있도록 한다.
    public Staff(@NotNull String namespacedId, @NotNull String id, @NotNull ItemProvider provider) {
        this.namespacedId = namespacedId;
        this.id = id;
        this.provider = provider;
    }

    // 추후 아이템 프로바이더를 변경하여 아이템을 생성할 필요가 있을 경우 해당 세터를 통해 변경한다.
    // 해당 세터는 절대로 오버라이드 하면 안되는 메소드이다, 이러한 메소드의 경우 반드시 final 태그를 부착하여 관리하자.
    public final void setProvider(@NotNull ItemProvider provider) {
        this.provider = provider;
    }

    // 마찬가지로 오버라이드 할 필요 없는 메소드이다. final 태그를 부착하자.
    // Optional을 사용한다면 그 반환값을 함수형 프로그래밍 방식으로 관리할 수 있으며, stream api와도 연동할 수 있다.
    // 이는 코드 가독성을 높히는데 도움을 줄 수 있다.
    // 함수형 프로그래밍은 다음 자료를 통해 더 알아보자. https://wikidocs.net/157858
    public final @NotNull ItemStack getItemStack() {
        // 프로바이더를 통해 아이템을 가져올 수 없을 경우, 빈 아이템을 반환한다.
        // 만약 아이템을 가져오는 과정에서 오류가 발생한다면, 그것은 프로바이더가 관리해야 할 문제이다.
        // ItemsAdderProvider 클래스를 확인해보자.
        return provider.findItemStackById(namespacedId).orElseGet(() -> new ItemStack(Material.AIR));
    }

    // 마찬가지로 오버리아드 할 필요 없다.
    // 모든 로직에서 공통적으로 수행할 수 있는 비교 방식으로 변경하였다.
    public final boolean isStaff(@Nullable ItemStack item) {
        // Optional 과 함수형 프로그래밍 방식을 활용하여 보다 직관적으로 로직을 작성할 수 있다.
        return provider.findItemStackById(namespacedId) // id를 사용하여 아이템을 가져온다.
                .map(staff -> staff.isSimilar(item)) // 아이템을 찾아오는데 성공했다면 비교 작업을 수행한다.
                .orElse(false); // 비교 결과를 반환하고, 만약 아이템을 가져오는데 실패했다면 false를 반환한다.
    }

    // 템플릿 메소드 패턴을 활용하여 액션 트리거 입력에 따른 동작을 실행한다.
    // 이는 절대 오버라이드 되면 안되는 메소드이다. final 태그를 부착하자.
    // 다음 링크를 확인하여 템플릿 메소드 패턴을 더 알아보자.
    // https://refactoring.guru/ko/design-patterns/template-method
    public final void action(@NotNull Player player, @NotNull ActionType type) {
        switch (type) {
            case LEFT -> runLeftAction(player);
            case RIGHT -> runRightAction(player);
            case SHIFT_LEFT -> runShiftLeftAction(player);
            case SHIFT_RIGHT -> runShiftRightAction(player);
        }
    }

    // 아래 메소드들은 각 액션에 맞춰 작동할 기능을 정의한다.
    // 이는 외부 클래스에서는 접근하면 안되고, 자식 클래스에서만 오버라이딩 해야 하기 때문에 접근 제어자를 protected로 설정한다.
    protected abstract void runLeftAction(@NotNull Player player);
    protected abstract void runRightAction(@NotNull Player player);
    protected abstract void runShiftLeftAction(@NotNull Player player);
    protected abstract void runShiftRightAction(@NotNull Player player);

}
