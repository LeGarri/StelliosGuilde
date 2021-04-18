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
			if(main.getDataManager().getGuildeByPlayer(player) == null) return String.valueOf("Pas de Guilde");
			
			return String.valueOf(main.getDataManager().getGuildeByPlayer(player).getGuildeName());
		}
		
		if(identifier.equals("level")) {
			if(main.getDataManager().getGuildeByPlayer(player) == null) return String.valueOf("Pas de Guilde");
			
			return String.valueOf(main.getDataManager().getGuildeByPlayer(player).getLevel());
		}
		
		if(identifier.equals("exp")) {
			if(main.getDataManager().getGuildeByPlayer(player) == null) return String.valueOf("");
			
			float exp = main.getDataManager().getGuildeByPlayer(player).getExp();
			float explvlup = main.getDataManager().getGuildeByPlayer(player).getExpToLevelUp();
			
			float percent = (exp / explvlup) * 100;
			
			int green = (int) (36 - (36 - (36 * percent / 100)));
			int gray = 36 - green;

			String result = "§a";
			
			for(int i = 0; i < green; i++) result += "\u25AE";
			result += "§7";
			for(int i = 0; i < gray; i++) result += "\u25AE";
			
			return String.valueOf("§8[" + result + "§8]");
		}
		
		if(identifier.equals("online")) {
			if(main.getDataManager().getGuildeByPlayer(player) == null) return String.valueOf("Pas de Guilde");
			
			int connected = 0;
			
			for(OfflinePlayer op : main.getDataManager().getGuildeByPlayer(player).getPlayers()) if(op.isOnline()) connected++;
			
			return String.valueOf(connected);
		}
		
		if(identifier.equals("benefits")) {
			if(main.getDataManager().getGuildeByPlayer(player) == null) return String.valueOf("Pas de Guilde");
			
			return String.valueOf(main.getDataManager().getGuildeByPlayer(player).getAdvantage());
		}
		
		return null;
	}
	
}





































