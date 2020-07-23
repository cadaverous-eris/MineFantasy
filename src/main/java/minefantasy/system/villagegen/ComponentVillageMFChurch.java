package minefantasy.system.villagegen;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;

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
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.ChestGenHooks;

public class ComponentVillageMFChurch extends ComponentVillageMF {

	private boolean hasChest;
	private boolean hasFoodPrep;
	
	public ComponentVillageMFChurch() {
	}

	public ComponentVillageMFChurch(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFChurch create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				-1, 0, 0, 13, 22, 23, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFChurch(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}
	
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
        super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Chest", this.hasChest);
        par1NBTTagCompound.setBoolean("FoodPrep", this.hasFoodPrep);
    }
	
	@Override
    protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
        super.func_143011_b(par1NBTTagCompound);
        this.hasChest = par1NBTTagCompound.getBoolean("Chest");
        this.hasFoodPrep = par1NBTTagCompound.getBoolean("FoodPrep");
    }
	
	protected int getRoadLevel(World par1World, StructureBoundingBox par2StructureBoundingBox) {
		int x = this.getXWithOffset(5, -1);
		int z = this.getZWithOffset(5,  -1);
		
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
				this.field_143015_k = roadLvl + 1;
			} else {
				this.field_143015_k = roadLvl;
			}

            if (this.field_143015_k < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 21 - 1, 0);
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
		
		this.fillWithBlocks(world, structureboundingbox, 3, 1, 0, 7, 20, 4, 0, 0, false);
		this.fillWithBlocks(world, structureboundingbox, 0, 1, 4, 10, 20, 18, 0, 0, false);
		this.fillWithBlocks(world, structureboundingbox, 2, 1, 19, 8, 20, 21, 0, 0, false);
		
		this.buildFromBlueprint(world, rand, structureboundingbox, blueprint, key);
		
		if (!this.hasChest) {
            int i = this.getYWithOffset(19);
            int j = this.getXWithOffset(5, 3);
            int k = this.getZWithOffset(5, 3);

            if (structureboundingbox.isVecInside(j, i, k)) {
                this.hasChest = true;
                this.generateStructureChestContents(world, structureboundingbox, rand, 5, 19, 3, ChestGenHooks.getItems(DUNGEON_CHEST, rand), ChestGenHooks.getCount(DUNGEON_CHEST, rand));
            }
        }
		if (!this.hasFoodPrep) {
			int i = this.getYWithOffset(3);
	        int j = this.getXWithOffset(7, 20);
	        int k = this.getZWithOffset(7, 20);

	        if (structureboundingbox.isVecInside(j, i, k)) {
	        	this.hasFoodPrep = true;
	            world.setBlock(j, i, k, BlockListMF.foodPrep.blockID, 0, 2);
	            TileEntityPrepBlock tePrep = (TileEntityPrepBlock) world.getBlockTileEntity(j, i, k);
	            if (tePrep != null) {
	                tePrep.direction = (this.coordBaseMode) & 3;
	                tePrep.setInventorySlotContents(1, new ItemStack(Block.cloth.blockID, 1, 14));
	            }
	        }
        }
		
		this.placeDoorAtCurrentPosition(world, structureboundingbox, rand, 5, 1, 4, this.getMetadataWithOffset(Block.doorWood.blockID, 3));
		
		for (int i = 0; i < 3; i++) {
			if (this.getBlockIdAtCurrentPosition(world, 4 + i, 0, -1, structureboundingbox) == 0) {
	            this.placeBlockAtCurrentPosition(world, BlockListMF.stairCobbBrick.blockID, this.getMetadataWithOffset(BlockListMF.stairCobbBrick.blockID, 3), 4 + i, 0, 0, structureboundingbox);
	        } else {
	        	this.placeBlockAtCurrentPosition(world, BlockListMF.cobbBrick.blockID, 0, 4 + i, 0, 0, structureboundingbox);
	        }
		}
		
		for (int l = 0; l < 4; ++l) {
            for (int i1 = 3; i1 < 8; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, structureboundingbox);
            }
        }
		for (int l = 4; l < 19; ++l) {
            for (int i1 = 0; i1 < 11; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, structureboundingbox);
            }
        }
		for (int l = 19; l < 22; ++l) {
            for (int i1 = 2; i1 < 9; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, structureboundingbox);
            }
        }
		for (int l = 0; l < 22; l++) {
        	for (int i1 = 0; i1 < 11; i1++) {
        		this.clearCurrentPositionBlocksUpwards(world, i1, 21, l, structureboundingbox);
        	}
        }
		
        this.spawnVillagers(world, structureboundingbox, 5, 1, 11, 1);
        
		return true;
	}
	
	@Override
	protected int getVillagerType(int par1) {
		return 2;
	}
	
	private static String[][][] blueprint = new String[][][] {
		{
			{ "", "", "stone", "stone", "stone_brick", "stone_brick", "stone_brick", "stone", "stone", "", "" },
			{ "", "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "", "" },
			{ "", "", "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "", "" },
			{ "stone", "stone_brick", "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "stone", "stone" },
			{ "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick" },
			{ "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick" },
			{ "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone" },
			{ "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick" },
			{ "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick" },
			{ "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick" },
			{ "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone" },
			{ "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick" },
			{ "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick" },
			{ "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone" },
			{ "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick" },
			{ "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick" },
			{ "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone" },
			{ "stone", "stone", "stone_brick", "stone", "stone_brick", "plank_oak", "stone_brick", "stone", "stone", "stone_brick", "stone" },
			{ "", "", "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "stone", "", "", "" },
			{ "", "", "", "stone", "", "", "", "stone", "", "", "" }
		},
		{
			{ "", "", "stone", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone", "", "" },
			{ "", "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "", "" },
			{ "", "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "", "" },
			{ "stone", "stone_brick", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "stone", "stone" },
			{ "stone_brick", "", "", "oak_stair_n", "plank_oak", "plank_oak", "plank_oak", "oak_stair_n", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "spruce_stair_s", "spruce_stair_s", "spruce_stair_s", "", "spruce_stair_s", "spruce_stair_s", "spruce_stair_s", "", "stone_brick" },
			{ "stone", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "spruce_stair_s", "spruce_stair_s", "spruce_stair_s", "", "spruce_stair_s", "spruce_stair_s", "spruce_stair_s", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "spruce_stair_s", "spruce_stair_s", "spruce_stair_s", "", "spruce_stair_s", "spruce_stair_s", "spruce_stair_s", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "spruce_stair_s", "spruce_stair_s", "spruce_stair_s", "", "spruce_stair_s", "spruce_stair_s", "spruce_stair_s", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone", "stone_brick", "stone_brick", "stone", "stone_brick", " ", "stone_brick", "stone", "stone_brick", "stone_brick", "stone" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone", "", "", "" }
		},
		{
			{ "", "", "stone_brick", "stone_brick", "stone_brick", "cobble", "cobble", "stone_brick", "stone_brick", "", "" },
			{ "", "", "cobble", "spruce_stair_w", "", "", "", "fence", "stone_brick", "", "" },
			{ "", "", "stone_brick", "", "", "oak_slab", "", "", "cobble", "", "" },
			{ "stone_brick", "stone_brick", "cobble", "", "", "log_y", "", "", "cobble", "stone_brick", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "stone_brick", "cobble", "stone_brick", "cobble", " ", "stone_brick", "stone_brick", "cobble", "stone_brick", "stone_brick" },
			{ "", "", "", "cobble", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "stone_brick", "cobble", "cobble", "cobble", "cobble", "cobble", "stone_brick", "", "" },
			{ "", "", "cobble", "", "", "", "", "benchtop", "cobble", "", "" },
			{ "", "", "cobble", "", "", "", "", "", "cobble", "", "" },
			{ "stone_brick", "cobble", "cobble", "", "", "spruce_slab", "", "", "cobble", "cobble", "stone_brick" },
			{ "cobble", "", "lantern", "", "", "", "", "", "lantern", "", "stone_brick" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "lantern", "", "", "", "", "", "lantern", "", "cobble" },
			{ "stone_brick", "cobble", "cobble", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "cobble", "cobble", "stone_brick" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "", "" },
			{ "", "", "cobble", "", "", "", "", "", "cobble", "", "" },
			{ "", "", "cobble", "lantern", "", "", "", "lantern", "cobble", "", "" },
			{ "stone_brick", "cobble", "cobble", "", "", "", "", "", "cobble", "cobble", "stone_brick" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "glass_pane", "", "", "", "", "", "", "", "", "", "glass_pane" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "lantern", "", "", "", "", "cobble" },
			{ "stone_brick", "cobble", "cobble", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "cobble", "cobble", "stone_brick" },
			{ "", "", "", "cobble", "", "lantern", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick_stair_wu", "", "stone_brick_stair_eu", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "stone_brick", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "stone_brick", "", "" },
			{ "", "", "cobble", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "cobble", "", "" },
			{ "", "", "cobble", "", "", "", "", "", "cobble", "", "" },
			{ "stone_brick", "cobble", "cobble", "cobble_stair_wu", "", "", "", "cobble_stair_eu", "cobble", "cobble", "stone_brick" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "lantern", "", "", "", "lantern", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "lantern", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "lantern", "", "", "", "lantern", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "lantern", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "lantern", "", "", "", "lantern", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "cobble", "", "", "", "", "", "", "", "", "", "cobble" },
			{ "stone_brick", "cobble", "cobble", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "cobble", "cobble", "stone_brick" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick", "stone_brick_slab_u", "stone_brick", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "stone_brick_stair_n", "", "", "", "", "", "stone_brick_stair_n", "", "" },
			{ "", "", "stone_brick", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "stone_brick", "", "" },
			{ "", "", "cobble", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "cobble", "", "" },
			{ "stone_brick", "cobble", "cobble", "cobble", "cobble_stair_wu", "cobble_slab_u", "cobble_stair_eu", "cobble", "cobble", "cobble", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "", "", "", "", "", "stone_brick" },
			{ "stone_brick", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "stone_brick" },
			{ "stone_brick", "", "", "", "", "log_z", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "log_z", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "log_z", "", "", "", "", "stone_brick" },
			{ "stone_brick", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "stone_brick" },
			{ "stone_brick", "", "", "", "", "log_z", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "log_z", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "log_z", "", "", "", "", "stone_brick" },
			{ "stone_brick", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "log_x", "stone_brick" },
			{ "stone_brick", "", "", "", "", "log_z", "", "", "", "", "stone_brick" },
			{ "stone_brick", "", "", "", "", "log_z", "", "", "", "", "stone_brick" },
			{ "stone_brick", "cobble", "cobble", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "cobble", "cobble", "stone_brick" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "stone_brick", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "stone_brick_stair_n", "", "", "", "", "", "stone_brick_stair_n", "", "" },
			{ "", "", "stone_brick", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "stone_brick", "", "" },
			{ "stone_brick", "cobble", "cobble", "cobble", "cobble", "cobble", "cobble", "cobble", "cobble", "cobble", "stone_brick" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "log_y", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "log_y", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "log_y", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "slate_roof_e", "plank_spruce", "", "", "", "", "", "", "", "plank_spruce", "slate_roof_w" },
			{ "stone_brick", "cobble", "cobble", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "cobble", "cobble", "stone_brick" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "stone_brick_stair_n", "", "", "", "", "", "stone_brick_stair_n", "", "" },
			{ "stone_brick_stair_w", "stone_brick", "stone_brick", "cobble", "cobble", "cobble", "cobble", "cobble", "stone_brick", "stone_brick", "stone_brick_stair_e" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "log_y", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "log_y", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "log_y", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "stone_brick_stair_w", "stone_brick", "cobble", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "cobble", "stone_brick", "stone_brick_stair_e" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone_brick_stair_w", "stone_brick", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "stone_brick", "stone_brick_stair_e", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "log_y", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "log_y", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "log_y", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "stone_brick_stair_w", "stone_brick", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "stone_brick", "stone_brick_stair_e", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "stone_brick_stair_w", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "stone_brick_stair_e", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "log_y", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "log_y", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "log_y", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "stone_brick_stair_w", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "stone_brick_stair_e", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick_slab_u", "", "", "", "stone_brick_slab_u", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "stone_brick_slab_u", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "log_y", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "log_y", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "log_y", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "", "", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "log_y", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "log_y", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "log_y", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick_stair_w", "stone_brick", "cobble", "stone_brick", "stone_brick_stair_e", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "lantern", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "stone_brick_stair_w", "stone_brick", "stone_brick_stair_e", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick_slab", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick_slab", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick_slab", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick_slab", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick_slab", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick_slab", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick_slab", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "stone_brick_slab", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick_slab_u", "", "", "", "stone_brick_slab_u", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "stone_brick_slab_u", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "cobble", "", "", "", "cobble", "", "", "" },
			{ "", "", "", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick_stair_w", "stone_brick", "stone_brick_stair_e", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick_stair_n", "", "chest", "", "stone_brick_stair_n", "", "", "" },
			{ "", "", "", "stone_brick", "", "lantern", "", "stone_brick", "", "", "" },
			{ "", "", "", "stone_brick_stair_s", "", "", "", "stone_brick_stair_s", "", "", "" },
			{ "", "", "", "stone_brick", "stone_brick_stair_w", "stone_brick", "stone_brick_stair_e", "stone_brick", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "", "stone_brick_slab", "", "stone_brick", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick_slab", "", "", "", "stone_brick_slab", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "stone_brick", "", "stone_brick_slab", "", "stone_brick", "", "", "" }
		}
	};

}
