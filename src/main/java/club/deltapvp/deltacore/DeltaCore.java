package club.deltapvp.deltacore;

import club.deltapvp.deltacore.api.DeltaPlugin;
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
