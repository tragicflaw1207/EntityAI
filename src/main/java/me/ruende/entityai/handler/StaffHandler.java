package me.ruende.entityai.handler;

import me.ruende.entityai.actions.ActionType;
import me.ruende.entityai.items.staff.Staff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StaffHandler implements Listener {

    // 스태프 객체는 외부에서 주입받자. 다양한 스태프가 추가되더라도 핸들러를 재사용 할 수 있다.
    private final @NotNull Staff staff;

    public StaffHandler(@NotNull Staff staff) {
        this.staff = staff;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!staff.isStaff(item)) {
            return;
        }

        // 스태프는 액션 트리거의 타입을 결정하기에 적합하지 않다.
        // 액션은 자신이 무엇에 의해 트리거 되는지 관심이 없다.
        // 그렇다면 그것은 핸들러가 할 일이다.
        ActionType type = ActionType.match(event.getAction().isLeftClick(), player.isSneaking());

        // 핸들러는 파라메터로 넘겨야 할 값을 결정하고 단순히 넘기기만 한다.
        // 이제 그 작동이 어떻게 되는지는 각 스태프가 처리할 일이다.
        staff.action(player, type);
    }
}
