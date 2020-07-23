package minefantasy.system.villagegen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.ComponentVillage;
import net.minecraft.world.gen.structure.ComponentVillageChurch;
import net.minecraft.world.gen.structure.ComponentVillageField;
import net.minecraft.world.gen.structure.ComponentVillageField2;
import net.minecraft.world.gen.structure.ComponentVillageHall;
import net.minecraft.world.gen.structure.ComponentVillageHouse1;
import net.minecraft.world.gen.structure.ComponentVillageHouse2;
import net.minecraft.world.gen.structure.ComponentVillageHouse3;
import net.minecraft.world.gen.structure.ComponentVillageHouse4_Garden;
import net.minecraft.world.gen.structure.ComponentVillagePathGen;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.ComponentVillageTorch;
import net.minecraft.world.gen.structure.ComponentVillageWell;
import net.minecraft.world.gen.structure.ComponentVillageWoodHut;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieceWeight;

public class StructureVillageMFPieces {

	public static List<StructureVillagePieceWeight> extraStructures = new ArrayList<StructureVillagePieceWeight>();
	
	public static void func_143016_a() {
        // MapGenStructureIO.func_143031_a(ComponentVillageHouse1.class, "ViBH");
        MapGenStructureIO.func_143031_a(ComponentVillageMFField1.class, "ViField1_MF");
        // MapGenStructureIO.func_143031_a(ComponentVillageField2.class, "ViF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFTorch.class, "ViTorch_MF");
        // MapGenStructureIO.func_143031_a(ComponentVillageHall.class, "ViPH");
        // MapGenStructureIO.func_143031_a(ComponentVillageHouse4_Garden.class, "ViSH");
        // MapGenStructureIO.func_143031_a(ComponentVillageWoodHut.class, "ViSmH");
        MapGenStructureIO.func_143031_a(ComponentVillageMFChurch.class, "ViChurch_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFSmithy.class, "ViSmithy_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFStartPiece.class, "ViStart_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFPathGen.class, "ViPath_MF");
        // MapGenStructureIO.func_143031_a(ComponentVillageHouse3.class, "ViTRH");
        // MapGenStructureIO.func_143031_a(ComponentVillageWell.class, "ViW");
        MapGenStructureIO.func_143031_a(ComponentVillageMFHovel1.class, "ViHovel1_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFHovel2.class, "ViHovel2_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFHouse1.class, "ViHouse1_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFHouse2.class, "ViHouse2_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFGranary.class, "ViGranary_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFGarden1.class, "ViGarden1_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFGarden2.class, "ViGarden2_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFGarden3.class, "ViGarden3_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFPen1.class, "ViPen1_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFWindmill.class, "ViWindmill_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFBakery.class, "ViBakery_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFButcher.class, "ViButcher_MF");
        MapGenStructureIO.func_143031_a(ComponentVillageMFTailor.class, "ViTailor_MF");
    }
	
	public static List getStructureVillageWeightedPieceList(Random par0Random, int par1) {
		ArrayList arraylist = new ArrayList();
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFHovel1.class, 10,
				MathHelper.getRandomIntegerInRange(par0Random, 3 + par1, 6 + par1 * 2)));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFHovel2.class, 10,
				MathHelper.getRandomIntegerInRange(par0Random, 3 + par1, 6 + par1 * 2)));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFHouse1.class, 15,
				MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + (int) (par1 * 1.5))));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFHouse2.class, 15,
				MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + (int) (par1 * 1.5))));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFSmithy.class, 20, 1 + par1));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFGranary.class, 10, 1 + par1));
		// arraylist.add(new StructureVillagePieceWeight(ComponentVillageHouse4_Garden.class, 4,
		// 		MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFChurch.class, 20, 1 + par1));
		// arraylist.add(new StructureVillagePieceWeight(ComponentVillageHouse1.class, 20,
		// 		MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
		// arraylist.add(new StructureVillagePieceWeight(ComponentVillageHall.class, 15,
		// 		MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFGarden1.class, 5,
				MathHelper.getRandomIntegerInRange(par0Random, 1 + par1, 2 + par1)));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFGarden2.class, 5,
				MathHelper.getRandomIntegerInRange(par0Random, 1 + par1, 2 + par1)));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFGarden3.class, 15,
				MathHelper.getRandomIntegerInRange(par0Random, 1 + par1, 2 + par1)));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFField1.class, 3,
				MathHelper.getRandomIntegerInRange(par0Random, 1 + par1, 3 + par1 * 2)));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFPen1.class, 5,
				MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 3 + par1 * 2)));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFWindmill.class, 5, 1 + par1));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFBakery.class, 20, 1 + par1));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFButcher.class, 20, 1 + par1));
		arraylist.add(new StructureVillagePieceWeight(ComponentVillageMFTailor.class, 20, 1 + par1));
		// VillagerRegistry.addExtraVillageComponents(arraylist, par0Random, par1);
		arraylist.addAll(extraStructures);
		
		Iterator iterator = arraylist.iterator();

		while (iterator.hasNext()) {
			if (((StructureVillagePieceWeight) iterator.next()).villagePiecesLimit == 0) {
				iterator.remove();
			}
		}

		return arraylist;
	}

	private static int func_75079_a(List par0List) {
		boolean flag = false;
		int i = 0;
		StructureVillagePieceWeight structurevillagepieceweight;

		for (Iterator iterator = par0List.iterator(); iterator
				.hasNext(); i += structurevillagepieceweight.villagePieceWeight) {
			structurevillagepieceweight = (StructureVillagePieceWeight) iterator.next();

			if (structurevillagepieceweight.villagePiecesLimit > 0
					&& structurevillagepieceweight.villagePiecesSpawned < structurevillagepieceweight.villagePiecesLimit) {
				flag = true;
			}
		}

		return flag ? i : -1;
	}

	private static ComponentVillageMF func_75083_a(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			StructureVillagePieceWeight par1StructureVillagePieceWeight, List par2List, Random par3Random, int par4,
			int par5, int par6, int par7, int par8) {
		Class oclass = par1StructureVillagePieceWeight.villagePieceClass;
		Object object = null;

		if (oclass == ComponentVillageMFHovel1.class) {
			object = ComponentVillageMFHovel1.create(par0ComponentVillageStartPiece, par2List, par3Random,
					par4, par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFHovel2.class) {
			object = ComponentVillageMFHovel2.create(par0ComponentVillageStartPiece, par2List, par3Random,
					par4, par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFHouse1.class) {
			object = ComponentVillageMFHouse1.create(par0ComponentVillageStartPiece, par2List, par3Random,
					par4, par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFHouse2.class) {
			object = ComponentVillageMFHouse2.create(par0ComponentVillageStartPiece, par2List, par3Random,
					par4, par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFChurch.class) {
			object = ComponentVillageMFChurch.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFSmithy.class) {
			object = ComponentVillageMFSmithy.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFGranary.class) {
			object = ComponentVillageMFGranary.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFGarden1.class) {
			object = ComponentVillageMFGarden1.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFGarden2.class) {
			object = ComponentVillageMFGarden2.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFGarden3.class) {
			object = ComponentVillageMFGarden3.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFField1.class) {
			object = ComponentVillageMFField1.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFPen1.class) {
			object = ComponentVillageMFPen1.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFWindmill.class) {
			object = ComponentVillageMFWindmill.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFBakery.class) {
			object = ComponentVillageMFBakery.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFButcher.class) {
			object = ComponentVillageMFButcher.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else if (oclass == ComponentVillageMFTailor.class) {
			object = ComponentVillageMFTailor.create(par0ComponentVillageStartPiece, par2List, par3Random, par4,
					par5, par6, par7, par8);
		} else {
			for (IComponentInstantiationHandler handler : componentInstantiationHandlers) {
				ComponentVillageMF component = handler.instantiate(oclass);
				if (component != null) {
					return component;
				}
			}

			return null;
		}

		return (ComponentVillageMF) object;
	}
	
	public static List<IComponentInstantiationHandler> componentInstantiationHandlers = new ArrayList<IComponentInstantiationHandler>();
	
	public static interface IComponentInstantiationHandler {
		
		public ComponentVillageMF instantiate(Class<ComponentVillageMF> componentClass);
		
	}

	/**
	 * attempts to find a next Village Component to be spawned
	 */
	private static ComponentVillage getNextVillageComponent(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		int j1 = func_75079_a(par0ComponentVillageStartPiece.structureVillageWeightedPieceList);

		if (j1 <= 0) {
			return null;
		} else {
			
			if (par2Random.nextInt(4) == 0) {
				StructureBoundingBox structureboundingbox = ComponentVillageMFTorch
						.func_74904_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);

				if (structureboundingbox != null) {
					return new ComponentVillageMFTorch(par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox,
							par6);
				}
			}
			
			int k1 = 0;

			while (k1 < 5) {
				++k1;
				int l1 = par2Random.nextInt(j1);
				Iterator iterator = par0ComponentVillageStartPiece.structureVillageWeightedPieceList.iterator();

				while (iterator.hasNext()) {
					StructureVillagePieceWeight structurevillagepieceweight = (StructureVillagePieceWeight) iterator
							.next();
					l1 -= structurevillagepieceweight.villagePieceWeight;

					if (l1 < 0) {
						if (!structurevillagepieceweight.canSpawnMoreVillagePiecesOfType(par7)
								|| structurevillagepieceweight == par0ComponentVillageStartPiece.structVillagePieceWeight
										&& par0ComponentVillageStartPiece.structureVillageWeightedPieceList
												.size() > 1) {
							break;
						}

						ComponentVillageMF componentvillage = func_75083_a(par0ComponentVillageStartPiece,
								structurevillagepieceweight, par1List, par2Random, par3, par4, par5, par6, par7);

						if (componentvillage != null) {
							++structurevillagepieceweight.villagePiecesSpawned;
							par0ComponentVillageStartPiece.structVillagePieceWeight = structurevillagepieceweight;

							if (!structurevillagepieceweight.canSpawnMoreVillagePieces()) {
								par0ComponentVillageStartPiece.structureVillageWeightedPieceList
										.remove(structurevillagepieceweight);
							}

							return componentvillage;
						}
					}
				}
			}

			StructureBoundingBox structureboundingbox = ComponentVillageMFTorch
					.func_74904_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);

			if (structureboundingbox != null && par2Random.nextBoolean()) {
				return new ComponentVillageMFTorch(par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox,
						par6);
			} else {
				return null;
			}
		}
	}

	/**
	 * attempts to find a next Structure Component to be spawned, private
	 * Village function
	 */
	private static StructureComponent getNextVillageStructureComponent(
			ComponentVillageMFStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3,
			int par4, int par5, int par6, int par7) {
		if (par7 > 100) {
			return null;
		} else if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= 112
				&& Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112) {
			ComponentVillage componentvillage = getNextVillageComponent(par0ComponentVillageStartPiece, par1List,
					par2Random, par3, par4, par5, par6, par7 + 1);

			if (componentvillage != null) {
				int j1 = (componentvillage.getBoundingBox().minX + componentvillage.getBoundingBox().maxX) / 2;
				int k1 = (componentvillage.getBoundingBox().minZ + componentvillage.getBoundingBox().maxZ) / 2;
				int l1 = componentvillage.getBoundingBox().maxX - componentvillage.getBoundingBox().minX;
				int i2 = componentvillage.getBoundingBox().maxZ - componentvillage.getBoundingBox().minZ;
				int j2 = l1 > i2 ? l1 : i2;

				if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(j1, k1, j2 / 2 + 4,
						MapGenVillageMF.villageMFSpreadBiomes)) {
					par1List.add(componentvillage);
					par0ComponentVillageStartPiece.field_74932_i.add(componentvillage);
					return componentvillage;
				}
			}

			return null;
		} else {
			return null;
		}
	}

	private static StructureComponent getNextComponentVillagePath(
			ComponentVillageMFStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3,
			int par4, int par5, int par6, int par7) {
		if (par7 > 4 + par0ComponentVillageStartPiece.terrainType) {
			return null;
		} else if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= (112 * 1.5)
				&& Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= (112 * 1.5)) {
			StructureBoundingBox structureboundingbox = ComponentVillageMFPathGen
					.func_74933_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7);

			if (structureboundingbox != null && structureboundingbox.minY > 10) {
				ComponentVillageMFPathGen componentvillagepathgen = new ComponentVillageMFPathGen(
						par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox, par6);
				int j1 = (componentvillagepathgen.getBoundingBox().minX + componentvillagepathgen.getBoundingBox().maxX)
						/ 2;
				int k1 = (componentvillagepathgen.getBoundingBox().minZ + componentvillagepathgen.getBoundingBox().maxZ)
						/ 2;
				int l1 = componentvillagepathgen.getBoundingBox().maxX - componentvillagepathgen.getBoundingBox().minX;
				int i2 = componentvillagepathgen.getBoundingBox().maxZ - componentvillagepathgen.getBoundingBox().minZ;
				int j2 = l1 > i2 ? l1 : i2;

				if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(j1, k1, j2 / 2 + 4,
						MapGenVillageMF.villageMFSpreadBiomes)) {
					par1List.add(componentvillagepathgen);
					par0ComponentVillageStartPiece.field_74930_j.add(componentvillagepathgen);
					return componentvillagepathgen;
				}
			}

			return null;
		} else {
			return null;
		}
	}

	/**
	 * attempts to find a next Structure Component to be spawned
	 */
	static StructureComponent getNextStructureComponent(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		return getNextVillageStructureComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5,
				par6, par7);
	}

	static StructureComponent getNextStructureComponentVillagePath(
			ComponentVillageMFStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3,
			int par4, int par5, int par6, int par7) {
		return getNextComponentVillagePath(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6,
				par7);
	}

}
