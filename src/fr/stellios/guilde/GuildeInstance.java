package fr.stellios.guilde;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class GuildeInstance {

	private Main main;
	
	private String guildeName;
	
	private List<OfflinePlayer> players;
	
	public GuildeInstance(Main main, String guildeName, List<OfflinePlayer> players) {
		this.main = main;
		this.guildeName = guildeName;
		this.players = players;
	}
	
	
	public String getGuildeName() { return this.guildeName; }
	
	public List<OfflinePlayer> getPlayers() { return this.players; }
	
	
	public void saveGuilde() {
		List<String> playersUUID = new ArrayList<String>();
		
		for(OfflinePlayer op : players) {
			playersUUID.add(op.getUniqueId().toString());
		}
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "$*!*") + ".players", playersUUID);
		
		main.saveData();
	}
	
	public void addPlayer(OfflinePlayer player) {
		players.add(player);
		
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
