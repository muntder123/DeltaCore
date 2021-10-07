package club.deltapvp.core.impl.hologram;

import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.utilities.hologram.Hologram;
import club.deltapvp.deltacore.api.utilities.hologram.HologramInteractEvent;
import club.deltapvp.deltacore.api.utilities.hologram.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.function.BiConsumer;

public class HologramListener implements Listener {

    private final HologramManager manager;

    public HologramListener() {
        manager = DeltaAPI.getInstance().getHologramManager();
    }

    @EventHandler
    public void onEntityInteractAt(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity rightClicked = event.getRightClicked();
        if (!(rightClicked instanceof ArmorStand))
            return;

        Hologram hologram = manager.getHologram((ArmorStand) rightClicked);
        if (hologram == null)
            return;

        event.setCancelled(true);

        HologramInteractEvent e = new HologramInteractEvent(player, hologram, false);
        Bukkit.getPluginManager().callEvent(e);
    }

    @EventHandler
    public void onEntityInteractE(PlayerInteractEntityEvent event) {
        Entity rightClicked = event.getRightClicked();
        Player player = event.getPlayer();

        if (!(rightClicked instanceof ArmorStand))
            return;

        Hologram hologram = manager.getHologram((ArmorStand) rightClicked);
        if (hologram == null)
            return;

        event.setCancelled(true);

        HologramInteractEvent e = new HologramInteractEvent(player, hologram, false);
        Bukkit.getPluginManager().callEvent(e);
    }

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (!(damager instanceof Player) || !(entity instanceof ArmorStand))
            return;

        Hologram hologram = manager.getHologram((ArmorStand) entity);
        if (hologram == null)
            return;

        event.setCancelled(true);

        HologramInteractEvent e = new HologramInteractEvent((Player) damager, hologram, true);
        Bukkit.getPluginManager().callEvent(e);
    }

    @EventHandler
    public void onHologramInteraction(HologramInteractEvent event) {
        BiConsumer<Player, HologramInteractEvent> interactionFunction = event.getHologram().getInteractionFunction();
        if (interactionFunction == null)
            return;

        interactionFunction.accept(event.getPlayer(), event);
    }

    @EventHandler
    public void onHologramInteract(club.deltapvp.deltacore.api.utilities.hologram.v2.HologramInteractEvent event) {
        event.getHologram().onHologramInteract(event);
    }
}
