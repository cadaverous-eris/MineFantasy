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
import minefantasy.api.tailor.CraftingManagerTailor;
import minefantasy.api.tailor.ShapedTailorRecipes;
import minefantasy.api.tailor.StringList;
import minefantasy.item.ItemListMF;
import minefantasy.client.gui.GuiTailor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.item.ItemStack;

public class TailorRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Tailor Bench";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/tailor.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "tailoring";
	}

	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1, 1, 1, 1);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(0, 0, 7, 21, 162, 72);
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(108, 27, 24, 18), "tailoring"));
    }
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("tailoring") && getClass() == TailorRecipeHandler.class) {
			for (Object r : CraftingManagerTailor.getInstance().recipes) {
				if (r != null && r instanceof ShapedTailorRecipes) {
					ShapedTailorRecipes recipe = (ShapedTailorRecipes) r;
					arecipes.add(new CachedTailorRecipe(recipe));
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (Object r : CraftingManagerTailor.getInstance().recipes) {
			if (r != null && r instanceof ShapedTailorRecipes) {
				ShapedTailorRecipes recipe = (ShapedTailorRecipes) r;
				if (recipe.getRecipeOutput() != null && (NEIServerUtils.areStacksSameTypeCrafting(result, recipe.getRecipeOutput()) || NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result))) {
					arecipes.add(new CachedTailorRecipe(recipe));
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
		if (StringList.isString(ingredient)) {
			for (Object r : CraftingManagerTailor.getInstance().recipes) {
				if (r != null && r instanceof ShapedTailorRecipes) {
					ShapedTailorRecipes recipe = (ShapedTailorRecipes) r;
					if (recipe != null && StringList.doesMatchTier(ingredient, recipe.getString())) {
						arecipes.add(new CachedTailorRecipe(recipe));
					}
				}
			}
		}
		for (Object r : CraftingManagerTailor.getInstance().recipes) {
			if (r != null && r instanceof ShapedTailorRecipes) {
				ShapedTailorRecipes recipe = (ShapedTailorRecipes) r;
				for (ItemStack stack : recipe.getIngredients()) {
					if (stack != null && NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient) && (!StringList.doesMatchTier(ingredient, recipe.getString()))) {
						arecipes.add(new CachedTailorRecipe(recipe));
						break;
					}
				}
			}
		}
	}
	
	public List<ItemStack> getStrings(int tier, int count) {
		List<ItemStack> strings = new ArrayList<ItemStack>();
		
		for (Entry<Integer, Integer> string : StringList.stringID.entrySet()) {
			if (string.getValue() == tier) {
				strings.add(new ItemStack(string.getKey(), count, 0));
			}
		}
		for (Entry<List<Integer>, Integer> string : StringList.stringList.entrySet()) {
			if (string.getValue() == tier) {
				strings.add(new ItemStack(string.getKey().get(0), count, string.getKey().get(1)));
			}
		}
		
		return strings;
	}
	
	public static final PositionedStack t1 = new PositionedStack(new ItemStack(ItemListMF.needleBone), 109, 6);
	public static final PositionedStack t2 = new PositionedStack(new ItemStack(ItemListMF.needleBronze), 109, 6);
	public static final PositionedStack t4 = new PositionedStack(new ItemStack(ItemListMF.needleDeepIron), 109, 6);
	public static final PositionedStack t6 = new PositionedStack(new ItemStack(ItemListMF.needleMithril), 109, 6);
	
	public class CachedTailorRecipe extends CachedRecipe {

		public List<PositionedStack> inputs;
		public PositionedStack output;
		public int stringTier;
		public int needleTier;
		public int stitchCount;
		
		public CachedTailorRecipe(ShapedTailorRecipes recipe) {
			this.inputs = new ArrayList<PositionedStack>();
			
			int width = recipe.recipeWidth;
			int height = recipe.recipeHeight;
			int i = 0;
			for (ItemStack s : recipe.getIngredients()) {
				int x = i % width;
				int y = i / width;
				
				if (s != null) {
					ItemStack stack = s.copy();
					s.stackSize = 1;
					this.inputs.add(new PositionedStack(s, 26 + (x * 18) + 1, (y * 18) + 1));
				}
				
				i++;
			}
			
			this.output = new PositionedStack(recipe.getRecipeOutput(), 141, 28);
			this.stringTier = recipe.getString();
			this.needleTier = recipe.getTier();
			this.stitchCount = recipe.getStitchCount();
			
			this.inputs.add(new PositionedStack(getStrings(this.stringTier, this.stitchCount), 1, 1));
		}
		
		@Override
		public PositionedStack getResult() {
			return this.output;
		}
		
		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, this.inputs);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			if (this.needleTier <= 1) {
				stacks.add(t1);
			} else if (this.needleTier <= 2) {
				stacks.add(t2);
			} else if (this.needleTier <= 4) {
				stacks.add(t4);
			} else if (this.needleTier <= 6) {
				stacks.add(t6);
			}
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
	}

}
