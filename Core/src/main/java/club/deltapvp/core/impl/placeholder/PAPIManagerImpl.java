package club.deltapvp.core.impl.placeholder;

import club.deltapvp.deltacore.api.DeltaPlugin;
import club.deltapvp.deltacore.api.utilities.placeholder.PAPIManager;
import club.deltapvp.deltacore.api.utilities.placeholder.PAPIPlaceholder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class PAPIManagerImpl extends PAPIManager {

    private final HashMap<DeltaPlugin, ArrayList<PAPIPlaceholder>> registeredPlaceholders = new HashMap<>();

    public PAPIManagerImpl() {
        setInstance(this);
    }

    @Override
    public void registerPlaceholder(DeltaPlugin deltaPlugin, PAPIPlaceholder... papiPlaceholders) {
        ArrayList<PAPIPlaceholder> placeholders = registeredPlaceholders.get(deltaPlugin);
        if (placeholders == null) placeholders = new ArrayList<>();

        placeholders.addAll(Arrays.asList(papiPlaceholders));
    }

    @Override
    public String request(DeltaPlugin plugin, Player player, String[] params) {
        ArrayList<PAPIPlaceholder> placeholders = registeredPlaceholders.get(plugin);
        if (placeholders == null)
            return null;

        int checkingIndex = 0;
        Optional<PAPIPlaceholder> first = placeholders.stream().filter(papiPlaceholder -> {
            String identifier = papiPlaceholder.getIdentifier();
            int triggerOnArgument = papiPlaceholder.triggerOnArgument();
            return (checkingIndex == triggerOnArgument && params[checkingIndex].equalsIgnoreCase(identifier));
        }).findFirst();

        return (first.map(papiPlaceholder -> papiPlaceholder.request(player, params, checkingIndex)).orElse(null));
    }
}
