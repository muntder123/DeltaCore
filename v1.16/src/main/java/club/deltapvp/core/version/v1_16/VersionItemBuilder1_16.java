package club.deltapvp.core.version.v1_16;

import club.deltapvp.deltacore.api.utilities.builder.itembuilder.AbstractVersionItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VersionItemBuilder1_16 extends AbstractVersionItemBuilder {
    @Override
    public void setUnbreakable(ItemStack itemStack, boolean b) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(b);
        itemStack.setItemMeta(itemMeta);
    }
}
