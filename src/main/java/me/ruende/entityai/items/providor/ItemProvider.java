package me.ruende.entityai.items.providor;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * ItemStack 생성을 관리하는 추상 클래스
 * <p>아이템 공급자에 따른 아이템 생성 로직을 처리한다.
 */
public abstract class ItemProvider {

    // 빈 값을 반환할 수 있는 경우, Optional 클래스를 활용하자.
    // 아래의 글을 참고해서 자세히 알아볼 수 있다.
    // https://dev-coco.tistory.com/178
    // https://mangkyu.tistory.com/203
    public abstract @NotNull Optional<ItemStack> findItemStackById(@NotNull String id);

}
