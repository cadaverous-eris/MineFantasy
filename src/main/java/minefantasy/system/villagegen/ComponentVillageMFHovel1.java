package minefantasy.system.villagegen;

import java.util.List;
import java.util.Random;

import minefantasy.block.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentVillageHouse1;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageMFHovel1 extends ComponentVillageMF {

	public ComponentVillageMFHovel1() {
	}

	public ComponentVillageMFHovel1(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFHovel1 create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 7, 7, 7, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFHovel1(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}
	
	protected int getRoadLevel(World par1World, StructureBoundingBox par2StructureBoundingBox) {
		int x = this.getXWithOffset(3, -1);
		int z = this.getZWithOffset(3,  -1);
		
		int level = par1World.getTopSolidOrLiquidBlock(x, z);
		
		if (level <= 0) {
			return -1;
		}
		
		return level;
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureboundingbox) {
		if (this.field_143015_k < 0) {
			int avgGroundLvl = this.getAverageGroundLevel(world, structureboundingbox);
			int roadLvl = this.getRoadLevel(world, structureboundingbox);
			if (avgGroundLvl >= roadLvl) {
				this.field_143015_k = roadLvl;
			} else {
				this.field_143015_k = roadLvl - 1;
			}

            if (this.field_143015_k < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
        }
		
		this.fillWithBlocks(world, structureboundingbox, 0, 1, 0, 6, 6, 6, 0, 0, false);
		
        this.fillWithBlocks(world, structureboundingbox, 1, 0, 1, 5, 0, 5, Block.stone.blockID, Block.stone.blockID, false);
        for (int i = 2; i < 5; i++) {
        	if (rand.nextInt(5) < 3) {
        		this.placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 1, 0, i, structureboundingbox);
        	}
        	if (rand.nextInt(5) < 3) {
        		this.placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 5, 0, i, structureboundingbox);
        	}
        	if (rand.nextInt(5) < 3) {
        		this.placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, i, 0, 1, structureboundingbox);
        	}
        	if (rand.nextInt(5) < 3) {
        		this.placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, i, 0, 5, structureboundingbox);
        	}
        }
        this.fillWithBlocks(world, structureboundingbox, 2, 0, 2, 4, 0, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.placeBlockAtCurrentPosition(world, Block.planks.blockID, 0, 3, 0, 1, structureboundingbox);

        int w_u = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 5);
        int e_u = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 4);
        
        this.fillWithMetadataBlocks(world, structureboundingbox, 1, 1, 1, 1, 3, 1, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 1, 1, 5, 1, 3, 5, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 5, 1, 1, 5, 3, 1, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 5, 1, 5, 5, 3, 5, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
        this.fillWithBlocks(world, structureboundingbox, 2, 1, 1, 4, 4, 1, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(world, structureboundingbox, 2, 1, 5, 4, 4, 5, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(world, structureboundingbox, 1, 1, 2, 1, 3, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(world, structureboundingbox, 5, 1, 2, 5, 3, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(world, structureboundingbox, 3, 5, 1, 3, 5, 5, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 2, 4, 2, 2, 4, 4, Block.stairsWoodOak.blockID, w_u, Block.stairsWoodOak.blockID, w_u, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 4, 4, 2, 4, 4, 4, Block.stairsWoodOak.blockID, e_u, Block.stairsWoodOak.blockID, e_u, false);
        
        
        int w = this.getMetadataWithOffset(BlockListMF.hayRoof.blockID, 1);
        int e = this.getMetadataWithOffset(BlockListMF.hayRoof.blockID, 0);
        int n_u = this.getMetadataWithOffset(BlockListMF.hayRoof.blockID, 7);
        int s_u = this.getMetadataWithOffset(BlockListMF.hayRoof.blockID, 6);
        w_u = this.getMetadataWithOffset(BlockListMF.hayRoof.blockID, 5);
        e_u = this.getMetadataWithOffset(BlockListMF.hayRoof.blockID, 4);
        
        this.fillWithMetadataBlocks(world, structureboundingbox, 0, 3, 0, 0, 3, 6, BlockListMF.hayRoof.blockID, e, BlockListMF.hayRoof.blockID, e, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 6, 3, 0, 6, 3, 6, BlockListMF.hayRoof.blockID, w, BlockListMF.hayRoof.blockID, w, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 1, 4, 0, 1, 4, 6, BlockListMF.hayRoof.blockID, e, BlockListMF.hayRoof.blockID, e, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 5, 4, 0, 5, 4, 6, BlockListMF.hayRoof.blockID, w, BlockListMF.hayRoof.blockID, w, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 2, 5, 0, 2, 5, 6, BlockListMF.hayRoof.blockID, e, BlockListMF.hayRoof.blockID, e, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 4, 5, 0, 4, 5, 6, BlockListMF.hayRoof.blockID, w, BlockListMF.hayRoof.blockID, w, false);
        this.fillWithMetadataBlocks(world, structureboundingbox, 3, 6, 0, 3, 6, 6, BlockListMF.woodSingleSlab.blockID, 3, BlockListMF.woodSingleSlab.blockID, 3, false);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, w_u, 1, 3, 0, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, w_u, 1, 3, 6, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, e_u, 5, 3, 0, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, e_u, 5, 3, 6, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, w_u, 2, 4, 0, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, w_u, 2, 4, 6, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, e_u, 4, 4, 0, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, e_u, 4, 4, 6, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, n_u, 3, 5, 0, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.hayRoof.blockID, s_u, 3, 5, 6, structureboundingbox);
        
        w = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 1);
        e = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 0);
        
        for (int i = 0; i < 3; i++) {
        	this.placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, e, i, 3 + i, 1, structureboundingbox);
        	this.placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, e, i, 3 + i, 5, structureboundingbox);
        	this.placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, w, 6 - i, 3 + i, 1, structureboundingbox);
        	this.placeBlockAtCurrentPosition(world, Block.stairsWoodOak.blockID, w, 6 - i, 3 + i, 5, structureboundingbox);
        }
        this.placeBlockAtCurrentPosition(world, Block.woodSingleSlab.blockID, 0, 3, 6, 1, structureboundingbox);
    	this.placeBlockAtCurrentPosition(world, Block.woodSingleSlab.blockID, 0, 3, 6, 5, structureboundingbox);
        
        this.fillWithBlocks(world, structureboundingbox, 3, 1, 1, 3, 2, 1, 0, 0, false);
        this.placeDoorAtCurrentPosition(world, structureboundingbox, rand, 3, 1, 1, this.getMetadataWithOffset(Block.doorWood.blockID, 3));
        
        this.placeBlockAtCurrentPosition(world, BlockListMF.lantern.blockID, 0, 3, 3, 4, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 2, 1, 4, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, Block.pressurePlatePlanks.blockID, 0, 2, 2, 4, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.woodSingleSlab.blockID, 3, 4, 1, 4, structureboundingbox);
        this.placeBlockAtCurrentPosition(world, BlockListMF.woodSingleSlab.blockID, 3, 4, 1, 3, structureboundingbox);
        
        if (this.getBlockIdAtCurrentPosition(world, 3, 0, -1, structureboundingbox) == 0) {
            this.placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 3, 0, 0, structureboundingbox);
        } else {
        	this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 3, 0, 0, structureboundingbox);
        }

        for (int l = 1; l < 6; ++l) {
            for (int i1 = 1; i1 < 6; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, structureboundingbox);
            }
        }
        this.fillCurrentPositionBlocksDownwards(world, Block.cobblestone.blockID, 0, 3, -1, 0, structureboundingbox);
        for (int l = 0; l < 7; l++) {
        	for (int i1 = 0; i1 < 3; i1++) {
        		this.clearCurrentPositionBlocksUpwards(world, i1, 4 + i1, l, structureboundingbox);
        		this.clearCurrentPositionBlocksUpwards(world, 6 - i1, 4 + i1, l, structureboundingbox);
        	}
        	this.clearCurrentPositionBlocksUpwards(world, 3, 7, l, structureboundingbox);
        }

        this.spawnVillagers(world, structureboundingbox, 2, 1, 2, 1);
        return true;
	}

	@Override
	protected int getVillagerType(int par1) {
		return 0;
	}

}
