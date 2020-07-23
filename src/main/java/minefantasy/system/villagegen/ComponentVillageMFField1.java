package minefantasy.system.villagegen;

import java.util.List;
import java.util.Random;

import minefantasy.block.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageMFField1 extends ComponentVillageMF {

	public ComponentVillageMFField1() {
	}

	public ComponentVillageMFField1(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFField1 create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 16, 8, 16, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFField1(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		for (int i = 0; i < 1 + rand.nextInt(3); i++) {
			int j = rand.nextInt(10) + 3;
			int k = rand.nextInt(10) + 3;
			
			if (sbb.isVecInside(j + this.boundingBox.minX, 64, this.boundingBox.minZ + k) && sbb.isVecInside(j + this.boundingBox.minX + 2, 64, this.boundingBox.minZ + k + 2)) {
				this.addHaystack(world, rand, sbb, this.boundingBox.minX + j, this.boundingBox.minZ + k);
			}
		}
		
		for (int j = this.boundingBox.minX; j <= this.boundingBox.maxX; ++j) {
			for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k) {
				if (sbb.isVecInside(j, 64, k)) {
					int y = world.getTopSolidOrLiquidBlock(j, k) - 1;

					if (world.getBlockId(j, y, k) != BlockListMF.hayRoof.blockID && world.getBlockId(j, y, k) != BlockListMF.woodSingleSlab.blockID && world.getBlockId(j, y, k) != BlockListMF.woodDoubleSlab.blockID
							&& (world.isAirBlock(j, y + 1, k) || world.getBlockMaterial(j, y + 1, k).isReplaceable())) {
						world.setBlock(j, y, k, Block.tilledField.blockID, 0, 2);
						world.setBlock(j, y + 1, k, Block.crops.blockID, rand.nextInt(4) + 4, 2);
					}
				}
			}
		}

		return true;
	}

	protected boolean addHaystack(World world, Random rand, StructureBoundingBox sbb, int x, int z) {
		int yLvl = 0;
		
		int t = 0;
		
		for (int i = x; i < x + 3; i++) {
			for (int j = z; j < z + 3; j++) {
				int lvl = world.getTopSolidOrLiquidBlock(i, j);
				
				int id = world.getBlockId(i, lvl, j);	
				if (id == BlockListMF.hayRoof.blockID || id == BlockListMF.woodDoubleSlab.blockID || id == BlockListMF.woodSingleSlab.blockID) {
					return false;
				}
				t += lvl;
				 
				if (lvl > yLvl) {
					yLvl = lvl;
				}
			}
		}
		
		yLvl = t / 9;
		
		if (yLvl <= 0) {
			return false;
		}

		for (int i = x; i < x + 3; i++) {
			for (int j = z; j < z + 3; j++) {
				for (int k = 0; k < 2; k++) {
					world.setBlock(i, yLvl + k, j, BlockListMF.woodDoubleSlab.blockID, 3, 2);
				}
			}
		}
		if (rand.nextInt(3) > 0) {
			world.setBlock(x, yLvl + 1, z, BlockListMF.hayRoof.blockID, rand.nextBoolean() ? 0 : 2, 2);
		} else {
			world.setBlock(x, yLvl + 1, z, BlockListMF.woodSingleSlab.blockID, 3, 2);
		}
		if (rand.nextInt(3) > 0) {
			world.setBlock(x + 2, yLvl + 1, z, BlockListMF.hayRoof.blockID, rand.nextBoolean() ? 1 : 2, 2);
		} else {
			world.setBlock(x + 2, yLvl + 1, z, BlockListMF.woodSingleSlab.blockID, 3, 2);
		}
		if (rand.nextInt(3) > 0) {
			world.setBlock(x, yLvl + 1, z + 2, BlockListMF.hayRoof.blockID, rand.nextBoolean() ? 0 : 3, 2);
		} else {
			world.setBlock(x, yLvl + 1, z + 2, BlockListMF.woodSingleSlab.blockID, 3, 2);
		}
		if (rand.nextInt(3) > 0) {
			world.setBlock(x + 2, yLvl + 1, z + 2, BlockListMF.hayRoof.blockID, rand.nextBoolean() ? 1 : 3, 2);
		} else {
			world.setBlock(x + 2, yLvl + 1, z + 2, BlockListMF.woodSingleSlab.blockID, 3, 2);
		}

		world.setBlock(x + 1, yLvl + 2, z + 1, BlockListMF.woodDoubleSlab.blockID, 3, 2);
		if (rand.nextBoolean()) {
			world.setBlock(x + 1, yLvl + 2, z, BlockListMF.hayRoof.blockID, 2, 2);
		} else {
			world.setBlock(x + 1, yLvl + 2, z, BlockListMF.woodSingleSlab.blockID, 3, 2);
		}
		if (rand.nextBoolean()) {
			world.setBlock(x, yLvl + 2, z + 1, BlockListMF.hayRoof.blockID, 0, 2);
		} else {
			world.setBlock(x, yLvl + 2, z + 1, BlockListMF.woodSingleSlab.blockID, 3, 2);
		}
		if (rand.nextBoolean()) {
			world.setBlock(x + 2, yLvl + 2, z + 1, BlockListMF.hayRoof.blockID, 1, 2);
		} else {
			world.setBlock(x + 2, yLvl + 2, z + 1, BlockListMF.woodSingleSlab.blockID, 3, 2);
		}
		if (rand.nextBoolean()) {
			world.setBlock(x + 1, yLvl + 2, z + 2, BlockListMF.hayRoof.blockID, 3, 2);
		} else {
			world.setBlock(x + 1, yLvl + 2, z + 2, BlockListMF.woodSingleSlab.blockID, 3, 2);
		}

		for (int i = x; i < x + 3; i++) {
			for (int j = z; j < z + 3; j++) {
				for (int y = yLvl - 1; y > 10; y--) {
					if (world.isAirBlock(i, y, j) || world.getBlockMaterial(i, y, j).isLiquid() || world.getBlockMaterial(i, y, j).isReplaceable()) {
						world.setBlock(i, y, j, Block.dirt.blockID, 0, 2);
					} else {
						break;
					}
				}
			}
		}

		return true;
	}

}
