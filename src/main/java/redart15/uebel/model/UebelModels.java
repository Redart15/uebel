package redart15.uebel.model;

import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import redart15.uebel.block.UebelBlocks;
import redart15.uebel.item.UebelItems;
import redart15.uebel.model.block.BlockModelOverlay;
import turniplabs.halplibe.util.ModelEntrypoint;

import static redart15.uebel.UebelMod.MOD_ID;

public class UebelModels implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
		String[] bloodsplats = new String[20];
		for (int i = 0; i < bloodsplats.length; i++) {
			bloodsplats[i] = MOD_ID + ":block/bloodsplat/bloodsplat_" + i;
		}
		dispatcher.addDispatch((new BlockModelOverlay<>(UebelBlocks.OVERLAY_BLOOD, bloodsplats)));

	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		dispatcher.addDispatch(new ItemModelStandard(UebelItems.CONGEALED_BLOOD, null).setIcon(MOD_ID + ":item/congealed_blood"));
		dispatcher.addDispatch(new ItemModelStandard(UebelItems.BLOODWURST_RAW, null).setIcon(MOD_ID + ":item/bloodwurst_raw"));
		dispatcher.addDispatch(new ItemModelStandard(UebelItems.BLOODWURST, null).setIcon(MOD_ID + ":item/bloodwurst"));
		dispatcher.addDispatch(new ItemModelStandard(UebelItems.BUCKET_BLOOD, null).setIcon(MOD_ID + ":item/bucket_blood"));
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
