package fr.stellios.guilde;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.stellios.guilde.commands.Guilde;
import fr.stellios.guilde.expansion.GuildeExpansion;
import fr.stellios.guilde.listener.MenuListener;
import fr.stellios.guilde.listener.PlayerListener;
import fr.stellios.guilde.manager.ConfigManager;
import fr.stellios.guilde.manager.DataManager;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private File dataFile, configFile;
	private FileConfiguration dataConfig, configConfig;
	
	private ConfigManager configManager;
	private DataManager dataManager;
	
	private static Economy economy = null;
	
	private SignMenuFactory signMenuFactory;
	
	@Override
	public void onEnable() {
		if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - DÃ©sactivÃ© car le plugin Vault n'est pas installï¿½ !", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		this.signMenuFactory = new SignMenuFactory(this);
		
		loadData();
		loadConfig();
		
		getCommand("guilde").setExecutor(new Guilde(this));
		getCommand("guilde").setTabCompleter(new Guilde(this));
		
		getServer().getPluginManager().registerEvents(new MenuListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		configManager = new ConfigManager(this);
		dataManager = new DataManager(this);
		
		new GuildeExpansion(this).register();
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
	
}
