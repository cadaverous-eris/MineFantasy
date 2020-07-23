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
import minefantasy.api.tanner.LeathercuttingRecipes;
import minefantasy.api.tanner.TanningRecipes;
import minefantasy.block.BlockListMF;
import minefantasy.compat.nei.TailorRecipeHandler.CachedTailorRecipe;
import minefantasy.item.ItemListMF;
import net.minecraft.item.ItemStack;

public class TanningRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Tanning";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/tanningNEI.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "tanning";
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(67, 23, 24, 18), "tanning"));
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
		if (outputId.equals("tanning") && getClass() == TanningRecipeHandler.class) {
			for (Entry<Integer, ItemStack> recipe : TanningRecipes.instance().tanningList.entrySet()) {
				arecipes.add(new CachedTanningRecipe(new ItemStack(recipe.getKey(), 1, -1), recipe.getValue()));
			}
			for (Entry<List<Integer>, ItemStack> recipe : TanningRecipes.instance().metaTanningList.entrySet()) {
				arecipes.add(new CachedTanningRecipe(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), recipe.getValue()));
			}
			for (Entry<Integer, ItemStack> recipe : LeathercuttingRecipes.instance().tanningList.entrySet()) {
				arecipes.add(new CachedCuttingRecipe(new ItemStack(recipe.getKey(), 1, -1), recipe.getValue()));
			}
			for (Entry<List<Integer>, ItemStack> recipe : LeathercuttingRecipes.instance().metaTanningList.entrySet()) {
				arecipes.add(new CachedCuttingRecipe(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (Entry<Integer, ItemStack> recipe : TanningRecipes.instance().tanningList.entrySet()) {
			if (recipe != null && recipe.getValue() != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), result)) {
				arecipes.add(new CachedTanningRecipe(new ItemStack(recipe.getKey(), 1, -1), recipe.getValue()));
			}
		}
		for (Entry<List<Integer>, ItemStack> recipe : TanningRecipes.instance().metaTanningList.entrySet()) {
			if (recipe != null && recipe.getValue() != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), result)) {
				arecipes.add(new CachedTanningRecipe(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), recipe.getValue()));
			}
		}
		for (Entry<Integer, ItemStack> recipe : LeathercuttingRecipes.instance().tanningList.entrySet()) {
			if (recipe != null && recipe.getValue() != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), result)) {
				arecipes.add(new CachedCuttingRecipe(new ItemStack(recipe.getKey(), 1, -1), recipe.getValue()));
			}
		}
		for (Entry<List<Integer>, ItemStack> recipe : LeathercuttingRecipes.instance().metaTanningList.entrySet()) {
			if (recipe != null && recipe.getValue() != null && NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), result)) {
				arecipes.add(new CachedCuttingRecipe(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), recipe.getValue()));
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
		ItemStack in = ingredient.copy();
		in.stackSize = 1;
		ItemStack out = TanningRecipes.instance().getTanningResult(in);
		if (out != null) {
			arecipes.add(new CachedTanningRecipe(in, out));
		}
		out = LeathercuttingRecipes.instance().getCuttingResult(in);
		if (out != null) {
			arecipes.add(new CachedCuttingRecipe(in, out));
		}
	}
	
	private static final ItemStack[] knives = new ItemStack[] {
			new ItemStack(ItemListMF.knifeStone),
			new ItemStack(ItemListMF.knifeCopper),
			new ItemStack(ItemListMF.knifeTin),
			new ItemStack(ItemListMF.knifeBronze),
			new ItemStack(ItemListMF.knifeIron),
			new ItemStack(ItemListMF.knifeSteel),
			new ItemStack(ItemListMF.knifeDragon),
			new ItemStack(ItemListMF.knifeDeepIron),
			new ItemStack(ItemListMF.knifeMithril)
	};
	private static final ItemStack[] shearss = new ItemStack[] {
			new ItemStack(ItemListMF.shearsCopper),
			new ItemStack(ItemListMF.shearsTin),
			new ItemStack(ItemListMF.shearsBronze),
			new ItemStack(ItemListMF.shearsIron),
			new ItemStack(ItemListMF.shearsSteel),
			new ItemStack(ItemListMF.shearsDragon),
			new ItemStack(ItemListMF.shearsDeepIron),
			new ItemStack(ItemListMF.shearsMithril)
	};
	public static final PositionedStack knife = new PositionedStack(knives, 42, 6);
	public static final PositionedStack shears = new PositionedStack(shearss, 42, 6);
	
	public static final PositionedStack rack = new PositionedStack(new ItemStack(BlockListMF.tanner), 42, 42);
	
	public class CachedTanningRecipe extends CachedRecipe {

		public PositionedStack input, output;
		
		public CachedTanningRecipe(ItemStack in, ItemStack out) {
			this.input = new PositionedStack(in, 42, 24);
			this.output = new PositionedStack(out, 104, 24);
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
			stacks.add(rack);
			stacks.add(knife);
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
	}
	
	public class CachedCuttingRecipe extends CachedTanningRecipe {
		
		public CachedCuttingRecipe(ItemStack in, ItemStack out) {
			super(in, out);
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(rack);
			stacks.add(shears);
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
	}

}
