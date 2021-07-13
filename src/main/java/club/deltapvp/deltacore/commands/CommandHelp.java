package club.deltapvp.deltacore.commands;

import club.deltapvp.deltacore.api.commands.ICommand;
import club.deltapvp.deltacore.api.commands.annotation.CommandInfo;
import club.deltapvp.deltacore.api.utilities.Message;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "help")
public class CommandHelp extends ICommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        new Message("you have got help lol").send(sender);
    }
}
