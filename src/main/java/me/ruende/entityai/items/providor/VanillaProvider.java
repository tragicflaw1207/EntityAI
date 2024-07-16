package me.ruende.entityai.items.providor;

import com.google.common.collect.Maps;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

/**
 * 버킷 API를 사용하여 제작한 아이템을 반환한다.
 */
public class VanillaProvider extends ItemProvider {

    // 외부 스케쥴러를 사용할 때 접근할 수 있는 필드이다. 비동기 프로그래밍 환경을 고려해보자.
    // 아래 링크를 참고하여 Concurrent 패키지와 ConcurrentHashMap에 대해 알아보자.
    // https://wiki.yowu.dev/ko/Knowledge-base/Java/java-s-java-util-concurrent-package-for-parallel-programming
    // https://velog.io/@alsgus92/ConcurrentHashMap%EC%9D%98-Thread-safe-%EC%9B%90%EB%A6%AC
    private static final Map<String, ItemStack> itemHolder = Maps.newConcurrentMap();

    static {
        // itemHolder에 무언가 추가하는 로직이 작성될 수 있다.
    }

    @NotNull
    @Override
    public Optional<ItemStack> findItemStackById(@NotNull String id) {
        // itemHolder에 파라메터로 입력받은 id에 해당하는 값이 없다면 빈 값을 반환할 수 있다.
        // 빈 값 대신 Optional 클래스를 활용하여 nullptr 컨트롤을 진행해보자.
        return Optional.ofNullable(itemHolder.get(id));
    }

}
