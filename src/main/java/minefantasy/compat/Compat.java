package minefantasy.compat;

import minefantasy.compat.minetweaker.Anvil;
import minefantasy.compat.minetweaker.BlastFurnace;
import minefantasy.compat.minetweaker.Bloomery;
import minefantasy.compat.minetweaker.Crucible;
import minefantasy.compat.minetweaker.Forging;
import minefantasy.compat.minetweaker.Furnace;
import minefantasy.compat.minetweaker.MineTweakerHelper;
import minefantasy.compat.minetweaker.Misc;
import minefantasy.compat.minetweaker.Tailor;
import minetweaker.MineTweakerAPI;

public class Compat {

	public static void initMineTweakerCompat() {
		MineTweakerAPI.registerClass(Anvil.class);
		MineTweakerAPI.registerClass(Tailor.class);
		MineTweakerAPI.registerClass(Forging.class);
		MineTweakerAPI.registerClass(Furnace.class);
		MineTweakerAPI.registerClass(BlastFurnace.class);
		MineTweakerAPI.registerClass(Crucible.class);
		MineTweakerAPI.registerClass(Bloomery.class);
		MineTweakerAPI.registerClass(Misc.class);
	}
	
}
