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
import org.bukkit.potion.PotionEffectType;

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
	
	private PotionEffectType boost = null;
	
	private List<PotionEffectType> boostList = new ArrayList<PotionEffectType>();
	
	
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
		
		if(this.main.getData().getString("guildes." + guildeName.replaceAll(" ", "bbb149") + ".boost") != null) {
			boost = PotionEffectType.getByName(this.main.getData().getString("guildes." + guildeName.replaceAll(" ", "bbb149") + ".boost"));
		}
		
		if(this.main.getData().getStringList("guildes." + guildeName.replaceAll(" ", "bbb149") + ".boostlist") != null) {
			for(String s : this.main.getData().getStringList("guildes." + guildeName.replaceAll(" ", "bbb149") + ".boostlist")) boostList.add(PotionEffectType.getByName(s));
		}
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
	
	public PotionEffectType getBoost() { return this.boost; }
	
	public List<PotionEffectType> getBoostList() { 
		if(boostList.isEmpty()) {
			PotionEffectType[] boosts = {null, null, null};
			int i = 0;
			
			do {
				int random = 0 + (int)(Math.random() * ((5 - 0) + 1));
				if(i == 3) i = 0;
				
				if(random == 0) boosts[i] = PotionEffectType.SPEED;
				if(random == 1) boosts[i] = PotionEffectType.INCREASE_DAMAGE;
				if(random == 2) boosts[i] = PotionEffectType.DAMAGE_RESISTANCE;
				if(random == 3) boosts[i] = PotionEffectType.REGENERATION;
				if(random == 4) boosts[i] = PotionEffectType.FAST_DIGGING;
				if(random == 5) boosts[i] = PotionEffectType.WATER_BREATHING;
				
				i++;
			} while(boosts[0] == boosts[1] || boosts[1] == boosts[2] || boosts[0] == boosts[2] || boosts[0] == PotionEffectType.ABSORPTION || boosts[1] == PotionEffectType.ABSORPTION || boosts[2] == PotionEffectType.ABSORPTION);
			
			for(int j = 0; j < 3; j++) {
				this.boostList.add(boosts[j]);
			}
			
			saveGuilde();
		}
		
		return this.boostList;
	}
	
	
	
	
	
	
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
		
		main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".boost", boost);
		
		List<String> boosts = new ArrayList<String>();
		for(PotionEffectType pet : boostList) boosts.add(pet.getName());
		
		if(!boosts.isEmpty()) main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".boostlist", boosts);
		else main.getData().set("guildes." + guildeName.replaceAll(" ", "bbb149") + ".boostlist", null);
		
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
		if((level == 1 && exp >= 10000) || (level == 2 && exp >= 25000) || (level == 3 && exp >= 50000) || (level == 4 && exp >= 90000)) {
			level++;
			
			sendMessage(main.getConfigManager().LEVEL_UP_BROADCAST.replaceAll("%level%", String.valueOf(level)));
			
			for(OfflinePlayer op : players) if(op.isOnline()) op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1, 100);
			
			addlevel();
			
			return;
		}
		
		saveGuilde();
	}
	
	public int getExpToLevelUp() {
		if(level == 1) return 10000;
		else if(level == 2) return 25000;
		else if(level == 3) return 50000;
		else if(level == 4) return 90000;
		else return 0;
	}
	
	public void addExp(int exp) {
		this.exp += exp;
		
		addlevel();
		
		saveGuilde();
	}
	
	public String getAdvantage() {
		if(level == 1) return "§eAucun avantage";
		if(level == 2) return "§7- §eExpérience gagnée §6x1.1,§7- §eSoin reçu §6+ 5%";
		if(level == 3) return "§7- §eExpérience gagnée §6x1.1,§7- §eSoin reçu §6+ 5%,§7- §eDurées des potions §6+15%,§7- §61 §eCoeur en plus";
		if(level == 4) return "§7- §eExpérience gagnée §6x1.2,§7- §eSoin reçu §6+ 5%,§7- §eDurées des potions §6+15%,§7- §61 §eCoeur en plus,§7- §eDégâts émis §6+1";
		if(level == 5) return "§7- §eExpérience gagnée §6x1.2,§7- §eSoin reçu §6+ 5%,§7- §eDurées des potions §6+30%,§7- §61 §eCoeur en plus,§7- §eDégâts émis §6+1,§7- §eDébloque les §6boosts";
		
		return "";
	}
	
	public void setBoost(PotionEffectType pet) {
		this.boost = pet;
		
		saveGuilde();
	}
	
	public void resetBoost() {
		this.boost = null;
		this.boostList.clear();
		
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
