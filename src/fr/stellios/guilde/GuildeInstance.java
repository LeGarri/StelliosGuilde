package fr.stellios.guilde;

import java.util.List;

import org.bukkit.OfflinePlayer;

public class GuildeInstance {

	private String guildeName;
	
	private List<OfflinePlayer> players;
	
	public GuildeInstance(String guildeName, List<OfflinePlayer> players) {
		this.guildeName = guildeName;
		this.players = players;
	}
	
	
	public String getGuildeName() { return this.guildeName; }
	
	public List<OfflinePlayer> getPlayers() { return this.players; }
	
	
	
}
