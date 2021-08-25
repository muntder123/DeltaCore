package club.deltapvp.deltacore.impl.update;

import club.deltapvp.deltacore.api.utilities.checker.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class iUpdateChecker implements UpdateChecker {
    @Override
    public void commence(JavaPlugin plugin, int resourceID, Consumer<String> function) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceID).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    function.accept(scanner.next());
                }
            } catch (IOException exception) {
                plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }
}
