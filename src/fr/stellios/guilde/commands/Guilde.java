package fr.stellios.guilde.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Guilde implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(cmd.getName().equalsIgnoreCase("guilde")) {
			if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) { // /guilde ou /guilde help
				showHelpMessage(sender);
			}
			
			return true;
		}
		
		return false;
	}
	
	private void showHelpMessage(CommandSender sender) {
		sender.sendMessage("§6/guilde create §e<Nom de la Guilde> §6- §eCréer votre guilde");
		sender.sendMessage("§6/guilde invite §e<Nom du Joueur> §6- §eInviter un joueur");
		sender.sendMessage("§6/guilde join §e<Nom de la Guilde> §6- §eRejoind une guilde");
		sender.sendMessage("§6/guilde list §6- §eAffiche la liste des guildes existante");
		sender.sendMessage("§6/guilde desc §e<Description> §6- §eEditer la description de la guilde");
		sender.sendMessage("§6/guilde rename §e<Nouveau nom de la Guilde> §6- §eChnager le nom de la guilde");
	}

}
