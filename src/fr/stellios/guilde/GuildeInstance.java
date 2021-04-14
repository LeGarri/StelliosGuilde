package fr.stellios.guilde;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class GuildeInstance {

	private Main main;
	
	private String guildeName;
	
	private String desc;
	
	private List<OfflinePlayer> players;
	
	private Material icon;
	
	public GuildeInstance(Main main, String guildeName, String desc, List<OfflinePlayer> players, Material icon) {
		this.main = main;
		this.guildeName = guildeName;
		this.desc = desc;
		this.players = players;
		this.icon = icon;
	}
	
	
	
	
	public String getGuildeName() { return this.guildeName; }
	
	public String getDesc() { return this.desc; }
	
	public List<OfflinePlayer> getPlayers() { return this.players; }
	
	public Material getIcon() { return this.icon; }
	
	
	
	
	
	
	public void saveGuilde() {
		main.getData().set("guildes." + guildeName.replaceAll(" ", "$*!*") + ".desc", desc);
		
		List<String> playersUUID = new ArrayList<String>();
		
		for(OfflinePlayer op : players) {
			playersUUID.add(op.getUniqueId().toString());
		}
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "$*!*") + ".players", playersUUID);
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "$*!*") + ".icon", icon.toString());
		
		main.saveData();
	}
	
	public void addPlayer(OfflinePlayer player) {
		players.add(player);
		
		saveGuilde();
	}
	
	public void setGuildeName(String name) {
		this.guildeName = name;
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "$*!*"), null);
		
		saveGuilde();
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
		
		saveGuilde();
	}
	
	public void removePlayer(OfflinePlayer player) {
		players.remove(player);
		
		if(players.isEmpty()) {
			main.getData().set("guildes." + guildeName.replaceAll(" ", "$*!*"), null);
			main.saveData();
			
			return;
		}
		
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
