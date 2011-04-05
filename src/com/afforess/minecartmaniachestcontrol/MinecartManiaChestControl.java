package com.afforess.minecartmaniachestcontrol;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.config.MinecartManiaConfigurationParser;

public class MinecartManiaChestControl extends JavaPlugin {
	public static Logger log;
	public static Server server;
	public static PluginDescriptionFile description;
	public static MinecartManiaActionListener listener = new MinecartManiaActionListener();

	public void onEnable(){
		server = this.getServer();
		description = this.getDescription();
		log = Logger.getLogger("Minecraft");
		MinecartManiaConfigurationParser.read(description.getName() + "Configuration.xml", MinecartManiaCore.dataDirectory, new ChestControlSettingParser());
		getServer().getPluginManager().registerEvent(Event.Type.CUSTOM_EVENT, listener, Priority.High, this);
		log.info( description.getName() + " version " + description.getVersion() + " is enabled!" );
	}
	
	public void onDisable(){
		
	}
}
