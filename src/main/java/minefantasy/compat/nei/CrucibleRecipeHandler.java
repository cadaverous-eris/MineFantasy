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
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.refine.Alloy;
import minefantasy.api.refine.AlloyRecipes;
import minefantasy.api.refine.BloomRecipe;
import minefantasy.api.tailor.CraftingManagerTailor;
import minefantasy.api.tailor.ShapedTailorRecipes;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntitySmelter;
import minefantasy.compat.nei.BloomeryRecipeHandler.CachedBloomeryRecipe;
import minefantasy.compat.nei.TailorRecipeHandler.CachedTailorRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class CrucibleRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Crucible";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/alloySml.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "crucible";
	}
	
	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1, 1, 1, 1);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(10, 0, 23, 11, 146, 64);
	}
	
	@Override
	public int recipiesPerPage() {
		return 2;
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(93, 23, 24, 18), "crucible"));
    }
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("crucible") && getClass() == CrucibleRecipeHandler.class) {
			for (Alloy alloy : AlloyRecipes.alloys) {
				if (alloy != null && alloy.getRecipeOutput() != null && alloy.getIngredients().size() <= 9) {
					arecipes.add(new CachedCrucibleRecipe(alloy));
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack output) {
		for (Alloy alloy : AlloyRecipes.alloys) {
			if (alloy != null && alloy.getRecipeOutput() != null && alloy.getIngredients().size() <= 9) {
				ItemStack out = alloy.getRecipeOutput();
				if (NEIServerUtils.areStacksSameTypeCrafting(out, output)) {
					arecipes.add(new CachedCrucibleRecipe(alloy));
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
		for (Alloy alloy : AlloyRecipes.alloys) {
			if (alloy != null && alloy.getRecipeOutput() != null && alloy.getIngredients().size() <= 9) {
				for (Object s : alloy.getIngredients()) {
					if (s != null && s instanceof ItemStack) {
						ItemStack stack = (ItemStack) s;
						if (NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
							arecipes.add(new CachedCrucibleRecipe(alloy));
							break;
						}
					}
				}
			}
		}
	}
	
	public static final PositionedStack stone = new PositionedStack(new ItemStack(BlockListMF.smelter, 1, 1), 79 + 10, 42);
	public static final PositionedStack granite = new PositionedStack(new ItemStack(BlockListMF.smelter, 1, 2), 79 + 10, 42);
	
	public class CachedCrucibleRecipe extends CachedRecipe {

		public PositionedStack output;
		public List<PositionedStack> inputs;
		public int level;
		
		public CachedCrucibleRecipe(Alloy alloy) {
			inputs = new ArrayList<PositionedStack>();
			
			int i = 0;
			for (Object s : alloy.getIngredients()) {
				int x = i % 3;
				int y = i / 3;
				
				if (s != null && s instanceof ItemStack) {
					this.inputs.add(new PositionedStack((ItemStack) s, 13 + (x * 18) + 1 + 10, 5 + (y * 18) + 1));
				}
				
				i++;
			}
			
			this.output = new PositionedStack(alloy.getRecipeOutput(), 112 + 10, 24);
			this.level = alloy.getLevel();
		}
		
		@Override
		public PositionedStack getResult() {
			return output;
		}
		
		@Override
		public List<PositionedStack> getIngredients() {
			return new ArrayList<PositionedStack>(this.inputs);
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			if (this.level <= 0) {
				stacks.add(stone);
			} else if (this.level == 1) {
				stacks.add(granite);
			}
			
			return stacks;
		}
		
	}

}
