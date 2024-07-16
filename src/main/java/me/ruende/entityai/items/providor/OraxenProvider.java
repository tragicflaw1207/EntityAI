package me.ruende.entityai.items.providor;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Oraxen 플러그인으로 제작한 아이템을 반환한다.
 */
public class OraxenProvider extends ItemProvider {

    // 적당히 Oraxen 플러그인의 아이템 스토리지를 가져오는 코드

    @NotNull
    @Override
    public Optional<ItemStack> findItemStackById(@NotNull String id) {
        // 적당히 스토리지에서 아이디 기반으로 아이템을 조회하는 코드
        return Optional.empty(); // 조회한 아이템을 적당히 Optional에 담아 반환하는 코드
    }

}
