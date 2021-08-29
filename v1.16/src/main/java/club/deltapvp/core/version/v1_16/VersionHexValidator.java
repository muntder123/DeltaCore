package club.deltapvp.core.version.v1_16;

import club.deltapvp.core.abstractapi.AbstractHexValidator;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionHexValidator extends AbstractHexValidator {

    private final Pattern pattern;

    public VersionHexValidator() {
        setInstance(this);

        pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    }

    @Override
    public String validate(String input) {
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String color = input.substring(matcher.start(), matcher.end());
            input = input.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(input);
        }
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
