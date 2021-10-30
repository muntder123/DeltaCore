package club.deltapvp.core.version.v1_13_2.hologram;

import club.deltapvp.core.version.v1_13_2.hologram.nms.PacketReader;
import club.deltapvp.deltacore.api.DeltaPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class HologramPlayerListener implements Listener {

    private final DeltaPlugin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PacketReader packetReader = new PacketReader(plugin, player);
        packetReader.inject();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PacketReader packetReader = new PacketReader(plugin, player);
        packetReader.unInject();
    }
}
