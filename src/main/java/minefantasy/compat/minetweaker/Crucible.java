package minefantasy.compat.minetweaker;

import java.util.Arrays;
import java.util.List;

import minefantasy.api.refine.Alloy;
import minefantasy.api.refine.AlloyRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Crucible")
public class Crucible {

	@ZenMethod
	public static void addAlloyRecipe(IItemStack output, IItemStack[] inputs, int level, int dupes) {
		ItemStack out = MineTweakerHelper.toStack(output);
		List<ItemStack> in = Arrays.asList(MineTweakerHelper.toStacks(inputs));
		
		if (dupes > 1) {
			for (int i = 2; i <= dupes; i++) {
				ItemStack outDuped = out.copy();
				outDuped.stackSize *= i;
				List<ItemStack> inDuped = AlloyRecipes.createDupeList(in, i);
				
				MineTweakerAPI.apply(new AddAlloy(new Alloy(outDuped, level, inDuped)));
			}
		}
		MineTweakerAPI.apply(new AddAlloy(new Alloy(out, level, in)));
	}
	
	@ZenMethod
	public static void addAlloyRecipe(IItemStack output, IItemStack[] inputs, int level) {
		addAlloyRecipe(output, inputs, level, 1);
	}
	
	private static class AddAlloy implements IUndoableAction {
		private final Alloy alloy;
		
		public AddAlloy(Alloy alloy) {
			this.alloy = alloy;
		}
		
		@Override
		public void apply() {
			AlloyRecipes.alloys.add(this.alloy);
		}
		
		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			AlloyRecipes.alloys.remove(this.alloy);
		}
		
		@Override
		public String describe() {
			return "Adding Crucible Recipe for Item " + alloy.getRecipeOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Crucible Recipe for Item " + alloy.getRecipeOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
		
	}
	
}
