package com.afforess.minecartmaniachestcontrol;

import java.util.ArrayList;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import com.afforess.minecartmaniacore.AbstractItem;
import com.afforess.minecartmaniacore.Item;
import com.afforess.minecartmaniacore.MinecartManiaChest;
import com.afforess.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.MinecartManiaStorageCart;
import com.afforess.minecartmaniacore.MinecartManiaWorld;
import com.afforess.minecartmaniacore.utils.ItemUtils;
import com.afforess.minecartmaniacore.utils.SignUtils;
import com.afforess.minecartmaniacore.utils.StringUtils;

public abstract class ChestStorage {
	
	public static boolean doMinecartCollection(MinecartManiaMinecart minecart) {
		if (minecart.getBlockTypeAhead() != null) {
			if (minecart.getBlockTypeAhead().getType().getId() == Item.CHEST.getId()) {
				MinecartManiaChest chest = MinecartManiaWorld.getMinecartManiaChest((Chest)minecart.getBlockTypeAhead().getState());
				
				if (SignCommands.isNoCollection(chest)) {
					return false;
				}
				
				if (minecart instanceof MinecartManiaStorageCart) {
					MinecartManiaStorageCart storageCart = (MinecartManiaStorageCart)minecart;
					boolean failed = false;
					for (ItemStack item : storageCart.getInventory().getContents()) {
						if (!chest.addItem(item)) {
							failed = true;
							break;
						}
					}
					if (!failed) {
						storageCart.getInventory().clear();
					}
				}
				if (chest.addItem(minecart.getType().getId())) {
					
					minecart.kill(false);
					return true;
				}
			}
		}
		return false;
	}

//	public static boolean doChestStorage(MinecartManiaStorageCart minecart) {
//		ArrayList<Block> blockList = minecart.getAdjacentBlocks(minecart.getRange());
//		boolean action = false;
//		
//		minecart.setDataValue("Direction Signs", new HashMap<Location, ArrayList<CompassDirection>>());
//		
//		for (Block block : blockList) {
//			if (block.getState() instanceof Sign) {
//				Sign sign = (Sign)block.getState();
//				action = action || doChestStorage(minecart, sign);
//			}	
//		}
//		return action;
//	}
	
//	private static boolean doChestStorage(MinecartManiaStorageCart minecart, Sign sign) {
//		boolean action = false;
//		MinecartManiaInventory withdraw = (MinecartManiaInventory)minecart;
//		MinecartManiaInventory deposit = null;
//		ArrayList<Block> list = BlockUtils.getAdjacentBlocks(sign.getBlock().getLocation(), 1);
//		for (Block loop : list) {
//			
//			if (loop.getState() instanceof Chest) {
//				deposit = MinecartManiaWorld.getMinecartManiaChest((Chest)loop.getState());
//				//check for double chest
//				if (deposit != null && ((MinecartManiaChest) deposit).getNeighborChest() != null) {
//					deposit = new MinecartManiaDoubleChest((MinecartManiaChest) deposit, ((MinecartManiaChest) deposit).getNeighborChest());
//				}
//			}
//			else if (loop.getState() instanceof Dispenser) {
//				deposit = MinecartManiaWorld.getMinecartManiaDispenser((Dispenser)loop.getState());
//			}
//			else if (loop.getState() instanceof Furnace) {
//				deposit = MinecartManiaWorld.getMinecartManiaFurnace((Furnace)loop.getState());
//				
//			}
//			if (sign.getLine(0).toLowerCase().contains("collect items")) {
//				sign.setLine(0, "[Collect Items]");
//				action = InventoryUtils.doInventoryTransaction(withdraw, deposit, sign, minecart.getDirectionOfMotion());
//			}
//			else if (sign.getLine(0).toLowerCase().contains("deposit items")) {
//				sign.setLine(0, "[Deposit Items]");
//				action = InventoryUtils.doInventoryTransaction(deposit, withdraw, sign, minecart.getDirectionOfMotion());
//			}
//		}
//
//		if (isConditionalDirection(sign)) {
//			@SuppressWarnings("unchecked")
//			HashMap<Location, ArrayList<CompassDirection>> recent = (HashMap<Location, ArrayList<CompassDirection>>) minecart.getDataValue("Direction Signs");
//			ArrayList<CompassDirection> direction = new ArrayList<CompassDirection>();
//			direction.add(minecart.getDirectionOfMotion());
//			recent.put(sign.getBlock().getLocation(), direction);
//		}
//		return action;
//	}
	
//	public static void updateConditionalCollectionStorage(MinecartManiaStorageCart minecart) {
//		@SuppressWarnings("unchecked")
//		HashMap<Location, ArrayList<CompassDirection>> recent = (HashMap<Location, ArrayList<CompassDirection>>) minecart.getDataValue("Direction Signs");
//		if (recent != null) {
//			Iterator<Entry<Location, ArrayList<CompassDirection>>> i = recent.entrySet().iterator();
//			while(i.hasNext()) {
//				Entry<Location, ArrayList<CompassDirection>> e = i.next();
//				if (e.getKey().getBlock().getState() instanceof Sign) {
//					if (!e.getValue().contains(minecart.getDirectionOfMotion())) {
//						doChestStorage(minecart, (Sign)e.getKey().getBlock().getState());
//					}
//				}
//			}
//		}
//	}
	
//	private static boolean isConditionalDirection(Sign sign) {
//		return sign.getLine(1).contains("+") || sign.getLine(2).contains("+") || sign.getLine(3).contains("+");
//	}
	
//	public static boolean doFurnaceStorage(MinecartManiaStorageCart minecart) {
//		boolean action = false;
//		ArrayList<Block> blockList = minecart.getAdjacentBlocks(minecart.getRange());
//		for (Block block : blockList) {
//			if (block.getState() instanceof Furnace) {
//				MinecartManiaFurnace furnace = MinecartManiaWorld.getMinecartManiaFurnace((Furnace)block.getState());
//				System.out.println(furnace.getContents());
//				ArrayList<Sign> signList = SignUtils.getAdjacentSignList(minecart.minecart.getWorld(), block.getX(), block.getY(), block.getZ(), 1);
//				for (Sign sign : signList) {
//					for (int i = 0; i < 4; i++) {
//					    String line = sign.getLine(i).toLowerCase();
//					    int slot = -1;
//					    if(line.contains("smelt")) slot = 0;
//					    else if(line.contains("fuel")) slot = 1;
//					    
//					    line = line.replace("smelt", "");
//					    line = line.replace("fuel", "");
//					    if(line.startsWith(":"))line = line.substring(1);
//					    if (slot != -1) {
//					    	System.out.println(line);
//					    	action = InventoryUtils.doFurnaceTransaction(minecart, furnace, slot, line);
//					    	sign.setLine(i, StringUtils.addBrackets(sign.getLine(i)));
//					    }
//					}
//					sign.update();
//				}
//			}
//		}
//		return action;
//	}
	
