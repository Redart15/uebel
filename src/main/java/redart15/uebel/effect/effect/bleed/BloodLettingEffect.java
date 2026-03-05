package redart15.uebel.effect.effect.bleed;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import redart15.uebel.effect.effect.UebelEffects;
import sunsetsatellite.catalyst.effects.api.effect.EffectContainer;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.List;
import java.util.Random;

public class BloodLettingEffect extends BleedingEffect {
	public static final Random random = new Random();

	public BloodLettingEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
		super(nameKey, id, modifiers, effectTimeType, maxStack);
	}

	@Override
	public <T> void expired(EffectStack effectStack, EffectContainer<T> effectContainer) {
		effectContainer.remove(UebelEffects.blood_letting);
		EffectStack newStack = new EffectStack((IHasEffects<?>)effectContainer.getParent(), UebelEffects.blood_letting, effectStack.getAmount() - 1);
		effectContainer.add(newStack);
		newStack.start(effectContainer);
	}

	@Override
	public boolean canApplyTo(Entity target) {
		return target instanceof Mob && super.canApplyTo(target);
	}
}
