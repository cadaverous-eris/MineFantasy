package minefantasy.system.villagegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import minefantasy.block.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageMFWindmill extends ComponentVillageMF {

	public ComponentVillageMFWindmill() {
	}

	public ComponentVillageMFWindmill(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFWindmill create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 13, 18, 8, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFWindmill(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}
	
	protected int getRoadLevel(World par1World, StructureBoundingBox par2StructureBoundingBox) {
		int x = this.getXWithOffset(6, -1);
		int z = this.getZWithOffset(6,  -1);
		
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

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 18 - 1, 0);
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
		key.put("chimney", new int[] {BlockListMF.chimney.blockID, 2});
		key.put("log_x", new int[] {Block.wood.blockID, 4});
		key.put("log_y", new int[] {Block.wood.blockID, 0});
		key.put("log_z", new int[] {Block.wood.blockID, 8});
		key.put("glass_pane", new int[] {Block.thinGlass.blockID});
		key.put("iron_bar", new int[] {Block.fenceIron.blockID});
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
		key.put("stone_brick_stair_n", new int[] {Block.stairsStoneBrick.blockID, 3});
		key.put("stone_brick_stair_s", new int[] {Block.stairsStoneBrick.blockID, 2});
		key.put("stone_brick_stair_w", new int[] {Block.stairsStoneBrick.blockID, 1});
		key.put("stone_brick_stair_e", new int[] {Block.stairsStoneBrick.blockID, 0});
		key.put("stone_brick_stair_nu", new int[] {Block.stairsStoneBrick.blockID, 7});
		key.put("stone_brick_stair_su", new int[] {Block.stairsStoneBrick.blockID, 6});
		key.put("stone_brick_stair_wu", new int[] {Block.stairsStoneBrick.blockID, 5});
		key.put("stone_brick_stair_eu", new int[] {Block.stairsStoneBrick.blockID, 4});
		key.put("cobble_stair_n", new int[] {Block.stairsCobblestone.blockID, 3});
		key.put("cobble_stair_s", new int[] {Block.stairsCobblestone.blockID, 2});
		key.put("cobble_stair_w", new int[] {Block.stairsCobblestone.blockID, 1});
		key.put("cobble_stair_e", new int[] {Block.stairsCobblestone.blockID, 0});
		key.put("cobble_stair_nu", new int[] {Block.stairsCobblestone.blockID, 7});
		key.put("cobble_stair_su", new int[] {Block.stairsCobblestone.blockID, 6});
		key.put("cobble_stair_wu", new int[] {Block.stairsCobblestone.blockID, 5});
		key.put("cobble_stair_eu", new int[] {Block.stairsCobblestone.blockID, 4});
		key.put("hay_slab", new int[] {BlockListMF.woodSingleSlab.blockID, 3});
		key.put("oak_slab", new int[] {Block.woodSingleSlab.blockID});
		key.put("spruce_slab", new int[] {Block.woodSingleSlab.blockID, 1});
		key.put("spruce_slab_u", new int[] {Block.woodSingleSlab.blockID, 9});
		key.put("stone_brick_slab", new int[] {Block.stoneSingleSlab.blockID, 5});
		key.put("stone_brick_slab_u", new int[] {Block.stoneSingleSlab.blockID, 13});
		key.put("cobble_slab", new int[] {Block.stoneSingleSlab.blockID, 3});
		key.put("cobble_slab_u", new int[] {Block.stoneSingleSlab.blockID, 11});
		key.put("cobble_wall", new int[] {Block.cobblestoneWall.blockID});
		key.put("limestone", new int[] {BlockListMF.limestone.blockID});
		
		this.fillWithBlocks(world, sbb, 0, 1, 0, 12, 17, 7, 0, 0, false);
		
		this.buildFromBlueprint(world, rand, sbb, blueprint, key);
		
		this.placeDoorAtCurrentPosition(world, sbb, rand, 6, 1, 1, this.getMetadataWithOffset(Block.doorWood.blockID, 3));
		
		if (this.getBlockIdAtCurrentPosition(world, 6, 0, -1, sbb) == 0) {
            this.placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 6, 0, 0, sbb);
        } else {
        	this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 6, 0, 0, sbb);
        }
		
		for (int l = 1; l < 8; ++l) {
            for (int i1 = 3; i1 < 10; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, sbb);
            }
        }
		for (int l = 0; l < 8; ++l) {
            for (int i1 = 0; i1 < 13; ++i1) {
                this.clearCurrentPositionBlocksUpwards(world, i1, 18, l, sbb);
            }
        }
		
		return true;
	}
	
	private static String[][][] blueprint = new String[][][] {
		{
			{ "", "", "", "stone", "stone", "stone_brick", "stone_brick", "stone", "stone", "stone", "", "", "" },
			{ "", "", "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone", "", "", "" },
			{ "", "", "", "stone_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone", "stone_brick", "stone", "cobble_brick", "stone_brick", "stone", "stone", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "stone", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone", "stone", "", "", "" },
			{ "", "", "", "stone_brick", "oak_stair_e", "limestone", "limestone", "limestone", "oak_stair_w", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "oak_stair_e", "limestone", "limestone", "limestone", "oak_stair_w", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "oak_stair_e", "limestone", "limestone", "limestone", "oak_stair_w", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "oak_stair_n", "oak_stair_n", "oak_stair_n", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone", "stone_brick", "stone_brick", " ", "cobble", "stone_brick", "stone", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "stone_brick", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "plank_oak", "", "oak_stair_e", "", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "", "", "log_y", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick", "cobble", " ", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "", "plank_oak", "oak_stair_wu", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "log_y", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_spruce", "", "log_y", "", "plank_spruce", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_spruce", "", "", "", "plank_spruce", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_spruce", "", "lantern", "", "plank_spruce", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "stone_brick_slab", "", "stone_brick_stair_s", "", "stone_brick_stair_s", "", "stone_brick_slab", "", "", "" },
			{ "", "", "", "", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "", "", "", "" },
			{ "", "", "", "stone_brick_stair_e", "stone_brick", "", "log_y", "", "stone_brick", "stone_brick_stair_w", "", "", "" },
			{ "", "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "", "" },
			{ "", "", "", "stone_brick_stair_e", "stone_brick", "", "", "", "stone_brick", "stone_brick_stair_w", "", "", "" },
			{ "", "", "", "", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "", "", "", "" },
			{ "", "", "", "stone_brick_slab", "", "stone_brick_stair_n", "", "stone_brick_stair_n", "", "stone_brick_slab", "", "", "" },
			{ "", "", "", "fence", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "stone_brick", "cobble", "stone_brick", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "", "log_y", "", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "stone_brick", "cobble", "stone_brick", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "fence", "fence", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "cobble", "cobble", "stone_brick", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "log_y", "", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "", "", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "cobble", "cobble", "stone_brick", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "fence", "fence", "fence", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "log_y", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "fence", "fence", "fence", "", "", "", "fence", "fence", "fence" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "cobble", "cobble", "stone_brick", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "log_y", "", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "", "", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "fence", "fence", "", "", "fence", "fence", "fence", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "plank_spruce", "log_y", "plank_spruce", "cobble", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "plank_spruce", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "", "", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "stone_brick", "cobble", "cobble", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "fence", "", "fence", "fence", "fence", "", "" }
		},
		{
			{ "", "", "", "", "", "", "log_z", "", "", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "stone_brick", "log_z", "cobble", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "log_z", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "cobble", "plank_spruce", "log_z", "plank_spruce", "cobble", "", "", "", "" },
			{ "", "", "", "", "cobble", "", "log_z", "", "cobble", "", "", "", "" },
			{ "", "", "", "", "stone_brick", "cobble", "log_z", "cobble", "stone_brick", "", "", "", "" },
			{ "", "", "", "", "", "", "log_z", "", "", "", "", "", "" },
			{ "", "", "", "fence", "fence", "fence", "log_z", "fence", "fence", "fence", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "plank_spruce", "cobble", "cobble", "cobble", "plank_spruce", "", "", "", "" },
			{ "", "", "", "", "plank_spruce", "", "", "", "plank_spruce", "", "", "", "" },
			{ "", "", "", "", "plank_spruce", "", "plank_spruce", "", "plank_spruce", "", "", "", "" },
			{ "", "", "", "", "plank_spruce", "", "", "", "plank_spruce", "", "", "", "" },
			{ "", "", "", "", "plank_spruce", "cobble", "cobble", "cobble", "plank_spruce", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "fence", "fence", "fence", "", "fence", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "oak_stair_e", "slate_roof_wu", "", "", "", "slate_roof_eu", "oak_stair_w", "", "", "" },
			{ "", "", "", "oak_stair_e", "plank_spruce", "plank_spruce", "cobble", "plank_spruce", "plank_spruce", "oak_stair_w", "", "", "" },
			{ "", "", "", "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "oak_stair_w", "", "", "" },
			{ "", "", "", "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "oak_stair_w", "", "", "" },
			{ "", "", "", "oak_stair_e", "plank_spruce", "", "lantern", "", "plank_spruce", "oak_stair_w", "", "", "" },
			{ "", "", "", "oak_stair_e", "plank_spruce", "plank_spruce", "cobble", "plank_spruce", "plank_spruce", "oak_stair_w", "", "", "" },
			{ "", "", "", "oak_stair_e", "slate_roof_wu", "", "", "", "slate_roof_eu", "oak_stair_w", "", "", "" },
			{ "", "fence", "fence", "fence", "", "", "fence", "fence", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "slate_roof_e", "slate_roof_wu", "", "slate_roof_eu", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "slate_roof_wu", "", "slate_roof_eu", "slate_roof_w", "", "", "", "" },
			{ "fence", "fence", "fence", "", "", "", "fence", "fence", "fence", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "slate_roof_e", "oak_stair_su", "slate_roof_w", "", "", "", "", "" },
			{ "", "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "" },
			{ "", "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "" },
			{ "", "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "" },
			{ "", "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "" },
			{ "", "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "" },
			{ "", "", "", "", "", "slate_roof_e", "oak_stair_nu", "slate_roof_w", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "fence", "fence", "fence", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "oak_stair_n", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "oak_slab", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "oak_slab", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "oak_slab", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "oak_slab", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "oak_slab", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "oak_stair_s", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "fence", "fence", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "fence", "", "", "" }
		}
	};

}
