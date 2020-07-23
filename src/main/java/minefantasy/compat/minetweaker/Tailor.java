package minefantasy.compat.minetweaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import minefantasy.api.anvil.CraftingManagerAnvil;
import minefantasy.api.anvil.IAnvilRecipe;
import minefantasy.api.anvil.ShapedAnvilRecipes;
import minefantasy.api.anvil.ShapelessAnvilRecipes;
import minefantasy.api.tailor.CraftingManagerTailor;
import minefantasy.api.tailor.ITailorRecipe;
import minefantasy.api.tailor.ShapedTailorRecipes;
import minefantasy.api.tailor.StringList;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Tailor")
public class Tailor {
	
	@ZenMethod
	public static void addShapedRecipe(IItemStack output, IItemStack[][] input, int stitches, float time, int needle, int string) {
		if (input.length < 1 || input[0].length < 1) {
			return;
		}
		ItemStack[] inputs = new ItemStack[input[0].length * input.length];
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length; j++) {
				int k = (input[0].length * i) + j;
				inputs[k] = MineTweakerHelper.toStack(input[i][j]);
			}
		}
		
		ITailorRecipe r = new ShapedTailorRecipes(input[0].length, input.length, inputs, MineTweakerHelper.toStack(output), needle, stitches, time, string);
		MineTweakerAPI.apply(new AddTailor(r));
	}
	
	@ZenMethod
	public static void addShapedRecipe(IItemStack output, IItemStack[][] input, int stitches, float time, int string) {
		if (input.length < 1 || input[0].length < 1) {
			return;
		}
		ItemStack[] inputs = new ItemStack[input[0].length * input.length];
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length; j++) {
				int k = (input[0].length * i) + j;
				inputs[k] = MineTweakerHelper.toStack(input[i][j]);
			}
		}
		
		ITailorRecipe r = new ShapedTailorRecipes(input[0].length, input.length, inputs, MineTweakerHelper.toStack(output), 1, stitches, time, string);
		MineTweakerAPI.apply(new AddTailor(r));
	}
	
	@ZenMethod
	public static void addShapedRecipe(IItemStack output, IItemStack[][] input, int stitches, float time) {
		if (input.length < 1 || input[0].length < 1) {
			return;
		}
		ItemStack[] inputs = new ItemStack[input[0].length * input.length];
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length; j++) {
				int k = (input[0].length * i) + j;
				inputs[k] = MineTweakerHelper.toStack(input[i][j]);
			}
		}
		
		ITailorRecipe r = new ShapedTailorRecipes(input[0].length, input.length, inputs, MineTweakerHelper.toStack(output), 1, stitches, time, 1);
		MineTweakerAPI.apply(new AddTailor(r));
	}
	
	private static class AddTailor implements IUndoableAction {
		private final ITailorRecipe recipe;

		public AddTailor(ITailorRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			CraftingManagerTailor.getInstance().recipes.add(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			CraftingManagerTailor.getInstance().recipes.remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding Tailor Recipe for Item " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Tailor Recipe for Item " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		if (MineTweakerHelper.toStack(output) != null)
			MineTweakerAPI.apply(new RemoveTailor(MineTweakerHelper.toStack(output)));
	}

	private static class RemoveTailor implements IUndoableAction {
		private final ItemStack output;
		List<ITailorRecipe> removedRecipes = new ArrayList<ITailorRecipe>();

		public RemoveTailor(ItemStack output) {
			this.output = output;
		}

		@Override
		public void apply() {
			Iterator<ITailorRecipe> it = CraftingManagerTailor.getInstance().recipes.iterator();
			while (it.hasNext()) {
				ITailorRecipe r = it.next();
				if (r != null && r.getRecipeOutput() != null && r.getRecipeOutput().isItemEqual(output)
						&& (r.getRecipeOutput().getItemDamage() == output.getItemDamage())) {
					removedRecipes.add(r);
					it.remove();
				}
			}
		}

		@Override
		public void undo() {
			if (removedRecipes != null) {
				for (ITailorRecipe recipe : removedRecipes) {
					if (recipe != null) {
						CraftingManagerTailor.getInstance().recipes.add(recipe);
					}
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Tailor Recipes for Item " + output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Re-Adding Tailor Recipes for Item " + output.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public boolean canUndo() {
			return true;
		}
	}
	
	@ZenMethod
	public static void addString(IItemStack item, int tier) {
		ItemStack string = MineTweakerHelper.toStack(item);
		
		MineTweakerAPI.apply(new AddString(string, tier));
	}
	
	private static class AddString implements IUndoableAction {
		private final ItemStack item;
		private final int tier;
		
		public AddString(ItemStack stack, int tier) {
			this.item = stack;
			this.tier = tier;
		}
		
		@Override
		public void apply() {
			if (item.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
				StringList.stringID.put(item.itemID, tier);
			} else {
				StringList.stringList.put(Arrays.asList(item.itemID, item.getItemDamage()), tier);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (item.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
				StringList.stringID.remove(item.itemID);
			} else {
				StringList.stringList.remove(Arrays.asList(item.itemID, item.getItemDamage()));
			}
		}

		@Override
		public String describe() {
			return "Adding Tailoring String for Item " + item.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Tailoring String for Item " + item.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
		
	}

}
