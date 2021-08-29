package club.deltapvp.core.impl.hex;

import club.deltapvp.core.abstractapi.AbstractHexValidator;
import club.deltapvp.core.version.v1_16.VersionHexValidator;
import club.deltapvp.deltacore.api.utilities.file.enums.ServerVersion;
import club.deltapvp.deltacore.api.utilities.hex.HexValidator;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class iHexValidator implements HexValidator {
    private final boolean isHexVersion;
    private AbstractHexValidator abstractHexValidator;

    public iHexValidator() {
        isHexVersion = ServerVersion.fromServerPackageName(Bukkit.getServer().getClass().getName()).isAtLeast(ServerVersion.V1_16);
        if (isHexVersion)
            abstractHexValidator = new VersionHexValidator();
    }

    @SneakyThrows
    @Override
    public String validate(String input) {
        return (isHexVersion ? abstractHexValidator.validate(input) :
                ChatColor.translateAlternateColorCodes('&', input));
    }
}
