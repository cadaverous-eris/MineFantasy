package minefantasy.system.villagegen;

import static net.minecraftforge.common.ChestGenHooks.VILLAGE_BLACKSMITH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityAnvil;
import minefantasy.block.tileentity.TileEntityFirepit;
import minefantasy.block.tileentity.TileEntityForge;
import minefantasy.block.tileentity.TileEntityPrepBlock;
import minefantasy.block.tileentity.TileEntityRoast;
import minefantasy.block.tileentity.TileEntitySmelter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.ChestGenHooks;

public class ComponentVillageMFSmithy extends ComponentVillageMF {

	private boolean hasChest;
	private boolean hasFoodPrep;
	private boolean hasBloomery;
	private boolean hasForge;
	private boolean hasAnvil;
	
	public ComponentVillageMFSmithy() {
	}

	public ComponentVillageMFSmithy(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFSmithy create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 13, 9, 10, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFSmithy(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}
	
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
        super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Chest", this.hasChest);
        par1NBTTagCompound.setBoolean("FoodPrep", this.hasFoodPrep);
        par1NBTTagCompound.setBoolean("Bloomery", this.hasBloomery);
        par1NBTTagCompound.setBoolean("Forge", this.hasForge);
        par1NBTTagCompound.setBoolean("Anvil", this.hasAnvil);
    }
	
