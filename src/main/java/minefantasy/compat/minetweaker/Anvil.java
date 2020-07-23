package minefantasy.compat.minetweaker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import minefantasy.api.anvil.CraftingManagerAnvil;
import minefantasy.api.anvil.IAnvil;
import minefantasy.api.anvil.IAnvilRecipe;
import minefantasy.api.anvil.ShapedAnvilRecipes;
import minefantasy.api.anvil.ShapelessAnvilRecipes;
import minefantasy.api.refine.CrushRecipe;
import minefantasy.api.refine.CrushRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Anvil")
public class Anvil {
	
	@ZenMethod
	public static void addShapedRecipe(IItemStack output, IItemStack[][] input, int time, int hammer, int anvil, float xp, boolean hot) {
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
		
		IAnvilRecipe r = new ShapedAnvilRecipes(input[0].length, input.length, inputs, MineTweakerHelper.toStack(output), time, hammer, anvil, xp, hot);
		MineTweakerAPI.apply(new Add(r));
	}
	
	@ZenMethod
	public static void addShapedRecipe(IItemStack output, IItemStack[][] input, int time, int hammer, int anvil, boolean hot) {
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
		
		IAnvilRecipe r = new ShapedAnvilRecipes(input[0].length, input.length, inputs, MineTweakerHelper.toStack(output), time, hammer, anvil, 0F, hot);
		MineTweakerAPI.apply(new Add(r));
	}
	
	@ZenMethod
	public static void addShapedRecipe(IItemStack output, IItemStack[][] input, int time, int anvil, boolean hot) {
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
		
		IAnvilRecipe r = new ShapedAnvilRecipes(input[0].length, input.length, inputs, MineTweakerHelper.toStack(output), time, 0, anvil, 0F, hot);
		MineTweakerAPI.apply(new Add(r));
	}
	
	@ZenMethod
	public static void addShapelessRecipe(IItemStack output, IItemStack[] input, int time, int hammer, int anvil, float xp, boolean hot) {
		if (input.length < 1) {
			return;
		}
		List inputs = new ArrayList<ItemStack>();
		for (int i = 0; i < input.length; i++) {
			inputs.add(MineTweakerHelper.toStack(input[i]));
		}
		
		IAnvilRecipe r = new ShapelessAnvilRecipes(MineTweakerHelper.toStack(output), xp, hammer, anvil, time, inputs, hot);
		MineTweakerAPI.apply(new Add(r));
	}
	
	private static class Add implements IUndoableAction {
		private final IAnvilRecipe recipe;

		public Add(IAnvilRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			CraftingManagerAnvil.getInstance().recipes.add(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			CraftingManagerAnvil.getInstance().recipes.remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding Anvil Recipe for Item " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Anvil Recipe for Item " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		if (MineTweakerHelper.toStack(output) != null)
			MineTweakerAPI.apply(new Remove(MineTweakerHelper.toStack(output)));
	}

	private static class Remove implements IUndoableAction {
		private final ItemStack output;
		List<IAnvilRecipe> removedRecipes = new ArrayList<IAnvilRecipe>();

		public Remove(ItemStack output) {
			this.output = output;
		}

		@Override
		public void apply() {
			Iterator<IAnvilRecipe> it = CraftingManagerAnvil.getInstance().recipes.iterator();
			while (it.hasNext()) {
				IAnvilRecipe r = it.next();
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
				for (IAnvilRecipe recipe : removedRecipes) {
					if (recipe != null) {
						CraftingManagerAnvil.getInstance().recipes.add(recipe);
					}
				}
			}
		}

		@Override
		public String describe() {
			return "Removing Anvil Recipes for Item " + output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Re-Adding Anvil Recipes for Item " + output.getDisplayName();
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
	public static void addCrushing(IItemStack output, IItemStack input) {
		ItemStack in = MineTweakerHelper.toStack(input);
		ItemStack out = MineTweakerHelper.toStack(output);
		
		MineTweakerAPI.apply(new AddCrushing(new CrushRecipe(in, out)));
	}
	
	private static class AddCrushing implements IUndoableAction {
		private final CrushRecipe recipe;

		public AddCrushing(CrushRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			CrushRecipes.recipeList.add(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			CrushRecipes.recipeList.remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding Crushing Recipe for Item " + recipe.output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Crushing Recipe for Item " + recipe.output.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

}
