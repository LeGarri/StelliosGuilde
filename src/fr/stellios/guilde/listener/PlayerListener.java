package fr.stellios.guilde.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.stellios.guilde.Main;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;

public class PlayerListener implements Listener {

	private Main main;
	
	public PlayerListener(Main main) {
		this.main = main;
	}
	
	private List<Player> expBottle = new ArrayList<Player>();
	
	/////////////////////////////////////////
	//									   //
	//				BOOST            	   //
	//									   //
	/////////////////////////////////////////
	
	@EventHandler
	public void onPlayerReceiveHeal(EntityRegainHealthEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			
			if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) != null) {
				if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).getLevel() >= 2) {
					e.setAmount(e.getAmount() * 1.05);
				}
			}
		}
	}
	
	
	@EventHandler
	public void onPlayerReceiveExp(PlayerExpChangeEvent e) {
		if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId())) != null) {
			if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId())).getLevel() >= 2) {
				if(!expBottle.contains(e.getPlayer())) e.setAmount((int) (e.getAmount() * 1.1));
				else expBottle.remove(e.getPlayer());
			}
		}
	}
	
	
	@EventHandler
	public void onPlayerUseExpBottle(PlayerInteractEvent e) {
		if(e.getItem() != null && e.getItem().getType() == Material.EXPERIENCE_BOTTLE) {
			if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId())) != null) {
				expBottle.add(e.getPlayer());
			}
		}
	}
	
	
	/////////////////////////////////////////
	//									   //
	//				EXP            		   //
	//									   //
	/////////////////////////////////////////
	
	@EventHandler
	public void onPlayerKillMythicMobs(MythicMobDeathEvent e) {
		if(e.getKiller() instanceof Player) {
			Player player = (Player) e.getKiller();
			
			if(main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) != null) {
				main.getDataManager().getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())).addExp(1);
			}
		}
	}
	
	
}






































