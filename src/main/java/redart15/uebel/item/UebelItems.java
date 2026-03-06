package redart15.uebel.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.Items;
import redart15.uebel.config.UebelConfig;
import turniplabs.halplibe.helper.ItemBuilder;

import static redart15.uebel.UebelMod.MOD_ID;

public class UebelItems {
	public static Item CONGEALED_BLOOD;
	public static Item BUCKET_BLOOD;
	public static ItemFood BLOODWURST_RAW;
	public static ItemFood BLOODWURST;

	public static String itemKey(String string) {
		return MOD_ID + ":item/" + string;
	}

	private static boolean init = false;
	private UebelItems(){}
	public static void init(){
		if(init) return;
		init = true;

		UebelItems.createBlood();
		UebelItems.createFood();
	}

	private static void createBlood() {
		CONGEALED_BLOOD = new ItemBuilder(MOD_ID).build(new ItemBlood("congealed.blood", itemKey("congealed_blood"), UebelConfig.nextItemID()));
		BUCKET_BLOOD = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.setContainerItem(() -> Items.BUCKET)
			.build(new ItemBloodBucket("bucket.blood", itemKey("bucket_blood"), UebelConfig.nextItemID()));
	}

	private static void createFood() {
		BLOODWURST_RAW = new ItemBuilder(MOD_ID)
			.build(new ItemFood("bloodwurst.raw", itemKey("food_bloodwurst_raw"), UebelConfig.nextItemID(), 3, 24, true, 8));
		BLOODWURST = new ItemBuilder(MOD_ID)
			.build(new ItemFood("bloodwurst.cooked", itemKey("food_bloodwurst_cooked"), UebelConfig.nextItemID(), 3, 8, true, 8));
	}
}
