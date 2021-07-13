package club.deltapvp.deltacore.impl.version;

import club.deltapvp.deltacore.api.utilities.version.ServerVersion;
import club.deltapvp.deltacore.api.utilities.version.VersionChecker;
import org.bukkit.Bukkit;

public class VersionCheckerImpl extends VersionChecker {

    private final ServerVersion serverVersion;
    public VersionCheckerImpl() {
        setInstance(this);

        serverVersion = ServerVersion.fromServerPackageName(Bukkit.getServer().getClass().getName());
    }

    @Override
    public boolean isModern() {
        return serverVersion.isAtLeast(ServerVersion.V1_13);
    }

    @Override
    public boolean isLegacy() {
        return serverVersion.isAtMost(ServerVersion.V1_12); // Could also return !isModern()
    }
}
