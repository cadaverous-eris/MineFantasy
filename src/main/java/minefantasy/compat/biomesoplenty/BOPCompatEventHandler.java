package minefantasy.compat.biomesoplenty;

import biomesoplenty.worldgen.structure.BOPMapGenVillage;
import minefantasy.MineFantasyBase;
import minefantasy.system.villagegen.MapGenVillageMF;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.InitMapGenEvent;

public class BOPCompatEventHandler {
	
	@ForgeSubscribe(priority=EventPriority.LOWEST)
	public void initMapGen(InitMapGenEvent event) {
		if (event.type == InitMapGenEvent.EventType.VILLAGE && event.originalGen instanceof BOPMapGenVillage) {
			event.newGen = new BOPMapGenVillageMF();
		}
	}

}
