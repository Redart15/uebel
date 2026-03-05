package redart15.uebel.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.NotNull;
import redart15.helver.metadata.BlockMetadata;

public class BlockLogicOverlay extends BlockLogic {

	public static final float DEPTH = 0.0625F / 10;
	private final IItemConvertible drop;

	public BlockLogicOverlay(Block<?> block, Material material, IItemConvertible drop) {
		super(block, material);
		this.drop = drop;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, DEPTH, 1.0F);
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(drop, 1)};
	}

	@Override
	public AABB getBlockBoundsFromState(WorldSource world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		float pixel = DEPTH;
		switch (sideFromMeta(meta)) {
			case BOTTOM:
				return AABB.getTemporaryBB(0.0F, 1.0F - pixel, 0.0F, 1.0F, 1.0F, 1.0F);
			case TOP:
				return AABB.getTemporaryBB(0.0F, 0.0, 0.0F, 1.0F, pixel, 1.0F);
			case NORTH:
				return AABB.getTemporaryBB( 0.0F, 0.0F, 1.0F - pixel, 1.0F, 1.0F, 1.0F);
			case SOUTH:
				return AABB.getTemporaryBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, pixel);
			case WEST:
				return AABB.getTemporaryBB(1.0F - pixel, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			case EAST:
			default:
				return AABB.getTemporaryBB(0.0F, 0.0F, 0.0F, pixel, 1.0F, 1.0F);
		}
	}

	private static Side sideFromMeta(int meta) {
		return Side.sides[MathHelper.clamp(BlockMetadata.getBitBlock(meta, 0, 2), 0, 5)];
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, Side side) {
		if(side == Side.TOP){
			return world.canPlaceOnSurfaceOfBlock(x, y - 1, z);
		}
		return world.isBlockNormalCube(x - side.getOffsetX(), y - side.getOffsetY(), z - side.getOffsetZ());
	}

	@Override
	public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
		return null;
	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		Side side = Side.sides[BlockMetadata.getBitBlock(world.getBlockMetadata(x,y,z), 0, 2)];
		if (!this.canPlaceBlockOnSide(world, x, y, z, side)) {
			this.dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, world.getBlockMetadata(x, y, z), null, null);
			world.setBlockWithNotify(x, y, z, 0);
		}
	}

	@Override
	public void onBlockPlacedOnSide(World world, int x, int y, int z, @NotNull Side side, double xPlaced, double yPlaced) {
		world.setBlockMetadataWithNotify(x, y, z, BlockMetadata.setBitBlock(world.getBlockMetadata(x,y,z), 0, 2, side.getId()));
	}

}
