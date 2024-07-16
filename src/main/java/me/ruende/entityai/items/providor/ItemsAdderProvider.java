package me.ruende.entityai.items.providor;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * ItemsAdder를 사용하여 제작한 아이템을 반환한다.
 */
public class ItemsAdderProvider extends ItemProvider {

    @NotNull
    @Override
    public Optional<ItemStack> findItemStackById(@NotNull String id) {
        CustomStack stack = CustomStack.getInstance(id);
        if (stack == null) {
            // 절대로 발생하지 말아야 할 작동이라면 직접적인 에러 컨트롤을 시도하자.
            // 콘솔에 함수 콜스택과 로그 메시지가 남는다면 훨씬 디버깅 하기 쉽다.
            // 경우에 따라 Exception or RuntimeException 클래스를 상속하여 직접 Exception을 구현할 수 있다.
            throw new NullPointerException(id + " 아이템을 ItemsAdder에서 불러올 수 없습니다.");
        }

        // stack.getItemStack() 메소드의 반환값이 null safe 하다고 명시되어있지 않기 때문에
        // ofNullable 메소드로 래핑하여 반환한다.
        return Optional.ofNullable(stack.getItemStack());
    }

}
