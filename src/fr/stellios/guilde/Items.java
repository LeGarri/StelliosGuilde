package fr.stellios.guilde;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

	/////////////////////////////////////////
	//									   //
	//				GUI            		   //
	//									   //
	/////////////////////////////////////////
	
	
	public static ItemStack getBlueGlass() {
		ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(" ");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getLightBlueGlass() {
		ItemStack item = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(" ");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getPaperNext() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§aPage Suivante");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getPaperPrevious() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§cPage Précédente");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getHomeGuilde() {
		ItemStack item = new ItemStack(Material.GRASS_BLOCK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§eSe téléporter au home de la guilde");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
}
