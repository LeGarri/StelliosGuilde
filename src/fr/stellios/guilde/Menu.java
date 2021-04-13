package fr.stellios.guilde;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class Menu {

	public static Inventory getGuildeList() {
		Inventory inv = Bukkit.createInventory(null, 6*9, "§6Liste des Guildes");
		
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
		inv.setItem(49, Items.getPaperPrevious());
		inv.setItem(50, Items.getLightBlueGlass());
		inv.setItem(51, Items.getPaperNext());
		inv.setItem(52, Items.getBlueGlass());
		inv.setItem(53, Items.getBlueGlass());
		
		return inv;
	}
	
	public static Inventory getGuildeMenu() {
		Inventory inv = Bukkit.createInventory(null, 6*9, "§6Menu Guilde");
		return inv;
	}
	
	public static Inventory getGuildeIcon() {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§6Changement icon de la Guilde");
		
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
