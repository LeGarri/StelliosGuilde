package fr.stellios.guilde.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
	
	public OfflinePlayer getGuildeOwner(String guildeName) {
		return Bukkit.getOfflinePlayer(UUID.fromString(main.getData().getString(getGuilde(guildeName) + ".owner")));
	}
	
	public Location getGuildeHome(String guildeName) {
		return main.getData().getObject(getGuilde(guildeName) + ".home", Location.class);
	}
	
	public double getGuildeAccount(String guildeName) {
		return main.getData().getDouble(getGuilde(guildeName) + ".account");
	}
	
	public int getGuildeLevel(String guildeName) {
		return main.getData().getInt(getGuilde(guildeName) + ".level");
	}
	
	public int getGuildeExp(String guildeName) {
		return main.getData().getInt(getGuilde(guildeName) + ".exp");
	}
	
	
	public GuildeInstance createGuilde(String guildeName, OfflinePlayer player) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set lands.ownlands.2");
		
		return new GuildeInstance(main, guildeName, "&eUne incroyable guilde", Arrays.asList(Bukkit.getOfflinePlayer(player.getUniqueId())), Material.DIRT, player, player.getPlayer().getLocation(), 0, 1, 0);
	}
	
	public List<GuildeInstance> getAllGuilde(){
		List<GuildeInstance> guildes = new ArrayList<GuildeInstance>();
		
		for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
			guildes.add(new GuildeInstance(main, s.replaceAll("bbb149", " "),
					getGuildeDesc(s),
					getGuildePlayers(s),
					getGuildeIcon(s),
					getGuildeOwner(s),
					getGuildeHome(s),
					getGuildeAccount(s),
					getGuildeLevel(s),
					getGuildeExp(s)));
		}
		
		return guildes;
	}
	
	public GuildeInstance getGuildeByPlayer(OfflinePlayer player) {
		if(main.getData().getConfigurationSection("guildes") == null || main.getData().getConfigurationSection("guildes").getKeys(false).isEmpty()) return null;
		
		for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
			if(getGuildePlayers(s).contains(player)){
				return new GuildeInstance(main, s.replaceAll("bbb149", " "),
						getGuildeDesc(s),
						getGuildePlayers(s),
						getGuildeIcon(s),
						getGuildeOwner(s),
						getGuildeHome(s),
						getGuildeAccount(s),
						getGuildeLevel(s),
						getGuildeExp(s));
			}
		}
		
		return null;
	}
	
	public GuildeInstance getGuildeByName(String name) {
		if(main.getData().getConfigurationSection("guildes") == null || main.getData().getConfigurationSection("guildes").getKeys(false).isEmpty()) return null;
		
		for(String s : main.getData().getConfigurationSection("guildes").getKeys(false)) {
			if(s.replaceAll("bbb149", " ").equalsIgnoreCase(name)) {
				return new GuildeInstance(main, s.replaceAll("bbb149", " "),
						getGuildeDesc(s),
						getGuildePlayers(s),
						getGuildeIcon(s),
						getGuildeOwner(s),
						getGuildeHome(s),
						getGuildeAccount(s),
						getGuildeLevel(s),
						getGuildeExp(s));
			}
		}
		
		return null;
	}
	
	private String getGuilde(String guildeName) {
		return "guildes." + guildeName;
	}
	
}
