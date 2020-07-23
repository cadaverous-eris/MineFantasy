package minefantasy.system.villagegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureVillageStart;

public class MapGenVillageMF extends MapGenVillage {

	//public static List villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.plains });
	public static ArrayList villageMFSpawnBiomes = new ArrayList();
	public static ArrayList villageMFSpreadBiomes = new ArrayList();
	static {
		villageMFSpawnBiomes.add(BiomeGenBase.plains);
		
		villageMFSpreadBiomes.add(BiomeGenBase.plains);
		villageMFSpreadBiomes.add(BiomeGenBase.beach);
		villageMFSpreadBiomes.add(BiomeGenBase.forest);
		villageMFSpreadBiomes.add(BiomeGenBase.extremeHillsEdge);
		villageMFSpreadBiomes.add(BiomeGenBase.jungle);
		villageMFSpreadBiomes.add(BiomeGenBase.mushroomIsland);
		villageMFSpreadBiomes.add(BiomeGenBase.swampland);
	}
	
	protected int maxDistance;
    protected int minDistance;
	
	public MapGenVillageMF() {
		super();
		this.maxDistance = 32; // vanilla -> 32
        this.minDistance = 16; // vanilla -> 8
	}
	
	public MapGenVillageMF(Map par1Map) {
        this();
        Iterator iterator = par1Map.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry entry = (Entry)iterator.next();

            if (((String)entry.getKey()).equals("distance")) {
                this.maxDistance = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.maxDistance, this.minDistance + 1);
            }
        }
    }

	@Override
	protected StructureStart getStructureStart(int par1, int par2) {
		return new StructureVillageMFStart(this.worldObj, this.rand, par1, par2, 0);
	}

	@Override
	public String func_143025_a() {
		return "VillageMF";
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
					villageMFSpawnBiomes);

			if (flag) {
				return true;
			}
		}

		return false;
	}

}
