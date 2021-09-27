package club.deltapvp.core.impl.message;

import club.deltapvp.core.core.Replacer;
import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.utilities.hex.HexValidator;
import club.deltapvp.deltacore.api.utilities.message.iface.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class iMessage implements Message {

    private final String message;
    private final HexValidator validator;

    public iMessage(List<String> list) {
        this(list.toArray(new String[0]));
    }

    public iMessage(String... msg) {
        message = String.join("\n", msg);

        validator = DeltaAPI.getInstance().getHexValidator();
    }

    @Override
    public void send(CommandSender commandSender, String... replacers) {
        Set<Replacer<String, String>> replacerSet = new HashSet<>();
        Replacer<String, String> replacer = new Replacer<>(null, null);
        for (int i = 0; i < replacers.length; ++i) {
            if (i % 2 == 0) {
                replacer.setInput(replacers[i]);
            } else {
                replacer.setOutput(replacers[i]);
                replacerSet.add(replacer);
                replacer = new Replacer<>(null, null);
            }
        }

        List<String> pendingMessages = new ArrayList<>();
        for (String msg : message.split("\n")) {
            final String[] m = {msg};
            replacerSet.stream().filter(r -> m[0].contains(r.getInput())).forEach(r -> m[0] = m[0].replaceAll(r.getInput(), r.getOutput()));
            pendingMessages.add(m[0]);
        }

        pendingMessages.forEach(s -> commandSender.sendMessage(validator.validate(s)));
    }

    @Override
    public void send(CommandSender commandSender) {
        commandSender.sendMessage(validator.validate(message));
    }

    @Override
    public void send(List<Player> list, String... replacers) {
        Set<Replacer<String, String>> replacerSet = new HashSet<>();
        Replacer<String, String> replacer = new Replacer<>(null, null);
        for (int i = 0; i < replacers.length; ++i) {
            if (i % 2 == 0) {
                replacer.setInput(replacers[i]);
            } else {
                replacer.setOutput(replacers[i]);
                replacerSet.add(replacer);
                replacer = new Replacer<>(null, null);
            }
        }

        List<String> pendingMessages = new ArrayList<>();
        for (String msg : message.split("\n")) {
            final String[] m = {msg};
            replacerSet.stream().filter(r -> m[0].contains(r.getInput())).forEach(r -> m[0] = m[0].replaceAll(r.getInput(), r.getOutput()));
            pendingMessages.add(m[0]);
        }

        list.forEach(player -> pendingMessages.forEach(s -> player.sendMessage(validator.validate(s))));
    }

    @Override
    public void send(List<Player> list) {
        list.forEach(player -> player.sendMessage(validator.validate(message)));
    }

    @Override
    public void broadcast(String... replacers) {
        Set<Replacer<String, String>> replacerSet = new HashSet<>();
        Replacer<String, String> replacer = new Replacer<>(null, null);
        for (int i = 0; i < replacers.length; ++i) {
            if (i % 2 == 0) {
                replacer.setInput(replacers[i]);
            } else {
                replacer.setOutput(replacers[i]);
                replacerSet.add(replacer);
                replacer = new Replacer<>(null, null);
            }
        }

        List<String> pendingMessages = new ArrayList<>();
        for (String msg : message.split("\n")) {
            final String[] m = {msg};
            replacerSet.stream().filter(r -> m[0].contains(r.getInput())).forEach(r -> m[0] = m[0].replaceAll(r.getInput(), r.getOutput()));
            pendingMessages.add(m[0]);
        }

        pendingMessages.forEach(s -> Bukkit.broadcastMessage(validator.validate(s)));
    }

    @Override
    public void broadcast() {
        Bukkit.broadcastMessage(validator.validate(message));
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getMessage(String... replacers) {
        Set<Replacer<String, String>> replacerSet = new HashSet<>();
        Replacer<String, String> replacer = new Replacer<>(null, null);
        for (int i = 0; i < replacers.length; ++i) {
            if (i % 2 == 0) {
                replacer.setInput(replacers[i]);
            } else {
                replacer.setOutput(replacers[i]);
                replacerSet.add(replacer);
                replacer = new Replacer<>(null, null);
            }
        }

        final String[] pendingMessage = {message};
        replacerSet.stream().filter(r -> pendingMessage[0].contains(r.getInput()))
                .forEach(r -> pendingMessage[0] = pendingMessage[0].replaceAll(r.getInput(), r.getOutput()));
        return pendingMessage[0];
    }
}
