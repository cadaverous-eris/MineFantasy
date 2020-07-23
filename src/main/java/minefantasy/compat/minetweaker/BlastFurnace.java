package minefantasy.compat.minetweaker;

import minefantasy.api.forge.ItemHandler;
import minefantasy.api.refine.BlastFurnaceFuel;
import minefantasy.api.refine.BlastRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.BlastFurnace")
public class BlastFurnace {
	
	@ZenMethod
	public static void addBlastRecipe(IItemStack output, IItemStack input) {
		ItemStack in = MineTweakerHelper.toStack(input);
		ItemStack out = MineTweakerHelper.toStack(output);
		
		MineTweakerAPI.apply(new AddBlastRecipe(new BlastRecipes(in, out)));
	}
	
	private static class AddBlastRecipe implements IUndoableAction {
		private final BlastRecipes recipe;

		public AddBlastRecipe(BlastRecipes recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			BlastRecipes.recipeList.add(this.recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BlastRecipes.recipeList.remove(this.recipe);
		}

		@Override
		public String describe() {
			return "Adding Blast Furnace Recipe for Item " + recipe.result.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Blast Furnace Recipe for Item " + recipe.result.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	@ZenMethod
	public static void addBlastFuel(IItemStack item, float amount) {
		ItemStack stack = MineTweakerHelper.toStack(item);
		
		MineTweakerAPI.apply(new AddBlastFuel(new BlastFurnaceFuel(stack, amount)));
	}
	
	private static class AddBlastFuel implements IUndoableAction {
		private final BlastFurnaceFuel item;

		public AddBlastFuel(BlastFurnaceFuel fuel) {
			this.item = fuel;
		}

		@Override
		public void apply() {
			ItemHandler.BlastFuel.add(item);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			ItemHandler.BlastFuel.remove(item);
		}

		@Override
		public String describe() {
			return "Adding Blast Furnace Fuel Item " + item.fuel.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Blast Furnace Fuel Item " + item.fuel.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

}
