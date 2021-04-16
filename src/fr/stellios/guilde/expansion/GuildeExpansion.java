package fr.stellios.guilde.expansion;

import javax.annotation.Nonnull;

import org.bukkit.OfflinePlayer;

import fr.stellios.guilde.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class GuildeExpansion extends PlaceholderExpansion {

	private Main main;
	
	public GuildeExpansion(Main main) {
		this.main = main;
	}
	
	@Override
    public boolean persist(){
        return true;
    }
	
	@Override
	public boolean canRegister() {
		return true;
	}
	
	@Override
	public @Nonnull String getAuthor() {
		return "LeGarri, StreeTixX";
	}
	
	@Override
	public @Nonnull String getVersion() {
		return "1.0.0";
	}
	
	
	@Override
	public @Nonnull String getIdentifier() {
		return "guilde";
	}
	
	@Override
	public String onRequest(OfflinePlayer player, @Nonnull String identifier) {
		if(identifier.equals("name")) {
			if(main.getDataManager().getGuildeByPlayer(player) == null) return String.valueOf("§ePas de Guilde");
			
			return String.valueOf(main.getDataManager().getGuildeByPlayer(player).getGuildeName());
		}
		
		return null;
	}
	
}
