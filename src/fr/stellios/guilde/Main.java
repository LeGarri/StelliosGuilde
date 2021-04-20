package fr.stellios.guilde;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import fr.stellios.guilde.commands.Guilde;
import fr.stellios.guilde.expansion.GuildeExpansion;
import fr.stellios.guilde.listener.MenuListener;
import fr.stellios.guilde.listener.PlayerListener;
import fr.stellios.guilde.manager.ConfigManager;
import fr.stellios.guilde.manager.DataManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private File dataFile, configFile;
	private FileConfiguration dataConfig, configConfig;
	
	private ConfigManager configManager;
	private DataManager dataManager;
	
	private static Economy economy = null;
	private static Chat chat = null;
	
	private SignMenuFactory signMenuFactory;
	
	private ProtocolManager protocolManager;
	
	@Override
	public void onEnable() {
		if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - DÃ©sactivÃ© car le plugin Vault n'est pas installï¿½ !", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		setupChat();
		
		this.signMenuFactory = new SignMenuFactory(this);
		
		protocolManager = ProtocolLibrary.getProtocolManager();
		
		loadData();
		loadConfig();
		
		getCommand("guilde").setExecutor(new Guilde(this));
		getCommand("guilde").setTabCompleter(new Guilde(this));
		
		getServer().getPluginManager().registerEvents(new MenuListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		configManager = new ConfigManager(this);
		dataManager = new DataManager(this);
		
		new GuildeExpansion(this).register();
		
		
		//Guilde Avantage: potion plus longue
		protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_EFFECT) {
			@Override
			public void onPacketSending(PacketEvent e) {
				if(e.getPacketType() == PacketType.Play.Server.ENTITY_EFFECT) {
					if(dataManager.getGuildeByPlayer(Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId())) != null) {
						GuildeInstance gi = dataManager.getGuildeByPlayer(Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId()));
						
						if(gi.getLevel() >= 3) e.getPacket().getIntegers().write(1, (int) (e.getPacket().getIntegers().getValues().get(1) * 1.15));
						else if(gi.getLevel() >= 5) e.getPacket().getIntegers().write(1, (int) (e.getPacket().getIntegers().getValues().get(1) * 1.30));
					}
				}
			}
		});
		
		//Guilde Avantage: Coeur en plus
		new BukkitRunnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if(!Bukkit.getOnlinePlayers().isEmpty()) {
					for(Player player : Bukkit.getOnlinePlayers()) {
						if(dataManager.getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())) != null) {
							GuildeInstance gi = dataManager.getGuildeByPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
							int plusMaxHealth = 0;
							int maxHealth = 20;
							
							if(gi.getLevel() >= 3) plusMaxHealth = 2;
							
							for(ItemStack is : player.getInventory().getArmorContents()) {
								if(is != null && is.getType() != Material.AIR) {
									if(is.hasItemMeta() && is.getItemMeta().getAttributeModifiers(Attribute.GENERIC_MAX_HEALTH) != null) {
										for(AttributeModifier am : is.getItemMeta().getAttributeModifiers(Attribute.GENERIC_MAX_HEALTH)) {
											Double amValue = Double.parseDouble(new DecimalFormat("#.##").format(am.getAmount()));
											maxHealth += amValue * 10;
										}
									}
								}
							}
							
							if(player.getMaxHealth() < (maxHealth + plusMaxHealth)) {
								player.setMaxHealth(20 + plusMaxHealth);
							}
							
							if(gi.getLevel() >= 5 && player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
								if(gi.getBoost() != null) {
									boolean haveEffect = false;
									
									if(player.getActivePotionEffects().isEmpty()) {
										for(PotionEffect effect : player.getActivePotionEffects()) {
											if(effect.getType().equals(gi.getBoost())) haveEffect = true;
										}
									}
									
									if(!haveEffect) {
										if(gi.getBoost().equals(PotionEffectType.FAST_DIGGING)) player.addPotionEffect(new PotionEffect(gi.getBoost(), 9999, 1));
										else player.addPotionEffect(new PotionEffect(gi.getBoost(), 9999, 0));
									}
								}
							}
						}
					}
				}
				
				
				if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 0 && Calendar.getInstance().get(Calendar.MINUTE) == 1) {
					for(GuildeInstance gi : dataManager.getAllGuilde()) {
						gi.resetBoost();
					}
				}
			}
		}.runTaskTimer(this, 0, 20L);
	}
	
	public static Chat getChat() {
        return chat;
    }
	
	
	
	public ConfigManager getConfigManager() {
		return this.configManager;
	}
	
	public DataManager getDataManager() {
		return this.dataManager;
	}
	
	
	public SignMenuFactory getSignMenuFactory() {
        return this.signMenuFactory;
    }
	
	public void addMoneyFromSign(Player target) {
	    SignMenuFactory.Menu menu = signMenuFactory.newMenu(Arrays.asList("", "^^^^^^", "Montant à", "déposer"))
	            .reopenIfFail(true)
	            .response((player, strings) -> {
	            	try {
	            		double money = Double.parseDouble(strings[0]);
	            		
	            		if(economy.getBalance(Bukkit.getOfflinePlayer(target.getUniqueId())) >= money) {
	            			economy.withdrawPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()), money);
	            			
	            			GuildeInstance gi = dataManager.getGuildeByPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
	            			
	            			gi.addMoney(money);
	            			
	            			gi.sendMessage(configManager.VALIDE_DEPOSIT_BROADCAST.replaceAll("%player%", target.getName()).replaceAll("%money%", String.valueOf(money)));
	            		} else target.sendMessage(configManager.NOT_ENOUGH_MONEY);
	            	} catch(NumberFormatException e) {
	            		target.sendMessage(configManager.INVALIDE_DEPOSIT);
	            	}
	                return true;
	            });

	    menu.open(target);
	}
	
	public void getMoneyFromSign(Player target) {
	    SignMenuFactory.Menu menu = signMenuFactory.newMenu(Arrays.asList("", "^^^^^^", "Montant à", "retirer"))
	            .reopenIfFail(true)
	            .response((player, strings) -> {
	            	try {
	            		double money = Double.parseDouble(strings[0]);

	            		GuildeInstance gi = dataManager.getGuildeByPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
	            		
	            		if(gi.getAccount() >= money) {
	            			gi.removeMoney(money);
	            			
	            			economy.depositPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()), money);
	            			
	            			gi.sendMessage(configManager.VALIDE_WITHDRAW_BROADCAST.replaceAll("%player%", target.getName()).replaceAll("%money%", String.valueOf(money)));
	            		} else target.sendMessage(configManager.GUILDE_ENOUGH_MONEY);
	            	} catch(NumberFormatException e) {
	            		target.sendMessage(configManager.INVALIDE_DEPOSIT);
	            	}
	                return true;
	            });

	    menu.open(target);
	}
	
	
	public static void removeItems(Inventory inventory, Material type, int amount) {
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type == is.getType()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }
	
	
	public void reloadData() {
		loadData();
		dataManager = new DataManager(this);
	}
	
	public void reloadConfig() {
		loadConfig();
		configManager = new ConfigManager(this);
	}
	
	public void loadData() {
		dataFile = new File(getDataFolder(), "data.yml");
		if(!dataFile.exists()) {
			saveResource("data.yml", false);
		}
		
		dataConfig = new YamlConfiguration();
		try {
			dataConfig.load(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getData() {
		return this.dataConfig;
	}
	
	public File getDataFile() {
		return this.dataFile;
	}
	
	public void saveData() {
		try {
			getData().save(getDataFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void loadConfig() {
		configFile = new File(getDataFolder(), "config.yml");
		if(!configFile.exists()) {
			saveResource("config.yml", false);
		}
		
		configConfig = new YamlConfiguration();
		try {
			configConfig.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getConfig() {
		return this.configConfig;
	}
	
	public File getConfigFile() {
		return this.configFile;
	}
	
	public void saveConfig() {
		try {
			getConfig().save(getConfigFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        
        economy = rsp.getProvider();
        return economy != null;
    }
	
	private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
	
}
