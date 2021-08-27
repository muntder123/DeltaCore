package club.deltapvp.core.abstractapi;

public abstract class AbstractHexValidator {

    private static AbstractHexValidator instance;

    public static AbstractHexValidator getInstance() {
        return instance;
    }

    public static void setInstance(AbstractHexValidator instance) {
        AbstractHexValidator.instance = instance;
    }

    public abstract String validate(String input);
}
