package redart15.uebel.mixin.item.bloodstone;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import redart15.uebel.entity.TriggerOnPickup;

@Mixin(value = EntityItem.class, remap = false)
public abstract class EntityItemMixinBloodInsert {


	@WrapOperation(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;insertItem(Lnet/minecraft/core/item/ItemStack;Z)V"))
	private void bloodHeal(ContainerInventory container, ItemStack itemStack, boolean hotbarOffset, Operation<Void> original, Player player) {
		if (itemStack == null || !(itemStack.getItem() instanceof TriggerOnPickup) || !((TriggerOnPickup) itemStack.getItem()).canTrigger(container, itemStack, player)) {
			original.call(container, itemStack, hotbarOffset);
			return;
		}
		EntityItem entityItem = (EntityItem) (Object) this;
		((TriggerOnPickup) itemStack.getItem()).onPickUp(player, entityItem, container, itemStack, hotbarOffset);
	}
}
