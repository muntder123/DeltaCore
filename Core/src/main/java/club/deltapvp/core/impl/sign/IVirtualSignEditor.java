package club.deltapvp.core.impl.sign;

import club.deltapvp.core.Core;
import club.deltapvp.core.version.v1_13_2.SignMenuFactory1_13;
import club.deltapvp.core.version.v1_16.SignMenuFactory1_16;
import club.deltapvp.core.version.v1_8_8.SignMenuFactory1_8;
import club.deltapvp.deltacore.api.utilities.sign.AbstractSignFactory;
import club.deltapvp.deltacore.api.utilities.sign.AbstractSignMenu;
import club.deltapvp.deltacore.api.utilities.sign.VirtualSignEditor;
import club.deltapvp.deltacore.api.utilities.version.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.BiPredicate;

public class IVirtualSignEditor implements VirtualSignEditor {

    private AbstractSignFactory signFactory;

    public IVirtualSignEditor() {
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib"))
            return;

        ServerVersion version = ServerVersion.fromServerPackageName(Bukkit.getServer().getClass().getName());
        Core plugin = Core.getInstance();
        if (version.equals(ServerVersion.V1_8))
            signFactory = new SignMenuFactory1_8(plugin);
        if (version.equals(ServerVersion.V1_13))
            signFactory = new SignMenuFactory1_13(plugin);
        else
            signFactory = new SignMenuFactory1_16(plugin);
    }


    @Override
    public AbstractSignMenu createSign(List<String> list) {
        return signFactory.newMenu(list);
    }

    @Override
    public AbstractSignMenu createSign(List<String> list, boolean b) {
        return createSign(list).reOpenIfFail(b);
    }

    @Override
    public AbstractSignMenu createSign(List<String> list, boolean b, BiPredicate<Player, String[]> biPredicate) {
        return createSign(list, b).response(biPredicate);
    }

    @Override
    public AbstractSignMenu createSign(List<String> list, BiPredicate<Player, String[]> biPredicate) {
        return signFactory.newMenu(list).response(biPredicate);
    }
}
