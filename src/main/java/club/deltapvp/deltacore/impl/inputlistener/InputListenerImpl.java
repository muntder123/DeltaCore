package club.deltapvp.deltacore.impl.inputlistener;

import club.deltapvp.deltacore.DeltaCore;
import club.deltapvp.deltacore.api.utilities.InputListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class InputListenerImpl extends InputListener implements Listener {

    private Map<UUID, BiConsumer<Player, String>> listening = new HashMap<>();

    public InputListenerImpl() {
        Bukkit.getPluginManager().registerEvents(this, DeltaCore.getInstance());
    }

    @Override
    public void listen(UUID uuid, BiConsumer<Player, String> function) {

    }

    private void handleInput(Cancellable event, Player player, String message) {
        UUID uuid = player.getUniqueId();
        if (listening.containsKey(uuid)) {
            BiConsumer<Player, String> consumer = listening.get(uuid);
            try {
                event.setCancelled(true);
                consumer.accept(player, message);
            } catch (Exception ignored) {
            } finally {
                listening.remove(uuid, consumer);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        listening.remove(uuid);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        handleInput(event, event.getPlayer(), event.getMessage());
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        handleInput(event, event.getPlayer(), event.getMessage());
    }
}
