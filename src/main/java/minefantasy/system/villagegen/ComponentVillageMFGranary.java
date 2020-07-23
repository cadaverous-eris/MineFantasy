package minefantasy.system.villagegen;

import static net.minecraftforge.common.ChestGenHooks.VILLAGE_BLACKSMITH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityAnvil;
import minefantasy.block.tileentity.TileEntityForge;
import minefantasy.block.tileentity.TileEntityPrepBlock;
import minefantasy.block.tileentity.TileEntitySmelter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.ChestGenHooks;

public class ComponentVillageMFGranary extends ComponentVillageMF {

	public ComponentVillageMFGranary() {
	}

	public ComponentVillageMFGranary(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFGranary create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 9, 7, 7, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFGranary(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}
	
	protected int getRoadLevel(World par1World, StructureBoundingBox par2StructureBoundingBox) {
		int x = this.getXWithOffset(4, -1);
		int z = this.getZWithOffset(4,  -1);
		
		int level = par1World.getTopSolidOrLiquidBlock(x, z);
		
		if (level <= 0) {
			return -1;
		}
		
		return level;
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureboundingbox) {
		if (this.field_143015_k < 0) {
			this.field_143015_k = this.getRoadLevel(world, structureboundingbox);
			
            if (this.field_143015_k < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
        }
		
		Map<String, int[]> key = new HashMap<String, int[]>();
		key.put("stone", new int[] {BlockListMF.stoneDoubleSlab.blockID, 3});
		key.put("stone_brick", new int[] {Block.stoneBrick.blockID});
		key.put("cobble", new int[] {Block.cobblestone.blockID});
		key.put("cobble_brick", new int[] {BlockListMF.cobbBrick.blockID});
		key.put("clay_wall", new int[] {BlockListMF.clayWall.blockID});
		key.put("plank_oak", new int[] {Block.planks.blockID});
		key.put("plank_spruce", new int[] {Block.planks.blockID, 1});
		key.put("fence", new int[] {Block.fence.blockID});
		key.put("lantern", new int[] {BlockListMF.lantern.blockID});
		key.put("chimney", new int[] {BlockListMF.chimney.blockID, 2});
		key.put("log_x", new int[] {Block.wood.blockID, 4});
		key.put("log_y", new int[] {Block.wood.blockID, 0});
		key.put("log_z", new int[] {Block.wood.blockID, 8});
		key.put("hay_x", new int[] {Block.hay.blockID, 4});
		key.put("hay_y", new int[] {Block.hay.blockID, 0});
		key.put("hay_z", new int[] {Block.hay.blockID, 8});
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
		key.put("oak_slab_u", new int[] {Block.woodSingleSlab.blockID, 8});
		key.put("spruce_slab", new int[] {Block.woodSingleSlab.blockID, 1});
		key.put("spruce_slab_u", new int[] {Block.woodSingleSlab.blockID, 9});
		key.put("stone_brick_slab", new int[] {Block.stoneSingleSlab.blockID, 5});
		key.put("stone_brick_slab_u", new int[] {Block.stoneSingleSlab.blockID, 13});
		key.put("cobble_slab", new int[] {Block.stoneSingleSlab.blockID, 3});
		key.put("cobble_slab_u", new int[] {Block.stoneSingleSlab.blockID, 11});
		key.put("cobble_wall", new int[] {Block.cobblestoneWall.blockID});
		
		this.fillWithBlocks(world, structureboundingbox, 0, 0, 0, 8, 6, 6, 0, 0, false);
		
		this.buildFromBlueprint(world, rand, structureboundingbox, blueprint, key);
		
		this.placeDoorAtCurrentPosition(world, structureboundingbox, rand, 4, 2, 2, this.getMetadataWithOffset(Block.doorWood.blockID, 3)); 
		
		this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, 1, -1, 2, structureboundingbox);
		this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, 1, -1, 5, structureboundingbox);
		this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, 4, -1, 2, structureboundingbox);
		this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, 4, -1, 5, structureboundingbox);
		this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, 7, -1, 2, structureboundingbox);
		this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, 7, -1, 5, structureboundingbox);
        for (int l = 0; l < 7; l++) {
        	for (int i1 = 0; i1 < 9; i1++) {
        		this.clearCurrentPositionBlocksUpwards(world, i1, 7, l, structureboundingbox);
        	}
        }
        
		return true;
	}
	
	private static String[][][] blueprint = new String[][][] {
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "", "", "stone", "", "", "stone", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "", "", "stone", "", "", "stone", "" },
			{ "", "", "", "", "oak_stair_su", "", "", "", "" },
			{ "", "", "", "", "oak_stair_n", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "stone_brick_stair_wu", "stone_brick_stair_eu", "stone_brick", "stone_brick_stair_wu", "stone_brick_stair_eu", "stone_brick", "" },
			{ "", "stone_brick_stair_nu", "oak_slab_u", "oak_slab_u", "stone_brick_stair_nu", "oak_slab_u", "oak_slab_u", "stone_brick_stair_nu", "" },
			{ "", "stone_brick_stair_su", "oak_slab_u", "oak_slab_u", "stone_brick_stair_su", "oak_slab_u", "oak_slab_u", "stone_brick_stair_su", "" },
			{ "", "stone_brick", "stone_brick_stair_wu", "stone_brick_stair_eu", "stone_brick", "stone_brick_stair_wu", "stone_brick_stair_eu", "stone_brick", "" },
			{ "", "", "", "", "oak_stair_n", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "cobble", "cobble", "clay_wall", "clay_wall", "clay_wall", "cobble", "cobble", "" },
			{ "", "cobble", "hay_z", "hay_x", "hay_x", "hay_y", "hay_y", "cobble", "" },
			{ "", "clay_wall", "hay_z", "", "", "hay_z", "hay_z", "cobble", "" },
			{ "", "cobble", "cobble", "clay_wall", " ", "cobble", "cobble", "cobble", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "cobble", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "cobble", "" },
			{ "", "clay_wall", "hay_z", "", "", "hay_x", "hay_y", "clay_wall", "" },
			{ "", "clay_wall", "", "", "", "", "hay_z", "clay_wall", "" },
			{ "", "cobble", "clay_wall", "clay_wall", " ", "clay_wall", "clay_wall", "cobble", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "hay_roof_s", "oak_stair_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "oak_stair_s", "hay_roof_s" },
			{ "hay_roof_nu", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "hay_roof_nu" },
			{ "", "clay_wall", "", "", "", "", "hay_z", "clay_wall", "" },
			{ "", "clay_wall", "", "", "lantern", "", "", "clay_wall", "" },
			{ "hay_roof_su", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "hay_roof_su" },
			{ "hay_roof_n", "oak_stair_n", "hay_roof_n", "hay_roof_n", "hay_roof_n", "hay_roof_n", "hay_roof_n", "oak_stair_n", "hay_roof_n" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "hay_roof_s", "oak_stair_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "oak_stair_s", "hay_roof_s" },
			{ "hay_roof_nu", "clay_wall", "oak_stair_nu", "oak_stair_nu", "oak_stair_nu", "oak_stair_nu", "oak_stair_nu", "clay_wall", "hay_roof_nu" },
			{ "hay_roof_su", "clay_wall", "oak_stair_su", "oak_stair_su", "oak_stair_su", "oak_stair_su", "oak_stair_su", "clay_wall", "hay_roof_su" },
			{ "hay_roof_n", "oak_stair_n", "hay_roof_n", "hay_roof_n", "hay_roof_n", "hay_roof_n", "hay_roof_n", "oak_stair_n", "hay_roof_n" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "hay_roof_s", "oak_stair_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "oak_stair_s", "hay_roof_s" },
			{ "hay_roof_n", "oak_stair_n", "hay_roof_n", "hay_roof_n", "hay_roof_n", "hay_roof_n", "hay_roof_n", "oak_stair_n", "hay_roof_n" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		}
	};

}
