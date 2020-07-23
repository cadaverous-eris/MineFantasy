package minefantasy.compat.nei;

import static codechicken.core.gui.GuiDraw.changeTexture;
import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.refine.Alloy;
import minefantasy.api.refine.BloomRecipe;
import minefantasy.api.refine.SpecialFurnaceRecipes;
import minefantasy.api.refine.SpecialFurnaceRecipes.SpecialSmelt;
import minefantasy.api.tailor.CraftingManagerTailor;
import minefantasy.api.tailor.ShapedTailorRecipes;
import minefantasy.block.tileentity.TileEntitySmelter;
import minefantasy.client.gui.GuiBloomery;
import minefantasy.compat.nei.BigFurnaceRecipeHandler.CachedFurnaceAlloyRecipe;
import minefantasy.compat.nei.BigFurnaceRecipeHandler.CachedFurnaceRecipe;
import minefantasy.compat.nei.TailorRecipeHandler.CachedTailorRecipe;
import minefantasy.item.ItemBloom;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class BloomeryRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Bloomery";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/Bloomery.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "bloomery";
	}
	
	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1, 1, 1, 1);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(0, 0, 7, 11, 162, 64);
	}
	
	@Override
	public int recipiesPerPage() {
		return 2;
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(90, 5, 9, 29), "bloomery"));
        transferRects.add(new RecipeTransferRect(new Rectangle(66, 23, 28, 18), "bloomery"));
        transferRects.add(new RecipeTransferRect(new Rectangle(48, 17, 16, 17), "fuel"));
    }
	
	@Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiBloomery.class;
    }
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("bloomery") && getClass() == BloomeryRecipeHandler.class) {
			HashMap<Integer, ItemStack> recipes = (HashMap<Integer, ItemStack>) FurnaceRecipes.smelting().getSmeltingList();
			HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) FurnaceRecipes.smelting()
					.getMetaSmeltingList();

			for (BloomRecipe recipe : BloomRecipe.recipeList) {
				ItemStack result = BloomRecipe.getResult(recipe.input1);
				arecipes.add(new CachedBloomeryRecipe(recipe.input1, result));
			}
			for (Entry<Integer, ItemStack> recipe : recipes.entrySet()) {
				ItemStack in = new ItemStack(recipe.getKey(), 1, 0);
				ItemStack result = new TileEntitySmelter().getResult(in);
				boolean dupe = BloomRecipe.getResult(in) != null;
				if (!dupe && result != null) {
					arecipes.add(new CachedBloomeryRecipe(new ItemStack(recipe.getKey(), 1, -1), result));
				}
			}
			for (Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet()) {
				ItemStack in = new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1));
				ItemStack result = new TileEntitySmelter().getResult(in);
				boolean dupe = BloomRecipe.getResult(in) != null;
				if (!dupe && result != null) {
					arecipes.add(new CachedBloomeryRecipe(in, result));
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack output) {
		HashMap<Integer, ItemStack> recipes = (HashMap<Integer, ItemStack>) FurnaceRecipes.smelting().getSmeltingList();
		HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) FurnaceRecipes.smelting()
				.getMetaSmeltingList();

		for (BloomRecipe recipe : BloomRecipe.recipeList) {
			ItemStack result = BloomRecipe.getResult(recipe.input1);
			if (NEIServerUtils.areStacksSameTypeCrafting(result, output) && ItemStack.areItemStackTagsEqual(result, output)) {
				arecipes.add(new CachedBloomeryRecipe(recipe.input1, result));
			}
		}
		for (Entry<Integer, ItemStack> recipe : recipes.entrySet()) {
			ItemStack in = new ItemStack(recipe.getKey(), 1, 0);
			ItemStack result = new TileEntitySmelter().getResult(in);
			boolean dupe = BloomRecipe.getResult(in) != null;
			if (!dupe && result != null) {
				if (NEIServerUtils.areStacksSameTypeCrafting(result, output) && ItemStack.areItemStackTagsEqual(result, output)) {
					arecipes.add(new CachedBloomeryRecipe(new ItemStack(recipe.getKey(), 1, -1), result));
				}
			}
		}
		for (Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet()) {
			ItemStack in = new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1));
			ItemStack result = new TileEntitySmelter().getResult(in);
			boolean dupe = BloomRecipe.getResult(in) != null;
			if (!dupe && result != null) {
				if (NEIServerUtils.areStacksSameTypeCrafting(result, output) && ItemStack.areItemStackTagsEqual(result, output)) {
					arecipes.add(new CachedBloomeryRecipe(in, result));
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("item"))
			loadUsageRecipes((ItemStack) ingredients[0]);
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		HashMap<Integer, ItemStack> recipes = (HashMap<Integer, ItemStack>) FurnaceRecipes.smelting().getSmeltingList();
		HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) FurnaceRecipes.smelting()
				.getMetaSmeltingList();
		
		if (ItemHandler.isCarbon(ingredient)) {
			for (BloomRecipe recipe : BloomRecipe.recipeList) {
				arecipes.add(new CachedBloomeryRecipe(recipe.input1, BloomRecipe.getResult(recipe.input1)));
			}
			for (Entry<Integer, ItemStack> recipe : recipes.entrySet()) {
				ItemStack in = new ItemStack(recipe.getKey(), 1, 0);
				ItemStack result = new TileEntitySmelter().getResult(in);
				boolean dupe = BloomRecipe.getResult(in) != null;
				if (!dupe && result != null) {
					arecipes.add(new CachedBloomeryRecipe(new ItemStack(recipe.getKey(), 1, -1), result));
				}
			}
			for (Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet()) {
				ItemStack in = new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1));
				ItemStack result = new TileEntitySmelter().getResult(in);
				boolean dupe = BloomRecipe.getResult(in) != null;
				if (!dupe && result != null) {
					arecipes.add(new CachedBloomeryRecipe(in, result));
				}
			}
		} else {
			ItemStack in = ingredient.copy();
			in.stackSize = 1;
			ItemStack result = new TileEntitySmelter().getResult(ingredient);
			if (result != null) {
				arecipes.add(new CachedBloomeryRecipe(in, result));
			}
		}
	}
	
	public static final PositionedStack carbon = new PositionedStack(new ArrayList<ItemStack>(ItemHandler.carbon), 95, 35);
	
	public class CachedBloomeryRecipe extends CachedRecipe {

		public PositionedStack input, output;

		public CachedBloomeryRecipe(ItemStack in, ItemStack out, boolean bloomOut) {
			this.input = new PositionedStack(in, 72, 6);
			if (bloomOut) {
				this.output = new PositionedStack(ItemBloom.createBloom(out), 72, 42);
			} else {
				this.output = new PositionedStack(out, 72, 42);
			}
		}
		
		public CachedBloomeryRecipe(ItemStack in, ItemStack out) {
			this.input = new PositionedStack(in, 72, 6);
			this.output = new PositionedStack(out, 72, 42);
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(input);
			stacks.add(carbon);
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			
			return stacks;
		}

	}

}
