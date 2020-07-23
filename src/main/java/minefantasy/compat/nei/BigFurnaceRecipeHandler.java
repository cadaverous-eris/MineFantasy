package minefantasy.compat.nei;

import static codechicken.core.gui.GuiDraw.changeTexture;
import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.FurnaceRecipeHandler.SmeltingPair;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import minefantasy.api.anvil.CraftingManagerAnvil;
import minefantasy.api.anvil.ShapedAnvilRecipes;
import minefantasy.api.anvil.ShapelessAnvilRecipes;
import minefantasy.api.refine.Alloy;
import minefantasy.api.refine.SpecialFurnaceRecipes;
import minefantasy.api.refine.SpecialFurnaceRecipes.SpecialSmelt;
import minefantasy.api.tailor.CraftingManagerTailor;
import minefantasy.api.tailor.ShapedTailorRecipes;
import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityFurnaceMF;
import minefantasy.client.gui.GuiFurnaceMF;
import minefantasy.compat.nei.AnvilRecipeHandler.CachedAnvilShapedRecipe;
import minefantasy.compat.nei.AnvilRecipeHandler.CachedAnvilShapelessRecipe;
import minefantasy.compat.nei.TailorRecipeHandler.CachedTailorRecipe;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class BigFurnaceRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return "Big Furnace";
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy:textures/gui/bigFurnace.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "furnace_big";
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(71, 23, 24, 18), "furnace_big"));
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
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiFurnaceMF.class;
    }

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("furnace_big") && getClass() == BigFurnaceRecipeHandler.class) {
			HashMap<Integer, ItemStack> recipes = (HashMap<Integer, ItemStack>) FurnaceRecipes.smelting().getSmeltingList();
			HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) FurnaceRecipes.smelting()
					.getMetaSmeltingList();

			for (Entry<Integer, SpecialSmelt> recipe : SpecialFurnaceRecipes.specials.entrySet()) {
				ItemStack item = recipe.getValue().result;
				int lvl = recipe.getValue().lvl;
				arecipes.add(new CachedFurnaceRecipe(new ItemStack(recipe.getKey(), 1, -1), recipe.getValue().result, lvl));
			}
			for (Entry<List<Integer>, SpecialSmelt> recipe : SpecialFurnaceRecipes.specialsMeta.entrySet()) {
				ItemStack item = recipe.getValue().result;
				int lvl = recipe.getValue().lvl;
				arecipes.add(new CachedFurnaceRecipe(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), recipe.getValue().result, lvl));
			}
			for (Entry<Integer, ItemStack> recipe : recipes.entrySet()) {
				ItemStack item = recipe.getValue();
				if (!SpecialFurnaceRecipes.specials.containsKey(recipe.getKey())) {
					if (!(new ItemStack(recipe.getKey(), 1, 0).getItem() instanceof ItemFood)) {
						arecipes.add(new CachedFurnaceRecipe(new ItemStack(recipe.getKey(), 1, -1), recipe.getValue()));
					}
				}
			}
			if (metarecipes == null)
				return;
			for (Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet()) {
				ItemStack item = recipe.getValue();
				boolean skip = false;
				for (List<Integer> metaId : SpecialFurnaceRecipes.specialsMeta.keySet()) {
					if (metaId.get(0) == recipe.getKey().get(0) && metaId.get(1) == recipe.getKey().get(1)) {
						skip = true;
						break;
					}
				}
				if (!(new ItemStack(recipe.getKey().get(1), 1, recipe.getKey().get(1)).getItem() instanceof ItemFood) && !skip) {
					arecipes.add(new CachedFurnaceRecipe(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), recipe.getValue()));
				}
			}
			for (Alloy recipe : SpecialFurnaceRecipes.alloys) {
				arecipes.add(new CachedFurnaceAlloyRecipe(recipe));
			}
			for (Item item : Item.itemsList) {
				if (item != null && item instanceof ItemFood) {
					arecipes.add(new CachedFurnaceRecipe(new ItemStack(item), new ItemStack(Item.coal, 1, 1)));
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		HashMap<Integer, ItemStack> recipes = (HashMap<Integer, ItemStack>) FurnaceRecipes.smelting().getSmeltingList();
		HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) FurnaceRecipes.smelting()
				.getMetaSmeltingList();

		if (result != null && result.getItem() != null && result.getItem() instanceof ItemFood) {
			return;
		}
		for (Entry<Integer, SpecialSmelt> recipe : SpecialFurnaceRecipes.specials.entrySet()) {
			ItemStack item = recipe.getValue().result;
			int lvl = recipe.getValue().lvl;
			if (NEIServerUtils.areStacksSameTypeCrafting(item, result)) {
				arecipes.add(new CachedFurnaceRecipe(new ItemStack(recipe.getKey(), 1, -1), item, lvl));
			}
		}
		for (Entry<List<Integer>, SpecialSmelt> recipe : SpecialFurnaceRecipes.specialsMeta.entrySet()) {
			ItemStack item = recipe.getValue().result;
			int lvl = recipe.getValue().lvl;
			if (NEIServerUtils.areStacksSameTypeCrafting(item, result)) {
				arecipes.add(new CachedFurnaceRecipe(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), item, lvl));
			}
		}
		for (Entry<Integer, ItemStack> recipe : recipes.entrySet()) {
			ItemStack item = recipe.getValue();
			boolean skip = false;
			for (Integer id : SpecialFurnaceRecipes.specials.keySet()) {
				if (id == recipe.getKey()) {
					skip = true;
					break;
				}
			}
			if (!skip) {
				for (List<Integer> metaId : SpecialFurnaceRecipes.specialsMeta.keySet()) {
					if (metaId.get(0) == recipe.getKey() && metaId.get(1) == 0) {
						skip = true;
						break;
					}
				}	
			}
			if (!skip) {
				if (!(new ItemStack(recipe.getKey(), 1, 0).getItem() instanceof ItemFood) && NEIServerUtils.areStacksSameTypeCrafting(item, result)) {
					arecipes.add(new CachedFurnaceRecipe(new ItemStack(recipe.getKey(), 1, -1), item));
				}
			}
		}
		if (metarecipes == null)
			return;
		for (Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet()) {
			ItemStack item = recipe.getValue();
			boolean skip = false;
			for (List<Integer> metaId : SpecialFurnaceRecipes.specialsMeta.keySet()) {
				if (metaId.get(0) == recipe.getKey().get(0) && metaId.get(1) == recipe.getKey().get(1)) {
					skip = true;
					break;
				}
			}
			if (recipe.getKey().get(1) == 0 && !skip) {
				for (Integer id : SpecialFurnaceRecipes.specials.keySet()) {
					if (id == recipe.getKey().get(0)) {
						skip = true;
						break;
					}
				}
			}
			if (!(new ItemStack(recipe.getKey().get(1), 1, recipe.getKey().get(1)).getItem() instanceof ItemFood) && !skip && NEIServerUtils.areStacksSameTypeCrafting(item, result)) {
				arecipes.add(new CachedFurnaceRecipe(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), item));
			}
		}
		for (Alloy recipe : SpecialFurnaceRecipes.alloys) {
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result)) {
				arecipes.add(new CachedFurnaceAlloyRecipe(recipe));
			}
		}
		if (result.getItem() == Item.coal && result.getItemDamage() == 1) {
			for (Item item : Item.itemsList) {
				if (item != null && item instanceof ItemFood) {
					arecipes.add(new CachedFurnaceRecipe(new ItemStack(item), new ItemStack(Item.coal, 1, 1)));
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
		
		ItemStack in = ingredient.copy();
		in.stackSize = 1;
		ItemStack out = new TileEntityFurnaceMF(1).getResult(in);
		if (out != null) {
			arecipes.add(new CachedFurnaceRecipe(in, out, 0));
		} else {
			out = new TileEntityFurnaceMF(2).getResult(in);
			if (out != null) {
				arecipes.add(new CachedFurnaceRecipe(in, out, 1));
			} else {
				out = new TileEntityFurnaceMF(3).getResult(in);
				if (out != null) {
					arecipes.add(new CachedFurnaceRecipe(in, out, 2));
				} else {
					out = new TileEntityFurnaceMF(4).getResult(in);
					if (out != null) {
						arecipes.add(new CachedFurnaceRecipe(in, out, 3));
					}
				}
			}
		}
		for (Alloy recipe : SpecialFurnaceRecipes.alloys) {
			for (Object s : recipe.getIngredients()) {
				if (s != null && s instanceof ItemStack) {
					ItemStack stack = (ItemStack) s;
					if (NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
						arecipes.add(new CachedFurnaceAlloyRecipe(recipe));
						break;
					}
				}
			}
		}
	}
	
	public static final PositionedStack bronze = new PositionedStack(new ItemStack(BlockListMF.furnace, 1, 1), 73, 46);
	public static final PositionedStack iron = new PositionedStack(new ItemStack(BlockListMF.furnace, 1, 2), 73, 46);
	public static final PositionedStack steel = new PositionedStack(new ItemStack(BlockListMF.furnace, 1, 3), 73, 46);
	public static final PositionedStack deepIron = new PositionedStack(new ItemStack(BlockListMF.furnace, 1, 4), 73, 46);

	public class CachedFurnaceRecipe extends CachedRecipe {

		public PositionedStack input, output;
		public int level = -1;

		public CachedFurnaceRecipe(ItemStack in, ItemStack out) {
			ItemStack i = in.copy();
			i.stackSize = 1;
			this.input = new PositionedStack(i, 29, 15);
			this.output = new PositionedStack(out, 99, 15);
		}
		
		public CachedFurnaceRecipe(ItemStack in, ItemStack out, int level) {
			ItemStack i = in.copy();
			i.stackSize = 1;
			this.input = new PositionedStack(i, 29, 15);
			this.output = new PositionedStack(out, 99, 15);
			this.level = level;
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			if (input != null) {
				stacks.add(input);
			}
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			if (level == 0) {
				stacks.add(bronze);
			} else if (level == 1) {
				stacks.add(iron);
			} else if (level == 2) {
				stacks.add(steel);
			} else if (level == 3) {
				stacks.add(deepIron);
			}
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}

	}
	
	public class CachedFurnaceAlloyRecipe extends CachedRecipe {

		public PositionedStack output;
		public List<PositionedStack> inputs;
		public int level;
		
		public CachedFurnaceAlloyRecipe(Alloy alloy) {
			inputs = new ArrayList<PositionedStack>();
			
			int i = 0;
			for (Object s : alloy.getIngredients()) {
				int x = i % 2;
				int y = i / 2;
				
				if (s != null && s instanceof ItemStack) {
					this.inputs.add(new PositionedStack((ItemStack) s, 28 + (x * 18) + 1, 14 + (y * 18) + 1));
				}
				
				i++;
			}
			
			this.output = new PositionedStack(alloy.getRecipeOutput(), 99, 15);
			this.level = alloy.getLevel();
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>(this.inputs);
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			if (level == 0) {
				stacks.add(bronze);
			} else if (level == 1) {
				stacks.add(iron);
			} else if (level == 2) {
				stacks.add(steel);
			} else if (level == 3) {
				stacks.add(deepIron);
			}
			
			return getCycledIngredients(cycleticks / 20, stacks);
		}

	}

}
