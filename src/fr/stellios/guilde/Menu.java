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
					
					GuildeInstance gi = main.getDataManager().getGuildeByName(s);
					
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
						lore.add("§7- §e" + op.getName());
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
		Inventory inv = Bukkit.createInventory(null, 6*9, "§6Menu Guilde");
		
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
		inv.setItem(50, Items.getLightBlueGlass());
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
		
		if(gi.getLevel() >= 2) inv.setItem(11, Items.getGuildeLevel(2, true, Arrays.asList("", "§eAvantages:", "§7- §eExpérience gagnée §6x1.1", "§7- §eSoin recu + 5%", "")));
		else inv.setItem(11, Items.getGuildeLevel(2, false, Arrays.asList("", "§eXP: §6" + gi.getExp() + " / 100", "", "§eAvantages:", "§7- §eExpérience gagnée §6x1.1", "§7- §eSoin recu + 5%", "")));
		
		return inv;
	}
	
}

































