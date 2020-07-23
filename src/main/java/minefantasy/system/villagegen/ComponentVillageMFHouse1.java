package minefantasy.system.villagegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityFirepit;
import minefantasy.block.tileentity.TileEntityPrepBlock;
import minefantasy.block.tileentity.TileEntityRoast;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageMFHouse1 extends ComponentVillageMF {

	private boolean hasSpitRoast;
	private boolean hasFoodPrep;
	
	public ComponentVillageMFHouse1() {
	}

	public ComponentVillageMFHouse1(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFHouse1 create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 13, 11, 11, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFHouse1(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}
	
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
        super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Roast", this.hasSpitRoast);
        par1NBTTagCompound.setBoolean("FoodPrep", this.hasFoodPrep);
    }
	
	@Override
    protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
        super.func_143011_b(par1NBTTagCompound);
        this.hasSpitRoast = par1NBTTagCompound.getBoolean("Roast");
        this.hasFoodPrep = par1NBTTagCompound.getBoolean("FoodPrep");
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

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 11 - 1, 0);
        }
		
		Map<String, int[]> key = new HashMap<String, int[]>();
		key.put("stone", new int[] {BlockListMF.stoneDoubleSlab.blockID, 3});
		key.put("stone_brick", new int[] {Block.stoneBrick.blockID});
		key.put("cobble", new int[] {Block.cobblestone.blockID});
		key.put("cobble_brick", new int[] {BlockListMF.cobbBrick.blockID});
		key.put("brick", new int[] {Block.brick.blockID});
		key.put("clay_wall", new int[] {BlockListMF.clayWall.blockID});
		key.put("plank_oak", new int[] {Block.planks.blockID});
		key.put("plank_spruce", new int[] {Block.planks.blockID, 1});
		key.put("bookshelf", new int[] {Block.bookShelf.blockID});
		key.put("pot_fern", new int[] {Block.flowerPot.blockID, 11});
		key.put("pot_oak", new int[] {Block.flowerPot.blockID, 3});
		key.put("fence", new int[] {Block.fence.blockID});
		key.put("lantern", new int[] {BlockListMF.lantern.blockID});
		// key.put("firepit", new int[] {BlockListMF.firepit.blockID});
		// key.put("benchtop", new int[] {BlockListMF.foodPrep.blockID});
		key.put("chimney", new int[] {BlockListMF.chimney.blockID, 3});
		key.put("log_x", new int[] {Block.wood.blockID, 4});
		key.put("log_y", new int[] {Block.wood.blockID, 0});
		key.put("log_z", new int[] {Block.wood.blockID, 8});
		key.put("glass_pane", new int[] {Block.thinGlass.blockID});
		key.put("brick_stair_n", new int[] {Block.stairsBrick.blockID, 3});
		key.put("brick_stair_s", new int[] {Block.stairsBrick.blockID, 2});
		key.put("brick_stair_w", new int[] {Block.stairsBrick.blockID, 1});
		key.put("brick_stair_e", new int[] {Block.stairsBrick.blockID, 0});
		key.put("oak_stair_n", new int[] {Block.stairsWoodOak.blockID, 3});
		key.put("oak_stair_s", new int[] {Block.stairsWoodOak.blockID, 2});
		key.put("oak_stair_w", new int[] {Block.stairsWoodOak.blockID, 1});
		key.put("oak_stair_e", new int[] {Block.stairsWoodOak.blockID, 0});
		key.put("oak_stair_nu", new int[] {Block.stairsWoodOak.blockID, 7});
		key.put("oak_stair_su", new int[] {Block.stairsWoodOak.blockID, 6});
		key.put("oak_stair_wu", new int[] {Block.stairsWoodOak.blockID, 5});
		key.put("oak_stair_eu", new int[] {Block.stairsWoodOak.blockID, 4});
		key.put("spruce_stair_n", new int[] {Block.stairsWoodSpruce.blockID, 3});
		key.put("spruce_stair_s", new int[] {Block.stairsWoodSpruce.blockID, 2});
		key.put("spruce_stair_w", new int[] {Block.stairsWoodSpruce.blockID, 1});
		key.put("spruce_stair_e", new int[] {Block.stairsWoodSpruce.blockID, 0});
		key.put("spruce_stair_nu", new int[] {Block.stairsWoodSpruce.blockID, 7});
		key.put("spruce_stair_su", new int[] {Block.stairsWoodSpruce.blockID, 6});
		key.put("spruce_stair_wu", new int[] {Block.stairsWoodSpruce.blockID, 5});
		key.put("spruce_stair_eu", new int[] {Block.stairsWoodSpruce.blockID, 4});
		key.put("slate_roof_n", new int[] {BlockListMF.slateStairsTile.blockID, 3});
		key.put("slate_roof_s", new int[] {BlockListMF.slateStairsTile.blockID, 2});
		key.put("slate_roof_w", new int[] {BlockListMF.slateStairsTile.blockID, 1});
		key.put("slate_roof_e", new int[] {BlockListMF.slateStairsTile.blockID, 0});
		key.put("slate_roof_nu", new int[] {BlockListMF.slateStairsTile.blockID, 7});
		key.put("slate_roof_su", new int[] {BlockListMF.slateStairsTile.blockID, 6});
		key.put("slate_roof_wu", new int[] {BlockListMF.slateStairsTile.blockID, 5});
		key.put("slate_roof_eu", new int[] {BlockListMF.slateStairsTile.blockID, 4});
		// key.put("spit_roast", new int[] {BlockListMF.roast.blockID});
		key.put("hay_slab", new int[] {BlockListMF.woodSingleSlab.blockID, 3});
		key.put("oak_slab", new int[] {Block.woodSingleSlab.blockID});
		key.put("spruce_slab", new int[] {Block.woodSingleSlab.blockID, 1});
		key.put("spruce_slab_u", new int[] {Block.woodSingleSlab.blockID, 9});
		
		this.fillWithBlocks(world, structureboundingbox, 0, 1, 0, 6, 10, 10, 0, 0, false);
		this.fillWithBlocks(world, structureboundingbox, 7, 1, 2, 12, 10, 10, 0, 0, false);
		
		this.buildFromBlueprint(world, rand, structureboundingbox, blueprint, key);
		
		if (!this.hasSpitRoast) {
            int i = this.getYWithOffset(1);
            int j = this.getXWithOffset(6, 8);
            int k = this.getZWithOffset(6, 8);

            if (structureboundingbox.isVecInside(j, i, k) && structureboundingbox.isVecInside(j, i + 1, k)) {
                this.hasSpitRoast = true;
                world.setBlock(j, i, k, BlockListMF.firepit.blockID, 0, 2);
                world.setBlock(j, i + 1, k, BlockListMF.roast.blockID, 0, 2);
                TileEntityFirepit teFirepit = (TileEntityFirepit) world.getBlockTileEntity(j, i, k);
                TileEntityRoast teRoast = (TileEntityRoast) world.getBlockTileEntity(j, i + 1, k);
                if (teFirepit != null) {
                	teFirepit.fuel = rand.nextInt(2400) + 1200;
                }
                if (teRoast != null) {
                	teRoast.direction = (this.coordBaseMode) & 3;
                }
            }
        }
		if (!this.hasFoodPrep) {
			int successes = 0;
			
			for (int l = 0; l < 3; l++) {
				int i = this.getYWithOffset(2);
	            int j = this.getXWithOffset(2, 6 + l);
	            int k = this.getZWithOffset(2, 6 + l);

	            if (structureboundingbox.isVecInside(j, i, k)) {
	                successes++;
	                world.setBlock(j, i, k, BlockListMF.foodPrep.blockID, 0, 2);
	                TileEntityPrepBlock tePrep = (TileEntityPrepBlock) world.getBlockTileEntity(j, i, k);
	                if (tePrep != null) {
	                	tePrep.direction = (this.coordBaseMode) & 3;
	                	tePrep.setInventorySlotContents(1, new ItemStack(Block.planks.blockID, 1, 1));
	                }
	            }
			}
            
            this.hasFoodPrep = successes >= 3;
        }
		
		this.placeDoorAtCurrentPosition(world, structureboundingbox, rand, 3, 1, 1, this.getMetadataWithOffset(Block.doorWood.blockID, 3));
		
		if (this.getBlockIdAtCurrentPosition(world, 3, 0, -1, structureboundingbox) == 0) {
            this.placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 3, 0, 0, structureboundingbox);
        } else {
        	this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 3, 0, 0, structureboundingbox);
        }
		
		for (int l = 1; l < 10; ++l) {
            for (int i1 = 1; i1 < 6; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, structureboundingbox);
            }
        }
		for (int l = 3; l < 10; ++l) {
            for (int i1 = 6; i1 < 12; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, structureboundingbox);
            }
        }
		this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, 2, -1, 0, structureboundingbox);
		this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, 4, -1, 0, structureboundingbox);
        this.fillCurrentPositionBlocksDownwards(world, Block.cobblestone.blockID, 0, 3, -1, 0, structureboundingbox);
        for (int l = 0; l < 11; l++) {
        	for (int i1 = 0; i1 < 13; i1++) {
        		this.clearCurrentPositionBlocksUpwards(world, i1, 11, l, structureboundingbox);
        	}
        }

        this.spawnVillagers(world, structureboundingbox, 2, 1, 2, 1);
        
		return true;
	}
	
	@Override
	protected int getVillagerType(int par1) {
		return 1;
	}
	
	private static String[][][] blueprint = new String[][][] {
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone", "stone", "stone", "stone", "stone", "stone", "stone", "stone", "stone", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "cobble_brick", "cobble_brick", "cobble_brick", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "cobble_brick", "cobble_brick", "cobble_brick", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "stone", "stone", "stone", "stone", "stone", "stone", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "stone", "", "", "", "", "", "", "" },
			{ "", "stone", "stone", "plank_oak", "stone", "stone", "", "", "", "", "", "", "" },
			{ "", "", "stone_brick", "", "stone_brick", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone", "stone_brick", "stone", "stone_brick", "brick", "stone_brick", "stone_brick", "stone_brick", "stone", "stone", "" },
			{ "", "stone", "fence", "", "", "brick", "firepit", "brick", "", "hay_slab", "hay_slab", "stone", "" },
			{ "", "stone_brick", "fence", "spruce_stair_e", "", "brick_stair_n", "", "brick_stair_n", "", "", "", "stone_brick", "" },
			{ "", "stone_brick", "fence", "", "", "", "", "", "", "", "spruce_stair_nu", "stone_brick", "" },
			{ "", "stone_brick", "", "", "", "", "", "", "", "spruce_stair_w", "spruce_slab_u", "stone_brick", "" },
			{ "", "stone_brick", "", "", "", "", "", "", "", "", "spruce_stair_su", "stone_brick", "" },
			{ "", "stone_brick", "", "", "", "stone", "stone", "stone_brick", "stone_brick", "stone_brick", "stone", "stone", "" },
			{ "", "stone", "pot_fern", "", "pot_fern", "stone_brick", "", "", "", "", "", "", "" },
			{ "", "stone", "stone_brick", " ", "stone_brick", "stone", "", "", "", "", "", "", "" },
			{ "", "", "fence", "", "fence", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone_brick", "cobble", "glass_pane", "cobble", "brick", "cobble", "glass_pane", "stone_brick", "stone_brick", "stone", "" },
			{ "", "stone_brick", "benchtop", "", "", "brick", "spit_roast", "brick", "", "", "", "stone_brick", "" },
			{ "", "cobble", "benchtop", "", "", "", "", "", "", "", "", "stone_brick", "" },
			{ "", "stone_brick", "benchtop", "", "", "", "", "", "", "", "", "glass_pane", "" },
			{ "", "cobble", "", "", "", "", "", "", "", "", "", "cobble", "" },
			{ "", "cobble", "", "", "", "", "", "", "", "", "pot_oak", "stone_brick", "" },
			{ "", "stone_brick", "", "", "", "stone_brick", "stone_brick", "cobble", "glass_pane", "stone_brick", "stone_brick", "stone", "" },
			{ "", "stone_brick", "", "", "", "stone_brick", "", "", "", "", "", "", "" },
			{ "", "stone", "stone_brick", " ", "stone_brick", "stone_brick", "", "", "", "", "", "", "" },
			{ "", "", "fence", "", "fence", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "cobble", "cobble", "cobble", "cobble", "brick", "cobble", "cobble", "cobble", "stone_brick", "stone_brick", "" },
			{ "", "cobble", "", "", "", "brick_stair_e", "chimney", "brick_stair_w", "", "", "bookshelf", "stone_brick", "" },
			{ "", "cobble", "", "", "", "", "", "", "", "", "bookshelf", "cobble", "" },
			{ "", "cobble", "", "", "lantern", "", "", "", "lantern", "", "bookshelf", "cobble", "" },
			{ "", "cobble", "", "", "", "", "", "", "", "", "bookshelf", "cobble", "" },
			{ "", "cobble", "", "lantern", "", "", "", "", "", "", "bookshelf", "cobble", "" },
			{ "", "cobble", "", "", "", "stone_brick", "cobble", "cobble", "cobble", "cobble", "stone_brick", "stone_brick", "" },
			{ "", "stone_brick", "", "", "", "cobble", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "", "", "", "", "" },
			{ "", "", "oak_stair_e", "oak_stair_n", "oak_stair_w", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "" },
			{ "", "clay_wall", "", "log_z", "", "", "chimney", "", "", "", "", "clay_wall", "" },
			{ "", "clay_wall", "", "log_z", "", "", "", "", "", "", "", "clay_wall", "" },
			{ "", "clay_wall", "log_x", "log_z", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "clay_wall", "" },
			{ "", "clay_wall", "", "log_z", "", "", "", "", "", "", "", "clay_wall", "" },
			{ "", "clay_wall", "", "log_z", "", "", "", "", "", "", "", "clay_wall", "" },
			{ "", "plank_spruce", "", "log_z", "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "" },
			{ "", "plank_spruce", "", "log_z", "", "plank_spruce", "", "", "", "", "", "", "" },
			{ "", "plank_spruce", "clay_wall", "clay_wall", "clay_wall", "plank_spruce", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s" },
			{ "slate_roof_nu", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_nu" },
			{ "", "plank_spruce", "", "", "", "", "chimney", "", "", "", "", "plank_spruce", "" },
			{ "", "clay_wall", "", "", "", "", "", "", "", "", "", "clay_wall", "" },
			{ "", "clay_wall", "", "log_y", "", "", "", "", "", "log_y", "", "clay_wall", "" },
			{ "", "clay_wall", "", "", "", "", "", "", "", "", "", "clay_wall", "" },
			{ "", "plank_spruce", "", "", "", "", "", "", "", "", "", "plank_spruce", "" },
			{ "slate_roof_su", "plank_spruce", "", "", "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_su" },
			{ "oak_stair_n", "plank_spruce", "", "", "", "plank_spruce", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n" },
			{ "oak_stair_e", "plank_spruce", "plank_spruce", "clay_wall", "plank_spruce", "plank_spruce", "oak_stair_w", "", "", "", "", "", "" },
			{ "oak_stair_e", "slate_roof_wu", "", "", "", "slate_roof_eu", "oak_stair_w", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s" },
			{ "slate_roof_nu", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "chimney", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_nu" },
			{ "", "plank_spruce", "", "", "", "", "", "", "", "", "", "plank_spruce", "" },
			{ "", "clay_wall", "", "log_y", "", "", "", "", "", "log_y", "", "clay_wall", "" },
			{ "", "plank_spruce", "", "", "", "", "", "", "", "", "", "plank_spruce", "" },
			{ "slate_roof_su", "plank_spruce", "plank_spruce", "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_su" },
			{ "slate_roof_n", "slate_roof_n", "plank_spruce", "", "plank_spruce", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n" },
			{ "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "slate_roof_wu", "", "slate_roof_eu", "slate_roof_w", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "chimney", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s" },
			{ "slate_roof_nu", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_nu" },
			{ "", "plank_spruce", "", "log_y", "", "", "", "", "", "log_y", "", "plank_spruce", "" },
			{ "slate_roof_su", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_su" },
			{ "slate_roof_n", "slate_roof_n", "slate_roof_n", "plank_spruce", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "oak_stair_nu", "slate_roof_w", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "chimney", "", "", "", "", "", "" },
			{ "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s" },
			{ "oak_stair_eu", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "oak_stair_wu" },
			{ "slate_roof_n", "slate_roof_n", "slate_roof_n", "oak_stair_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_stair_s", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "chimney", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "oak_stair_w", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_stair_e" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "chimney", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		}
	};

}
