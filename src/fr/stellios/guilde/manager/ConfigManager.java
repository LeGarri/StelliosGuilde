package fr.stellios.guilde.manager;

import java.util.HashMap;

import fr.stellios.guilde.Main;
import io.lumine.xikage.mythicmobs.MythicMobs;	

public class ConfigManager {

	private Main main;
	
	
	private HashMap<String, Integer> mobs = new HashMap<String, Integer>();
	

	public final String PREFIX;
	
	public final String PERMISSION;
	public final String ALREADY_IN_GUILDE;
	public final String GUILD_ALREADY_EXIST;
	public final String GUILD_CREATED;
	public final String PLAYER_NOT_ONLINE;
	public final String DONT_HAVE_GUILDE;
	public final String ALREADY_HAVE_GUILDE;
	public final String ALREADY_INVITED;
	public final String INVITE_PLAYER;
	public final String INVITE_EXPIRED;
	public final String INVITE_RECEIVED;
	public final String DONT_INVITE;
	public final String DONT_INVITE_GUILDE;
	public final String JOIN_GUILDE;
	public final String DENY_GUILDE;
	public final String JOIN_GUILDE_BROADCAST;
	public final String DENY_GUILDE_BROADCAST;
	public final String GUILDE_LEAVE;
	public final String GUILDE_LEAVE_BROADCAST;
	public final String GUILDE_RENAME;
	public final String GUILDE_RENAME_BROADCAST;
	public final String DESC_CHANGED;
	public final String DESC_CHANGED_BROADCAST;
	public final String NOT_OWNER;
	public final String NEW_OWNER_BROADCAST;
	public final String OWNER_ALREADY_OWNER;
	public final String PLAYER_NOT_IN_GUILDE;
	public final String HOME_CHANGED;
	public final String INVALIDE_DEPOSIT;
	public final String NOT_ENOUGH_MONEY;
	public final String VALIDE_DEPOSIT_BROADCAST;
	public final String GUILDE_ENOUGH_MONEY;
	public final String VALIDE_WITHDRAW_BROADCAST;
	public final String LEVEL_UP_BROADCAST;
	public final String BOOST_CHOICE_BROADCAST;
	
	
	
