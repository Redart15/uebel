package redart15.uebel.effect.effect;

import net.minecraft.core.Global;
import net.minecraft.core.entity.Entity;
import redart15.uebel.effect.effect.bleed.BleedingEffect;
import redart15.uebel.effect.effect.bleed.BloodLettingEffect;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRenderer;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRendererDispatcher;
import sunsetsatellite.catalyst.effects.net.SyncEffectContainerForEntityNetworkMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

import java.util.ArrayList;
import java.util.List;

import static redart15.uebel.UebelMod.*;

public class UebelEffects {
	private static boolean init = false;
	public static Effect blood_letting;
	public static Effect bleeding;

	private UebelEffects() {}
	public static void init() {
		if (init) {
			return;
		}
		init = true;
		assignEffects();
		registerEffects();
		if (!EnvironmentHelper.isServerEnvironment()) {
			assignEffectRenderers();
		}
	}

	private static void assignEffects() {
		blood_letting = new BloodLettingEffect(
			"effect." + MOD_ID + ".blood_letting",
			MOD_ID + ":blood_letting",
			new ArrayList<>(),
			EffectTimeType.RESET, 2
		).setDefaultDuration(8 * Global.TICKS_PER_SECOND);

		bleeding = new BleedingEffect(
			"effect." + MOD_ID + ".bleeding",
			MOD_ID + ":bleeding",
			new ArrayList<>(),
			EffectTimeType.ADD, 1
		)
			.setDefaultDuration(8 * Global.TICKS_PER_SECOND)
			.setDurationIncrease(Global.TICKS_PER_SECOND);
	}

	private static void registerEffects() {
		Effects effects = Effects.getInstance();
		effects.register(blood_letting.id, blood_letting);
		effects.register(bleeding.id, bleeding);
	}

	private static void assignEffectRenderers() {
		EffectRendererDispatcher dispatcher = EffectRendererDispatcher.getInstance();
		dispatcher.addDispatch(blood_letting, new EffectRenderer<>(blood_letting).setIcon("bleeding.png"));
		dispatcher.addDispatch(bleeding, new EffectRenderer<>(bleeding).setIcon("bleeding.png"));
	}

	public static void quickStartEffect(IHasEffects<?> victom, Effect effect, int stackSize) {
		EffectStack stack = new EffectStack(victom, effect, stackSize);
		stack.start(victom.getContainer());
		UebelEffects.add(victom.getContainer(), stack);
	}

	public static void add(EffectContainer<?> container, EffectStack currentEffect) {
		if (!currentEffect.getEffect().canApplyTo((Entity) container.getParent())) return;
		List<EffectStack> effects = container.getEffects();
		for (EffectStack effectStack : effects) {
			if (effectStack.getEffect() == currentEffect.getEffect()) {
				int amount = Math.min(currentEffect.getAmount(), effectStack.getEffect().getMaxStack() - effectStack.getAmount());
				add(effectStack, amount, container);
				syncEffectContainer(container);
				return;
			}
		}
		container.add(currentEffect);
	}


	public static void add(EffectStack stack, int amount, EffectContainer<?> effectContainer) {
		if (amount > 0) {
			stack.add(amount, effectContainer);
		}
	}

	private static void syncEffectContainer(EffectContainer<?> container) {
		if (EnvironmentHelper.isServerEnvironment()) {
			NetworkHandler.sendToAllPlayers(new SyncEffectContainerForEntityNetworkMessage((Entity) container.getParent()));
		}
	}
}
