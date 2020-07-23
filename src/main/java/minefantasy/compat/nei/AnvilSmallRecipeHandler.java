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
import minefantasy.api.anvil.CraftingManagerAnvil;
import minefantasy.api.anvil.ShapedAnvilRecipes;
import minefantasy.api.anvil.ShapelessAnvilRecipes;
import minefantasy.api.refine.BloomRecipe;
import minefantasy.api.refine.CrushRecipe;
import minefantasy.api.refine.CrushRecipes;
import minefantasy.api.refine.ICustomCrushRecipe;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntitySmelter;
import minefantasy.compat.nei.AnvilRecipeHandler.CachedAnvilShapedRecipe;
import minefantasy.compat.nei.AnvilRecipeHandler.CachedAnvilShapelessRecipe;
import minefantasy.compat.nei.BloomeryRecipeHandler.CachedBloomeryRecipe;
import minefantasy.item.ItemBloom;
import minefantasy.item.ItemListMF;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class AnvilSmallRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Small Anvil";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/anvil.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "anvil_small";
	}

	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1, 1, 1, 1);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(0, 0, 5, 5, 166, 76);
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}
	
	private List<ItemStack> getBlooms() {
		HashMap<Integer, ItemStack> recipes = (HashMap<Integer, ItemStack>) FurnaceRecipes.smelting().getSmeltingList();
		HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) FurnaceRecipes.smelting()
				.getMetaSmeltingList();
		
		List<ItemStack> blooms = new ArrayList<ItemStack>();

		for (BloomRecipe recipe : BloomRecipe.recipeList) {
			ItemStack result = BloomRecipe.getResult(recipe.input1);
			if (result != null && result.getItem() == ItemListMF.bloom) {
				boolean skip = false;
				for (ItemStack bloom : blooms) {
					if (ItemStack.areItemStackTagsEqual(bloom, result)) {
						skip = true;
						break;
					}
				}
				if (!skip) {
					blooms.add(result);
				}
			}
		}
		for (Entry<Integer, ItemStack> recipe : recipes.entrySet()) {
			ItemStack in = new ItemStack(recipe.getKey(), 1, 0);
			ItemStack result = new TileEntitySmelter().getResult(in);
			boolean dupe = BloomRecipe.getResult(in) != null;
			if (!dupe && result != null) {
				if (result.getItem() == ItemListMF.bloom) {
					boolean skip = false;
					for (ItemStack bloom : blooms) {
						if (ItemStack.areItemStackTagsEqual(bloom, result)) {
							skip = true;
							break;
						}
					}
					if (!skip) {
						blooms.add(result);
					}
				}
			}
		}
		for (Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet()) {
			ItemStack in = new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1));
			ItemStack result = new TileEntitySmelter().getResult(in);
			boolean dupe = BloomRecipe.getResult(in) != null;
			if (!dupe && result != null) {
				if (result.getItem() == ItemListMF.bloom) {
					boolean skip = false;
					for (ItemStack bloom : blooms) {
						if (ItemStack.areItemStackTagsEqual(bloom, result)) {
							skip = true;
							break;
						}
					}
					if (!skip) {
						blooms.add(result);
					}
				}
			}
		}
		return blooms;
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(104, 29, 24, 18), "anvil_small"));
    }

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("anvil_small")) {
			for (Object r : CraftingManagerAnvil.getInstance().getRecipeList()) {
				if (r instanceof ShapedAnvilRecipes) {
					ShapedAnvilRecipes recipe = (ShapedAnvilRecipes) r;
					if ((recipe.recipeWidth <= 5 && recipe.recipeHeight <= 4)) {
						arecipes.add(new CachedAnvilShapedRecipe(recipe));
					}
				} else if (r instanceof ShapelessAnvilRecipes) {
					ShapelessAnvilRecipes recipe = (ShapelessAnvilRecipes) r;
					if (recipe.getIngredients().size() <= 20) {
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
				if ((recipe.recipeWidth <= 5 && recipe.recipeHeight <= 4) && NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result)) {
					arecipes.add(new CachedAnvilShapedRecipe(recipe));
				}
			} else if (r instanceof ShapelessAnvilRecipes) {
				ShapelessAnvilRecipes recipe = (ShapelessAnvilRecipes) r;
				if (recipe.getIngredients().size() <= 20 && NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result)) {
					arecipes.add(new CachedAnvilShapelessRecipe(recipe));
				}
			}
		}
		for (CrushRecipe r : CrushRecipes.recipeList) {
			if (r != null && r.output != null && NEIServerUtils.areStacksSameTypeCrafting(r.output, result)) {
				arecipes.add(new CachedAnvilShapelessRecipe(r));
			}
		}
		for (ItemStack bloom : this.getBlooms()) {
			ItemStack bloomOut = ItemBloom.getItem(bloom);
			if (bloomOut != null && NEIServerUtils.areStacksSameTypeCrafting(bloomOut, result)) {
				arecipes.add(new CachedCrushCustomRecipe(ItemBloom.createBloom(bloomOut), bloomOut));
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
				if ((recipe.recipeWidth <= 5 && recipe.recipeHeight <= 4)) {
					for (ItemStack stack : recipe.getIngredients()) {
						if (NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
							arecipes.add(new CachedAnvilShapedRecipe(recipe));
							break;
						}
					}
				}
			} else if (r instanceof ShapelessAnvilRecipes) {
				ShapelessAnvilRecipes recipe = (ShapelessAnvilRecipes) r;
				if (recipe.getIngredients().size() <= 20) {
					for (Object s : recipe.getIngredients()) {
						if (s instanceof ItemStack && NEIServerUtils.areStacksSameTypeCrafting((ItemStack) s, ingredient)) {
							arecipes.add(new CachedAnvilShapelessRecipe(recipe));
							break;
						}
					}
				}
			}
		}
		for (CrushRecipe r : CrushRecipes.recipeList) {
			if (r != null && r.input != null && NEIServerUtils.areStacksSameTypeCrafting(r.input, ingredient)) {
				arecipes.add(new CachedAnvilShapelessRecipe(r));
			}
		}
		if (ingredient.getItem() == ItemListMF.bloom) {
			ItemStack out = ItemBloom.getItem(ingredient);
			if (out != null) {
				arecipes.add(new CachedCrushCustomRecipe(ingredient.copy(), out));
			}
		}
	}

	private static final ItemStack[] hammers = new ItemStack[] { new ItemStack(ItemListMF.hammerStone),
			new ItemStack(ItemListMF.hammerTin), new ItemStack(ItemListMF.hammerCopper),
			new ItemStack(ItemListMF.hammerBronze), new ItemStack(ItemListMF.hammerIron),
			new ItemStack(ItemListMF.hammerSteel), new ItemStack(ItemListMF.hammerDeepIron),
			new ItemStack(ItemListMF.hammerMithril), new ItemStack(ItemListMF.hammerDragon) };
	public static final PositionedStack hammer = new PositionedStack(hammers, 139, 3);
	public static final PositionedStack hammerOrnate = new PositionedStack(new ItemStack(ItemListMF.hammerOrnate), 139,
			3);

	public static final PositionedStack anvilStone = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 0), 139,
			57);
	public static final PositionedStack anvilBronze = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 1), 139,
			57);
	public static final PositionedStack anvilIron = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 3), 139,
			57);
	public static final PositionedStack anvilSteel = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 5), 139,
			57);
	public static final PositionedStack anvilDeepIron = new PositionedStack(new ItemStack(BlockListMF.anvil, 1, 7), 139,
			57);

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
					this.ingredients.add(new PositionedStack(stack, 2 + (18 * x) + 1, 2 + (18 * y) + 1));
				}

				i++;
			}

			this.output = new PositionedStack(recipe.getRecipeOutput(), 139, 30);
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
				stacks.add(anvilStone);
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
					this.ingredients.add(new PositionedStack((ItemStack) s, 2 + (18 * x) + 1, 2 + (18 * y) + 1));
				}

				i++;
			}

			this.output = new PositionedStack(recipe.getRecipeOutput(), 139, 30);
			this.tier = recipe.getAnvil();
			this.ornateHammer = recipe.getRecipeHammer() > 0;
		}
		
		public CachedAnvilShapelessRecipe(CrushRecipe recipe) {
			this.ingredients = new ArrayList<PositionedStack>();
			this.ingredients.add(new PositionedStack(recipe.input, 2 + 1, 2 + 1));

			this.output = new PositionedStack(recipe.output, 139, 30);
			this.tier = -1;
			this.ornateHammer = false;
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
				stacks.add(anvilStone);
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
	
	public class CachedCrushCustomRecipe extends CachedRecipe {

		public PositionedStack ingredient;
		public PositionedStack output;
		
		public CachedCrushCustomRecipe(ItemStack in, ItemStack out) {
			this.ingredient = new PositionedStack(in, 2 + 1, 2 + 1);
			this.output = new PositionedStack(out, 139, 30);
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(ingredient);
			return getCycledIngredients(cycleticks / 20, stacks);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(hammer);
			stacks.add(anvilStone);
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
	}

}
