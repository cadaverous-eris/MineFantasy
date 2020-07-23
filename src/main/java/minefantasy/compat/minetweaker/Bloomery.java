package minefantasy.compat.minetweaker;

import java.util.Arrays;

import minefantasy.api.refine.BloomRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Bloomery")
public class Bloomery {
	
	@ZenMethod
	public static void addBloomeryRecipe(IItemStack output, IItemStack input, int time) {
		ItemStack in = MineTweakerHelper.toStack(input);
		ItemStack out = MineTweakerHelper.toStack(output);
		
		MineTweakerAPI.apply(new AddBloom(new BloomRecipe(in, out, time)));
	}
	
	@ZenMethod
	public static void addBloomeryRecipe(IItemStack output, IItemStack input) {
		ItemStack in = MineTweakerHelper.toStack(input);
		ItemStack out = MineTweakerHelper.toStack(output);
		
		MineTweakerAPI.apply(new AddBloom(new BloomRecipe(in, out)));
	}
	
	private static class AddBloom implements IUndoableAction {
		private final BloomRecipe recipe;
		
		public AddBloom(BloomRecipe recipe) {
			this.recipe = recipe;
		}
		
		@Override
		public void apply() {
			BloomRecipe.recipeList.add(this.recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BloomRecipe.recipeList.remove(this.recipe);
		}

		@Override
		public String describe() {
			return "Adding Bloomery Recipe for Item " + recipe.result.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Bloomery Recipe for Item " + recipe.result.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
		
	}

}
