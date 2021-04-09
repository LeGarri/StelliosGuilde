package fr.stellios.guilde.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Guilde implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(cmd.getName().equalsIgnoreCase("relique")) {
			if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) { // /guilde ou /guilde help
				showHelpMessage(sender);
			}
			
			return true;
		}
		
		return false;
	}
	
	private void showHelpMessage(CommandSender sender) {
		
	}

}
