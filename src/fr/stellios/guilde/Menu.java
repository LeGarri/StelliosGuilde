package fr.stellios.guilde;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
		inv.setItem(48, Items.getLightBlueGlass());
		if(page > 0) inv.setItem(49, Items.getPaperPrevious());
		inv.setItem(50, Items.getLightBlueGlass());
		inv.setItem(51, Items.getPaperNext());
		inv.setItem(52, Items.getBlueGlass());
		inv.setItem(53, Items.getBlueGlass());
		
		int i = 28 * page;
		
		for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
			if(i < 28 * (page + 1)) {
				GuildeInstance gi = main.getDataManager().getGuildeByName(s);
				
				ItemStack item = new ItemStack(gi.getIcon());
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName("§6" + gi.getGuildeName());
				List<String> lore = new ArrayList<String>();
				lore.add("");
				lore.add("§eMembres:");
				for(OfflinePlayer op : gi.getPlayers()) {
					lore.add("§7- §e" + op.getName());
				}
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				
				inv.setItem(i, item);
			}
			
			i++;
		}
		
		return inv;
	}
	
	public static Inventory getGuildeMenu() {
		Inventory inv = Bukkit.createInventory(null, 6*9, "§6Menu Guilde");
		return inv;
	}
	
	public static Inventory getGuildeIcon() {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§6Icone de la Guilde");
		
		inv.setItem(0, Items.getBlueGlass());
		inv.setItem(1, Items.getBlueGlass());
		inv.setItem(2, Items.getLightBlueGlass());
		inv.setItem(3, Items.getLightBlueGlass());
		inv.setItem(4, Items.getLightBlueGlass());
		inv.setItem(5, Items.getLightBlueGlass());
		inv.setItem(6, Items.getLightBlueGlass());
		inv.setItem(7, Items.getLightBlueGlass());
		inv.setItem(8, Items.getBlueGlass());
		inv.setItem(9, Items.getBlueGlass());
		inv.setItem(10, Items.getBlueGlass());
		inv.setItem(11, Items.getLightBlueGlass());
		inv.setItem(12, Items.getLightBlueGlass());
		inv.setItem(13, Items.getLightBlueGlass());
		inv.setItem(15, Items.getLightBlueGlass());
		inv.setItem(16, Items.getLightBlueGlass());
		inv.setItem(17, Items.getLightBlueGlass());
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
