package club.deltapvp.core.version.v1_13_2.hologram.nms;

import club.deltapvp.core.version.v1_13_2.hologram.HologramHandler1_13;
import club.deltapvp.deltacore.api.DeltaPlugin;
import club.deltapvp.deltacore.api.utilities.hologram.v2.Hologram;
import club.deltapvp.deltacore.api.utilities.hologram.v2.HologramInteractEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.SneakyThrows;
import net.minecraft.server.v1_13_R2.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class PacketReader {

    private final Player player;
    private final DeltaPlugin plugin;

    public PacketReader(DeltaPlugin plugin, Player player) {
        this.player = player;
        this.plugin = plugin;
    }

    public boolean inject() {
        CraftPlayer nmsPlayer = (CraftPlayer) player;
        Channel channel = nmsPlayer.getHandle().playerConnection.networkManager.channel;

        if (channel.pipeline().get("PacketInjector") != null)
            return false;

        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, PacketPlayInUseEntity packetPlayInUseEntity, List<Object> list) {
                list.add(packetPlayInUseEntity);
                read(packetPlayInUseEntity);

            }
        });

        return true;
    }

    public boolean unInject() {
        CraftPlayer nmsPlayer = (CraftPlayer) player;
        Channel channel = nmsPlayer.getHandle().playerConnection.networkManager.channel;

        if (channel.pipeline().get("PacketInjector") == null)
            return false;

        channel.pipeline().remove("PacketInjector");
        return true;
    }

    private void read(PacketPlayInUseEntity packet) {
        int id = (int) getValue(packet, "a");

//        String action = getValue(packet, "action").toString();
//        if (action.equalsIgnoreCase("attack"))
//            return;

        if (HologramHandler1_13.getByID(id).isPresent()) {
            Optional<Hologram> byID = HologramHandler1_13.getByID(id);
            assert byID.isPresent();
            Bukkit.getScheduler().runTask(plugin, () -> {
                HologramInteractEvent event = new HologramInteractEvent(player, byID.get());
                event.call();
            });
        }
    }

    @SneakyThrows
    private Object getValue(Object instance, String name) {
        Object result;

        Field field = instance.getClass().getDeclaredField(name);
        field.setAccessible(true);
        result = field.get(instance);
        field.setAccessible(false);

        return result;
    }
}
