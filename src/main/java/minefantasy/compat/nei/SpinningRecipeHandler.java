package minefantasy.compat.nei;

import static codechicken.core.gui.GuiDraw.changeTexture;
import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.refine.BlastRecipes;
import minefantasy.api.tailor.StringRecipes;
import minefantasy.api.tanner.LeathercuttingRecipes;
import minefantasy.api.tanner.TanningRecipes;
import minefantasy.block.BlockListMF;
import minefantasy.compat.nei.BlastFurnaceRecipeHandler.CachedBlastRecipe;
import minefantasy.compat.nei.TanningRecipeHandler.CachedCuttingRecipe;
import minefantasy.compat.nei.TanningRecipeHandler.CachedTanningRecipe;
import minefantasy.item.ItemListMF;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SpinningRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Spinning Wheel";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/spinningNEI.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "spinning";
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(67, 23, 24, 18), "spinning"));
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
		if (outputId.equals("spinning") && getClass() == SpinningRecipeHandler.class) {
			for (StringRecipes recipe : StringRecipes.recipes) {
				arecipes.add(new CachedSpinningRecipe(recipe));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (StringRecipes recipe : StringRecipes.recipes) {
			if (recipe != null && recipe.output != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.output, result)) {
				arecipes.add(new CachedSpinningRecipe(recipe));
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
		if (ingredient.getItem() == Item.stick) {
			for (StringRecipes recipe : StringRecipes.recipes) {
				if (recipe != null) {
					arecipes.add(new CachedSpinningRecipe(recipe));
				}
			}
		} else {
			for (StringRecipes recipe : StringRecipes.recipes) {
				if (recipe != null && recipe.input != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.input, ingredient)) {
					arecipes.add(new CachedSpinningRecipe(recipe));
				}
			}
		}
	}
	
	public static final PositionedStack stick = new PositionedStack(new ItemStack(Item.stick), 42, 34);
	public static final PositionedStack spinningWheel = new PositionedStack(new ItemStack(BlockListMF.spinningWheel), 73, 40);
	
	public class CachedSpinningRecipe extends CachedRecipe {

		public PositionedStack input;
		public PositionedStack output;
		
		public CachedSpinningRecipe(StringRecipes recipe) {
			this.input = new PositionedStack(recipe.input, 42, 14);
			this.output = new PositionedStack(recipe.output, 104, 24);
		}
		
		@Override
		public PositionedStack getResult() {
			return this.output;
		}
		
		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(this.input);
			stacks.add(stick);
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(spinningWheel);
			
			return stacks;
		}
		
	}

}
