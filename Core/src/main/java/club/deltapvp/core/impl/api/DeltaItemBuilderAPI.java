package club.deltapvp.core.impl.api;

import club.deltapvp.core.version.v1_16.VersionItemBuilder1_16;
import club.deltapvp.core.version.v1_8_8.VersionItemBuilder1_8;
import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.utilities.builder.itembuilder.APIItemBuilder;
import club.deltapvp.deltacore.api.utilities.builder.itembuilder.AbstractVersionItemBuilder;
import club.deltapvp.deltacore.api.utilities.version.VersionChecker;

public class DeltaItemBuilderAPI extends APIItemBuilder {

    private final AbstractVersionItemBuilder versionItemBuilder;

    public DeltaItemBuilderAPI() {
        setInstance(this);

        VersionChecker versionChecker = DeltaAPI.getInstance().getVersionChecker();
        boolean legacy = versionChecker.isLegacy();

        if (legacy)
            versionItemBuilder = new VersionItemBuilder1_8();
        else
            versionItemBuilder = new VersionItemBuilder1_16();

    }

    @Override
    public AbstractVersionItemBuilder getVersionItemBuilder() {
        return versionItemBuilder;
    }
}
