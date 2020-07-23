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
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import minefantasy.api.cooking.FoodPrepRecipe;
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.refine.Alloy;
import minefantasy.api.refine.BlastRecipes;
import minefantasy.api.refine.SpecialFurnaceRecipes;
import minefantasy.api.refine.SpecialFurnaceRecipes.SpecialSmelt;
import minefantasy.compat.nei.BigFurnaceRecipeHandler.CachedFurnaceAlloyRecipe;
import minefantasy.compat.nei.BigFurnaceRecipeHandler.CachedFurnaceRecipe;
import minefantasy.compat.nei.CounterRecipeHandler.CachedCounterRecipe;
import minefantasy.item.ItemListMF;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class BlastFurnaceRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Blast Furnace";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/blastNEI.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "blast";
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(76, 23, 24, 18), "blast"));
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
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("blast") && getClass() == BlastFurnaceRecipeHandler.class) {
			for (BlastRecipes recipe : BlastRecipes.recipeList) {
				if (recipe != null) {
					arecipes.add(new CachedBlastRecipe(recipe));
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (NEIServerUtils.areStacksSameTypeCrafting(ItemListMF.component(ItemListMF.slag), result)) {
			for (BlastRecipes recipe : BlastRecipes.recipeList) {
				if (recipe != null) {
					arecipes.add(new CachedBlastRecipe(recipe));
				}
			}
		} else {
			for (BlastRecipes recipe : BlastRecipes.recipeList) {
				if (recipe != null && recipe.result != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.result, result)) {
					arecipes.add(new CachedBlastRecipe(recipe));
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
		if (ItemHandler.isCarbon(ingredient) || ItemHandler.isFlux(ingredient)) {
			for (BlastRecipes recipe : BlastRecipes.recipeList) {
				if (recipe != null) {
					arecipes.add(new CachedBlastRecipe(recipe));
				}
			}
		} else {
			for (BlastRecipes recipe : BlastRecipes.recipeList) {
				if (recipe != null && recipe.input != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.input, ingredient)) {
					arecipes.add(new CachedBlastRecipe(recipe));
				}
			}
		}
	}
	
	public static final PositionedStack carbon = new PositionedStack(ItemHandler.carbon, 41, 6);
	public static final PositionedStack flux = new PositionedStack(ItemHandler.flux, 66, 6);
	public static final PositionedStack slag = new PositionedStack(ItemListMF.component(ItemListMF.slag), 105, 33);
	
	public class CachedBlastRecipe extends CachedRecipe {

		public PositionedStack output;
		public PositionedStack input;
		
		public CachedBlastRecipe(BlastRecipes recipe) {
			this.input = new PositionedStack(recipe.input.copy(), 53, 42);
			this.output = new PositionedStack(recipe.result.copy(), 105, 15);
		}
		
		@Override
		public PositionedStack getResult() {
			return this.output;
		}
		
		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(this.input);
			stacks.add(carbon);
			stacks.add(flux);
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(slag);
			
			return stacks;
		}
		
	}

}
