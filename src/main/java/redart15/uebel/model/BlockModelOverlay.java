package redart15.uebel.model;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import redart15.helver.metadata.BlockMetadata;

public class BlockModelOverlay<T extends BlockLogic> extends BlockModelStandard<T> {
	protected IconCoordinate[] carpet = new IconCoordinate[]{TextureRegistry.getTexture("minecraft:block/texture_missing")};

	public BlockModelOverlay(Block<T> block, String... texturePaths) {
		super(block);
		if (texturePaths != null) {
			this.carpet = new IconCoordinate[texturePaths.length];
			for (int i = 0; i < texturePaths.length; i++) {
				carpet[i] = TextureRegistry.getTexture(texturePaths[i]);
			}
		}
	}

	public int getTextureSize() {
		return carpet.length;
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		int meta = renderBlocks.blockAccess.getBlockMetadata(x, y, z);
		Side side = Side.sides[MathHelper.clamp(BlockMetadata.getBitBlock(meta, 0, 2), 0, 5)];
		float brightness = 1.0F;
		if (LightmapHelper.isLightmapEnabled()) {
			tessellator.setLightmapCoord(LightmapHelper.max(
					this.block.getLightmapCoord(renderBlocks.blockAccess, x, y, z),
					this.block.getLightmapCoord(renderBlocks.blockAccess, x - side.getOffsetX(), y - side.getOffsetY(), z - side.getOffsetZ())
				)
			);
		} else {
			brightness = Math.max(this.getBlockBrightness(renderBlocks.blockAccess, x, y, z), this.getBlockBrightness(renderBlocks.blockAccess, x - side.getOffsetX(), y - side.getOffsetY(), z - side.getOffsetZ()));
		}
		tessellator.setColorOpaque_F(brightness, brightness, brightness);
		switch (side) {
			case TOP:
				this.renderTopFace(tessellator, this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z), x, y, z, this.getBlockTextureFromSideAndMetadata(Side.TOP, renderBlocks.blockAccess.getBlockMetadata(x, y, z)));
				break;
			case BOTTOM:
				this.renderBottomFace(tessellator, this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z), x, y, z, this.getBlockTextureFromSideAndMetadata(Side.BOTTOM, renderBlocks.blockAccess.getBlockMetadata(x, y, z)));
				break;
			case NORTH:
				this.renderNorthFace(tessellator, this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z), x, y, z, this.getBlockTextureFromSideAndMetadata(Side.SOUTH, renderBlocks.blockAccess.getBlockMetadata(x, y, z)));
				break;
			case EAST:
				this.renderEastFace(tessellator, this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z), x, y, z, this.getBlockTextureFromSideAndMetadata(Side.WEST, renderBlocks.blockAccess.getBlockMetadata(x, y, z)));
				break;
			case SOUTH:
				this.renderSouthFace(tessellator, this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z), x, y, z, this.getBlockTextureFromSideAndMetadata(Side.NORTH, renderBlocks.blockAccess.getBlockMetadata(x, y, z)));
				break;
			case WEST:
				this.renderWestFace(tessellator, this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z), x, y, z, this.getBlockTextureFromSideAndMetadata(Side.EAST, renderBlocks.blockAccess.getBlockMetadata(x, y, z)));
				break;
		}
		return true;
	}

	@Override
	public boolean shouldItemRender3d() {
		return false;
	}


	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
		int size = BlockMetadata.getBitBlock(data, 3, 7);
		return this.carpet[MathHelper.clamp(size, 0, this.carpet.length - 1)];
	}
}

