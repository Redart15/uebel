package redart15.uebel.recipe;

import net.minecraft.core.data.DataLoader;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static redart15.uebel.UebelMod.MOD_ID;

public class UebelRecipes implements RecipeEntrypoint {
	@Override
	public void onRecipesReady() {
		UebelRecipes.vanillaMachines();
	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}

	public static void vanillaMachines() {
		DataLoader.loadRecipesFromFile("/assets/" + MOD_ID + "/recipes/workbench.json");
		DataLoader.loadRecipesFromFile("/assets/" + MOD_ID + "/recipes/furnace.json");
		DataLoader.loadRecipesFromFile("/assets/" + MOD_ID + "/recipes/blast_furnace.json");
	}
}
