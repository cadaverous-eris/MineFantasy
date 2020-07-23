package minefantasy.system.villagegen;

import java.util.List;
import java.util.Random;

import minefantasy.block.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.BiomeEvent;

public class ComponentVillageMFStartPiece extends ComponentVillageStartPiece {

	public ComponentVillageMFStartPiece() {
	}

	public ComponentVillageMFStartPiece(WorldChunkManager par1WorldChunkManager, int par2, Random par3Random, int par4,
			int par5, List par6List, int par7) {
		super(par1WorldChunkManager, par2, par3Random, par4, par5, par6List, par7);
	}

	@Override
	public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
		StructureVillageMFPieces.getNextStructureComponentVillagePath(
				(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1,
				this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 1, this.getComponentType());
		StructureVillageMFPieces.getNextStructureComponentVillagePath(
				(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1,
				this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 3, this.getComponentType());
		StructureVillageMFPieces.getNextStructureComponentVillagePath(
				(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1,
				this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, 2, this.getComponentType());
		StructureVillageMFPieces.getNextStructureComponentVillagePath(
				(ComponentVillageMFStartPiece) par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1,
				this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
	}

	@Override
	public boolean addComponentParts(World par1World, Random par2Random,
			StructureBoundingBox par3StructureBoundingBox) {
		if (this.field_143015_k < 0) {
			this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);

			if (this.field_143015_k < 0) {
				return true;
			}

			this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
		}

		this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 1, 4, 12, 4, BlockListMF.cobbBrick.blockID,
				Block.waterMoving.blockID, false);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 2, 12, 2, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 3, 12, 2, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 2, 12, 3, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 3, 12, 3, par3StructureBoundingBox);

		this.placeBlockAtCurrentPosition(par1World, BlockListMF.stoneSingleSlab.blockID, 0, 2, 12, 1,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, BlockListMF.stoneSingleSlab.blockID, 0, 3, 12, 1,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, BlockListMF.stoneSingleSlab.blockID, 0, 2, 12, 4,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, BlockListMF.stoneSingleSlab.blockID, 0, 3, 12, 4,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, BlockListMF.stoneSingleSlab.blockID, 0, 1, 12, 2,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, BlockListMF.stoneSingleSlab.blockID, 0, 1, 12, 3,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, BlockListMF.stoneSingleSlab.blockID, 0, 4, 12, 2,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, BlockListMF.stoneSingleSlab.blockID, 0, 4, 12, 3,
				par3StructureBoundingBox);

		this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 13, 1, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 4, 13, 1, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 13, 4, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 4, 13, 4, par3StructureBoundingBox);

