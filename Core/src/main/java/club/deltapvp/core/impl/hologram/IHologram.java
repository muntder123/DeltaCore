package club.deltapvp.core.impl.hologram;

import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.utilities.hex.HexValidator;
import club.deltapvp.deltacore.api.utilities.hologram.Hologram;
import club.deltapvp.deltacore.api.utilities.hologram.HologramInteractEvent;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public class IHologram implements Hologram {

    private final LinkedList<String> lines;
    private final LinkedList<ArmorStand> entities;
    private final HexValidator hexValidator;
    private Location location;
    @Setter
    private String stringID;
    @Setter
    private int intID = -1;
    private double spacesInBetween = .35;
    private BiConsumer<Player, HologramInteractEvent> interact;

    public IHologram(Location location, String... text) {
        this.location = location;

        lines = new LinkedList<>();
        lines.addAll(Arrays.asList(text));

        entities = new LinkedList<>();

        hexValidator = DeltaAPI.getInstance().getHexValidator();

        load();
    }

    @Override
    public String getStringID() {
        return stringID;
    }

    @Override
    public int getIntID() {
        return intID;
    }

    @Override
    public LinkedList<String> getLines() {
        return lines;
    }

    @Override
    public LinkedList<ArmorStand> getEntities() {
        return entities;
    }

    @Override
    public void update() {
        remove();
        load();
    }

    @Override
    public void remove() {
        if (entities == null || entities.isEmpty())
            return;

        entities.forEach(Entity::remove);
    }

    @Override
    public void load() {
        Location clone = location.clone();
        for (String line : lines) {
            ArmorStand armorStand = create(clone, line);
            entities.add(armorStand);
            clone.subtract(0, spacesInBetween, 0);
        }
    }

    private ArmorStand create(Location location, String text) {
        ArmorStand entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        entity.setGravity(false);
        entity.setArms(false);
        entity.setBasePlate(false);
        entity.setSmall(true);
        entity.setVisible(false);
        entity.setCustomName(hexValidator.validate(text));
        entity.setCustomNameVisible(true);

        return entity;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        update();
    }

    @Override
    public void onInteraction(BiConsumer<Player, HologramInteractEvent> biConsumer) {
        this.interact = biConsumer;
    }

    @Override
    public BiConsumer<Player, HologramInteractEvent> getInteractionFunction() {
        return interact;
    }

    @Override
    public void addLine(String s) {
        lines.add(s);
        update();
    }

    @Override
    public void setLine(int i, String s) {
        lines.set(i, s);
        update();
    }

    @Override
    public void setSpaceInBetween(double v) {
        this.spacesInBetween = v;
        update();
    }
}
