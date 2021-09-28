package club.deltapvp.core.impl.event;

import club.deltapvp.deltacore.api.utilities.event.items.damage.PlayerDamageByEntityEvent;
import club.deltapvp.deltacore.api.utilities.event.items.damage.PlayerDamageByPlayerEvent;
import club.deltapvp.deltacore.api.utilities.event.items.damage.PlayerDamageEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CustomPlayerEventListener implements Listener {

    // TODO: Make a better JumpEvent calculation thing.
//    @EventHandler
//    public void onJump(PlayerStatisticIncrementEvent event) {
//        Player player = event.getPlayer();
//        Statistic statistic = event.getStatistic();
//        boolean isJump = statistic.equals(Statistic.JUMP);
//        if (!isJump)
//            return;
//
//        PlayerJumpEvent e = new PlayerJumpEvent(player);
//        e.call();
//    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player))
            return;

        EntityDamageEvent.DamageCause cause = event.getCause();

        Player player = (Player) entity;
        PlayerDamageEvent e = new PlayerDamageEvent(player, cause);
        e.setDamage(event.getDamage());

        PlayerDamageEvent called = (PlayerDamageEvent) e.call();
        if (called.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        double damage = called.getDamage();
        event.setDamage(damage);

    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        Entity damager = event.getDamager();

        EntityDamageEvent.DamageCause cause = event.getCause();
        double damage = event.getDamage();

        if (!(damager instanceof Player)) {
            PlayerDamageByEntityEvent e = new PlayerDamageByEntityEvent(player, damager, cause);
            e.setDamage(damage);
            PlayerDamageByEntityEvent call = (PlayerDamageByEntityEvent) e.call();
            if (call.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            event.setDamage(call.getDamage());
        } else {
            Player attacker = (Player) damager;
            PlayerDamageByPlayerEvent e = new PlayerDamageByPlayerEvent(player, attacker, cause);
            e.setDamage(damage);
            PlayerDamageByPlayerEvent call = (PlayerDamageByPlayerEvent) e.call();
            if (call.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            event.setDamage(call.getDamage());
        }
    }
}
