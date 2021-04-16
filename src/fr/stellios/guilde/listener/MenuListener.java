package fr.stellios.guilde.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.stellios.guilde.GuildeInstance;
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
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eIcone de la guilde")) {
				if(!main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getOwner().equals(Bukkit.getOfflinePlayer(player.getUniqueId()))) {
					player.sendMessage(main.getConfigManager().NOT_OWNER);
					
					return;
				}
				
				player.closeInventory();
				player.openInventory(Menu.getGuildeIcon(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()))));
			}
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(Items.getHomeGuilde().getItemMeta().getDisplayName())) {
				player.closeInventory();
				player.teleport(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getHome());
			}
		}
		
		if(e.getCurrentItem() != null && e.getView().getTitle().equals("§6Icone de la Guilde")) {
			if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().equals(" ")) e.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if(e.getView().getTitle().equals("§6Icone de la Guilde")) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					GuildeInstance gi = main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId()));
					
					if(e.getInventory().getItem(13) == null) {
						Main.removeItems(e.getPlayer().getInventory(), gi.getIcon(), 1);
						e.getPlayer().openInventory(Menu.getGuildeIcon(gi));
						
						return;
					}
					
					gi.setIcon(e.getInventory().getItem(13).getType());
				}
			}.runTaskLater(main, 5L);
		}
	}
	
}








































