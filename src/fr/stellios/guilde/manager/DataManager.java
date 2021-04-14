package fr.stellios.guilde.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import fr.stellios.guilde.GuildeInstance;
import fr.stellios.guilde.Main;

public class DataManager {

	private Main main;
	
	public DataManager(Main main) {
		this.main = main;
	}
	
	
	public List<OfflinePlayer> getGuildePlayers(String guildeName) {
		List<String> playersUUID = main.getData().getStringList(getGuilde(guildeName) + ".players");
		List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		
		for(String s : playersUUID) {
			players.add(Bukkit.getOfflinePlayer(UUID.fromString(s)));
		}
		
		return players;
	}
	
	public Material getGuildeIcon(String guildeName) {
		return Material.getMaterial(main.getData().getString(getGuilde(guildeName) + ".icon"));
	}
	
	public String getGuildeDesc(String guildeName) {
		return main.getData().getString(getGuilde(guildeName) + ".desc");
	}
	
	
	public GuildeInstance createGuilde(String guildeName, OfflinePlayer player) {
		return new GuildeInstance(main, guildeName, "&eUne incroyable guilde", Arrays.asList(Bukkit.getOfflinePlayer(player.getUniqueId())), Material.DIRT);
	}
	
	public List<GuildeInstance> getAllGuilde(){
		List<GuildeInstance> guildes = new ArrayList<GuildeInstance>();
		
		for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
			guildes.add(new GuildeInstance(main, s.replaceAll("$*!*", " "),
					getGuildeDesc(s),
					getGuildePlayers(s),
					getGuildeIcon(s)));
		}
		
		return guildes;
	}
	
	public GuildeInstance getGuildeByPlayer(OfflinePlayer player) {
		for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
			if(getGuildePlayers(s).contains(player)){
				return new GuildeInstance(main, s.replaceAll("$*!*", " "),
						getGuildeDesc(s),
						getGuildePlayers(s),
						getGuildeIcon(s));
			}
		}
		
		return null;
	}
	
	public GuildeInstance getGuildeByName(String name) {
		for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
			if(s.replaceAll(" ", "$*!*").equalsIgnoreCase(name)) {
				return new GuildeInstance(main, s.replaceAll("$*!*", " "),
						getGuildeDesc(s),
						getGuildePlayers(s),
						getGuildeIcon(s));
			}
		}
		
		return null;
	}
	
	private String getGuilde(String guildeName) {
		return "guildes." + guildeName;
	}
	
}