	public ConfigManager(Main main) {
		this.main = main;

		this.PREFIX = this.main.getConfig().getString("message.prefix").replaceAll("&", "§");
		
        this.PERMISSION = PREFIX + this.main.getConfig().getString("message.permission").replaceAll("&", "§");
        this.ALREADY_IN_GUILDE = PREFIX + this.main.getConfig().getString("message.already-in-guilde").replaceAll("&", "§");
        this.GUILD_ALREADY_EXIST = PREFIX + this.main.getConfig().getString("message.guilde-already-exist").replaceAll("&", "§");
        this.GUILD_CREATED = PREFIX + this.main.getConfig().getString("message.guilde-created").replaceAll("&", "§");
        this.PLAYER_NOT_ONLINE = PREFIX + this.main.getConfig().getString("message.player-not-online").replaceAll("&", "§");
        this.DONT_HAVE_GUILDE = PREFIX + this.main.getConfig().getString("message.dont-have-guilde").replaceAll("&", "§");
        this.ALREADY_HAVE_GUILDE = PREFIX + this.main.getConfig().getString("message.already-have-guilde").replaceAll("&", "§");
        this.ALREADY_INVITED = PREFIX + this.main.getConfig().getString("message.already-invited").replaceAll("&", "§");
        this.INVITE_PLAYER = PREFIX + this.main.getConfig().getString("message.invite-player").replaceAll("&", "§");
        this.INVITE_EXPIRED = PREFIX + this.main.getConfig().getString("message.invite-expired").replaceAll("&", "§");
        this.INVITE_RECEIVED = PREFIX + this.main.getConfig().getString("message.invite-received").replaceAll("&", "§");
        this.DONT_INVITE = PREFIX + this.main.getConfig().getString("message.dont-invited").replaceAll("&", "§");
        this.DONT_INVITE_GUILDE = PREFIX + this.main.getConfig().getString("message.dont-invite-guilde").replaceAll("&", "§");
        this.JOIN_GUILDE = PREFIX + this.main.getConfig().getString("message.join-guilde").replaceAll("&", "§");
        this.DENY_GUILDE = PREFIX + this.main.getConfig().getString("message.deny-guilde").replaceAll("&", "§");
        this.JOIN_GUILDE_BROADCAST = PREFIX + this.main.getConfig().getString("message.join-guilde-broadcast").replaceAll("&", "§");
        this.DENY_GUILDE_BROADCAST = PREFIX + this.main.getConfig().getString("message.deny-guilde-broadcast").replaceAll("&", "§");
        this.GUILDE_LEAVE = PREFIX + this.main.getConfig().getString("message.guilde-leave").replaceAll("&", "§");
        this.GUILDE_LEAVE_BROADCAST = PREFIX + this.main.getConfig().getString("message.guilde-leave-broadcast").replaceAll("&", "§");
        this.GUILDE_RENAME = PREFIX + this.main.getConfig().getString("message.guilde-rename").replaceAll("&", "§");
        this.GUILDE_RENAME_BROADCAST = PREFIX + this.main.getConfig().getString("message.guilde-rename-broadcast").replaceAll("&", "§");
        this.DESC_CHANGED = PREFIX + this.main.getConfig().getString("message.desc-changed").replaceAll("&", "§");
        this.DESC_CHANGED_BROADCAST = PREFIX + this.main.getConfig().getString("message.desc-changed-broadcast").replaceAll("&", "§");
        this.NOT_OWNER = PREFIX + this.main.getConfig().getString("message.not-owner").replaceAll("&", "§");   
        this.NEW_OWNER_BROADCAST = PREFIX + this.main.getConfig().getString("message.new-owner-broadcast").replaceAll("&", "§");
        this.OWNER_ALREADY_OWNER = PREFIX + this.main.getConfig().getString("message.owner-already-owner").replaceAll("&", "§");
        this.PLAYER_NOT_IN_GUILDE = PREFIX + this.main.getConfig().getString("message.player-not-in-guilde").replaceAll("&", "§");
        this.HOME_CHANGED = PREFIX + this.main.getConfig().getString("message.home-changed").replaceAll("&", "§");
        this.INVALIDE_DEPOSIT = PREFIX + this.main.getConfig().getString("message.invalide-deposit").replaceAll("&", "§");
        this.NOT_ENOUGH_MONEY = PREFIX + this.main.getConfig().getString("message.not-enough-money").replaceAll("&", "§");
        this.VALIDE_DEPOSIT_BROADCAST = PREFIX + this.main.getConfig().getString("message.valide-deposit-broadcast").replaceAll("&", "§");
        this.GUILDE_ENOUGH_MONEY = PREFIX + this.main.getConfig().getString("message.guilde-enough-money").replaceAll("&", "§");
        this.VALIDE_WITHDRAW_BROADCAST = PREFIX + this.main.getConfig().getString("message.valide-withdraw-broadcast").replaceAll("&", "§");
        this.LEVEL_UP_BROADCAST = PREFIX + this.main.getConfig().getString("message.level-up-broadcast").replaceAll("&", "§");
        this.BOOST_CHOICE_BROADCAST = PREFIX + this.main.getConfig().getString("message.boost-choice-broadcast").replaceAll("&", "§");
        
        for(String s : this.main.getConfig().getConfigurationSection("mobs").getKeys(false)) {
        	mobs.put(MythicMobs.inst().getMobManager().getMythicMob(s).getDisplayName().toString(), this.main.getConfig().getInt("mobs." + s));
        }
	}
	
	public HashMap<String, Integer> getMobs(){
		return this.mobs;
	}
	
}

































