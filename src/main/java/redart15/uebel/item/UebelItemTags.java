package redart15.uebel.item;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import redart15.uebel.UebelMod;

import java.lang.reflect.Field;

public class UebelItemTags {
	private static boolean init = false;
	private UebelItemTags(){}
	public static void init(){
		if(init) return;
		init = true;
		UebelItemTags.initTags();
	}

	private static void initTags(){
		for(Field field : UebelItemTags.class.getDeclaredFields()) {
			if (field.getType().equals(Tag.class)) {
				try {
					Tag<Item> tag = (Tag)field.get(null);
					ItemTags.TAG_LIST.add(tag);
				} catch (Exception e) {
					UebelMod.LOGGER.error("Failed to add tag '{}'!", field.getName(), e);
				}
			}
		}
	}
}
