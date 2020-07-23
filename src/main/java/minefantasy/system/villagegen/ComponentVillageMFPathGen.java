package minefantasy.system.villagegen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

public class ComponentVillageMFPathGen extends ComponentVillageMFRoadPiece {

	private int averageGroundLevel;

	public ComponentVillageMFPathGen() {
	}

	public ComponentVillageMFPathGen(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
		this.averageGroundLevel = Math.max(par4StructureBoundingBox.getXSize(), par4StructureBoundingBox.getZSize());
	}

	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("Length", this.averageGroundLevel);
	}

	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
		this.averageGroundLevel = par1NBTTagCompound.getInteger("Length");
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current
	 * Location of StructGen
	 */
	public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
		boolean flag = false;
		int i;
		StructureComponent structurecomponent1;

		for (i = par3Random.nextInt(5); i < this.averageGroundLevel - 8; i += 2 + par3Random.nextInt(5)) {
			structurecomponent1 = this.getNextComponentNN((ComponentVillageMFStartPiece) par1StructureComponent, par2List,
					par3Random, 0, i);

			if (structurecomponent1 != null) {
				i += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
				flag = true;
			}
		}

		for (i = par3Random.nextInt(5); i < this.averageGroundLevel - 8; i += 2 + par3Random.nextInt(5)) {
		//for (i = par3Random.nextInt(7); i < this.averageGroundLevel - 8; i += 1 + par3Random.nextInt(3)) {
			structurecomponent1 = this.getNextComponentPP((ComponentVillageMFStartPiece) par1StructureComponent, par2List,
					par3Random, 0, i);

			if (structurecomponent1 != null) {
				i += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
				flag = true;
			}
		}

		if (flag && par3Random.nextInt(5) > 0) {
			switch (this.coordBaseMode) {
			case 0:
				StructureVillageMFPieces.getNextStructureComponentVillagePath(
						(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random,
						this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 1,
						this.getComponentType());
				break;
			case 1:
				StructureVillageMFPieces.getNextStructureComponentVillagePath(
						(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random,
						this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2,
						this.getComponentType());
				break;
			case 2:
				StructureVillageMFPieces.getNextStructureComponentVillagePath(
						(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random,
						this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1,
						this.getComponentType());
				break;
			case 3:
				StructureVillageMFPieces.getNextStructureComponentVillagePath(
						(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random,
						this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2,
						this.getComponentType());
			}
		}

		if (flag && par3Random.nextInt(5) > 0) {
			switch (this.coordBaseMode) {
			case 0:
				StructureVillageMFPieces.getNextStructureComponentVillagePath(
						(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random,
						this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 3,
						this.getComponentType());
				break;
			case 1:
				StructureVillageMFPieces.getNextStructureComponentVillagePath(
						(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random,
						this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0,
						this.getComponentType());
				break;
			case 2:
				StructureVillageMFPieces.getNextStructureComponentVillagePath(
						(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random,
						this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3,
						this.getComponentType());
				break;
			case 3:
				StructureVillageMFPieces.getNextStructureComponentVillagePath(
						(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random,
						this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0,
						this.getComponentType());
			}
		}
	}

	public static StructureBoundingBox func_74933_a(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int componentType) {
		for (int i1 = 7 * MathHelper.getRandomIntegerInRange(par2Random, 5 - (componentType / 2), Math.max(5 - (componentType / 2) + 1, 9 - componentType)); i1 >= 7; i1 -= 7) {
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4,
					par5, 0, 0, 0, 3, 3, i1, par6);

			if (structureboundingbox != null) {
				ComponentVillageMFPathGen componentvillagepathgen = new ComponentVillageMFPathGen(
						par0ComponentVillageStartPiece, componentType, par2Random, structureboundingbox, par6);
				int j1 = (componentvillagepathgen.getBoundingBox().minX + componentvillagepathgen.getBoundingBox().maxX)
						/ 2;
				int k1 = (componentvillagepathgen.getBoundingBox().minZ + componentvillagepathgen.getBoundingBox().maxZ)
						/ 2;
				int l1 = componentvillagepathgen.getBoundingBox().maxX - componentvillagepathgen.getBoundingBox().minX;
				int i2 = componentvillagepathgen.getBoundingBox().maxZ - componentvillagepathgen.getBoundingBox().minZ;
				int j2 = l1 > i2 ? l1 : i2;

				if (!par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(j1, k1, j2 / 2 + 4,
						MapGenVillageMF.villageMFSpreadBiomes)) {
					continue;
				}
			}
			
			if (StructureComponent.findIntersecting(par1List, structureboundingbox) == null) {
				return structureboundingbox;
			}
		}

		return null;
	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs,
	 * Mob Spawners, it closes Mineshafts at the end, it adds Fences...
	 */
	public boolean addComponentParts(World par1World, Random par2Random,
			StructureBoundingBox par3StructureBoundingBox) {
		int id = Block.cobblestone.blockID;
		int id1 = Block.cobblestoneMossy.blockID;
		int id2 = Block.gravel.blockID;

		for (int j = this.boundingBox.minX; j <= this.boundingBox.maxX; ++j) {
			for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k) {
				if (par3StructureBoundingBox.isVecInside(j, 64, k)) {
					int l = par1World.getTopSolidOrLiquidBlock(j, k) - 1;
					int rand = par2Random.nextInt(10);
					int i = rand < 3 ? id2 : rand < 6 ? id1 : id;
					
					par1World.setBlock(j, l, k, i, 0, 2);
				}
			}
		}

		return true;
	}

}
