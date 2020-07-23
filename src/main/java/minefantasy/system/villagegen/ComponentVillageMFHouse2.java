package minefantasy.system.villagegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityFirepit;
import minefantasy.block.tileentity.TileEntityPrepBlock;
import minefantasy.block.tileentity.TileEntityRoast;
import minefantasy.item.ItemListMF;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageMFHouse2 extends ComponentVillageMF {

	private boolean hasFoodPrep;
	private boolean hasFirepit;
	
	public ComponentVillageMFHouse2() {
	}

	public ComponentVillageMFHouse2(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFHouse2 create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 12, 14, 10, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFHouse2(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}
	
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
        super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("FoodPrep", this.hasFoodPrep);
        par1NBTTagCompound.setBoolean("Firepit", this.hasFirepit);
    }
	
	@Override
    protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
        super.func_143011_b(par1NBTTagCompound);
        this.hasFoodPrep = par1NBTTagCompound.getBoolean("FoodPrep");
        this.hasFirepit = par1NBTTagCompound.getBoolean("Firepit");
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
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		if (this.field_143015_k < 0) {
			int avgGroundLvl = this.getAverageGroundLevel(world, sbb);
			int roadLvl = this.getRoadLevel(world, sbb);
			if (avgGroundLvl >= roadLvl) {
				this.field_143015_k = roadLvl;
			} else {
				this.field_143015_k = roadLvl - 1;
			}

            if (this.field_143015_k < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 14 - 1, 0);
        }
		
		Map<String, int[]> key = new HashMap<String, int[]>();
		key.put("stone", new int[] {BlockListMF.stoneDoubleSlab.blockID, 3});
		key.put("stone_brick", new int[] {Block.stoneBrick.blockID});
		key.put("cobble", new int[] {Block.cobblestone.blockID});
		key.put("cobble_brick", new int[] {BlockListMF.cobbBrick.blockID});
		key.put("brick", new int[] {Block.brick.blockID});
		key.put("clay_wall", new int[] {BlockListMF.clayWall.blockID});
		key.put("clay_wall_cross", new int[] {BlockListMF.clayWall.blockID, 1});
		key.put("plank_oak", new int[] {Block.planks.blockID});
		key.put("plank_spruce", new int[] {Block.planks.blockID, 1});
		key.put("bookshelf", new int[] {Block.bookShelf.blockID});
		key.put("pot_fern", new int[] {Block.flowerPot.blockID, 11});
		key.put("pot_oak", new int[] {Block.flowerPot.blockID, 3});
		key.put("pot_rose", new int[] {Block.flowerPot.blockID, 1});
		key.put("fence", new int[] {Block.fence.blockID});
		key.put("lantern", new int[] {BlockListMF.lantern.blockID});
		key.put("chimney", new int[] {BlockListMF.chimney.blockID, 3});
		key.put("log_x", new int[] {Block.wood.blockID, 4});
		key.put("log_y", new int[] {Block.wood.blockID, 0});
		key.put("log_z", new int[] {Block.wood.blockID, 8});
		key.put("crafting_table", new int[] {Block.workbench.blockID});
		key.put("glass_pane", new int[] {Block.thinGlass.blockID});
		key.put("cobble_stair_n", new int[] {Block.stairsCobblestone.blockID, 3});
		key.put("cobble_stair_s", new int[] {Block.stairsCobblestone.blockID, 2});
		key.put("cobble_stair_w", new int[] {Block.stairsCobblestone.blockID, 1});
		key.put("cobble_stair_e", new int[] {Block.stairsCobblestone.blockID, 0});
		key.put("cobble_stair_nu", new int[] {Block.stairsCobblestone.blockID, 7});
		key.put("cobble_stair_su", new int[] {Block.stairsCobblestone.blockID, 6});
		key.put("cobble_stair_wu", new int[] {Block.stairsCobblestone.blockID, 5});
		key.put("cobble_stair_eu", new int[] {Block.stairsCobblestone.blockID, 4});
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
		key.put("hay_roof_n", new int[] {BlockListMF.hayRoof.blockID, 3});
		key.put("hay_roof_s", new int[] {BlockListMF.hayRoof.blockID, 2});
		key.put("hay_roof_w", new int[] {BlockListMF.hayRoof.blockID, 1});
		key.put("hay_roof_e", new int[] {BlockListMF.hayRoof.blockID, 0});
		key.put("hay_roof_nu", new int[] {BlockListMF.hayRoof.blockID, 7});
		key.put("hay_roof_su", new int[] {BlockListMF.hayRoof.blockID, 6});
		key.put("hay_roof_wu", new int[] {BlockListMF.hayRoof.blockID, 5});
		key.put("hay_roof_eu", new int[] {BlockListMF.hayRoof.blockID, 4});
		key.put("hay_slab", new int[] {BlockListMF.woodSingleSlab.blockID, 3});
		key.put("hay_slab_u", new int[] {BlockListMF.woodSingleSlab.blockID, 11});
		key.put("hay_slab_d", new int[] {BlockListMF.woodDoubleSlab.blockID, 3});
		key.put("slate_slab", new int[] {BlockListMF.slateSingleSlab.blockID, 1});
		key.put("slate_slab_u", new int[] {BlockListMF.slateSingleSlab.blockID, 9});
		key.put("slate_slab_d", new int[] {BlockListMF.slateDoubleSlab.blockID, 1});
		key.put("oak_slab", new int[] {Block.woodSingleSlab.blockID});
		key.put("oak_slab_u", new int[] {Block.woodSingleSlab.blockID, 8});
		key.put("spruce_slab", new int[] {Block.woodSingleSlab.blockID, 1});
		key.put("spruce_slab_u", new int[] {Block.woodSingleSlab.blockID, 9});
		key.put("brick_slab", new int[] {Block.stoneSingleSlab.blockID, 4});
		key.put("brick_slab_u", new int[] {Block.stoneSingleSlab.blockID, 12});
		key.put("cobble_slab", new int[] {Block.stoneSingleSlab.blockID, 3});
		key.put("cobble_slab_u", new int[] {Block.stoneSingleSlab.blockID, 11});
		key.put("ladder_n", new int[] {Block.ladder.blockID, 2});
		key.put("ladder_s", new int[] {Block.ladder.blockID, 3});
		key.put("ladder_w", new int[] {Block.ladder.blockID, 4});
		key.put("ladder_e", new int[] {Block.ladder.blockID, 5});
		key.put("cobble_wall", new int[] {Block.cobblestoneWall.blockID});
		
		this.fillWithBlocks(world, sbb, 0, 1, 0, 6, 13, 9, 0, 0, false);
		this.fillWithBlocks(world, sbb, 7, 1, 1, 11, 13, 9, 0, 0, false);
		
		this.buildFromBlueprint(world, rand, sbb, blueprint, key);
		
		if (!this.hasFoodPrep) {
			int successes = 0;
			
			for (int l = 0; l < 2; l++) {
				for (int m = 0; m < 3; m++) {
					int i = this.getYWithOffset(2);
					int j = this.getXWithOffset(7 + l, 4 + m);
					int k = this.getZWithOffset(7 + l, 4 + m);

					if (sbb.isVecInside(j, i, k)) {
						successes++;
						world.setBlock(j, i, k, BlockListMF.foodPrep.blockID, 0, 2);
						TileEntityPrepBlock tePrep = (TileEntityPrepBlock) world.getBlockTileEntity(j, i, k);
						if (tePrep != null) {
							tePrep.direction = (this.coordBaseMode) & 3;
							tePrep.setInventorySlotContents(1, new ItemStack(Block.planks.blockID, 1, 1));
						}
					}
				}
			}
            
            this.hasFoodPrep = successes >= 6;
		}
		
		if (!this.hasFirepit) {
			int i = this.getYWithOffset(1);
	        int j = this.getXWithOffset(3, 7);
	        int k = this.getZWithOffset(3, 7);

	        if (sbb.isVecInside(j, i, k) && sbb.isVecInside(j, i + 1, k)) {
	            this.hasFirepit = true;
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
		
		this.placeDoorAtCurrentPosition(world, sbb, rand, 3, 1, 2, this.getMetadataWithOffset(Block.doorWood.blockID, 3));
		
		if (this.getBlockIdAtCurrentPosition(world, 3, 0, -1, sbb) == 0) {
			this.placeBlockAtCurrentPosition(world, BlockListMF.stairCobbBrick.blockID, this.getMetadataWithOffset(BlockListMF.stairCobbBrick.blockID, 3), 3, 0, 0, sbb);
		} else {
			this.placeBlockAtCurrentPosition(world, BlockListMF.cobbBrick.blockID, 0, 3, 0, 0, sbb);
		}
		
		for (int l = 2; l < 9; ++l) {
            for (int i1 = 1; i1 < 11; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, sbb);
            }
        }
		for (int l = 0; l < 2; ++l) {
            for (int i1 = 2; i1 < 5; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, sbb);
            }
        }
		for (int l = 0; l < 10; ++l) {
            for (int i1 = 0; i1 < 12; ++i1) {
                this.clearCurrentPositionBlocksUpwards(world, i1, 14, l, sbb);
            }
        }
		
		this.spawnVillagers(world, sbb, 3, 1, 4, 1);
		
		return true;
	}
	
	@Override
	protected int getVillagerType(int par1) {
		return 1;
	}
	
	private static String[][][] blueprint = new String[][][] {
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone", "stone_brick", "stone_brick", "stone", "stone", "stone_brick", "stone_brick", "stone", "stone", "" },
			{ "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "" },
			{ "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "" },
			{ "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "" },
			{ "", "stone", "stone", "plank_oak", "stone_brick", "stone", "stone", "stone_brick", "stone_brick", "stone", "stone", "" },
			{ "", "", "stone_brick", "cobble_brick", "stone_brick", "", "", "", "", "", "", "" },
			{ "", "", "stone_brick", "", "stone_brick", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone_brick", "brick", "cobble", "stone_brick", "stone_brick", "cobble", "stone_brick", "stone_brick", "stone", "" },
			{ "", "stone", "brick", "firepit", "brick", "stone_brick", "", "spruce_stair_n", "", "", "stone_brick", "" },
			{ "", "stone_brick", "brick_stair_n", "", "brick_stair_n", "", "", "fence", "fence", "", "cobble", "" },
			{ "", "cobble", "", "", "", "", "", "fence", "fence", "spruce_stair_e", "cobble", "" },
			{ "", "cobble", "", "", "", "", "", "fence", "fence", "", "stone_brick", "" },
			{ "", "stone_brick", "pot_fern", "", "ladder_w", "stone_brick", "", "", "spruce_stair_s", "", "stone_brick", "" },
			{ "", "stone", "stone_brick", " ", "cobble", "stone_brick", "stone_brick", "cobble", "cobble", "stone_brick", "stone_brick", "" },
			{ "", "", "cobble_wall", "", "cobble_wall", "", "", "", "", "", "", "" },
			{ "", "", "cobble_wall", "", "cobble_wall", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "cobble", "brick", "cobble", "stone_brick", "cobble", "glass_pane", "glass_pane", "cobble", "stone_brick", "" },
			{ "", "stone_brick", "brick", "spit_roast", "brick", "cobble", "", "", "", "", "cobble", "" },
			{ "", "cobble", "", "", "", "", "", "food_prep", "food_prep", "", "cobble", "" },
			{ "", "glass_pane", "", "", "", "", "", "food_prep", "food_prep", "", "cobble", "" },
			{ "", "cobble", "", "", "", "", "", "food_prep", "food_prep", "", "cobble", "" },
			{ "", "cobble", "", "", "ladder_w", "stone_brick", "", "", "", "", "cobble", "" },
			{ "", "stone_brick", "cobble", " ", "cobble", "stone_brick", "cobble", "glass_pane", "glass_pane", "cobble", "stone_brick", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "cobble_wall", "", "cobble_wall", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "cobble", "brick", "cobble", "cobble", "cobble", "cobble", "cobble", "cobble", "plank_spruce", "" },
			{ "", "cobble", "brick_stair_e", "chimney", "brick_stair_w", "cobble", "", "", "", "", "plank_spruce", "" },
			{ "", "cobble", "", "", "", "cobble_stair_nu", "", "", "", "", "plank_spruce", "" },
			{ "", "cobble", "", "lantern", "", "cobble_slab_u", "", "lantern", "", "", "plank_spruce", "" },
			{ "", "cobble", "", "", "", "cobble_stair_su", "", "", "", "", "plank_spruce", "" },
			{ "", "cobble", "", "", "ladder_w", "cobble", "", "", "", "", "plank_spruce", "" },
			{ "", "cobble", "cobble", "cobble", "cobble", "cobble", "cobble", "cobble", "cobble", "cobble", "plank_spruce", "" },
			{ "", "", "oak_stair_e", "oak_stair_nu", "oak_stair_w", "", "", "", "", "", "", "" },
			{ "", "", "oak_stair_e", "oak_slab_u", "oak_stair_w", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "slate_slab_u", "oak_stair_w" },
			{ "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "cobble", "cobble", "plank_spruce", "plank_spruce", "plank_spruce", "oak_stair_w" },
			{ "", "plank_spruce", "oak_slab_u", "chimney", "oak_slab_u", "plank_spruce", "", "", "", "plank_spruce", "plank_spruce", "oak_stair_w" },
			{ "", "plank_spruce", "log_x", "log_x", "log_x", "plank_spruce", "", "", "", "plank_spruce", "plank_spruce", "oak_stair_w" },
			{ "", "plank_spruce", "oak_slab_u", "log_z", "oak_slab_u", "plank_spruce", "log_x", "log_x", "log_x", "plank_spruce", "plank_spruce", "oak_stair_w" },
			{ "", "plank_spruce", "log_x", "log_x", "log_x", "plank_spruce", "", "", "", "plank_spruce", "plank_spruce", "oak_stair_w" },
			{ "", "plank_spruce", "oak_slab_u", "log_z", "ladder_w", "plank_spruce", "", "", "", "plank_spruce", "plank_spruce", "oak_stair_w" },
			{ "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "cobble", "cobble", "plank_spruce", "plank_spruce", "plank_spruce", "oak_stair_w" },
			{ "", "spruce_stair_nu", "spruce_slab_u", "spruce_stair_nu", "spruce_slab_u", "spruce_stair_nu", "", "", "", "", "slate_slab_u", "oak_stair_w" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "slate_slab_u", "slate_slab_d", "slate_slab", "" },
			{ "", "log_y", "clay_wall_cross", "clay_wall_cross", "clay_wall_cross", "log_y", "plank_spruce", "plank_spruce", "plank_spruce", "slate_slab_d", "slate_slab", "" },
			{ "", "clay_wall", "hay_slab", "chimney", "hay_slab", "clay_wall", "", "plank_spruce", "plank_spruce", "slate_slab_d", "slate_slab", "" },
			{ "", "clay_wall", "hay_slab", "", "hay_slab", "clay_wall", "", "plank_spruce", "plank_spruce", "slate_slab_d", "slate_slab", "" },
			{ "", "clay_wall", "", "", "", "clay_wall", "", "plank_spruce", "plank_spruce", "slate_slab_d", "slate_slab", "" },
			{ "", "clay_wall", "", "", "", "clay_wall", "", "plank_spruce", "plank_spruce", "slate_slab_d", "slate_slab", "" },
			{ "", "clay_wall", "spruce_stair_n", "", "ladder_w", "clay_wall", "", "plank_spruce", "plank_spruce", "slate_slab_d", "slate_slab", "" },
			{ "", "clay_wall", "spruce_stair_wu", "spruce_stair_eu", "bookshelf", "clay_wall", "plank_spruce", "plank_spruce", "plank_spruce", "slate_slab_d", "slate_slab", "" },
			{ "", "log_y", "clay_wall_cross", "clay_wall_cross", "clay_wall_cross", "log_y", "", "", "slat_slab_u", "slate_slab_d", "slate_slab", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "oak_stair_su", "slate_slab_d", "slate_slab", "", "", "" },
			{ "", "log_y", "clay_wall", "clay_wall", "clay_wall", "log_y", "plank_spruce", "slate_slab_d", "slate_slab", "", "", "" },
			{ "", "clay_wall", "", "chimney", "", "clay_wall", "plank_spruce", "slate_slab_d", "slate_slab", "", "", "" },
			{ "", "glass_pane", "", "", "", "clay_wall", "plank_spruce", "slate_slab_d", "slate_slab", "", "", "" },
			{ "", "clay_wall", "", "", "", "clay_wall", "plank_spruce", "slate_slab_d", "slate_slab", "", "", "" },
			{ "", "clay_wall", "", "", "", "clay_wall", "plank_spruce", "slate_slab_d", "slate_slab", "", "", "" },
			{ "", "glass_pane", "", "", "", "clay_wall", "plank_spruce", "slate_slab_d", "slate_slab", "", "", "" },
			{ "", "clay_wall", "pot_rose", "", "oak_slab", "clay_wall", "plank_spruce", "slate_slab_d", "slate_slab", "", "", "" },
			{ "", "log_y", "clay_wall", "glass_pane", "clay_wall", "log_y", "oak_stair_nu", "slate_slab_d", "slate_slab", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "oak_stair_n", "", "", "", "", "" },
			{ "", "log_y", "clay_wall", "clay_wall", "clay_wall", "log_y", "oak_slab", "", "", "", "", "" },
			{ "", "clay_wall", "", "chimney", "", "clay_wall", "oak_slab", "", "", "", "", "" },
			{ "", "clay_wall", "", "", "", "clay_wall", "oak_slab", "", "", "", "", "" },
			{ "", "clay_wall", "", "lantern", "", "clay_wall", "oak_slab", "", "", "", "", "" },
			{ "", "clay_wall", "", "", "", "clay_wall", "oak_slab", "", "", "", "", "" },
			{ "", "clay_wall", "", "", "", "clay_wall", "oak_slab", "", "", "", "", "" },
			{ "", "clay_wall", "", "", "", "clay_wall", "oak_slab", "", "", "", "", "" },
			{ "", "log_y", "clay_wall", "glass_pane", "clay_wall", "log_y", "oak_stair_s", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "plank_spruce", "clay_wall", "clay_wall", "clay_wall", "plank_spruce", "", "", "", "", "", "" },
			{ "", "plank_spruce", "", "chimney", "", "plank_spruce", "", "", "", "", "", "" },
			{ "", "plank_spruce", "log_x", "log_x", "log_x", "plank_spruce", "", "", "", "", "", "" },
			{ "", "plank_spruce", "", "log_z", "", "plank_spruce", "", "", "", "", "", "" },
			{ "", "plank_spruce", "log_x", "log_x", "log_x", "plank_spruce", "", "", "", "", "", "" },
			{ "", "plank_spruce", "", "log_z", "", "plank_spruce", "", "", "", "", "", "" },
			{ "", "plank_spruce", "", "log_z", "", "plank_spruce", "", "", "", "", "", "" },
			{ "", "plank_spruce", "clay_wall", "clay_wall", "clay_wall", "plank_spruce", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "oak_stair_e", "slate_roof_wu", "", "", "", "slate_roof_eu", "oak_stair_w", "", "", "", "", "" },
			{ "oak_stair_e", "plank_spruce", "plank_spruce", "clay_wall", "plank_spruce", "plank_spruce", "oak_stair_w", "", "", "", "", "" },
			{ "oak_stair_e", "plank_spruce", "", "chimney", "", "plank_spruce", "oak_stair_w", "", "", "", "", "" },
			{ "oak_stair_e", "plank_spruce", "", "log_y", "", "plank_spruce", "oak_stair_w", "", "", "", "", "" },
			{ "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "oak_stair_w", "", "", "", "", "" },
			{ "oak_stair_e", "plank_spruce", "", "log_y", "", "plank_spruce", "oak_stair_w", "", "", "", "", "" },
			{ "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "oak_stair_w", "", "", "", "", "" },
			{ "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "oak_stair_w", "", "", "", "", "" },
			{ "oak_stair_e", "plank_spruce", "plank_spruce", "clay_wall", "plank_spruce", "plank_spruce", "oak_stair_w", "", "", "", "", "" },
			{ "oak_stair_e", "slate_roof_wu", "", "", "", "slate_roof_eu", "oak_stair_w", "", "", "", "", "" }
		},
		{
			{ "", "slate_roof_e", "slate_roof_wu", "", "slate_roof_eu", "slate_roof_w", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_w", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "chimney", "plank_spruce", "slate_roof_w", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "log_y", "plank_spruce", "slate_roof_w", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "log_y", "plank_spruce", "slate_roof_w", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_w", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "slate_roof_wu", "", "slate_roof_eu", "slate_roof_w", "", "", "", "", "", "" }
		},
		{
			{ "", "", "slate_roof_e", "oak_stair_su", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "chimney", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "oak_stair_nu", "slate_roof_w", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "oak_stair_n", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "" },
			{ "", "", "", "chimney", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_stair_s", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "chimney", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "" }
		}
	};

}
