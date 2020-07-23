package minefantasy.compat.nei;

import java.util.ArrayList;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import minefantasy.block.BlockListMF;
import minefantasy.client.gui.GuiBloomery;
import minefantasy.client.gui.GuiFurnaceMF;
import minefantasy.client.gui.GuiTailor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class NEIMineFantasyConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		
		API.registerRecipeHandler(new AnvilRecipeHandler());
	    API.registerUsageHandler(new AnvilRecipeHandler());
	    API.registerRecipeHandler(new AnvilSmallRecipeHandler());
	    API.registerUsageHandler(new AnvilSmallRecipeHandler());
	    
	    API.registerRecipeHandler(new BigFurnaceRecipeHandler());
	    API.registerUsageHandler(new BigFurnaceRecipeHandler());
	    
	    API.registerRecipeHandler(new BloomeryRecipeHandler());
	    API.registerUsageHandler(new BloomeryRecipeHandler());
	    
	    API.registerRecipeHandler(new CrucibleRecipeHandler());
	    API.registerUsageHandler(new CrucibleRecipeHandler());
	    
	    API.registerRecipeHandler(new BlastFurnaceRecipeHandler());
	    API.registerUsageHandler(new BlastFurnaceRecipeHandler());
	    
	    API.registerRecipeHandler(new SpinningRecipeHandler());
	    API.registerUsageHandler(new SpinningRecipeHandler());
	    
	    API.registerRecipeHandler(new TailorRecipeHandler());
	    API.registerUsageHandler(new TailorRecipeHandler());
	    
	    API.registerRecipeHandler(new CounterRecipeHandler());
	    API.registerUsageHandler(new CounterRecipeHandler());
	    
	    API.registerRecipeHandler(new TanningRecipeHandler());
	    API.registerUsageHandler(new TanningRecipeHandler());
	    
	    // fix missing cobble brick forge & trip hammer crank in item list
	    API.addNBTItem(new ItemStack(BlockListMF.forge, 1, 0));
	    API.addNBTItem(new ItemStack(BlockListMF.forge, 1, 1));
	    API.addNBTItem(new ItemStack(BlockListMF.tripHammer, 1, 0));
	    API.addNBTItem(new ItemStack(BlockListMF.tripHammer, 1, 1));
	}

	@Override
	public String getName() {
		return "MineFantasy NEI Addon";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
	
	private static ArrayList<int[]> createSimpleDamageRange(int min, int max) {
		ArrayList<int[]> rangeList = new ArrayList<int[]>();
		rangeList.add(new int[] { min, max });
		rangeList.trimToSize();
		return rangeList;
	}
	private static ArrayList<int[]> createSimpleDamageRange(int max) {
		return createSimpleDamageRange(0, max);
	}

}
