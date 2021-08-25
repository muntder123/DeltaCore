package club.deltapvp.deltacore;

import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.DeltaPlugin;
import club.deltapvp.deltacore.impl.api.DeltaAPIImpl;
import club.deltapvp.deltacore.impl.api.DeltaSQLAPIImpl;
import club.deltapvp.deltacore.impl.gui.GUIListener;
import lombok.Getter;
import lombok.Setter;

public final class DeltaCore extends DeltaPlugin {

    @Getter
    @Setter
    private static DeltaCore instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        new DeltaAPIImpl();
        new DeltaSQLAPIImpl();
        registerListeners(
                new GUIListener()
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
