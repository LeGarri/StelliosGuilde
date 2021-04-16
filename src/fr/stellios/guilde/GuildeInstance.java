package fr.stellios.guilde;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class GuildeInstance {

	private Main main;
	
	private String guildeName;
	
	private String desc;
	
	private OfflinePlayer owner;
	
	private List<OfflinePlayer> players;
	
	private Material icon;
	
	private Location home;
	
	public GuildeInstance(Main main, String guildeName, String desc, List<OfflinePlayer> players, Material icon, OfflinePlayer owner, Location home) {
		this.main = main;
		this.guildeName = guildeName;
		this.desc = desc;
		this.players = players;
		this.icon = icon;
		this.owner = owner;
		this.home = home;
	}
	
	
	
	
	public String getGuildeName() { return this.guildeName; }
	
	public String getDesc() { return this.desc.replaceAll("&", "§"); }
	
	public List<OfflinePlayer> getPlayers() { return this.players; }
	
	public Material getIcon() { return this.icon; }
	
	public OfflinePlayer getOwner() { return this.owner; }
	
	public Location getHome() { return this.home; }
	
	
	
	
	
	
	public void saveGuilde() {
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".desc", desc);
		
		List<String> playersUUID = new ArrayList<String>();
		
		for(OfflinePlayer op : players) {
			playersUUID.add(op.getUniqueId().toString());
		}
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".players", playersUUID);
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".icon", icon.toString());
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".owner", owner.getUniqueId().toString());
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".home", home);
		
		main.saveData();
	}
	
	public void addPlayer(OfflinePlayer player) {
		players.add(player);
		
		saveGuilde();
	}
	
	public void setGuildeName(String name) {
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149"), null);
		
		this.guildeName = name;
		
		saveGuilde();
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
		
		saveGuilde();
	}
	
	public void setIcon(Material mat) {
		this.icon = mat;
		
		saveGuilde();
	}
	
	public void setHome(Location home) {
		this.home = home;
		
		saveGuilde();
	}
	
	public void removePlayer(OfflinePlayer player) {
		players.remove(player);
		
		if(players.isEmpty()) {
			main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149"), null);
			main.saveData();
			
			return;
		}
		
		if(owner.equals(player)) {
			owner = players.get(0);
		}
		
		saveGuilde();
	}
	
	public void setOwner(OfflinePlayer owner) {
		this.owner = owner;
		
		saveGuilde();
	}
	
	public void sendMessage(String msg) {
		for(OfflinePlayer op : players) {
			if(op.isOnline()) op.getPlayer().sendMessage(msg);
		}
	}
	
	public void sendMessage(String msg, List<Player> excludes) {
		for(OfflinePlayer op : players) {
			if(op.isOnline() && !excludes.contains(op.getPlayer())) op.getPlayer().sendMessage(msg);
		}
	}
	
	
	
}
