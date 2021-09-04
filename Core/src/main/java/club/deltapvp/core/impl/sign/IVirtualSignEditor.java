package club.deltapvp.core.impl.sign;

import club.deltapvp.core.Core;
import club.deltapvp.core.version.v1_16.SignMenuFactory1_16;
import club.deltapvp.core.version.v1_8_8.SignMenuFactory1_8;
import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.utilities.file.VersionChecker;
import club.deltapvp.deltacore.api.utilities.sign.AbstractSignFactory;
import club.deltapvp.deltacore.api.utilities.sign.AbstractSignMenu;
import club.deltapvp.deltacore.api.utilities.sign.VirtualSignEditor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.BiPredicate;

public class IVirtualSignEditor implements VirtualSignEditor {

    private AbstractSignFactory signFactory;

    public IVirtualSignEditor() {
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib"))
            return;

        VersionChecker versionChecker = DeltaAPI.getInstance().getVersionChecker();
        boolean modern = versionChecker.isModern();
        Core plugin = Core.getInstance();
        if (modern)
            signFactory = new SignMenuFactory1_16(plugin);
        else
            signFactory = new SignMenuFactory1_8(plugin);
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
