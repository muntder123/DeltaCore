package club.deltapvp.deltacore.impl;

import club.deltapvp.deltacore.impl.gui.GUIListener;
import club.deltapvp.deltacore.impl.inputlistener.InputListenerImpl;
import club.deltapvp.deltacore.impl.version.VersionCheckerImpl;

public class APIInitializer {

    public APIInitializer() {
        new GUIListener();
        new VersionCheckerImpl();
        new InputListenerImpl();
    }
}
