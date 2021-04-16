package fr.stellios.guilde.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.stellios.guilde.Items;
import fr.stellios.guilde.Main;
import fr.stellios.guilde.Menu;

public class MenuListener implements Listener {

	private Main main;
	
	public MenuListener(Main main) {
		this.main = main;
	}
	
	
	@EventHandler
	public void OnInteractInventory(InventoryClickEvent e) {
		if(e.getCurrentItem() != null && e.getView().getTitle().contains("§6Liste des Guildes - page")) {
			e.setCancelled(true);
			
			Player player = (Player) e.getWhoClicked();
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(Items.getPaperNext().getItemMeta().getDisplayName())) {
				String[] title = e.getView().getTitle().split(" ");
				int page = Integer.valueOf(title[title.length - 1]);
				
				player.closeInventory();
				player.openInventory(Menu.getGuildeList(main, page + 1));
			}
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(Items.getPaperPrevious().getItemMeta().getDisplayName())) {
				String[] title = e.getView().getTitle().split(" ");
				int page = Integer.valueOf(title[title.length - 1]);
				
				player.closeInventory();
				player.openInventory(Menu.getGuildeList(main, page - 1));
			}
		}
		
		if(e.getCurrentItem() != null && e.getView().getTitle().equals("§6Menu Guilde")) {
			e.setCancelled(true);
			
			Player player = (Player) e.getWhoClicked();
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Icone de la guilde")) {
				if(!main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getOwner().equals(Bukkit.getOfflinePlayer(player.getUniqueId()))) {
					player.sendMessage(main.getConfigManager().NOT_OWNER);
					
					return;
				}
				
				player.closeInventory();
				player.openInventory(Menu.getGuildeMenu(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()))));
			}
		}
		
		if(e.getCurrentItem() != null && e.getView().getTitle().equals("§6Icone de la Guilde")) {
			e.setCancelled(true);
		}
	}
	
}








































