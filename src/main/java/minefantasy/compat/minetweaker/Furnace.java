package minefantasy.compat.minetweaker;

import java.util.Arrays;
import java.util.List;

import minefantasy.api.refine.Alloy;
import minefantasy.api.refine.SpecialFurnaceRecipes;
import minefantasy.api.refine.SpecialFurnaceRecipes.SpecialSmelt;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Furnace")
public class Furnace {
	
	@ZenMethod
	public static void addSpecialFurnaceRecipe(IItemStack output, IItemStack input, int level) {
		ItemStack in = MineTweakerHelper.toStack(input);
		ItemStack out = MineTweakerHelper.toStack(output);
		int inId = in.itemID;
		int inMeta = in.getItemDamage();
		
		MineTweakerAPI.apply(new AddFurnaceSpecial(inId, inMeta, new SpecialSmelt(level, out)));
	}
	
	private static class AddFurnaceSpecial implements IUndoableAction {
		private final int inId, inMeta;
		private final SpecialSmelt output;

		public AddFurnaceSpecial(int inId, int inMeta, SpecialSmelt output) {
			this.inId = inId;
			this.inMeta = inMeta;
			this.output = output;
		}

		@Override
		public void apply() {
			if (this.inMeta == 32767) {
				SpecialFurnaceRecipes.specials.put(this.inId, this.output);
			} else {
				SpecialFurnaceRecipes.specialsMeta.put(Arrays.asList(this.inId, this.inMeta), output);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (this.inMeta == 32767) {
				SpecialFurnaceRecipes.specials.remove(this.inId);
			} else {
				SpecialFurnaceRecipes.specialsMeta.remove(Arrays.asList(this.inId, this.inMeta));
			}
		}

		@Override
		public String describe() {
			return "Adding Special Furnace Recipe for Item " + output.result.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Special Furnace Recipe for Item " + output.result.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	@ZenMethod
	public static void addFurnaceAlloy(IItemStack output, IItemStack[] inputs, int level) {
		ItemStack out = MineTweakerHelper.toStack(output);
		List<ItemStack> in = Arrays.asList(MineTweakerHelper.toStacks(inputs));
		
		MineTweakerAPI.apply(new AddFurnaceAlloy(new Alloy(out, level, in)));
	}
	
	private static class AddFurnaceAlloy implements IUndoableAction {
		private final Alloy alloy;

		public AddFurnaceAlloy(Alloy alloy) {
			this.alloy = alloy;
		}

		@Override
		public void apply() {
			SpecialFurnaceRecipes.alloys.add(this.alloy);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			SpecialFurnaceRecipes.alloys.remove(this.alloy);
		}

		@Override
		public String describe() {
			return "Adding Furnace Alloy Recipe for Item " + alloy.getRecipeOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Furnace Alloy Recipe for Item " + alloy.getRecipeOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

}
