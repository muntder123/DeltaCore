package club.deltapvp.deltacore.impl.bungeecordutil;

import club.deltapvp.deltacore.DeltaCore;
import club.deltapvp.deltacore.api.bungeecord.BungeeCord;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class iBungeeCord implements BungeeCord, PluginMessageListener {
    private final DeltaCore plugin;

    public iBungeeCord() {
        plugin = DeltaCore.getInstance();
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }

    @Override
    public void sendPlayerToServer(Player player, String server) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
    }

    @Override
    public void sendPlayerToServer(Player player, String server, int i) {
        new BukkitRunnable() {

            @Override
            public void run() {
                sendPlayerToServer(player, server);
            }
        }.runTaskLater(plugin, i);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

    }
}
