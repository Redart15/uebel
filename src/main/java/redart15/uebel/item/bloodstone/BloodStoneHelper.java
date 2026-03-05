package redart15.uebel.item.bloodstone;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import redart15.uebel.model.item.ItemRenderSpecial;

public class BloodStoneHelper {

	private BloodStoneHelper(){}

	public static void renderInventory(
		EntityRenderDispatcher instance, Tessellator tessellator,
		Entity entity, double x, double y, double z, float yaw, float partialTick, Operation<Void> original
	) {
		if (!(entity instanceof Player)) {
			return;
		}
		Player player = (Player) entity;
		for (int renderPass = 0; renderPass < player.inventory.armorInventory.length; renderPass++) {
			ItemStack itemstack = player.inventory.armorItemInSlot(renderPass);
			if (itemstack != null) {
				ItemModel model = ItemModelDispatcher.getInstance().getDispatch(itemstack.getItem());
				if (model instanceof ItemRenderSpecial) {
					((ItemRenderSpecial) model).preRenderInventory();
				}
			}
		}
		original.call(instance, tessellator, entity, x, y, z, yaw, partialTick);
		for (int renderPass = 0; renderPass < player.inventory.armorInventory.length; renderPass++) {
			ItemStack itemstack = player.inventory.armorItemInSlot(renderPass);
			if (itemstack != null) {
				ItemModel model = ItemModelDispatcher.getInstance().getDispatch(itemstack.getItem());
				if (model instanceof ItemRenderSpecial) {
					((ItemRenderSpecial) model).postRenderInventory();
				}
			}
		}
	}
}
