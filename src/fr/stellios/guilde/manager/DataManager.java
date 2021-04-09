package fr.stellios.guilde.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import fr.stellios.guilde.GuildeInstance;
import fr.stellios.guilde.Main;

public class DataManager {

	private Main main;
	
	public DataManager(Main main) {
		this.main = main;
	}
	
	public List<GuildeInstance> getAllGuilde(){
		List<GuildeInstance> guildes = new ArrayList<GuildeInstance>();
		
		for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
			guildes.add(new GuildeInstance(s.replaceAll("$*!*", " "),
					getGuildePlayers(s)));
		}
		
		return guildes;
	}
	
	public List<OfflinePlayer> getGuildePlayers(String guildeName) {
		List<String> playersUUID = main.getData().getStringList(getGuilde(guildeName) + ".players");
		List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		
		for(String s : playersUUID) {
			players.add(Bukkit.getOfflinePlayer(UUID.fromString(s)));
		}
		
		return players;
	}
	
	public GuildeInstance getGuildeByPlayer(OfflinePlayer player) {
		for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
			if(getGuildePlayers(s).contains(player)){
				return new GuildeInstance(s.replaceAll("$*!*", " "),
						getGuildePlayers(s));
			}
		}
		
		return null;
	}
	
	private String getGuilde(String guildeName) {
		return "guildes." + guildeName;
	}
	
}
