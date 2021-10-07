package club.deltapvp.core.impl.hologram.v2;

import club.deltapvp.core.Core;
import club.deltapvp.core.version.v1_16.hologram.HologramHandler1_16;
import club.deltapvp.core.version.v1_8_8.hologram.HologramHandler1_8;
import club.deltapvp.deltacore.api.DeltaPlugin;
import club.deltapvp.deltacore.api.utilities.hologram.v2.Hologram;
import club.deltapvp.deltacore.api.utilities.hologram.v2.backend.AbstractHologramHandler;
import club.deltapvp.deltacore.api.utilities.hologram.v2.backend.HologramManager;
import club.deltapvp.deltacore.api.utilities.version.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class HologramManagerImpl extends HologramManager {

    private final HashMap<DeltaPlugin, List<Hologram>> registeredHolograms = new HashMap<>();
    private AbstractHologramHandler handler = null;

    public HologramManagerImpl() {
        setInstance(this);

        ServerVersion version = ServerVersion.fromServerPackageName(Bukkit.getServer().getClass().getName());
        Core plugin = Core.getInstance();
        if (version.equals(ServerVersion.V1_8))
            handler = new HologramHandler1_8(plugin);
        if (version.equals(ServerVersion.V1_16))
            handler = new HologramHandler1_16(plugin);

        if (handler == null)
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
    }

    @Override
    public void registerHologram(DeltaPlugin deltaPlugin, Hologram hologram) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }

        List<Hologram> holograms = registeredHolograms.get(deltaPlugin);
        if (holograms == null)
            holograms = new ArrayList<>();

        if (holograms.contains(hologram))
            return;

        holograms.add(hologram);

        if (registeredHolograms.containsKey(deltaPlugin))
            registeredHolograms.replace(deltaPlugin, holograms);
        else
            registeredHolograms.put(deltaPlugin, holograms);
    }

    @Override
    public void updateHologram(String handle, Player player, boolean all) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }

        Optional<Hologram> hologram = getHologram(handle);
        if (!hologram.isPresent())
            return;

        handler.update(player, hologram.get(), all);
    }

    @Override
    public void updateHologram(Hologram hologram, Player player, boolean all) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }

        handler.update(player, hologram, all);
    }

    @Override
    public void removeHologram(String handle, Player player, boolean all) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }
        Optional<Hologram> hologram = getHologram(handle);
        if (!hologram.isPresent())
            return;

        handler.remove(player, hologram.get(), all);
    }

    @Override
    public void removeHologram(Hologram hologram, Player player, boolean all) {
        if (handler == null) {
            System.out.println("[DeltaAPI Error] This Version of Minecraft does not support the Packet-Based Hologram API.");
            return;
        }

        handler.remove(player, hologram, all);
    }

    Optional<Hologram> getHologram(String handle) {
        Optional<Map.Entry<DeltaPlugin, List<Hologram>>> first = registeredHolograms.entrySet()
                .stream()
                .filter(deltaPluginListEntry -> deltaPluginListEntry.getValue()
                        .stream()
                        .anyMatch(hologram -> hologram.handle().equalsIgnoreCase(handle)))
                .findFirst();

        if (!first.isPresent())
            return Optional.empty();

        Map.Entry<DeltaPlugin, List<Hologram>> e = first.get();
        return (e.getValue().stream().filter(hologram1 -> hologram1.handle().equalsIgnoreCase(handle)).findFirst());
    }

    @Override
    public List<Hologram> getHolograms(DeltaPlugin plugin) {
        List<Hologram> holograms = registeredHolograms.get(plugin);
        return (holograms == null ? Collections.emptyList() : holograms);
    }
}
