package minefantasy.api.forge;

import net.minecraft.item.ItemStack;

public class ForgeFuel {
	public ItemStack fuel;
	protected float duration;
	protected int baseHeat;
	protected boolean doesLight;
	
	public ForgeFuel(ItemStack item, float dura, int heat, boolean light)
	{
		fuel = item;
		duration = dura;
		baseHeat = heat;
		doesLight = light;
	}
}
