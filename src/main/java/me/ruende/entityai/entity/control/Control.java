package me.ruende.entityai.entity.control;

import me.ruende.entityai.utils.TargetingHelper;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * 해당 클래스를 상속받은 클래스에서 공통으로 접근해야 할 필드가 존재하기 때문에 추상 클래스로 구현하였다.
 */
public abstract class Control {

    // 추상화 하기 여렵고, 심지어 그 클래스가 대부분의 자식 클래스에서 쓰인다면 기왕 짬처리 한 김에 확실하게 하자.
    // 이 친구는 외부에서 주입받을 필요가 없을 가능성이 높다.
    // 자식 클래스에서 필드가 덮어씌워지면 안되기 때문에 protected 접근제어자를 사용하자.
    // 다만, 보통 이런 클래스들은 static 필드와 메소드를 사용하여 구현한 경우가 많다.
    // 다음 자료를 참고하여 유틸 클래스에 대해 더 알아보자.
    // https://velog.io/@ikjo39/%EC%9C%A0%ED%8B%B8-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%9E%80
    protected final TargetingHelper targetingHelper = new TargetingHelper();

    // 비슷한 기능들의 수많은 객체들이 여기 와도 어색하진 않다.

    // 외부에서 스태프를 컨트롤 하기 위해 실행해야 할 메소드이다.
    public abstract void execute(@NotNull Player player);

}
