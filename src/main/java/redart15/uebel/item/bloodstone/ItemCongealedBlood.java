package redart15.uebel.item.bloodstone;

import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import redart15.helver.metadata.BlockMetadata;
import redart15.uebel.entity.TriggerOnPickup;
import redart15.uebel.model.BlockModelOverlay;

public class ItemCongealedBlood extends Item implements TriggerOnPickup {
	public static final Block<?> OVERLAY_BLOOD = Blocks.OVERLAY_PEBBLES;

	public ItemCongealedBlood(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
	}

	@Override
	public boolean onUseItemOnBlock(ItemStack itemstack, Player player, World world, int ix, int iy, int iz, Side side, double xPlaced, double yPlaced) {
		int blockX = ix;
		int blockY = iy;
		int blockZ = iz;
		int id = world.getBlockId(ix, iy, iz);
		int meta = world.getBlockMetadata(ix, iy, iz);
		BlockModelOverlay<BlockLogic> model = ((BlockModelOverlay<BlockLogic>) BlockModelDispatcher.getInstance().getDispatch(OVERLAY_BLOOD));
		if (itemstack.stackSize <= 0 || iy == world.getHeightBlocks() - 1 && OVERLAY_BLOOD.getMaterial().isSolid()) {
			return false;
		}
		if (id != OVERLAY_BLOOD.id() && Blocks.blocksList[id] != null && Blocks.blocksList[id].hasTag(BlockTags.PLACE_OVERWRITES)) {
			id = 0;
			meta = 0;
		}

		if (id == OVERLAY_BLOOD.id()) {
			int newMeta = BlockMetadata.getBitBlock(meta, 3, 7) + 1;
			if (!world.isBlockOpaqueCube(ix - side.getOffsetX(), iy - side.getOffsetY(), iz - side.getOffsetZ())) {
				return false;
			}
			if (newMeta >= model.getTextureSize()) {
				newMeta = 0;
			}
			world.setBlockAndMetadataWithNotify(ix, iy, iz, OVERLAY_BLOOD.id(), BlockMetadata.setBitBlock(meta, 3, 7, newMeta));
			world.playBlockSoundEffect(player, ix + 0.5F, iy + 0.5F, iz + 0.5F, OVERLAY_BLOOD, EnumBlockSoundEffectType.PLACE);
			return true;
		}

		if (id != 0) {
			blockX += side.getOffsetX();
			blockY += side.getOffsetY();
			blockZ += side.getOffsetZ();
			id = world.getBlockId(blockX, blockY, blockZ);
			meta = world.getBlockMetadata(blockX, blockY, blockZ);
		}

		if (id == OVERLAY_BLOOD.id()) {
			int newMeta = BlockMetadata.getBitBlock(meta, 3, 7) + 1;
			AABB bbBox = AABB.getTemporaryBB(blockX, blockY, blockZ, blockX + 1.0F, blockY + (2 * (newMeta + 1)) / 16.0F, blockZ + 1.0F);
			if (!world.checkIfAABBIsClear(bbBox) || !world.isBlockOpaqueCube(ix, iy, iz)) {
				return false;
			}
			if (newMeta >= model.getTextureSize()) {
				newMeta = 0;
			}
			world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, OVERLAY_BLOOD.id(), BlockMetadata.setBitBlock(meta, 3, 7, newMeta));
			world.playBlockSoundEffect(player, blockX + 0.5F, blockY + 0.5F, blockZ + 0.5F, OVERLAY_BLOOD, EnumBlockSoundEffectType.PLACE);
			return true;

		}

		if (world.canBlockBePlacedAt(OVERLAY_BLOOD.id(), blockX, blockY, blockZ, false, side)
			&& world.isBlockOpaqueCube(ix, iy, iz)
			&& world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, OVERLAY_BLOOD.id(), 0)
		) {
			OVERLAY_BLOOD.onBlockPlacedByMob(world, blockX, blockY, blockZ, side, player, xPlaced, yPlaced);
			world.playBlockSoundEffect(player, blockX + 0.5F, blockY + 0.5F, blockZ + 0.5F, OVERLAY_BLOOD, EnumBlockSoundEffectType.PLACE);
			itemstack.consumeItem(player);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void onPickUp(Player player, EntityItem entityItem, ContainerInventory container, ItemStack itemStack, boolean hotbarOffset) {
		if (!player.getGamemode().canInteract()) {
			return;
		}
		player.heal(itemStack.stackSize);
//		player.world.playSoundAtEntity(player, player, "aether_battle:bloodstone.pickup", 0.2F + ((EntityAccessor) player).getRandom().nextFloat() * 0.2f, 0.2F + ((EntityAccessor) player).getRandom().nextFloat());
		entityItem.remove();
	}

}
