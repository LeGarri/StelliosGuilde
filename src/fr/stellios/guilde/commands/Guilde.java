package fr.stellios.guilde.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.stellios.guilde.GuildeInstance;
import fr.stellios.guilde.Main;
import fr.stellios.guilde.Menu;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class Guilde implements CommandExecutor, TabCompleter {

	private Main main;
	
	private HashMap<Player, String> invites = new HashMap<Player, String>();
	
	public Guilde(Main main) {
		this.main = main;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(cmd.getName().equalsIgnoreCase("guilde") && sender.hasPermission("guilde.use")) {
			if(args.length == 3 && args[0].equalsIgnoreCase("addexp")) {
				if(sender.isOp()) {
					int exp = Integer.valueOf(args[2]);
					
					if(Bukkit.getPlayer(args[1]) != null) {
						Player target = Bukkit.getPlayer(args[1]);
						
						if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(target.getUniqueId())) == null) {
							target.sendMessage(main.getConfigManager().EXP_RECEIVED_NO_GUILDE.replaceAll("%exp%", String.valueOf(exp)));
							
							return true;
						}
						
						main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(target.getUniqueId())).addExp(exp);
						
						target.sendMessage(main.getConfigManager().EXP_RECEIVED.replaceAll("%exp%", String.valueOf(exp)));
						
						return true;
					}
				}
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("guilde") && sender instanceof Player && sender.hasPermission("guilde.use")) {
			Player player = (Player) sender;
			
			if(args.length == 0) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) == null) {
					player.sendMessage(main.getConfigManager().DONT_HAVE_GUILDE);
					
					return true;
				}
				
				player.openInventory(Menu.getGuildeMenu(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()))));
				
				return true;
			}
			
			if(args.length == 1 && args[0].equalsIgnoreCase("help")) {
				showHelpMessage(player);
				
				return true;
			}
			
			if(args.length >= 2 && args[0].equalsIgnoreCase("create")) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) != null) {
					player.sendMessage(main.getConfigManager().ALREADY_IN_GUILDE);
					
					return true;
				}
				
				String guildeName = "";
				for(int i = 0; i < args.length; i++) {
					if(i > 0) guildeName += args[i] + " ";
				}
				
				if(main.getDataManager().getGuildeByName(guildeName) != null) {
					player.sendMessage(main.getConfigManager().GUILD_ALREADY_EXIST);
					
					return true;
				}
				
				main.getDataManager().createGuilde(guildeName, Bukkit.getOfflinePlayer(player.getUniqueId())).saveGuilde();
				
				player.sendMessage(main.getConfigManager().GUILD_CREATED.replaceAll("%guilde%", guildeName));
				
				return true;
			}
			
			if(args.length == 2 && args[0].equalsIgnoreCase("invite")) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) == null) {
					player.sendMessage(main.getConfigManager().DONT_HAVE_GUILDE);
					
					return true;
				}
				
				if(Bukkit.getPlayer(args[1]) == null) {
					player.sendMessage(main.getConfigManager().PLAYER_NOT_ONLINE.replaceAll("%player%", args[1]));
					
					return true;
				}
				
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(Bukkit.getPlayer(args[1]).getUniqueId())) != null) {
					player.sendMessage(main.getConfigManager().ALREADY_HAVE_GUILDE);
					
					return true;
				}
				
				if(invites.containsKey(Bukkit.getPlayer(args[1]))) {
					player.sendMessage(main.getConfigManager().ALREADY_INVITED);
					
					return true;
				}
				
				player.sendMessage(main.getConfigManager().INVITE_PLAYER.replaceAll("%player%", Bukkit.getPlayer(args[1]).getName()));
				
				invites.put(Bukkit.getPlayer(args[1]), main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getGuildeName());
				Bukkit.getPlayer(args[1]).sendMessage(main.getConfigManager().INVITE_RECEIVED.replaceAll("%guilde%", main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getGuildeName()));
				
				TextComponent yes = new TextComponent("§a[Accepter]");
				yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guilde join " + main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getGuildeName()));
				TextComponent no = new TextComponent("§c[Refuser]");
				no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guilde deny " + main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getGuildeName()));
				
				Bukkit.getPlayer(args[1]).spigot().sendMessage(new ComponentBuilder(yes).append(" ").append(no).create());
				
				new BukkitRunnable() {
					Player invited = Bukkit.getPlayer(args[1]);
					
					@Override
					public void run() {
						if(invites.containsKey(invited)) {
							invites.remove(invited);
						}
					}
				}.runTaskLater(main, 1200);
				
				return true;
			}
			
			if(args.length >= 2 && args[0].equalsIgnoreCase("join")) {
				if(!invites.containsKey(player)) {
					player.sendMessage(main.getConfigManager().DONT_INVITE);
					
					return true;
				}
				
				String guildeName = "";
				for(int i = 0; i < args.length; i++) {
					if(i > 0) guildeName += args[i] + " ";
				}
				
				if(!invites.get(player).equalsIgnoreCase(guildeName)) {
					player.sendMessage(main.getConfigManager().DONT_INVITE_GUILDE);
					
					return true;
				}
				
				main.getDataManager().getGuildeByName(guildeName).addPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
				
				main.getDataManager().getGuildeByName(guildeName).sendMessage(main.getConfigManager().JOIN_GUILDE_BROADCAST.replaceAll("%player%", player.getName()).replaceAll("%guilde%", guildeName), Arrays.asList(player));
				
				player.sendMessage(main.getConfigManager().JOIN_GUILDE.replaceAll("%guilde%", guildeName));
				
				return true;
			}
			
			if(args.length >= 2 && args[0].equalsIgnoreCase("deny")) {
				if(!invites.containsKey(player)) {
					player.sendMessage(main.getConfigManager().DONT_INVITE);
					
					return true;
				}
				
				String guildeName = "";
				for(int i = 0; i < args.length; i++) {
					if(i > 0) guildeName += args[i] + " ";
				}
				
				if(!invites.get(player).equalsIgnoreCase(guildeName)) {
					player.sendMessage(main.getConfigManager().DONT_INVITE_GUILDE);
					
					return true;
				}
				
				invites.remove(player);
				
				player.sendMessage(main.getConfigManager().DENY_GUILDE);
				
				main.getDataManager().getGuildeByName(guildeName).sendMessage(main.getConfigManager().DENY_GUILDE_BROADCAST.replaceAll("%player%", player.getName()));
				
				return true;
			}
			
			if(args.length == 1 && args[0].equalsIgnoreCase("leave")) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) == null) {
					player.sendMessage(main.getConfigManager().DONT_HAVE_GUILDE);
					
					return true;
				}
				
				GuildeInstance gi = main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
				gi.removePlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
				gi.sendMessage(main.getConfigManager().GUILDE_LEAVE_BROADCAST.replaceAll("%player%", player.getName()));
				
				player.sendMessage(main.getConfigManager().GUILDE_LEAVE);
				
				return true;
			}
			
			if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
				player.openInventory(Menu.getGuildeList(main, 0));
				
				return true;
			}
			
			if(args.length >= 2 && args[0].equalsIgnoreCase("rename")) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) == null) {
					player.sendMessage(main.getConfigManager().DONT_HAVE_GUILDE);
					
					return true;
				}
				
				if(!main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getOwner().equals(Bukkit.getOfflinePlayer(player.getUniqueId()))) {
					player.sendMessage(main.getConfigManager().NOT_OWNER);
					
					return true;
				}
				
				String guildeName = "";
				for(int i = 0; i < args.length; i++) {
					if(i > 0) guildeName += args[i] + " ";
				}
				
				if(main.getDataManager().getGuildeByName(guildeName) != null) {
					player.sendMessage(main.getConfigManager().GUILD_ALREADY_EXIST);
					
					return true;
				}
				
				GuildeInstance gi = main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
				gi.setGuildeName(guildeName);
				gi.sendMessage(main.getConfigManager().GUILDE_RENAME_BROADCAST.replaceAll("%player%", player.getName()).replaceAll("%guilde%", gi.getGuildeName()), Arrays.asList(player));
				
				player.sendMessage(main.getConfigManager().GUILDE_RENAME.replaceAll("%guilde%", gi.getGuildeName()));
				
				return true;
			}
			
			if(args.length >= 2 && args[0].equalsIgnoreCase("desc")) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) == null) {
					player.sendMessage(main.getConfigManager().DONT_HAVE_GUILDE);
					
					return true;
				}
				
				if(!main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getOwner().equals(Bukkit.getOfflinePlayer(player.getUniqueId()))) {
					player.sendMessage(main.getConfigManager().NOT_OWNER);
					
					return true;
				}
				
				String desc = "";
				for(int i = 0; i < args.length; i++) {
					if(i > 0) desc += args[i] + " ";
				}
				
				GuildeInstance gi = main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
				gi.setDesc(desc.replaceAll("&", "§"));
				gi.sendMessage(main.getConfigManager().DESC_CHANGED_BROADCAST.replaceAll("%player%", player.getName()), Arrays.asList(player));
				
				player.sendMessage(main.getConfigManager().DESC_CHANGED);
				
				return true;
			}
			
			if(args.length == 2 && args[0].equalsIgnoreCase("setowner")) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) == null) {
					player.sendMessage(main.getConfigManager().DONT_HAVE_GUILDE);
					
					return true;
				}
				
				if(!main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getOwner().equals(Bukkit.getOfflinePlayer(player.getUniqueId()))) {
					player.sendMessage(main.getConfigManager().NOT_OWNER);
					
					return true;
				}
				
				if(Bukkit.getPlayer(args[1]) == null) {
					player.sendMessage(main.getConfigManager().PLAYER_NOT_ONLINE.replaceAll("%player%", args[1]));
					
					return true;
				}
				
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getOwner().equals(Bukkit.getOfflinePlayer(Bukkit.getPlayer(args[1]).getUniqueId()))) {
					player.sendMessage(main.getConfigManager().OWNER_ALREADY_OWNER);
					
					return true;
				}
				
				if(!main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getPlayers().contains(Bukkit.getOfflinePlayer(Bukkit.getPlayer(args[1]).getUniqueId()))) {
					player.sendMessage(main.getConfigManager().PLAYER_NOT_IN_GUILDE.replaceAll("%player%", Bukkit.getPlayer(args[1]).getName()));
					
					return true;
				}
				
				GuildeInstance gi = main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
				gi.setOwner(Bukkit.getOfflinePlayer(Bukkit.getPlayer(args[1]).getUniqueId()));
				gi.sendMessage(main.getConfigManager().NEW_OWNER_BROADCAST.replaceAll("%player%", Bukkit.getPlayer(args[1]).getName()));
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset lands.ownlands.2");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + Bukkit.getPlayer(args[1]).getName() + " permission set lands.ownlands.2");
				
				return true;
			}
			
			if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				if(player.isOp()) {
					main.reloadConfig();
					player.sendMessage("§eFichier config.yml recharger avec succès");
					
					main.reloadData();
					player.sendMessage("§eFichier data.yml recharger avec succès");
				} else player.sendMessage(main.getConfigManager().PERMISSION);
				
				return true;
			}
			
			if(args.length == 1 && args[0].equalsIgnoreCase("sethome")) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) == null) {
					player.sendMessage(main.getConfigManager().DONT_HAVE_GUILDE);
					
					return true;
				}
				
				if(!main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getOwner().equals(Bukkit.getOfflinePlayer(player.getUniqueId()))) {
					player.sendMessage(main.getConfigManager().NOT_OWNER);
					
					return true;
				}
				
				GuildeInstance gi = main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
				gi.setHome(player.getLocation());
				gi.sendMessage(main.getConfigManager().HOME_CHANGED);
				
				return true;
			}
			
			if(args.length == 1 && args[0].equalsIgnoreCase("home")) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) == null) {
					player.sendMessage(main.getConfigManager().DONT_HAVE_GUILDE);
					
					return true;
				}
				
				player.teleport(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getHome());
				
				return true;
			}
			
			
			showHelpMessage(player);
			
			return true;
		}
		
		return false;
	}
	
	@Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {
        if(cmd.getName().equalsIgnoreCase("guilde")) {
            List<String> tab = new ArrayList<String>();

            //commande /guilde
            if(args.length == 1) {
                tab.add("help");
                tab.add("create");
                tab.add("invite");
                tab.add("join");
                tab.add("deny");
                tab.add("leave");
                tab.add("list");
                tab.add("desc");
                tab.add("rename");
                tab.add("setowner");
                tab.add("sethome");
                tab.add("home");
                if(sender.isOp()) tab.add("reload");
                if(sender.isOp()) tab.add("addexp");

                return tab;
            }

        }

        return null;
    }
	
	private void showHelpMessage(Player player) {
		//TODO: rendre le message plus beau
		player.sendMessage("§6================ GUILDE ================");
		player.sendMessage("");
		player.sendMessage("§6/guilde help - §eAffiche le message d'aide");
		player.sendMessage("§6/guilde create §e<Nom de la Guilde> §6- §eCréer votre guilde");
		player.sendMessage("§6/guilde invite §e<Nom du Joueur> §6- §eInviter un joueur");
		player.sendMessage("§6/guilde leave - §eQuitter votre guilde");
		player.sendMessage("§6/guilde list §6- §eAffiche la liste des guildes existante");
		player.sendMessage("§6/guilde desc §e<Description> §6- §eEditer la description de la guilde");
		player.sendMessage("§6/guilde rename §e<Nouveau nom de la Guilde> §6- §eChanger le nom de la guilde");
		player.sendMessage("§6/guilde setowner §e<Nom du Joueur> §6- §eChanger le propriétaire de la guilde");
		player.sendMessage("§6/guilde sethome - §eDéfinie le home de la guilde");
		player.sendMessage("§6/guilde home - §eVous téléporte au home de votre guilde");
	}

}
