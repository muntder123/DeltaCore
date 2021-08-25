package club.deltapvp.deltacore.impl.api;

import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.bungeecord.BungeeCord;
import club.deltapvp.deltacore.api.utilities.checker.UpdateChecker;
import club.deltapvp.deltacore.api.utilities.file.FileLoader;
import club.deltapvp.deltacore.api.utilities.file.VersionChecker;
import club.deltapvp.deltacore.api.utilities.input.InputListener;
import club.deltapvp.deltacore.api.utilities.message.Message;
import club.deltapvp.deltacore.api.utilities.serialization.BukkitSerializer;
import club.deltapvp.deltacore.api.utilities.time.TimeConversion;
import club.deltapvp.deltacore.impl.bungeecordutil.iBungeeCord;
import club.deltapvp.deltacore.impl.file.iFileLoader;
import club.deltapvp.deltacore.impl.inputlistener.iInputListener;
import club.deltapvp.deltacore.impl.message.iMessage;
import club.deltapvp.deltacore.impl.serialize.iBukkitSerializer;
import club.deltapvp.deltacore.impl.timeconverter.iTimeConverter;
import club.deltapvp.deltacore.impl.update.iUpdateChecker;
import club.deltapvp.deltacore.impl.version.iVersionChecker;

import java.util.List;

public class DeltaAPIImpl extends DeltaAPI {

    private final BungeeCord bungeeCord;
    private final InputListener inputListener;
    private final VersionChecker versionChecker;
    private final UpdateChecker updateChecker;
    private final TimeConversion timeConversion;
    private final FileLoader fileLoader;
    private final BukkitSerializer bukkitSerializer;

    public DeltaAPIImpl() {
        setInstance(this);

        bungeeCord = new iBungeeCord();
        inputListener = new iInputListener();
        versionChecker = new iVersionChecker();
        updateChecker = new iUpdateChecker();
        timeConversion = new iTimeConverter();
        fileLoader = new iFileLoader();
        bukkitSerializer = new iBukkitSerializer();
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
}
