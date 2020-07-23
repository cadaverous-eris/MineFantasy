package minefantasy.compat.nei;

import static codechicken.core.gui.GuiDraw.changeTexture;
import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import minefantasy.api.cooking.FoodPrepRecipe;
import minefantasy.api.tailor.CraftingManagerTailor;
import minefantasy.api.tailor.ShapedTailorRecipes;
import minefantasy.api.tailor.StringRecipes;
import minefantasy.block.BlockListMF;
import minefantasy.compat.nei.TailorRecipeHandler.CachedTailorRecipe;
import minefantasy.item.ItemListMF;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CounterRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Benchtop";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/counterNEI.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "benchtop";
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(67, 23, 24, 18), "benchtop"));
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
		if (outputId.equals("benchtop") && getClass() == CounterRecipeHandler.class) {
			for (FoodPrepRecipe recipe : FoodPrepRecipe.recipes) {
				arecipes.add(new CachedCounterRecipe(recipe));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (FoodPrepRecipe recipe : FoodPrepRecipe.recipes) {
			if (recipe != null && recipe.output != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.output, result)) {
				arecipes.add(new CachedCounterRecipe(recipe));
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
		for (FoodPrepRecipe recipe : FoodPrepRecipe.recipes) {
			if (recipe != null && recipe.input != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.input, ingredient)) {
				arecipes.add(new CachedCounterRecipe(recipe));
			}
		}
	}
	
	public static final PositionedStack benchtop = new PositionedStack(new ItemStack(BlockListMF.foodPrep), 42, 42);
	public static final PositionedStack mallet = new PositionedStack(new ItemStack[] {new ItemStack(ItemListMF.malletWood), new ItemStack(ItemListMF.malletIronbark), new ItemStack(ItemListMF.malletEbony)}, 42, 6);
	public static final PositionedStack knife = new PositionedStack(new ItemStack[] {new ItemStack(ItemListMF.knifeStone), new ItemStack(ItemListMF.knifeTin), new ItemStack(ItemListMF.knifeCopper), new ItemStack(ItemListMF.knifeBronze), new ItemStack(ItemListMF.knifeIron), new ItemStack(ItemListMF.knifeSteel), new ItemStack(ItemListMF.knifeDragon), new ItemStack(ItemListMF.knifeDeepIron), new ItemStack(ItemListMF.knifeMithril)}, 42, 6);
	
	public class CachedCounterRecipe extends CachedRecipe {

		public PositionedStack input, output;
		public String utensil;
		
		public CachedCounterRecipe(FoodPrepRecipe recipe) {
			this.input = new PositionedStack(recipe.input, 42, 24);
			this.output = new PositionedStack(recipe.output, 104, 24);
			this.utensil = recipe.utensil;
		}
		
		@Override
		public PositionedStack getResult() {
			return this.output;
		}
		
		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(this.input);
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(benchtop);
			if (this.utensil.equals("mallet")) {
				stacks.add(mallet);
			} else if (this.utensil.equals("knife")) {
				stacks.add(knife);
			}
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
	}

}
