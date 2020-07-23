package minefantasy.compat.biomesoplenty;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import biomesoplenty.configuration.BOPConfigurationTerrainGen;
import biomesoplenty.worldgen.structure.BOPMapGenVillage;
import minefantasy.system.villagegen.MapGenVillageMF;
import minefantasy.system.villagegen.StructureVillageMFStart;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BOPMapGenVillageMF extends BOPMapGenVillage {
	
	protected int maxDistance;
    protected int minDistance;
	
	public BOPMapGenVillageMF() {
		super();
		this.maxDistance = BOPConfigurationTerrainGen.villageDistance;
        this.minDistance = BOPConfigurationTerrainGen.villageDistance / 3;
	}
	/*static {
		System.out.println("\n\n\n\n\nSPAWN BIOMES");
		for (Object b : MapGenVillageMF.villageMFSpawnBiomes) {
			if (b instanceof BiomeGenBase) {
				System.out.println(((BiomeGenBase) b).biomeName);
			}
		}
		System.out.println("\n\n\n\n\nSPREAD BIOMES");
		for (Object b : MapGenVillageMF.villageMFSpreadBiomes) {
			if (b instanceof BiomeGenBase) {
				System.out.println(((BiomeGenBase) b).biomeName);
			}
		}
		System.out.println("\n\n\n");
	}*/
	
	public BOPMapGenVillageMF(Map par1Map) {
        this();
        Iterator iterator = par1Map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            if (((String)entry.getKey()).equals("distance")) {
                this.maxDistance = MathHelper.parseIntWithDefaultAndMax((String) entry.getValue(), this.maxDistance, this.minDistance + 1);
            }
        }
    }
	
	@Override
	protected StructureStart getStructureStart(int par1, int par2) {
		return new StructureVillageMFStart(this.worldObj, this.rand, par1, par2, 0);
	}

	@Override
	public String func_143025_a() {
		return "BOPVillageMF";
	}
	
	@Override
	protected boolean canSpawnStructureAtCoords(int par1, int par2) {
		int k = par1;
		int l = par2;

		if (par1 < 0) {
			par1 -= this.maxDistance - 1;
		}

		if (par2 < 0) {
			par2 -= this.maxDistance - 1;
		}

		int i1 = par1 / this.maxDistance;
		int j1 = par2 / this.maxDistance;
		Random random = this.worldObj.setRandomSeed(i1, j1, 10387312);
		i1 *= this.maxDistance;
		j1 *= this.maxDistance;
		i1 += random.nextInt(this.maxDistance - this.minDistance);
		j1 += random.nextInt(this.maxDistance - this.minDistance);

		if (k == i1 && l == j1) {
			boolean flag = this.worldObj.getWorldChunkManager().areBiomesViable(k * 16 + 8, l * 16 + 8, 0,
					MapGenVillageMF.villageMFSpawnBiomes);

			if (flag) {
				return true;
			}
		}

		return false;
	}

}
