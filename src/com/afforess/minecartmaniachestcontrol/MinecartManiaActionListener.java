package com.afforess.minecartmaniachestcontrol;

import org.bukkit.Material;
import org.bukkit.block.Chest;

import com.afforess.minecartmaniacore.DirectionUtils;
import com.afforess.minecartmaniacore.MinecartManiaChest;
import com.afforess.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.MinecartManiaWorld;
import com.afforess.minecartmaniacore.MinecartUtils;
import com.afforess.minecartmaniacore.event.ChestPoweredEvent;
import com.afforess.minecartmaniacore.event.MinecartActionEvent;
import com.afforess.minecartmaniacore.event.MinecartManiaListener;

public class MinecartManiaActionListener extends MinecartManiaListener{
	
	public void onChestPoweredEvent(ChestPoweredEvent event) {
		if (event.isPowered() && !event.isActionTaken()) {

			MinecartManiaChest chest = event.getChest();
			
			if (MinecartUtils.validMinecartTrack(chest.chest.getWorld(), chest.getX() - 1, chest.getY(), chest.getZ(), 2, DirectionUtils.CompassDirection.NORTH)){
				if (chest.chest.getInventory().contains(Material.MINECART)) {
					chest.removeItem(Material.MINECART.getId());
					MinecartManiaWorld.spawnMinecart(chest.chest.getWorld(), chest.getX() - 1, chest.getY(), chest.getZ(), 0, chest);
					event.setActionTaken(true);
				}
			}
			if (MinecartUtils.validMinecartTrack(chest.chest.getWorld(), chest.getX() + 1, chest.getY(), chest.getZ(), 2, DirectionUtils.CompassDirection.SOUTH)){
				if (chest.chest.getInventory().contains(Material.MINECART)) {
					chest.removeItem(Material.MINECART.getId());
					MinecartManiaWorld.spawnMinecart(chest.chest.getWorld(), chest.getX() + 1, chest.getY(), chest.getZ(), 0, chest);
					event.setActionTaken(true);
				}
			}
			if (MinecartUtils.validMinecartTrack(chest.chest.getWorld(), chest.getX(), chest.getY(), chest.getZ() - 1, 2, DirectionUtils.CompassDirection.EAST)){
				if (chest.chest.getInventory().contains(Material.MINECART)) {
					chest.removeItem(Material.MINECART.getId());
					MinecartManiaWorld.spawnMinecart(chest.chest.getWorld(), chest.getX(), chest.getY(), chest.getZ() - 1, 0, chest);
					event.setActionTaken(true);
				}
			}
			if (MinecartUtils.validMinecartTrack(chest.chest.getWorld(), chest.getX(), chest.getY(), chest.getZ() + 1, 2, DirectionUtils.CompassDirection.WEST)){
				if (chest.chest.getInventory().contains(Material.MINECART)) {
					chest.removeItem(Material.MINECART.getId());
					MinecartManiaWorld.spawnMinecart(chest.chest.getWorld(), chest.getX(), chest.getY(), chest.getZ() + 1, 0, chest);
					event.setActionTaken(true);
				}
			}
		}
	}
	
	public void onMinecartActionEvent(MinecartActionEvent event) {
		if (!event.isActionTaken()) {
			MinecartManiaMinecart minecart = event.getMinecart();
			if (minecart.getBlockTypeAhead() != null) {
				if (minecart.getBlockTypeAhead().getType().getId() == Material.CHEST.getId()) {
					MinecartManiaChest chest = MinecartManiaWorld.getMinecartManiaChest((Chest)minecart.getBlockTypeAhead().getState());
					chest.addItem(Material.MINECART.getId());
					minecart.kill(false);
					event.setActionTaken(true);
				}
			}
		}
	}

}
