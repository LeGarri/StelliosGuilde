package fr.stellios.guilde.manager;

import fr.stellios.guilde.Main;

public class ConfigManager {

	private Main main;
	

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
	
	
	public ConfigManager(Main main) {
		this.main = main;

		this.PREFIX = this.main.getConfig().getString("message.prefix").replaceAll("&", "�");
		
        this.PERMISSION = PREFIX + this.main.getConfig().getString("message.permission").replaceAll("&", "�");
        this.ALREADY_IN_GUILDE = PREFIX + this.main.getConfig().getString("message.already-in-guilde").replaceAll("&", "�");
        this.GUILD_ALREADY_EXIST = PREFIX + this.main.getConfig().getString("message.guilde-already-exist").replaceAll("&", "�");
        this.GUILD_CREATED = PREFIX + this.main.getConfig().getString("message.guilde-created").replaceAll("&", "�");
        this.PLAYER_NOT_ONLINE = PREFIX + this.main.getConfig().getString("message.player-not-online").replaceAll("&", "�");
        this.DONT_HAVE_GUILDE = PREFIX + this.main.getConfig().getString("message.dont-have-guilde").replaceAll("&", "�");
        this.ALREADY_HAVE_GUILDE = PREFIX + this.main.getConfig().getString("message.already-have-guilde").replaceAll("&", "�");
        this.ALREADY_INVITED = PREFIX + this.main.getConfig().getString("message.already-invited").replaceAll("&", "�");
        this.INVITE_PLAYER = PREFIX + this.main.getConfig().getString("message.invite-player").replaceAll("&", "�");
        this.INVITE_EXPIRED = PREFIX + this.main.getConfig().getString("message.invite-expired").replaceAll("&", "�");
        this.INVITE_RECEIVED = PREFIX + this.main.getConfig().getString("message.invite-received").replaceAll("&", "�");
        this.DONT_INVITE = PREFIX + this.main.getConfig().getString("message.dont-invited").replaceAll("&", "�");
        this.DONT_INVITE_GUILDE = PREFIX + this.main.getConfig().getString("message.dont-invite-guilde").replaceAll("&", "�");
        this.JOIN_GUILDE = PREFIX + this.main.getConfig().getString("message.join-guilde").replaceAll("&", "�");
        this.DENY_GUILDE = PREFIX + this.main.getConfig().getString("message.deny-guilde").replaceAll("&", "�");
        this.JOIN_GUILDE_BROADCAST = PREFIX + this.main.getConfig().getString("message.join-guilde-broadcast").replaceAll("&", "�");
        this.DENY_GUILDE_BROADCAST = PREFIX + this.main.getConfig().getString("message.deny-guilde-broadcast").replaceAll("&", "�");
        this.GUILDE_LEAVE = PREFIX + this.main.getConfig().getString("message.guilde-leave").replaceAll("&", "�");
        this.GUILDE_LEAVE_BROADCAST = PREFIX + this.main.getConfig().getString("message.guilde-leave-broadcast").replaceAll("&", "�");
        this.GUILDE_RENAME = PREFIX + this.main.getConfig().getString("message.guilde-rename").replaceAll("&", "�");
        this.GUILDE_RENAME_BROADCAST = PREFIX + this.main.getConfig().getString("message.guilde-rename-broadcast").replaceAll("&", "�");
        this.DESC_CHANGED =  PREFIX + this.main.getConfig().getString("message.desc-changed").replaceAll("&", "�");
        this.DESC_CHANGED_BROADCAST =  PREFIX + this.main.getConfig().getString("message.desc-changed-broadcast").replaceAll("&", "�");
        this.NOT_OWNER =  PREFIX + this.main.getConfig().getString("message.not-owner").replaceAll("&", "�");   
        this.NEW_OWNER_BROADCAST =  PREFIX + this.main.getConfig().getString("message.new-owner-broadcast").replaceAll("&", "�");
        this.OWNER_ALREADY_OWNER =  PREFIX + this.main.getConfig().getString("message.owner-already-owner").replaceAll("&", "�");
        this.PLAYER_NOT_IN_GUILDE =  PREFIX + this.main.getConfig().getString("message.player-not-in-guilde").replaceAll("&", "�");
	}
	
}

































