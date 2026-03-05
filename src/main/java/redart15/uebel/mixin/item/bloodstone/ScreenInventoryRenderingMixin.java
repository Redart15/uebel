package redart15.uebel.mixin.item.bloodstone;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import redart15.uebel.item.bloodstone.BloodStoneHelper;


@Mixin(value = ScreenInventory.class, remap = false)
public class ScreenInventoryRenderingMixin {
	@WrapOperation(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/EntityRenderDispatcher;renderEntityWithPosYaw(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Entity;DDDFF)V"))
	public void renderInventory(
		EntityRenderDispatcher instance, Tessellator tessellator,
		Entity entity, double x, double y, double z, float yaw, float partialTick, Operation<Void> original
	) {
		BloodStoneHelper.renderInventory(instance, tessellator, entity,  x, y, z, yaw, partialTick, original);
	}
}
