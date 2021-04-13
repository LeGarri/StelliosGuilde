package fr.stellios.guilde;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.stellios.guilde.commands.Guilde;
import fr.stellios.guilde.manager.ConfigManager;
import fr.stellios.guilde.manager.DataManager;

public class Main extends JavaPlugin {

	private File dataFile, configFile;
	private FileConfiguration dataConfig, configConfig;
	
	private ConfigManager configManager;
	private DataManager dataManager;
	
	@Override
	public void onEnable() {
		loadData();
		loadConfig();
		
		getCommand("guilde").setExecutor(new Guilde(this));
		getCommand("guilde").setTabCompleter(new Guilde(this));
		
		configManager = new ConfigManager(this);
		dataManager = new DataManager(this);
	}
	
	
	public ConfigManager getConfigManager() {
		return this.configManager;
	}
	
	public DataManager getDataManager() {
		return this.dataManager;
	}
	
	
	
	private void loadData() {
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
	
	
	
	
	private void loadConfig() {
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
	
}
