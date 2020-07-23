package minefantasy.compat.minetweaker;

import java.util.Arrays;

import minefantasy.api.tanner.TanningRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Misc")
public class Misc {
	
	@ZenMethod
	public static void addTanningRecipe(IItemStack output, IItemStack input) {
		ItemStack in = MineTweakerHelper.toStack(input);
		ItemStack out = MineTweakerHelper.toStack(output);
		
		MineTweakerAPI.apply(new AddTanning(in, out));
	}
	
	private static class AddTanning implements IUndoableAction {
		
		ItemStack in, out;
		
		public AddTanning(ItemStack in, ItemStack out) {
			this.in = in;
			this.out = out;
		}
		
		@Override
		public void apply() {
			if (in.getItemDamage() == 0) {
				TanningRecipes.instance().tanningList.put(in.itemID, out);
			} else {
				TanningRecipes.instance().metaTanningList.put(Arrays.asList(in.itemID, in.getItemDamage()), out);
			}
		}
		
		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (in.getItemDamage() == 0) {
				TanningRecipes.instance().tanningList.remove(in.itemID);
			} else {
				TanningRecipes.instance().metaTanningList.remove(Arrays.asList(in.itemID, in.getItemDamage()));
			}
		}
		
		@Override
		public String describe() {
			return "Adding Tanning Recipe for Item " + out.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Tanning Recipe for Item " + out.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
		
	}

}
