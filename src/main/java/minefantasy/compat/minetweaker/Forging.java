package minefantasy.compat.minetweaker;

import java.util.Arrays;

import minefantasy.api.forge.ForgeFuel;
import minefantasy.api.forge.HeatableItem;
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.refine.BlastFurnaceFuel;
import minefantasy.api.refine.SpecialFurnaceRecipes;
import minefantasy.api.refine.SpecialFurnaceRecipes.SpecialSmelt;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Forging")
public class Forging {
	
	@ZenMethod
	public static void addHeatable(IItemStack item, int workable, int unstable, int lost) {
		ItemStack stack = MineTweakerHelper.toStack(item);
		
		MineTweakerAPI.apply(new AddHotItem(new HeatableItem(stack, workable, unstable, lost)));
	}
	
	private static class AddHotItem implements IUndoableAction {
		private final HeatableItem item;

		public AddHotItem(HeatableItem item) {
			this.item = item;
		}

		@Override
		public void apply() {
			HeatableItem.items.add(item);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			HeatableItem.items.remove(item);
		}

		@Override
		public String describe() {
			return "Adding Heatable Item " + item.getItem().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Heatable Item " + item.getItem().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	@ZenMethod
	public static void addFlux(IItemStack item) {
		ItemStack stack = MineTweakerHelper.toStack(item);
		
		MineTweakerAPI.apply(new AddFlux(stack));
	}
	
	private static class AddFlux implements IUndoableAction {
		private final ItemStack item;

		public AddFlux(ItemStack stack) {
			this.item = stack;
		}

		@Override
		public void apply() {
			ItemHandler.flux.add(item);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			ItemHandler.flux.remove(item);
		}

		@Override
		public String describe() {
			return "Adding Flux Item " + item.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Flux Item " + item.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	@ZenMethod
	public static void addCarbon(IItemStack item) {
		ItemStack stack = MineTweakerHelper.toStack(item);
		
		MineTweakerAPI.apply(new AddCarbon(stack));
	}
	
	private static class AddCarbon implements IUndoableAction {
		private final ItemStack item;

		public AddCarbon(ItemStack stack) {
			this.item = stack;
		}

		@Override
		public void apply() {
			ItemHandler.carbon.add(item);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			ItemHandler.carbon.remove(item);
		}

		@Override
		public String describe() {
			return "Adding Carbon Item " + item.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Carbon Item " + item.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	@ZenMethod
	public static void addForgeFuel(IItemStack item, int duration, int heat, boolean light) {
		ItemStack stack = MineTweakerHelper.toStack(item);
		
		MineTweakerAPI.apply(new AddForgeFuel(new ForgeFuel(stack, duration, heat, light)));
	}
	
	@ZenMethod
	public static void addForgeFuel(IItemStack item, int duration, int heat) {
		ItemStack stack = MineTweakerHelper.toStack(item);
		
		MineTweakerAPI.apply(new AddForgeFuel(new ForgeFuel(stack, duration, heat, false)));
	}
	
	private static class AddForgeFuel implements IUndoableAction {
		private final ForgeFuel item;

		public AddForgeFuel(ForgeFuel fuel) {
			this.item = fuel;
		}

		@Override
		public void apply() {
			ItemHandler.forgeFuel.add(item);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			ItemHandler.forgeFuel.remove(item);
		}

		@Override
		public String describe() {
			return "Adding Forge Fuel for Item " + item.fuel.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Forge Fuel for Item " + item.fuel.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

}
