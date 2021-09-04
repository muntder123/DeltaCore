package club.deltapvp.core.impl.skull;

import club.deltapvp.deltacore.api.utilities.skull.CustomSkull;
import org.bukkit.inventory.ItemStack;

public class ICustomSkull implements CustomSkull {

    private final String url;

    public ICustomSkull(String url) {
        this.url = url;
    }

    @Override
    public ItemStack get() {
        return Skull.getCustomSkull(url);
    }
}