	public static boolean doCollectParallel(MinecartManiaMinecart minecart) {
		ArrayList<Block> blockList = minecart.getParallelBlocks();
		for (Block block : blockList) {
			if (block.getState() instanceof Chest) {
				MinecartManiaChest chest = MinecartManiaWorld.getMinecartManiaChest((Chest)block.getState());
				ArrayList<Sign> signList = SignUtils.getAdjacentSignList(chest.getWorld(), chest.getX(), chest.getY(), chest.getZ(), 1);
				for (Sign sign : signList) {
					for (int i = 0; i < 4; i++) {
						if (sign.getLine(i).toLowerCase().contains("parallel")) {
							sign.setLine(i, "[Parallel]");
							sign.update();
							if (!minecart.isMovingAway(block.getLocation())) {
								if (chest.addItem(minecart.getType().getId())) {
									minecart.kill(false);
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public static void doItemCompression(MinecartManiaStorageCart minecart) {
		ArrayList<Block> blockList = minecart.getAdjacentBlocks(minecart.getRange());
		for (Block block : blockList) {
			if (block.getTypeId() == Item.WORKBENCH.getId()) {
				ArrayList<Sign> signList = SignUtils.getAdjacentSignList(block.getWorld(), block.getX(), block.getY(), block.getZ(), 2);
				for (Sign sign : signList) {
					for (int i = 0; i < 4; i++) {
						if (sign.getLine(i).toLowerCase().contains("compress items")) { 
							sign.setLine(i, "[Compress Items]");
							sign.update();
							//TODO handling for custom recipies?
							Item[][] compressable = { {Item.IRON_INGOT, Item.GOLD_INGOT, Item.LAPIS_LAZULI}, {Item.IRON_BLOCK , Item.GOLD_BLOCK, Item.LAPIS_BLOCK} };
							int n = 0;
							for (Item m : compressable[0]) {
								int amt = 0;
								int slot = 0;
								for (ItemStack item : minecart.getContents()) {
									if (item != null && m.equals(item.getType())) {
										amt += item.getAmount();
										minecart.setItem(slot, null);
									}
									slot++;
								}
								int compressedAmt = amt / 9;
								int left = amt % 9;
								while (compressedAmt > 0) {
									minecart.addItem(compressable[1][n].getId(), Math.min(64, compressedAmt));
									compressedAmt -= 64;
								}
								if (left > 0) {
									minecart.addItem(compressable[0][n].getId(), left);
								}
								
								n++;
							}
						}
					}
				}
			}
		}
	}
	
	public static boolean doEmptyChestInventory(MinecartManiaStorageCart minecart) {
		ArrayList<Sign> signList = SignUtils.getAdjacentSignList(minecart, 2);
		for (Sign sign : signList) {
			if (sign.getLine(0).toLowerCase().contains("trash items")) {
				//return InventoryUtils.doInventoryTransaction(minecart, null, sign, minecart.getDirectionOfMotion());
			}
		}
		return false;
	}

	public static void setMaximumItems(MinecartManiaStorageCart minecart) {
		ArrayList<Sign> signList = SignUtils.getAdjacentSignList(minecart, 2);
		for (Sign sign : signList) {
			if (sign.getLine(0).toLowerCase().contains("max items")) {
				String[] list = {sign.getLine(1), sign.getLine(2), sign.getLine(3) };
				AbstractItem[] items = ItemUtils.getItemStringListToMaterial(list);
				for (AbstractItem item : items) {
					if (!item.isInfinite()) {
						minecart.setMaximumItem(item.type(), item.getAmount());
					}
				}
				sign.setLine(0, "[Max Items]");
				if (!sign.getLine(1).isEmpty()) {
					sign.setLine(1, StringUtils.addBrackets(sign.getLine(1)));
				}
				if (!sign.getLine(2).isEmpty()) {
					sign.setLine(2, StringUtils.addBrackets(sign.getLine(2)));
				}
				if (!sign.getLine(3).isEmpty()) {
					sign.setLine(3, StringUtils.addBrackets(sign.getLine(3)));
				}
				sign.update();
			}
		}
	}
	
	public static void setMinimumItems(MinecartManiaStorageCart minecart) {
		ArrayList<Sign> signList = SignUtils.getAdjacentSignList(minecart, 2);
		for (Sign sign : signList) {
			if (sign.getLine(0).toLowerCase().contains("min items")) {
				String[] list = {sign.getLine(1), sign.getLine(2), sign.getLine(3) };
				AbstractItem[] items = ItemUtils.getItemStringListToMaterial(list);
				for (AbstractItem item : items) {
					if (!item.isInfinite()) {
						minecart.setMinimumItem(item.type(), item.getAmount());
					}
				}
				sign.setLine(0, "[Min Items]");
				if (!sign.getLine(1).isEmpty()) {
					sign.setLine(1, StringUtils.addBrackets(sign.getLine(1)));
				}
				if (!sign.getLine(2).isEmpty()) {
					sign.setLine(2, StringUtils.addBrackets(sign.getLine(2)));
				}
				if (!sign.getLine(3).isEmpty()) {
					sign.setLine(3, StringUtils.addBrackets(sign.getLine(3)));
				}
				sign.update();
			}
		}
	}

}
