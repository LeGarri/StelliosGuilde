package fr.stellios.guilde.manager;

import fr.stellios.guilde.Main;

public class ConfigManager {

	private Main main;
	

	public final String PERMISSION;
	
	
	public ConfigManager(Main main) {
		this.main = main;

        this.PERMISSION = this.main.getData().getString("message.permission");
	}
	
}
