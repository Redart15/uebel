package redart15.uebel.effect.effect.bleed;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.MathHelper;
import redart15.helver.entity.particle.ParticleHelper;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectContainer;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.List;
import java.util.Random;

public class BleedingEffect extends Effect {
	public static final Random random = new Random();

	public BleedingEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
		super(nameKey, id, modifiers, effectTimeType, maxStack);
	}

	@Override
	public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
		if (!(effectContainer.getParent() instanceof Mob) || !(random.nextFloat() > 0.6)) {
			return;
		}
		Mob mob = (Mob)effectContainer.getParent();
		double angle = MathHelper.toRadians(random.nextInt(360));
		double radius = mob.bbWidth / 2.0f * random.nextFloat();
		double lx = mob.x + radius * Math.cos(angle);
		double lz = mob.z + radius * Math.sin(angle);
		double ly = mob.y + mob.bbHeight / 1.5f;

		if(mob instanceof Player){
			ly = mob.y - mob.heightOffset + mob.bbHeight / 2.0f;
		}
		ParticleHelper.spawnParticle(mob.world, "bleeding", lx, ly, lz, 0, -0.05, 0, 0, 16.0F);
	}
}
