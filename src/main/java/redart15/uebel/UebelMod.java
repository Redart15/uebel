package redart15.uebel;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redart15.uebel.block.UebelBlockDetails;
import redart15.uebel.block.UebelBlockTags;
import redart15.uebel.block.UebelBlocks;
import redart15.uebel.config.UebelConfig;
import redart15.uebel.effect.effect.UebelEffects;
import redart15.uebel.entity.UebelEntities;
import redart15.uebel.item.UebelItemTags;
import redart15.uebel.item.UebelItems;
import turniplabs.halplibe.util.GameStartEntrypoint;

public class UebelMod implements ModInitializer, GameStartEntrypoint {
	public static final String MOD_ID = "uebel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		LOGGER.info("Übel initialized.");
	}

	@Override
	public void beforeGameStart() {
		UebelConfig.init();
		UebelBlocks.init();
		UebelItems.init();
		UebelEntities.init();
	}

	@Override
	public void afterGameStart() {
		UebelBlockDetails.init();
		UebelBlockTags.init();
		UebelItemTags.init();
		UebelEffects.init();
	}
}
