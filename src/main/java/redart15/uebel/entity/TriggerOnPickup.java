package redart15.uebel.entity;

import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;

public interface TriggerOnPickup {
	default void onPickUp(Player player, EntityItem entityItem, ContainerInventory container, ItemStack itemStack, boolean hotbarOffset){
		container.insertItem(itemStack, hotbarOffset);
	}

	default boolean canTrigger(ContainerInventory container, ItemStack itemStack, Player player){
		return true;
	}
}
