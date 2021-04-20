package fr.stellios.guilde;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public class Items {

	/////////////////////////////////////////
	//									   //
	//				GUI            		   //
	//									   //
	/////////////////////////////////////////
	
	
	public static ItemStack getBlueGlass() {
		ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(" ");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getLightBlueGlass() {
		ItemStack item = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(" ");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getPaperNext() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§aPage Suivante");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getPaperPrevious() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§cPage Précédente");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getHomeGuilde() {
		ItemStack item = new ItemStack(Material.GRASS_BLOCK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§eSe téléporter au home de la guilde");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getAccountGuilde() {
		ItemStack item = new ItemStack(Material.GOLD_INGOT);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§eCompte de la guilde");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getGuildeMoney(double money) {
		ItemStack item = new ItemStack(Material.GOLD_INGOT);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§e" + money);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getGuildeDeposit() {
		ItemStack item = new ItemStack(Material.LIME_WOOL);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§aDéposer de l'argent");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getGuildeWithdraw() {
		ItemStack item = new ItemStack(Material.RED_WOOL);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§aRetirer de l'argent");
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getGuildeLevelIcon(GuildeInstance gi) {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§eNiveau de la guilde");
		itemMeta.setLore(Arrays.asList("", "§eNiveau: §6" + gi.getLevel(), "", "§eExpérience: §6" + gi.getExp(), ""));
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getGuildeLevel(int level, boolean debloqued, List<String> lore) {
		ItemStack item;
		
		if(debloqued) item = new ItemStack(Material.LIME_TERRACOTTA);
		else item = new ItemStack(Material.COAL_BLOCK);
		
		ItemMeta itemMeta = item.getItemMeta();
		
		if(debloqued) itemMeta.setDisplayName("§aNiveau " + level);
		else itemMeta.setDisplayName("§cNiveau " + level);
		
		itemMeta.setLore(lore);
		
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getGuildeBoost(GuildeInstance gi) {
		ItemStack item = new ItemStack(Material.HONEY_BOTTLE);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§6Choisir le boost");
		List<String> lore = Arrays.asList("", "§eChoisissez un boost de guilde pour", "§ela semaine. Le choix ne peut être","§efait qu'une seule fois par semaine", "");
		if(gi.getLevel() < 5) lore.addAll(Arrays.asList("§cNiveau de guilde 5 requis"));
		itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getGuildeActiveBoost(GuildeInstance gi) {
		ItemStack item = new ItemStack(Material.HONEY_BOTTLE);
		ItemMeta itemMeta = item.getItemMeta();
		if(gi.getBoost() == PotionEffectType.SPEED) itemMeta.setDisplayName("§eBoost: §6Vitesse");
		else if(gi.getBoost() == PotionEffectType.INCREASE_DAMAGE) itemMeta.setDisplayName("§eBoost: §6Force");
		else if(gi.getBoost() == PotionEffectType.DAMAGE_RESISTANCE) itemMeta.setDisplayName("§eBoost: §6Résistance");
		else if(gi.getBoost() == PotionEffectType.REGENERATION) itemMeta.setDisplayName("§eBoost: §6Régénération");
		else if(gi.getBoost() == PotionEffectType.FAST_DIGGING) itemMeta.setDisplayName("§eBoost: §6Haste");
		else if(gi.getBoost() == PotionEffectType.WATER_BREATHING) itemMeta.setDisplayName("§eBoost: §6Apnée");
		itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getGuildeChoiceBoost(PotionEffectType pet) {
		ItemStack item = new ItemStack(Material.HONEY_BOTTLE);
		ItemMeta itemMeta = item.getItemMeta();
		if(pet == PotionEffectType.SPEED) itemMeta.setDisplayName("§eBoost: §6Vitesse");
		else if(pet == PotionEffectType.INCREASE_DAMAGE) itemMeta.setDisplayName("§eBoost: §6Force");
		else if(pet == PotionEffectType.DAMAGE_RESISTANCE) itemMeta.setDisplayName("§eBoost: §6Résistance");
		else if(pet == PotionEffectType.REGENERATION) itemMeta.setDisplayName("§eBoost: §6Régénération");
		else if(pet == PotionEffectType.FAST_DIGGING) itemMeta.setDisplayName("§eBoost: §6Haste");
		else if(pet == PotionEffectType.WATER_BREATHING) itemMeta.setDisplayName("§eBoost: §6Apnée");
		itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	
	
}

































