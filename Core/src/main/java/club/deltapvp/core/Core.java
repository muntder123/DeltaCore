package club.deltapvp.core;

import club.deltapvp.core.impl.api.DeltaAPIImpl;
import club.deltapvp.core.impl.api.DeltaSQLAPIImpl;
import club.deltapvp.core.impl.gui.GUIListener;
import club.deltapvp.core.impl.hologram.HologramListener;
import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.DeltaPlugin;
import lombok.Getter;
import lombok.Setter;

public final class Core extends DeltaPlugin {

    @Getter
    @Setter
    private static Core instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        new DeltaAPIImpl();
        new DeltaSQLAPIImpl();
        registerListeners(
                new GUIListener(),
                new HologramListener()
        );

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DeltaAPI api = DeltaAPI.getInstance();
        api.getHologramManager().getHolograms().forEach(hologram -> api.getHologramManager().removeHologram(hologram));
    }

}
