package club.deltapvp.core.impl.api;

import club.deltapvp.core.impl.bungeecordutil.iBungeeCord;
import club.deltapvp.core.impl.file.iFileLoader;
import club.deltapvp.core.impl.hex.iHexValidator;
import club.deltapvp.core.impl.hologram.IHologramManager;
import club.deltapvp.core.impl.inputlistener.iInputListener;
import club.deltapvp.core.impl.message.iMessage;
import club.deltapvp.core.impl.serialize.iBukkitSerializer;
import club.deltapvp.core.impl.sign.IVirtualSignEditor;
import club.deltapvp.core.impl.skull.ICustomSkull;
import club.deltapvp.core.impl.timeconverter.iTimeConverter;
import club.deltapvp.core.impl.update.iUpdateChecker;
import club.deltapvp.core.impl.version.iVersionChecker;
import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.bungeecord.BungeeCord;
import club.deltapvp.deltacore.api.utilities.checker.UpdateChecker;
import club.deltapvp.deltacore.api.utilities.file.FileLoader;
import club.deltapvp.deltacore.api.utilities.hex.HexValidator;
import club.deltapvp.deltacore.api.utilities.hologram.HologramManager;
import club.deltapvp.deltacore.api.utilities.input.InputListener;
import club.deltapvp.deltacore.api.utilities.message.iface.Message;
import club.deltapvp.deltacore.api.utilities.serialization.BukkitSerializer;
import club.deltapvp.deltacore.api.utilities.sign.VirtualSignEditor;
import club.deltapvp.deltacore.api.utilities.skull.CustomSkull;
import club.deltapvp.deltacore.api.utilities.time.TimeConversion;
import club.deltapvp.deltacore.api.utilities.version.VersionChecker;

import java.util.List;

public class DeltaAPIImpl extends DeltaAPI {

    private final BungeeCord bungeeCord;
    private final InputListener inputListener;
    private final VersionChecker versionChecker;
    private final UpdateChecker updateChecker;
    private final TimeConversion timeConversion;
    private final FileLoader fileLoader;
    private final BukkitSerializer bukkitSerializer;
    private final HexValidator hexValidator;
    private final HologramManager hologramManager;
    private final VirtualSignEditor virtualSignEditor;

    public DeltaAPIImpl() {
        setInstance(this);

        bungeeCord = new iBungeeCord();
        inputListener = new iInputListener();
        versionChecker = new iVersionChecker();
        updateChecker = new iUpdateChecker();
        timeConversion = new iTimeConverter();
        fileLoader = new iFileLoader();
        bukkitSerializer = new iBukkitSerializer();
        hexValidator = new iHexValidator();
        hologramManager = new IHologramManager();
        virtualSignEditor = new IVirtualSignEditor();
    }

    @Override
    public BungeeCord getBungeeCord() {
        return bungeeCord;
    }

    @Override
    public VersionChecker getVersionChecker() {
        return versionChecker;
    }

    @Override
    public InputListener getInputListener() {
        return inputListener;
    }

    @Override
    public Message createMessage(String... strings) {
        return new iMessage(strings);
    }

    @Override
    public Message createMessage(String s) {
        return new iMessage(s);
    }

    @Override
    public Message createMessage(List<String> list) {
        return new iMessage(list);
    }

    @Override
    public TimeConversion getTimeConverter() {
        return timeConversion;
    }

    @Override
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    @Override
    public FileLoader getFileLoader() {
        return fileLoader;
    }

    @Override
    public BukkitSerializer getBukkitSerializer() {
        return bukkitSerializer;
    }

    @Override
    public HexValidator getHexValidator() {
        return hexValidator;
    }

    @Override
    public HologramManager getHologramManager() {
        return hologramManager;
    }

    @Override
    public CustomSkull createCustomSkull(String url) {
        return new ICustomSkull(url);
    }

    @Override
    public VirtualSignEditor getVirtualSignEditor() {
        return virtualSignEditor;
    }
}
