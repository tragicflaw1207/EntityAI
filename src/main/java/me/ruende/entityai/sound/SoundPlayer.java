package me.ruende.entityai.sound;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.SoundCategory;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@SuppressWarnings("CallToPrintStackTrace")
public class SoundPlayer {
    private final ProtocolManager protocolManager;

    public SoundPlayer() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void playSummonSound(Location location) {
        PacketContainer packet = createSoundPacket(location);

        for (Player player : location.getWorld().getPlayers()) {
            protocolManager.sendServerPacket(player, packet);
        }
    }

    private PacketContainer createSoundPacket(Location location) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.NAMED_SOUND_EFFECT);

        try {
            packet.getSoundEffects().write(0, Sound.ENTITY_ALLAY_DEATH);
            packet.getSoundCategories().write(0, SoundCategory.HOSTILE);

            packet.getIntegers()
                    .write(0, (int) (location.getX() * 8.0))
                    .write(1, (int) (location.getY() * 8.0))
                    .write(2, (int) (location.getZ() * 8.0));

            packet.getFloat()
                    .write(0, 1.0F)
                    .write(1, 1.0F);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return packet;
    }
}
