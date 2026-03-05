package redart15.uebel;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.entity.particle.ParticleDispatcher;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import redart15.uebel.entity.particle.ParticleBleeding;
import turniplabs.halplibe.helper.TextureHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import static redart15.uebel.UebelMod.*;

public class UebelClientMod implements ClientModInitializer, ClientStartEntrypoint {
	@Override
	public void onInitializeClient() {
		UebelClientMod.registerTextures();
	}

	@Override
	public void beforeClientStart() {
		ParticleDispatcher dispatcher = ParticleDispatcher.getInstance();
		dispatcher.addDispatch("bleeding", (world, x, y, z, xa, ya, za, id) -> new ParticleBleeding(world, x, y, z, xa, ya, za));
//		SoundRepository.registerNamespace(MOD_ID);
	}

	@Override
	public void afterClientStart() {

	}


	public static void registerTextures() {
		for(AtlasStitcher stitcher : TextureRegistry.stitcherMap.values()) {
			try {
				TextureHelper.initializeAllFiles(MOD_ID, stitcher, Integer.MAX_VALUE);
			} catch (Exception e) {
				LOGGER.error("Failed to initialize texture files!", e);
			}
		}

	}
}
