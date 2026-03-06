package redart15.uebel.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import redart15.uebel.block.UebelBlocks;

public class ItemBloodBucket extends Item {
	public ItemBloodBucket(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
	}

	@Override
	public ItemStack onUseItem(ItemStack stack, World world, Player player) {
		double reachDistance = player.getGamemode().getBlockReachDistance();
		HitResult rayTraceResult = player.rayTrace(reachDistance, 1.0F, false, false);
		if (rayTraceResult == null || rayTraceResult.hitType != HitResult.HitType.TILE) {
			return stack;
		}
		int x = rayTraceResult.x;
		int y = rayTraceResult.y;
		int z = rayTraceResult.z;
		Block<?> block = world.getBlock(x, y, z);
		boolean success = UebelItems.CONGEALED_BLOOD.onUseItemOnBlock(UebelItems.CONGEALED_BLOOD.getDefaultStack(), player, world, x, y, z, rayTraceResult.side, x, z);
		if ((block != null && block.id() == UebelBlocks.OVERLAY_BLOOD.id()) || !success) {
			return stack;
		}
		int metadata = stack.getMetadata();
		if(metadata > 0){
			stack.setMetadata(metadata - 1);
			return stack;
		}
		if (player.getGamemode().consumeBlocks()) {
			return new ItemStack(Items.BUCKET);
		}
		return stack;
	}
}
