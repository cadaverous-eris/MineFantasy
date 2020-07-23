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
import minefantasy.api.anvil.CraftingManagerAnvil;
import minefantasy.api.anvil.IAnvilRecipe;
import minefantasy.api.anvil.ShapedAnvilRecipes;
import minefantasy.api.anvil.ShapelessAnvilRecipes;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import net.minecraft.item.ItemStack;

public class AnvilRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Anvil";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/anvilArtisan.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "anvil";
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(50, 97, 24, 18), "anvil"));
    }

	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1, 1, 1, 1);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(0, 0, 5, 5, 166, 116);
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("anvil")) {
			for (Object r : CraftingManagerAnvil.getInstance().getRecipeList()) {
				if (r instanceof ShapedAnvilRecipes) {
					ShapedAnvilRecipes recipe = (ShapedAnvilRecipes) r;
					if ((recipe.recipeWidth > 5 || recipe.recipeHeight > 4)) {
						arecipes.add(new CachedAnvilShapedRecipe(recipe));
					}
				} else if (r instanceof ShapelessAnvilRecipes) {
					ShapelessAnvilRecipes recipe = (ShapelessAnvilRecipes) r;
					if (recipe.getIngredients().size() > 20) {
						arecipes.add(new CachedAnvilShapelessRecipe(recipe));
					}
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (Object r : CraftingManagerAnvil.getInstance().getRecipeList()) {
			if (r instanceof ShapedAnvilRecipes) {
				ShapedAnvilRecipes recipe = (ShapedAnvilRecipes) r;
				if ((recipe.recipeWidth > 5 || recipe.recipeHeight > 4) && NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result)) {
					arecipes.add(new CachedAnvilShapedRecipe(recipe));
				}
			} else if (r instanceof ShapelessAnvilRecipes) {
				ShapelessAnvilRecipes recipe = (ShapelessAnvilRecipes) r;
				if (recipe.getIngredients().size() > 20 && NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result)) {
					arecipes.add(new CachedAnvilShapelessRecipe(recipe));
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
		for (Object r : CraftingManagerAnvil.getInstance().getRecipeList()) {
			if (r instanceof ShapedAnvilRecipes) {
				ShapedAnvilRecipes recipe = (ShapedAnvilRecipes) r;
				if ((recipe.recipeWidth > 5 || recipe.recipeHeight > 4)) {
					for (ItemStack stack : recipe.getIngredients()) {
						if (NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
							arecipes.add(new CachedAnvilShapedRecipe(recipe));
							break;
						}
					}
				}
			} else if (r instanceof ShapelessAnvilRecipes) {
				ShapelessAnvilRecipes recipe = (ShapelessAnvilRecipes) r;
				if (recipe.getIngredients().size() > 20) {
					for (Object s : recipe.getIngredients()) {
						if (s instanceof ItemStack && NEIServerUtils.areStacksSameTypeCrafting((ItemStack) s, ingredient)) {
							arecipes.add(new CachedAnvilShapelessRecipe(recipe));
							break;
						}
					}
				}
			}
		}
	}

	private static final ItemStack[] hammers = new ItemStack[] { new ItemStack(ItemListMF.hammerStone),
			new ItemStack(ItemListMF.hammerTin), new ItemStack(ItemListMF.hammerCopper),
			new ItemStack(ItemListMF.hammerBronze), new ItemStack(ItemListMF.hammerIron),
			new ItemStack(ItemListMF.hammerSteel), new ItemStack(ItemListMF.hammerDeepIron),
			new ItemStack(ItemListMF.hammerMithril), new ItemStack(ItemListMF.hammerDragon) };
	public static final PositionedStack hammer = new PositionedStack(hammers, 12, 97);
	public static final PositionedStack hammerOrnate = new PositionedStack(new ItemStack(ItemListMF.hammerOrnate), 12,
			97);

	public static final PositionedStack anvilStone = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 0), 138,
			97);
	public static final PositionedStack anvilBronze = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 2), 138,
			97);
	public static final PositionedStack anvilIron = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 4), 138,
			97);
	public static final PositionedStack anvilSteel = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 6), 138,
			97);
	public static final PositionedStack anvilDeepIron = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 8), 138,
			97);

	public class CachedAnvilShapedRecipe extends CachedRecipe {

		public List<PositionedStack> ingredients;
		public PositionedStack output;
		public int tier;
		public boolean ornateHammer;

		public CachedAnvilShapedRecipe(ShapedAnvilRecipes recipe) {
			int width = recipe.recipeWidth;
			int height = recipe.recipeHeight;

			this.ingredients = new ArrayList<PositionedStack>();
			int i = 0;
			for (ItemStack stack : recipe.getIngredients()) {
				int x = i - (width * (i / width));
				int y = i / width;

				if (stack != null) {
					this.ingredients.add(new PositionedStack(stack, 11 + (18 * x) + 1, 2 + (18 * y) + 1));
				}

				i++;
			}

			this.output = new PositionedStack(recipe.getRecipeOutput(), 75, 97);
			this.tier = recipe.getAnvil();
			this.ornateHammer = recipe.getRecipeHammer() > 0;
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>(this.ingredients);
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			if (this.ornateHammer) {
				stacks.add(hammerOrnate);
			} else {
				stacks.add(hammer);
			}
			if (this.tier == -1) {
				stacks.add(anvilBronze);
			} else if (this.tier == 0) {
				stacks.add(anvilBronze);
			} else if (this.tier == 1) {
				stacks.add(anvilIron);
			} else if (this.tier == 2) {
				stacks.add(anvilSteel);
			} else if (this.tier >= 3) {
				stacks.add(anvilDeepIron);
			}
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}

	}
	
	public class CachedAnvilShapelessRecipe extends CachedRecipe {

		public List<PositionedStack> ingredients;
		public PositionedStack output;
		public int tier;
		public boolean ornateHammer;

		public CachedAnvilShapelessRecipe(ShapelessAnvilRecipes recipe) {
			boolean small = recipe.getIngredients().size() <= 20;
			int width = small ? 5 : 8;
			int height = small ? 4 : 5;

			this.ingredients = new ArrayList<PositionedStack>();
			int i = 0;
			for (Object s : recipe.getIngredients()) {
				int x = i - (width * (i / width));
				int y = i / width;

				if (s != null && s instanceof ItemStack) {
					this.ingredients.add(new PositionedStack((ItemStack) s, 11 + (18 * x) + 1, 2 + (18 * y) + 1));
				}

				i++;
			}

			this.output = new PositionedStack(recipe.getRecipeOutput(), 75, 97);
			this.tier = recipe.getAnvil();
			this.ornateHammer = recipe.getRecipeHammer() > 0;
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>(this.ingredients);
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			if (this.ornateHammer) {
				stacks.add(hammerOrnate);
			} else {
				stacks.add(hammer);
			}
			if (this.tier == -1) {
				stacks.add(anvilBronze);
			} else if (this.tier == 0) {
				stacks.add(anvilBronze);
			} else if (this.tier == 1) {
				stacks.add(anvilIron);
			} else if (this.tier == 2) {
				stacks.add(anvilSteel);
			} else if (this.tier >= 3) {
				stacks.add(anvilDeepIron);
			}
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}

	}

}
