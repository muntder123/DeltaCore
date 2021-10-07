package club.deltapvp.core.impl.scoreboard;

import club.deltapvp.core.Core;
import club.deltapvp.deltacore.api.DeltaPlugin;
import club.deltapvp.deltacore.api.utilities.scoreboard.Scoreboard;
import club.deltapvp.deltacore.api.utilities.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Objective;

import java.util.*;

public class ScoreboardManagerImpl extends ScoreboardManager {

    private final HashMap<DeltaPlugin, List<Scoreboard>> registeredScoreboards = new HashMap<>();
    private final HashMap<UUID, List<Scoreboard>> userScoreboards = new HashMap<>();

    public ScoreboardManagerImpl() {
        setInstance(this);

        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(), Core.getInstance());
    }

    @Override
    public void registerScoreboard(DeltaPlugin deltaPlugin, Scoreboard scoreboard) {
        List<Scoreboard> scoreboards = registeredScoreboards.get(deltaPlugin);
        if (scoreboards == null)
            scoreboards = new ArrayList<>();

        if (scoreboards.contains(scoreboard))
            return;

        scoreboards.add(scoreboard);
        if (registeredScoreboards.containsKey(deltaPlugin))
            registeredScoreboards.replace(deltaPlugin, scoreboards);
        else
            registeredScoreboards.put(deltaPlugin, scoreboards);
    }

    @Override
    public void unRegisterScoreboard(DeltaPlugin deltaPlugin, Scoreboard scoreboard) {
        List<Scoreboard> scoreboards = registeredScoreboards.get(deltaPlugin);
        if (scoreboards == null || !scoreboards.contains(scoreboard))
            return;

        scoreboards.remove(scoreboard);
        if (scoreboards.isEmpty())
            registeredScoreboards.remove(deltaPlugin);
        else
            registeredScoreboards.replace(deltaPlugin, scoreboards);
    }

    @Override
    public void applyScoreboard(Player player, Scoreboard scoreboard) {
        UUID uuid = player.getUniqueId();
        List<Scoreboard> scoreboards = userScoreboards.get(uuid);
        if (scoreboards == null)
            scoreboards = new ArrayList<>();

        if (scoreboards.contains(scoreboard))
            return;

        scoreboards.add(scoreboard);
        if (userScoreboards.containsKey(uuid))
            userScoreboards.replace(uuid, scoreboards);
        else
            userScoreboards.put(uuid, scoreboards);
        updateScoreboard(player);
    }

    @Override
    public void applyScoreboard(Player player, String handle) {
        Optional<Scoreboard> scoreboardByHandle = getScoreboardByHandle(handle);
        if (!scoreboardByHandle.isPresent())
            return;

        Scoreboard scoreboard = scoreboardByHandle.get();
        applyScoreboard(player, scoreboard);
    }

    @Override
    public void removeScoreboard(Player player, Scoreboard scoreboard) {
        UUID uuid = player.getUniqueId();
        List<Scoreboard> scoreboards = userScoreboards.get(uuid);
        if (scoreboards == null)
            return;

        scoreboards.remove(scoreboard);
        if (scoreboards.isEmpty())
            userScoreboards.remove(uuid);
        else
            userScoreboards.replace(uuid, scoreboards);
        updateScoreboard(player);
    }

    @Override
    public void removeScoreboard(Player player, String handle) {
        Optional<Scoreboard> scoreboardByHandle = getScoreboardByHandle(handle);
        if (!scoreboardByHandle.isPresent())
            return;

        Scoreboard scoreboard = scoreboardByHandle.get();
        removeScoreboard(player, scoreboard);
    }

    @Override
    public void removeAllScoreboards(Player player) {
        UUID uuid = player.getUniqueId();
        List<Scoreboard> scoreboards = userScoreboards.get(uuid);
        if (scoreboards == null)
            return;

        userScoreboards.remove(uuid);
        updateScoreboard(player);
    }

    @Override
    public void updateScoreboard(Player player) {
        UUID uuid = player.getUniqueId();
        List<Scoreboard> scoreboards = userScoreboards.get(uuid);

        player.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
        if (scoreboards == null) {
            Set<Objective> dummy = player.getScoreboard().getObjectivesByCriteria("dummy");
            if (dummy != null)
                dummy.forEach(Objective::unregister);
            return;
        }

        scoreboards.sort(Comparator.comparing(Scoreboard::priority));
        Scoreboard scoreboard = scoreboards.get(0);
        scoreboard.display(player);
    }

    @Override
    public Optional<Scoreboard> getScoreboard(Player player) {
        UUID uuid = player.getUniqueId();
        List<Scoreboard> scoreboards = userScoreboards.get(uuid);
        if (scoreboards == null)
            return Optional.empty();


        scoreboards.sort(Comparator.comparing(Scoreboard::priority));
        Scoreboard scoreboard = scoreboards.get(0);
        return Optional.of(scoreboard);
    }

    @Override
    public Optional<Scoreboard> getScoreboardByHandle(String handle) {
        for (DeltaPlugin deltaPlugin : registeredScoreboards.keySet()) {
            List<Scoreboard> scoreboards = registeredScoreboards.get(deltaPlugin);
            Optional<Scoreboard> first = scoreboards.stream().filter(scoreboard -> scoreboard.handle().equalsIgnoreCase(handle))
                    .findFirst();

            if (first.isPresent()) {
                return first;
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Scoreboard> getScoreboards(DeltaPlugin deltaPlugin) {
        return registeredScoreboards.get(deltaPlugin);
    }

    private class ScoreboardListener implements Listener {
        @EventHandler
        public void onQuit(PlayerQuitEvent event) {
            Player player = event.getPlayer();
            player.getScoreboard()
                    .getObjectivesByCriteria("delta").forEach(Objective::unregister);
        }
    }

}
