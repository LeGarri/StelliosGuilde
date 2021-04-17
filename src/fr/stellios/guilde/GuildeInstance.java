package fr.stellios.guilde;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class GuildeInstance {

	private Main main;
	
	private String guildeName;
	
	private String desc;
	
	private OfflinePlayer owner;
	
	private List<OfflinePlayer> players;
	
	private Material icon;
	
	private Location home;
	
	private double account;
	
	private int level;
	
	private int exp;
	
	public GuildeInstance(Main main, String guildeName, String desc, List<OfflinePlayer> players, Material icon, OfflinePlayer owner, Location home, double account, int level, int exp) {
		this.main = main;
		this.guildeName = guildeName;
		this.desc = desc;
		this.players = players;
		this.icon = icon;
		this.owner = owner;
		this.home = home;
		this.account = account;
		this.level = level;
		this.exp = exp;
	}
	
	
	
	
	public String getGuildeName() { return this.guildeName; }
	
	public String getDesc() { return this.desc.replaceAll("&", "§"); }
	
	public List<OfflinePlayer> getPlayers() { return this.players; }
	
	public Material getIcon() { return this.icon; }
	
	public OfflinePlayer getOwner() { return this.owner; }
	
	public Location getHome() { return this.home; }
	
	public double getAccount() { return this.account; }
	
	public int getLevel() { return this.level; }
	
	public int getExp() { return this.exp; }
	
	
	
	
	
	
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
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".account", account);
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".level", level);
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".exp", exp);
		
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
	
	public void addMoney(double money) {
		account += money;
		
		saveGuilde();
	}
	
	public void removeMoney(double money) {
		this.account -= money;
		
		saveGuilde();
	}
	
	private void addlevel() {
		if((level == 1 && exp >= 10000) || (level == 2 && exp >= 25000)) {
			level++;
			
			sendMessage(main.getConfigManager().LEVEL_UP_BROADCAST.replaceAll("%level%", String.valueOf(level)));
			
			for(OfflinePlayer op : players) if(op.isOnline()) op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1, 100);
			
			addlevel();
			
			return;
		}
		
		saveGuilde();
	}
	
	public void addExp(int exp) {
		this.exp += exp;
		
		addlevel();
		
		saveGuilde();
	}
	
	public void removePlayer(OfflinePlayer player) {
		players.remove(player);
		
		if(players.isEmpty()) {
			main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149"), null);
			main.saveData();
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset lands.ownlands.2");
			
			return;
		}
		
		if(owner.equals(player)) {
			owner = players.get(0);
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset lands.ownlands.2");
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + players.get(0).getName() + " permission set lands.ownlands.2");
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
