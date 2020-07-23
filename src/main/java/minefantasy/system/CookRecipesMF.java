package minefantasy.system;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.api.MineFantasyAPI;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class CookRecipesMF {

	public static void init() {
		FurnaceRecipes.smelting().addSmelting(ItemListMF.miscFood.itemID, 1, food(2), 0.0F);
	    FurnaceRecipes.smelting().addSmelting(ItemListMF.miscFood.itemID, 3, food(4), 0.0F);
	    FurnaceRecipes.smelting().addSmelting(ItemListMF.miscFood.itemID, 5, food(6), 0.0F);
	    FurnaceRecipes.smelting().addSmelting(ItemListMF.miscFood.itemID, 14, food(15), 0.0F);
	    FurnaceRecipes.smelting().addSmelting(ItemListMF.miscFood.itemID, 17, food(18), 0.0F);
	    FurnaceRecipes.smelting().addSmelting(ItemListMF.miscFood.itemID, 19, food(20), 0.0F);
	    FurnaceRecipes.smelting().addSmelting(ItemListMF.miscFood.itemID, 21, food(22), 0.0F);
	    FurnaceRecipes.smelting().addSmelting(ItemListMF.miscFood.itemID, 25, food(26), 0.0F);
		
		GameRegistry.addShapelessRecipe(food(16), new Object[] { com(10), food(2) });
		GameRegistry.addShapelessRecipe(food(23), new Object[] { com(10), food(18) });
		
		GameRegistry.addShapelessRecipe(food(7), new Object[] { food(0), food(0), food(2) });
		GameRegistry.addShapelessRecipe(food(8), new Object[] { food(0), food(0), food(4) });
		GameRegistry.addShapelessRecipe(food(9), new Object[] { food(0), food(0), food(6) });
		GameRegistry.addShapelessRecipe(food(10), new Object[] { food(0), food(0), food(15) });
		GameRegistry.addShapelessRecipe(food(27), new Object[] { food(0), food(0), food(26) });

		addPreps();
	}

	private static void addPreps() {
		String chop = "dig.wood";
		String sawBread = "dig.cloth";

		MineFantasyAPI.addFoodPrep(Item.bread.itemID, food(0, 12), 100.0F, "knife", sawBread);

		MineFantasyAPI.addFoodPrep(Item.beefRaw.itemID, food(1, 4), 60.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Item.beefCooked.itemID, food(2, 4), 40.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Item.porkRaw.itemID, food(3, 4), 50.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Item.porkCooked.itemID, food(4, 4), 40.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(ItemListMF.muttonRaw.itemID, food(25, 4), 50.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(ItemListMF.muttonCooked.itemID, food(26, 4), 40.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Item.chickenRaw.itemID, food(5, 4), 40.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Item.chickenCooked.itemID, food(6, 4), 30.0F, "knife", chop);

		MineFantasyAPI.addFoodPrep(Item.fishRaw.itemID, food(14, 2), 30.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Item.fishCooked.itemID, food(15, 2), 20.0F, "knife", chop);

		MineFantasyAPI.addFoodPrep(ItemListMF.basiliskRaw.itemID, food(17, 4), 80.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(ItemListMF.basiliskCooked.itemID, food(18, 4), 60.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(ItemListMF.drakeRaw.itemID, food(21, 6), 80.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(ItemListMF.drakeCooked.itemID, food(22, 6), 60.0F, "knife", chop);
		
		MineFantasyAPI.addFoodPrep(Block.mushroomBrown.blockID, food(11, 4), 20.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Block.mushroomRed.blockID, food(12, 4), 20.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Block.pumpkin.blockID, food(13, 8), 150.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Item.carrot.itemID, food(24, 4), 20.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Item.potato.itemID, food(19, 4), 30.0F, "knife", chop);
		MineFantasyAPI.addFoodPrep(Item.bakedPotato.itemID, food(20, 4), 20.0F, "knife", chop);
	}

	private static ItemStack food(int id) {
		return food(id, 1);
	}

	private static ItemStack com(int id) {
		return new ItemStack(ItemListMF.misc, 1, id);
	}

	private static ItemStack food(int id, int num) {
		return new ItemStack(ItemListMF.miscFood, num, id);
	}

	public static void initReplaced() {
		GameRegistry.addShapelessRecipe(new ItemStack(Item.bowlSoup),
				new Object[] { Item.bowlEmpty, food(11), food(12) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.pumpkinPie),
				new Object[] { food(13), Item.sugar, Item.egg });
	}

}
