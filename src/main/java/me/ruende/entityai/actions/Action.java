package me.ruende.entityai.actions;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// 외부에서는 액션이 어떻게 동작하는지 관심이 없다.
// 외부에서는 상황에 맞는 액션 클래스를 생성하고, 단순히 실행시키기만 하면 된다.
// 마찬가지로, 해당 클래스에서는 언제 액션이 트리거 되는지 관심이 없다.
// 트리거가 된다면 단순히 실행하기만 하면 된다.
// 따라서 트리거의 타입이 아니라, 기능의 작동에 집중하여 자식 클래스를 구성한다.
public abstract class Action {

    public abstract void execute(@NotNull Player player);

}
