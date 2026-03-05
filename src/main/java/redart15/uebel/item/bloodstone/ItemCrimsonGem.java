package redart15.uebel.item.bloodstone;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.Global;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import redart15.uebel.effect.effect.UebelEffects;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;

import java.util.ArrayList;
import java.util.List;

public class ItemCrimsonGem extends Item{
	public static final int COOLDOWN = 8 * Global.TICKS_PER_SECOND;

	public ItemCrimsonGem(NamespaceID namespaceId, int id) {
		super(namespaceId, id);
	}

	@Override
	public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
		if (!(entity instanceof Player)) {
			return;
		}
		this.applyEffect(itemstack, world, (Player) entity, slotId);
	}

	private void applyEffect(ItemStack itemstack, World world, Player player, int slotId) {
		CompoundTag tag = itemstack.getData();
		if (slotId >= player.inventory.mainInventory.length && slotId - player.inventory.mainInventory.length >= 6) {
			int time = advanceTime(tag);
			if (time <= COOLDOWN) {
				return;
			}
			tag.putInt("time", 0);
			List<Entity> entityList = world.getLoadedEntityList();
			for (Entity victim : new ArrayList<>(entityList)) {
				if (this.canApply(victim, player)) {
					continue;
				}
				UebelEffects.quickStartEffect((IHasEffects<?>) victim, UebelEffects.blood_letting, 1);
			}
			return;
		}
		tag.putInt("time", 0);
	}

	private boolean canApply(Entity victom, Player player) {
		return victom.distanceTo(player) >= 16
			|| !(victom instanceof IHasEffects)
			|| !(victom instanceof Mob)
			|| victom == player
			|| !((Mob) victom).nickname.isEmpty();
	}

	private static int advanceTime(CompoundTag tag) {
		int time = tag.getInteger("time");
		tag.putInt("time", ++time);
		return time;
	}

//	@Override
//	public void removeEffect(Player player, ItemStack accessory) {
//		CompoundTag tag = accessory.getData();
//		tag.putInt("time", 0);
//	}
}