		int n = this.getMetadataWithOffset(Block.stairsWoodSpruce.blockID, 3);
		int s = this.getMetadataWithOffset(Block.stairsWoodSpruce.blockID, 2);
		int w = this.getMetadataWithOffset(Block.stairsWoodSpruce.blockID, 1);
		int e = this.getMetadataWithOffset(Block.stairsWoodSpruce.blockID, 0);
		int n_u = this.getMetadataWithOffset(Block.stairsWoodSpruce.blockID, 7);
		int s_u = this.getMetadataWithOffset(Block.stairsWoodSpruce.blockID, 6);
		int w_u = this.getMetadataWithOffset(Block.stairsWoodSpruce.blockID, 5);
		int e_u = this.getMetadataWithOffset(Block.stairsWoodSpruce.blockID, 4);

		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, e, 1, 14, 1,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, s, 1, 14, 2,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, e, 1, 14, 3,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, e, 1, 14, 4,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, w_u, 2, 14, 1,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.woodSingleSlab.blockID, 9, 2, 14, 2,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, w_u, 2, 14, 3,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, w_u, 2, 14, 4,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, e_u, 3, 14, 1,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, s_u, 3, 14, 2,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, e_u, 3, 14, 3,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, e_u, 3, 14, 4,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, w, 4, 14, 1,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, w, 4, 14, 2,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.woodSingleSlab.blockID, 1, 4, 14, 3,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, w, 4, 14, 4,
				par3StructureBoundingBox);

		this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 15, 1, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 15, 2, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 15, 3, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 15, 4, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, e, 2, 15, 1,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, s, 2, 15, 2,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.woodSingleSlab.blockID, 1, 2, 15, 3,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, e, 2, 15, 4,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, w, 3, 15, 1,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.woodSingleSlab.blockID, 1, 3, 15, 2,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, w, 3, 15, 3,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodSpruce.blockID, w, 3, 15, 4,
				par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 4, 15, 1, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 4, 15, 2, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 4, 15, 3, par3StructureBoundingBox);
		this.placeBlockAtCurrentPosition(par1World, 0, 0, 4, 15, 4, par3StructureBoundingBox);

		for (int i = 0; i <= 5; ++i) {
			for (int j = 0; j <= 5; ++j) {
				if (j == 0 || j == 5 || i == 0 || i == 5) {
					int rand = par2Random.nextInt(10);
					int id = rand < 3 ? Block.gravel.blockID : rand < 6 ? Block.cobblestoneMossy.blockID : Block.cobblestone.blockID;
					this.placeBlockAtCurrentPosition(par1World, id, 0, j, 11, i, par3StructureBoundingBox);
					this.clearCurrentPositionBlocksUpwards(par1World, j, 12, i, par3StructureBoundingBox);
				}
			}
		}

		return true;
	}

	/**
	 * Gets the replacement block for the current biome
	 */
	@Override
	protected int getBiomeSpecificBlock(int par1, int par2) {
		return par1;
	}

	/**
	 * Gets the replacement block metadata for the current biome
	 */
	@Override
	protected int getBiomeSpecificBlockMetadata(int par1, int par2) {
		return par2;
	}
	
	@Override
	protected StructureComponent getNextComponentNN(ComponentVillageStartPiece par1ComponentVillageStartPiece,
			List par2List, Random par3Random, int par4, int par5) {
		if (par1ComponentVillageStartPiece instanceof ComponentVillageMFStartPiece) {
			ComponentVillageMFStartPiece startPiece = (ComponentVillageMFStartPiece) par1ComponentVillageStartPiece;
			switch (this.coordBaseMode) {
			case 0:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List,
						par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4,
						this.boundingBox.minZ + par5, 1, this.getComponentType());
			case 1:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List,
						par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4,
						this.boundingBox.minZ - 1, 2, this.getComponentType());
			case 2:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List,
						par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4,
						this.boundingBox.minZ + par5, 1, this.getComponentType());
			case 3:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List,
						par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4,
						this.boundingBox.minZ - 1, 2, this.getComponentType());
			default:
				return null;
			}
		}
		return null;
	}
	
	@Override
	protected StructureComponent getNextComponentPP(ComponentVillageStartPiece par1ComponentVillageStartPiece,
			List par2List, Random par3Random, int par4, int par5) {
		if (par1ComponentVillageStartPiece instanceof ComponentVillageMFStartPiece) {
			ComponentVillageMFStartPiece startPiece = (ComponentVillageMFStartPiece) par1ComponentVillageStartPiece;
			switch (this.coordBaseMode) {
			case 0:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3,
						this.getComponentType());
			case 1:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0,
						this.getComponentType());
			case 2:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3,
						this.getComponentType());
			case 3:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0,
						this.getComponentType());
			default:
				return null;
			}
		}
		return null;
	}
	
	@Override
	protected int getMetadataWithOffset(int id, int meta) {
		if (id > 0 && id < Block.blocksList.length && Block.blocksList[id] != null) {
			Block block = Block.blocksList[id];
			
			if (block instanceof BlockStairs) {
				int dirMeta = meta & 3;
				boolean upsidedown = (meta & 4) == 4;
				
				if (this.coordBaseMode == 0) {
					int retMeta = dirMeta;
					if (dirMeta == 2) {
						retMeta = 3;
					} else if (dirMeta == 3) {
						retMeta = 2;
					}
					if (upsidedown) {
						retMeta |= 4;
					}
					return retMeta;
				} else if (this.coordBaseMode == 1) {
					int retMeta = dirMeta;
					if (dirMeta == 0) {
						retMeta = 2;
					} else if (dirMeta == 1) {
						retMeta = 3;
					} else if (dirMeta == 2) {
						retMeta = 0;
					} else if (dirMeta == 3) {
						retMeta = 1;
					}
					if (upsidedown) {
						retMeta |= 4;
					}
					return retMeta;
				} else if (this.coordBaseMode == 2) {
					return meta;
				} else if (this.coordBaseMode == 3) {
					int retMeta = dirMeta;
					if (dirMeta == 0) {
						retMeta = 2;
					} else if (dirMeta == 1) {
						retMeta = 3;
					} else if (dirMeta == 2) {
						retMeta = 1;
					} else if (dirMeta == 3) {
						retMeta = 0;
					}
					if (upsidedown) {
						retMeta |= 4;
					}
					return retMeta;
				}
			}
		}
		return super.getMetadataWithOffset(id, meta);
    }

}
