package fr.stellios.guilde;

public class ConfigManager {

	private Main main;
	

	public String PERMISSION;
	
	
	public ConfigManager(Main main) {
		this.main = main;

        this.PERMISSION = this.main.getData().getString("message.permission");
	}
	
}
