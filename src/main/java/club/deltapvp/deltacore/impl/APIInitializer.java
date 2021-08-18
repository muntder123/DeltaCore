package club.deltapvp.deltacore.impl;

import club.deltapvp.deltacore.impl.bungeecordutil.BungeecordUtilImpl;
import club.deltapvp.deltacore.impl.inputlistener.InputListenerImpl;
import club.deltapvp.deltacore.impl.version.VersionCheckerImpl;

public class APIInitializer {

    public APIInitializer() {
        new VersionCheckerImpl();
        new InputListenerImpl();
        new BungeecordUtilImpl();
    }
}