	@Override
    protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
        super.func_143011_b(par1NBTTagCompound);
        this.hasChest = par1NBTTagCompound.getBoolean("Chest");
        this.hasFoodPrep = par1NBTTagCompound.getBoolean("FoodPrep");
        this.hasBloomery = par1NBTTagCompound.getBoolean("Bloomery");
        this.hasForge = par1NBTTagCompound.getBoolean("Forge");
        this.hasAnvil = par1NBTTagCompound.getBoolean("Anvil");
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
				this.field_143015_k = roadLvl;
			} else {
				this.field_143015_k = roadLvl - 1;
			}

            if (this.field_143015_k < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
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
		key.put("crafting_table", new int[] {Block.workbench.blockID});
		
		this.fillWithBlocks(world, structureboundingbox, 0, 1, 0, 6, 8, 9, 0, 0, false);
		this.fillWithBlocks(world, structureboundingbox, 7, 1, 3, 12, 8, 9, 0, 0, false);
		
		this.buildFromBlueprint(world, rand, structureboundingbox, blueprint, key);
		
		if (!this.hasChest) {
            int i = this.getYWithOffset(1);
            int j = this.getXWithOffset(4, 7);
            int k = this.getZWithOffset(4, 7);

            if (structureboundingbox.isVecInside(j, i, k)) {
                this.hasChest = true;
                this.generateStructureChestContents(world, structureboundingbox, rand, 4, 1, 7, ChestGenHooks.getItems(VILLAGE_BLACKSMITH, rand), ChestGenHooks.getCount(VILLAGE_BLACKSMITH, rand));
            }
        }
		if (!this.hasFoodPrep) {
			int i = this.getYWithOffset(2);
	        int j = this.getXWithOffset(2, 3);
	        int k = this.getZWithOffset(2, 3);

	        if (structureboundingbox.isVecInside(j, i, k)) {
	            this.hasFoodPrep = true;
	            world.setBlock(j, i, k, BlockListMF.foodPrep.blockID, 0, 2);
	            TileEntityPrepBlock tePrep = (TileEntityPrepBlock) world.getBlockTileEntity(j, i, k);
	            if (tePrep != null) {
	                tePrep.direction = (this.coordBaseMode) & 3;
	                tePrep.setInventorySlotContents(1, new ItemStack(Block.planks.blockID, 1, 1));
	            }
	        }
        }
		if (!this.hasBloomery) {
			int i = this.getYWithOffset(1);
	        int j = this.getXWithOffset(6, 7);
	        int k = this.getZWithOffset(6, 7);

	        if (structureboundingbox.isVecInside(j, i, k)) {
	            this.hasBloomery = true;
	            world.setBlock(j, i, k, BlockListMF.smelter.blockID, 0, 2);
	            TileEntitySmelter teBloomery = (TileEntitySmelter) world.getBlockTileEntity(j, i, k);
	            if (teBloomery != null) {
	                teBloomery.direction = (this.coordBaseMode) & 3;
	                teBloomery.setInventorySlotContents(0, new ItemStack(Item.coal.itemID, rand.nextInt(3) + 1, rand.nextInt(1)));
	            }
	        }
        }
		if (!this.hasForge) {
			int i = this.getYWithOffset(1);
	        int j = this.getXWithOffset(9, 7);
	        int k = this.getZWithOffset(9, 7);

	        if (structureboundingbox.isVecInside(j, i, k)) {
	            this.hasForge = true;
	            world.setBlock(j, i, k, BlockListMF.forge.blockID, 2, 2);
	            TileEntityForge teForge = (TileEntityForge) world.getBlockTileEntity(j, i, k);
	            if (teForge != null) {
	                teForge.direction = (this.coordBaseMode) & 3;
	            }
	        }
        }
		if (!this.hasAnvil) {
			int i = this.getYWithOffset(1);
	        int j = this.getXWithOffset(9, 5);
	        int k = this.getZWithOffset(9, 5);

	        if (structureboundingbox.isVecInside(j, i, k)) {
	            this.hasAnvil = true;
	            world.setBlock(j, i, k, BlockListMF.anvil.blockID, 0, 2);
	            TileEntityAnvil teAnvil = (TileEntityAnvil) world.getBlockTileEntity(j, i, k);
	            if (teAnvil != null) {
	            	teAnvil.direction = (this.coordBaseMode + 2) & 3;
	            }
	        }
        }
		
		this.placeDoorAtCurrentPosition(world, structureboundingbox, rand, 3, 1, 2, this.getMetadataWithOffset(Block.doorWood.blockID, 3));
		this.placeDoorAtCurrentPosition(world, structureboundingbox, rand, 5, 1, 6, this.getMetadataWithOffset(Block.doorWood.blockID, 2));
		
		if (this.getBlockIdAtCurrentPosition(world, 5, 0, 0, structureboundingbox) == 0 && this.getBlockIdAtCurrentPosition(world, 5, 0, -1, structureboundingbox) == 0) {
            this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 5, -1, 0, structureboundingbox);
        } else {
        	this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 5, 0, 0, structureboundingbox);
        }
		if (this.getBlockIdAtCurrentPosition(world, 5, 0, 1, structureboundingbox) == 0 && this.getBlockIdAtCurrentPosition(world, 5, 0, 0, structureboundingbox) == 0) {
            this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 5, -1, 1, structureboundingbox);
        } else {
        	this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 5, 0, 1, structureboundingbox);
        }
		if (this.getBlockIdAtCurrentPosition(world, 5, 0, 1, structureboundingbox) == 0) {
            this.placeBlockAtCurrentPosition(world, BlockListMF.stairCobbBrick.blockID, this.getMetadataWithOffset(BlockListMF.stairCobbBrick.blockID, 1), 4, 0, 1, structureboundingbox);
        } else {
        	this.placeBlockAtCurrentPosition(world, BlockListMF.cobbBrick.blockID, 0, 4, 0, 1, structureboundingbox);
        }
		for (int i = 6; i < 11; i++) {
			if (this.getBlockIdAtCurrentPosition(world, i, 0, 3, structureboundingbox) == 0) {
				this.placeBlockAtCurrentPosition(world, BlockListMF.stairCobbBrick.blockID, this.getMetadataWithOffset(BlockListMF.stairCobbBrick.blockID, 3), i, 0, 4, structureboundingbox);
			} else {
				this.placeBlockAtCurrentPosition(world, BlockListMF.cobbBrick.blockID, 0, i, 0, 4, structureboundingbox);
			}
		}
		for (int i = 5; i < 8; i++) {
			if (this.getBlockIdAtCurrentPosition(world, 12, 0, i, structureboundingbox) == 0) {
				this.placeBlockAtCurrentPosition(world, BlockListMF.stairCobbBrick.blockID, this.getMetadataWithOffset(BlockListMF.stairCobbBrick.blockID, 1), 11, 0, i, structureboundingbox);
			} else {
				this.placeBlockAtCurrentPosition(world, BlockListMF.cobbBrick.blockID, 0, 11, 0, i, structureboundingbox);
			}
		}
		
		for (int l = 2; l < 9; ++l) {
            for (int i1 = 1; i1 < 6; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, structureboundingbox);
            }
        }
		for (int l = 4; l < 9; ++l) {
            for (int i1 = 6; i1 < 12; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, structureboundingbox);
            }
        }
		for (int l = 0; l < 2; ++l) {
            for (int i1 = 2; i1 < 5; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, structureboundingbox);
            }
        }
        this.fillCurrentPositionBlocksDownwards(world, Block.cobblestone.blockID, 0, 5, -1, 0, structureboundingbox);
        this.fillCurrentPositionBlocksDownwards(world, Block.cobblestone.blockID, 0, 5, -1, 1, structureboundingbox);
        for (int l = 0; l < 10; l++) {
        	for (int i1 = 0; i1 < 13; i1++) {
        		this.clearCurrentPositionBlocksUpwards(world, i1, 9, l, structureboundingbox);
        	}
        }

        this.spawnVillagers(world, structureboundingbox, 2, 1, 5, 1);
        
		return true;
	}
	
	@Override
	protected int getVillagerType(int par1) {
		return 3;
	}
	
	private static String[][][] blueprint = new String[][][] {
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone", "stone", "stone_brick", "stone", "stone", "stone", "stone_brick", "stone_brick", "stone", "stone", "" },
			{ "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "", "" },
			{ "", "stone_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "", "" },
			{ "", "stone_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "", "" },
			{ "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "", "", "", "", "", "stone_brick", "" },
			{ "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "stone", "", "", "", "", "", "", "" },
			{ "", "stone", "stone", "cobble_brick", "stone", "stone", "", "", "", "", "", "", "" },
			{ "", "", "stone_brick", "cobble_brick", "", "", "", "", "", "", "", "", "" },
			{ "", "", "stone_brick", "stone_brick", "stone_brick", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "stone", "stone_brick", "stone_brick", "cobble", "stone_brick", "stone", "" },
			{ "", "stone_brick", "hay_slab", "crafting_table", "chest", "stone_brick", "bloomery", "", "cobble", "forge", "cobble", "", "" },
			{ "", "stone_brick", "hay_slab", "", "", " ", "", "", "", "", "", "", "" },
			{ "", "cobble", "", "", "", "stone_brick", "", "", "", "anvil", "", "", "" },
			{ "", "stone_brick", "spruce_stair_n", "", "", "cobble", "", "", "", "", "", "cobble", "" },
			{ "", "stone", "fence", "", "", "stone_brick", "", "", "", "", "", "", "" },
			{ "", "stone", "stone_brick", " ", "stone_brick", "stone", "", "", "", "", "", "", "" },
			{ "", "", "cobble_wall", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "cobble_wall", "cobble_wall", "cobble_wall", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "cobble", "iron_bar", "stone_brick", "stone_brick", "stone_brick", "stone_brick", "cobble", "cobble", "cobble", "stone_brick", "" },
			{ "", "cobble", "", "", "", "stone_brick", "chimney", "", "cobble_stair_n", "", "cobble_stair_n", "", "" },
			{ "", "cobble", "", "", "", " ", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "", "", "", "cobble", "", "", "", "", "", "", "" },
			{ "", "cobble", "", "", "", "stone_brick", "", "", "", "", "", "cobble", "" },
			{ "", "stone_brick", "benchtop", "", "", "stone_brick", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "stone_brick", " ", "cobble", "stone_brick", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "plank_spruce", "cobble", "cobble", "cobble", "plank_spruce", "cobble", "stone_brick", "cobble", "cobble", "cobble", "plank_spruce", "" },
			{ "", "plank_spruce", "", "lantern", "", "plank_spruce", "chimney", "", "", "chimney", "", "cobble_stair_nu", "" },
			{ "", "plank_spruce", "", "", "", "cobble", "lantern", "", "", "", "", "cobble_slab_u", "" },
			{ "", "plank_spruce", "", "", "", "plank_spruce", "", "", "", "", "", "cobble_stair_su", "" },
			{ "", "plank_spruce", "", "", "", "plank_spruce", "spruce_stair_wu", "spruce_slab_u", "spruce_slab_u", "spruce_slab_u", "spruce_stair_eu", "plank_spruce", "" },
			{ "", "plank_spruce", "", "", "", "plank_spruce", "", "", "", "", "", "", "" },
			{ "", "plank_spruce", "cobble", "cobble", "cobble", "plank_spruce", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "oak_stair_e", "slate_roof_wu", "", "", "", "slate_roof_eu", "oak_stair_w", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s" },
			{ "oak_stair_e", "plank_spruce", "plank_spruce", "cobble", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_nu" },
			{ "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "chimney", "", "", "chimney", "", "plank_spruce", "" },
			{ "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "", "", "", "", "", "cobble", "" },
			{ "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "", "", "", "", "", "plank_spruce", "" },
			{ "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_su" },
			{ "oak_stair_e", "plank_spruce", "", "", "", "plank_spruce", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n" },
			{ "oak_stair_e", "plank_spruce", "plank_spruce", "cobble", "plank_spruce", "plank_spruce", "oak_stair_w", "", "", "", "", "", "" },
			{ "oak_stair_e", "slate_roof_wu", "", "", "", "slate_roof_eu", "oak_stair_w", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "slate_roof_e", "slate_roof_wu", "", "slate_roof_eu", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s" },
			{ "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "plank_spruce", "chimney", "plank_spruce", "plank_spruce", "chimney", "plank_spruce", "plank_spruce", "slate_roof_nu" },
			{ "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "plank_spruce", "", "", "", "", "", "plank_spruce", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_su" },
			{ "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n" },
			{ "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "slate_roof_wu", "", "slate_roof_eu", "slate_roof_w", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "slate_roof_e", "oak_stair_su", "slate_roof_w", "", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_s", "slate_roof_s", "chimney", "slate_roof_s", "slate_roof_s", "chimney", "slate_roof_s", "slate_roof_s", "slate_roof_s" },
			{ "", "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "oak_stair_wu" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "oak_stair_nu", "slate_roof_w", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "oak_stair_n", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "chimney", "", "", "chimney", "", "", "" },
			{ "", "", "", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_stair_e" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_slab", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "oak_stair_s", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "chimney", "", "", "chimney", "", "", "" },
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
