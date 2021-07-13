package club.deltapvp.deltacore;

import club.deltapvp.deltacore.impl.APIInitializer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeltaCore extends JavaPlugin {

    @Getter
    @Setter
    private static DeltaCore instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        new APIInitializer();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
