package fr.stellios.guilde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class Menu {

	public static Inventory getGuildeList(Main main, int page) {
		Inventory inv = Bukkit.createInventory(null, 6*9, "§6Liste des Guildes - page " + page);
		
		inv.setItem(0, Items.getBlueGlass());
		inv.setItem(1, Items.getBlueGlass());
		inv.setItem(2, Items.getLightBlueGlass());
		inv.setItem(3, Items.getLightBlueGlass());
		inv.setItem(4, Items.getLightBlueGlass());
		inv.setItem(5, Items.getLightBlueGlass());
		inv.setItem(6, Items.getLightBlueGlass());
		inv.setItem(7, Items.getBlueGlass());
		inv.setItem(8, Items.getBlueGlass());
		inv.setItem(9, Items.getBlueGlass());
		inv.setItem(17, Items.getBlueGlass());
		inv.setItem(18, Items.getLightBlueGlass());
		inv.setItem(26, Items.getLightBlueGlass());
		inv.setItem(27, Items.getLightBlueGlass());
		inv.setItem(35, Items.getLightBlueGlass());
		inv.setItem(36, Items.getBlueGlass());
		inv.setItem(44, Items.getBlueGlass());
		inv.setItem(45, Items.getBlueGlass());
		inv.setItem(46, Items.getBlueGlass());
		inv.setItem(47, Items.getLightBlueGlass());
		if(page > 0) inv.setItem(48, Items.getPaperPrevious());
		inv.setItem(49, Items.getLightBlueGlass());
		inv.setItem(50, Items.getPaperNext());
		inv.setItem(51, Items.getLightBlueGlass());
		inv.setItem(52, Items.getBlueGlass());
		inv.setItem(53, Items.getBlueGlass());
		
		int i = 0;
		int count = 10;
		
		if(main.getData().getConfigurationSection("guildes") != null && !main.getData().getConfigurationSection("guildes").getKeys(false).isEmpty()) {
			for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
				if(i >= 28 * page && i < 28 * (page + 1)) {
					if(count == 17) count = 19;
					else if(count == 26) count = 28;
					else if(count == 35) count = 37;
					
					GuildeInstance gi = main.getDataManager().getGuildeByName(s.replaceAll("bbb149", " "));
					
					ItemStack item = new ItemStack(gi.getIcon());
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setDisplayName("§6" + gi.getGuildeName());
					List<String> lore = new ArrayList<String>();
					lore.add("");
					String[] desc = gi.getDesc().split(" ");
					int countWord = 0;
					int totalCountWord = 0;
					String line = "§f";
					for(String w : desc) {
						if(countWord < 6) {
							line += w + " ";
							if(totalCountWord == desc.length - 1) lore.add(line);
						}
						else {
							lore.add(line);
							line = "§f" + w + " ";
							countWord = 0;
						}
						
						countWord++;
						totalCountWord++;
					}
					lore.add(" ");
					lore.add("§eNiveau de la guilde: §6" + gi.getLevel());
					lore.add(" ");
					lore.add("§eMembres:");
					for(OfflinePlayer op : gi.getPlayers()) {
						String[] prefix = ChatColor.stripColor(Main.getChat().getPlayerPrefix(op.getPlayer())).split(" ");
						
						String prefixes = "";
						
						for(int j = 0; j < prefix.length; j++) {
							if(prefix[j].contains("&#")) {
								if(prefix[j].contains("Dieu") || prefix[j].contains("Demi-Dieu") || prefix[j].contains("Hero")) {
									String color = prefix[j].substring(1, 8);
									String rank = prefix[j].substring(8, prefix[j].length());
									
									ChatColor cc = ChatColor.of(color);
									
									String rankWithColor = cc + rank;
									
									prefixes += rankWithColor + " ";
								} else {
									String start = prefix[j].substring(0, 1);
									String color = prefix[j].substring(2, 9);
									String rank = prefix[j].substring(9, prefix[j].length() - 1);
									String end = prefix[j].substring(prefix[j].length() - 1);
									
									ChatColor cc = ChatColor.of(color);
									
									String rankWithColor = "§7" + start + cc + rank + "§7" + end;
									
									prefixes += rankWithColor + " ";
								}
							} else {
								String start = prefix[j].substring(0, 1);
								String rank = prefix[j].substring(1, prefix[j].length() - 1);
								String end = prefix[j].substring(prefix[j].length() - 1);
								
								String rankColor = "";
								if(rank.equalsIgnoreCase("Admin")) rankColor = "§c";
								else if(rank.equalsIgnoreCase("Développeur")) rankColor = "§c";
								else if(rank.equalsIgnoreCase("Modo")) rankColor = "§a";
								else if(rank.equalsIgnoreCase("Builder")) rankColor = "§e";
								else if(rank.equalsIgnoreCase("Domestique")) rankColor = "§7";
								else if(rank.equalsIgnoreCase("Esclave")) rankColor = "§8";
								
								String rankWithColor = "§7" + start + rankColor + rank + "§7" + end;
								
								prefixes += rankWithColor + " ";
							}
						}
						
						lore.add("§7- " + prefixes + "§f" + op.getName());
					}
					itemMeta.setLore(lore);
					item.setItemMeta(itemMeta);
					
					inv.setItem(count, item);
					
					count++;
				}
				
				i++;
			}
		}
		
		return inv;
	}
	
	public static Inventory getGuildeMenu(GuildeInstance gi) {
		Inventory inv = Bukkit.createInventory(null, 6*9, "§6Menu de la Guilde");
		
		inv.setItem(0, Items.getBlueGlass());
		inv.setItem(1, Items.getBlueGlass());
		inv.setItem(2, Items.getLightBlueGlass());
		inv.setItem(3, Items.getLightBlueGlass());
		inv.setItem(4, Items.getLightBlueGlass());
		inv.setItem(5, Items.getLightBlueGlass());
		inv.setItem(6, Items.getLightBlueGlass());
		inv.setItem(7, Items.getBlueGlass());
		inv.setItem(8, Items.getBlueGlass());
		inv.setItem(9, Items.getBlueGlass());
		inv.setItem(17, Items.getBlueGlass());
		inv.setItem(18, Items.getLightBlueGlass());
		inv.setItem(26, Items.getLightBlueGlass());
		inv.setItem(27, Items.getLightBlueGlass());
		inv.setItem(35, Items.getLightBlueGlass());
		inv.setItem(36, Items.getBlueGlass());
		inv.setItem(44, Items.getBlueGlass());
		inv.setItem(45, Items.getBlueGlass());
		inv.setItem(46, Items.getBlueGlass());
		inv.setItem(47, Items.getHomeGuilde());
		inv.setItem(48, Items.getAccountGuilde());
		inv.setItem(49, Items.getGuildeLevelIcon(gi));
		inv.setItem(50, Items.getGuildeBoost(gi));
		inv.setItem(51, Items.getLightBlueGlass());
		inv.setItem(52, Items.getBlueGlass());
		inv.setItem(53, Items.getBlueGlass());
		
		ItemStack icon = new ItemStack(gi.getIcon());
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§eIcone de la guilde");
		icon.setItemMeta(iconMeta);
		inv.setItem(4, icon);
		
		int count = 10;
		for(OfflinePlayer op : gi.getPlayers()) {
			if(count == 17) count = 19;
			else if(count == 26) count = 28;
			else if(count == 35) count = 37;
			
			ItemStack head = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta headMeta = (SkullMeta) head.getItemMeta();
			headMeta.setOwningPlayer(op);
			headMeta.setDisplayName("§e" + op.getName());
			if(gi.getOwner().equals(op)) headMeta.setLore(Arrays.asList("", "§cPropriétaire", ""));
			head.setItemMeta(headMeta);
			inv.setItem(count, head);
			
			count++;
		}
		
		return inv;
	}
	
	public static Inventory getGuildeIcon(GuildeInstance gi) {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§6Icone de la Guilde");
		
		inv.setItem(0, Items.getBlueGlass());
		inv.setItem(1, Items.getBlueGlass());
		inv.setItem(2, Items.getLightBlueGlass());
		inv.setItem(3, Items.getLightBlueGlass());
		inv.setItem(4, Items.getLightBlueGlass());
		inv.setItem(5, Items.getLightBlueGlass());
		inv.setItem(6, Items.getLightBlueGlass());
		inv.setItem(7, Items.getBlueGlass());
		inv.setItem(8, Items.getBlueGlass());
		inv.setItem(9, Items.getBlueGlass());
		inv.setItem(10, Items.getLightBlueGlass());
		inv.setItem(11, Items.getLightBlueGlass());
		inv.setItem(12, Items.getLightBlueGlass());
		inv.setItem(13, new ItemStack(gi.getIcon()));
		inv.setItem(14, Items.getLightBlueGlass());
		inv.setItem(15, Items.getLightBlueGlass());
		inv.setItem(16, Items.getLightBlueGlass());
		inv.setItem(17, Items.getBlueGlass());
		inv.setItem(18, Items.getBlueGlass());
		inv.setItem(19, Items.getBlueGlass());
		inv.setItem(20, Items.getLightBlueGlass());
		inv.setItem(21, Items.getLightBlueGlass());
		inv.setItem(22, Items.getLightBlueGlass());
		inv.setItem(23, Items.getLightBlueGlass());
		inv.setItem(24, Items.getLightBlueGlass());
		inv.setItem(25, Items.getBlueGlass());
		inv.setItem(26, Items.getBlueGlass());
		
		return inv;
	}
	
	public static Inventory getGuildeAccount(GuildeInstance gi) {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§6Compte de la Guilde");
		
		inv.setItem(0, Items.getBlueGlass());
		inv.setItem(1, Items.getBlueGlass());
		inv.setItem(2, Items.getLightBlueGlass());
		inv.setItem(3, Items.getLightBlueGlass());
		inv.setItem(4, Items.getLightBlueGlass());
		inv.setItem(5, Items.getLightBlueGlass());
		inv.setItem(6, Items.getLightBlueGlass());
		inv.setItem(7, Items.getBlueGlass());
		inv.setItem(8, Items.getBlueGlass());
		inv.setItem(9, Items.getBlueGlass());
		inv.setItem(10, Items.getLightBlueGlass());
		inv.setItem(11, Items.getGuildeDeposit());
		inv.setItem(12, Items.getLightBlueGlass());
		inv.setItem(13, Items.getGuildeMoney(gi.getAccount()));
		inv.setItem(14, Items.getLightBlueGlass());
		inv.setItem(15, Items.getGuildeWithdraw());
		inv.setItem(16, Items.getLightBlueGlass());
		inv.setItem(17, Items.getBlueGlass());
		inv.setItem(18, Items.getBlueGlass());
		inv.setItem(19, Items.getBlueGlass());
		inv.setItem(20, Items.getLightBlueGlass());
		inv.setItem(21, Items.getLightBlueGlass());
		inv.setItem(22, Items.getLightBlueGlass());
		inv.setItem(23, Items.getLightBlueGlass());
		inv.setItem(24, Items.getLightBlueGlass());
		inv.setItem(25, Items.getBlueGlass());
		inv.setItem(26, Items.getBlueGlass());
		
		return inv;
	}
	
	public static Inventory getGuildeLevel(GuildeInstance gi) {
		Inventory inv = Bukkit.createInventory(null, 6*9, "§6Niveau de la Guilde");
		
		inv.setItem(0, Items.getBlueGlass());
		inv.setItem(1, Items.getBlueGlass());
		inv.setItem(2, Items.getLightBlueGlass());
		inv.setItem(3, Items.getLightBlueGlass());
		inv.setItem(4, Items.getLightBlueGlass());
		inv.setItem(5, Items.getLightBlueGlass());
		inv.setItem(6, Items.getLightBlueGlass());
		inv.setItem(7, Items.getBlueGlass());
		inv.setItem(8, Items.getBlueGlass());
		inv.setItem(9, Items.getBlueGlass());
		inv.setItem(17, Items.getBlueGlass());
		inv.setItem(18, Items.getLightBlueGlass());
		inv.setItem(26, Items.getLightBlueGlass());
		inv.setItem(27, Items.getLightBlueGlass());
		inv.setItem(35, Items.getLightBlueGlass());
		inv.setItem(36, Items.getBlueGlass());
		inv.setItem(44, Items.getBlueGlass());
		inv.setItem(45, Items.getBlueGlass());
		inv.setItem(46, Items.getBlueGlass());
		inv.setItem(47, Items.getHomeGuilde());
		inv.setItem(48, Items.getAccountGuilde());
		inv.setItem(49, Items.getLightBlueGlass());
		inv.setItem(50, Items.getLightBlueGlass());
		inv.setItem(51, Items.getLightBlueGlass());
		inv.setItem(52, Items.getBlueGlass());
		inv.setItem(53, Items.getBlueGlass());
		
		inv.setItem(10, Items.getGuildeLevel(1, true, Arrays.asList("")));
		
		if(gi.getLevel() >= 2) inv.setItem(11, Items.getGuildeLevel(2, true, Arrays.asList("", "§eAvantages:", "§7- §eExpérience gagnée §6x1.1", "§7- §eSoin reçu §6+ 5%", "")));
		else inv.setItem(11, Items.getGuildeLevel(2, false, Arrays.asList("", "§eXP: §6" + gi.getExp() + " / " + gi.getExpToLevelUp(), "", "§eAvantages:", "§7- §eExpérience gagnée §6x1.1", "§7- §eSoin recu §6+ 5%", "")));
		
		if(gi.getLevel() >= 3) inv.setItem(12, Items.getGuildeLevel(3, true, Arrays.asList("", "§eAvantages:", "§7- §eDurées des potions §6+15%", "§7- §61 §eCoeur en plus", "")));
		else inv.setItem(12, Items.getGuildeLevel(3, false, Arrays.asList("", "§eXP: §6" + gi.getExp() + " / " + gi.getExpToLevelUp(), "", "§eAvantages:", "§7- §eDurée des potions §6+15%", "§7- §61 §eCoeur en plus", "")));
		
		if(gi.getLevel() >= 4) inv.setItem(13, Items.getGuildeLevel(4, true, Arrays.asList("", "§eAvantages:", "§7- §eExpérience gagnée §6x1.2", "§7- Dégâts émis §6+1", "")));
		else  inv.setItem(13, Items.getGuildeLevel(4, false, Arrays.asList("", "§eXP: §6" + gi.getExp() + " / " + gi.getExpToLevelUp(), "", "§eAvantages:", "§7- §eExpérience gagnée §6x1.2", "§7- §eDégâts émis §6+1", "")));
		
		if(gi.getLevel() >= 5) inv.setItem(14, Items.getGuildeLevel(5, true, Arrays.asList("", "§eAvantages:", "§7- §eDurées des potions §6+30%", "§7- §eDébloque les §6boosts", "")));
		else  inv.setItem(14, Items.getGuildeLevel(5, false, Arrays.asList("", "§eXP: §6" + gi.getExp() + " / " + gi.getExpToLevelUp(), "", "§eAvantages:", "§7- §eDurée des potions §6+30%", "§7- §eDébloque les §6boosts", "")));
		
		return inv;
	}
	
	public static Inventory getGuildeBoost(GuildeInstance gi) {
		Inventory inv;
		
		if(gi.getBoost() == null) inv = Bukkit.createInventory(null, 3*9, "§6Boost de la Guilde");
		else inv = Bukkit.createInventory(null, 3*9, "§6Choisir le boost de la Guilde");
		
		inv.setItem(0, Items.getBlueGlass());
		inv.setItem(1, Items.getBlueGlass());
		inv.setItem(2, Items.getLightBlueGlass());
		inv.setItem(3, Items.getLightBlueGlass());
		inv.setItem(4, Items.getLightBlueGlass());
		inv.setItem(5, Items.getLightBlueGlass());
		inv.setItem(6, Items.getLightBlueGlass());
		inv.setItem(7, Items.getBlueGlass());
		inv.setItem(8, Items.getBlueGlass());
		inv.setItem(9, Items.getBlueGlass());
		inv.setItem(10, Items.getLightBlueGlass());
		inv.setItem(11, Items.getLightBlueGlass());
		if(gi.getBoost() != null) inv.setItem(12, Items.getLightBlueGlass());
		else inv.setItem(12, Items.getGuildeChoiceBoost(gi.getBoostList().get(0)));
		if(gi.getBoost() != null) inv.setItem(13, Items.getGuildeActiveBoost(gi));
		else inv.setItem(13, Items.getGuildeChoiceBoost(gi.getBoostList().get(1)));
		if(gi.getBoost() != null) inv.setItem(14, Items.getLightBlueGlass());
		else inv.setItem(14, Items.getGuildeChoiceBoost(gi.getBoostList().get(2)));
		inv.setItem(15, Items.getLightBlueGlass());
		inv.setItem(16, Items.getLightBlueGlass());
		inv.setItem(17, Items.getBlueGlass());
		inv.setItem(18, Items.getBlueGlass());
		inv.setItem(19, Items.getBlueGlass());
		inv.setItem(20, Items.getLightBlueGlass());
		inv.setItem(21, Items.getLightBlueGlass());
		inv.setItem(22, Items.getLightBlueGlass());
		inv.setItem(23, Items.getLightBlueGlass());
		inv.setItem(24, Items.getLightBlueGlass());
		inv.setItem(25, Items.getBlueGlass());
		inv.setItem(26, Items.getBlueGlass());
		
		return inv;
	}
	
}

































