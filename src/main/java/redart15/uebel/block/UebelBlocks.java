package redart15.uebel.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import redart15.uebel.config.UebelConfig;
import redart15.uebel.item.UebelItems;
import turniplabs.halplibe.helper.BlockBuilder;

import static redart15.uebel.UebelMod.*;

public class UebelBlocks {
	public static Block<?> OVERLAY_BLOOD;

	private static boolean init = false;
	private UebelBlocks(){}
	public static void init(){
		if(init) return;
		init = true;
		UebelBlocks.createBlood();
	}


	private static void createBlood() {
		OVERLAY_BLOOD = (new BlockBuilder(MOD_ID))
			.setBlockSound(BlockSounds.STONE)
			.setHardness(0.0F)
			.setVisualUpdateOnMetadata()
			.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
			.build("overlay.blood", UebelConfig.nextBlockID(), (b) -> new BlockLogicOverlay(b, Material.decoration, UebelItems.CONGEALED_BLOOD))
			.setStatParent(() -> UebelItems.CONGEALED_BLOOD);
	}
}
