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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.ChestGenHooks;

public class ComponentVillageMFBakery extends ComponentVillageMF {

	private boolean hasFirepit;
	private boolean hasFoodPrep;
	private boolean hasFoodPrep2;
	private boolean hasChest;
	
	public ComponentVillageMFBakery() {
	}

	public ComponentVillageMFBakery(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFBakery create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 9, 13, 9, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFBakery(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}
	
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
        super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Firepit", this.hasFirepit);
        par1NBTTagCompound.setBoolean("FoodPrep", this.hasFoodPrep);
        par1NBTTagCompound.setBoolean("FoodPrep2", this.hasFoodPrep2);
        par1NBTTagCompound.setBoolean("Chest", this.hasChest);
    }
	
	@Override
    protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
        super.func_143011_b(par1NBTTagCompound);
        this.hasFirepit = par1NBTTagCompound.getBoolean("Firepit");
        this.hasFoodPrep = par1NBTTagCompound.getBoolean("FoodPrep");
        this.hasFoodPrep2 = par1NBTTagCompound.getBoolean("FoodPrep2");
        this.hasChest = par1NBTTagCompound.getBoolean("Chest");
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

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 13 - 1, 0);
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
		key.put("hay_slab", new int[] {BlockListMF.woodSingleSlab.blockID, 3});
		key.put("oak_slab", new int[] {Block.woodSingleSlab.blockID});
		key.put("oak_slab_u", new int[] {Block.woodSingleSlab.blockID, 8});
		key.put("spruce_slab", new int[] {Block.woodSingleSlab.blockID, 1});
		key.put("spruce_slab_u", new int[] {Block.woodSingleSlab.blockID, 9});
		key.put("brick_slab", new int[] {Block.stoneSingleSlab.blockID, 4});
		key.put("brick_slab_u", new int[] {Block.stoneSingleSlab.blockID, 12});
		key.put("ladder_n", new int[] {Block.ladder.blockID, 2});
		key.put("ladder_s", new int[] {Block.ladder.blockID, 3});
		key.put("ladder_w", new int[] {Block.ladder.blockID, 4});
		key.put("ladder_e", new int[] {Block.ladder.blockID, 5});
		
		this.fillWithBlocks(world, sbb, 0, 1, 0, 8, 12, 8, 0, 0, false);
		
		this.buildFromBlueprint(world, rand, sbb, blueprint, key);
		
		this.placeDoorAtCurrentPosition(world, sbb, rand, 4, 1, 2, this.getMetadataWithOffset(Block.doorWood.blockID, 3));
		
		this.placeBlockAtCurrentPosition(world, Block.torchWood.blockID, this.getMetadataWithOffset(Block.torchWood.blockID, 3), 4, 2, 6, sbb);
		
		if (!this.hasFirepit) {
			int i = this.getYWithOffset(1);
            int j = this.getXWithOffset(2, 5);
            int k = this.getZWithOffset(2, 5);

            if (sbb.isVecInside(j, i, k)) {
                this.hasFirepit = true;
                world.setBlock(j, i, k, BlockListMF.firepit.blockID, 0, 2);
                TileEntityFirepit teFirepit = (TileEntityFirepit) world.getBlockTileEntity(j, i, k);
                if (teFirepit != null) {
                	teFirepit.fuel = rand.nextInt(2400) + 1200;
                }
            }
		}
		
		if (!this.hasFoodPrep) {
			int successes = 0;
			
			for (int l = 0; l < 4; l++) {
				int i = this.getYWithOffset(2);
				int j;
				int k;
				if (l < 3) {
					j = this.getXWithOffset(5, 3 + l);
					k = this.getZWithOffset(5, 3 + l);
				} else {
					j = this.getXWithOffset(4, 5);
					k = this.getZWithOffset(4, 5);
				}

	            if (sbb.isVecInside(j, i, k)) {
	                successes++;
	                world.setBlock(j, i, k, BlockListMF.foodPrep.blockID, 0, 2);
	                TileEntityPrepBlock tePrep = (TileEntityPrepBlock) world.getBlockTileEntity(j, i, k);
	                if (tePrep != null) {
	                	if (l > 1) {
	                		tePrep.direction = (this.coordBaseMode) & 3;
	                	} else {
	                		tePrep.direction = (this.coordBaseMode + 3) & 3;
	                	}
	                	tePrep.setInventorySlotContents(1, new ItemStack(Block.planks.blockID, 1, 1));
	                	int r = rand.nextInt(3);
	                	if (r == 1) {
	                		tePrep.setInventorySlotContents(0, new ItemStack(Item.bread.itemID, 1, 0));
	                	} else if (r == 2) {
	                		tePrep.setInventorySlotContents(0, new ItemStack(ItemListMF.miscFood.itemID, 1, 0));
	                	}
	                }
	            }
			}
            
            this.hasFoodPrep = successes >= 4;
		}
		
		if (!this.hasFoodPrep2) {
			int successes = 0;
			
			for (int l = 0; l < 2; l++) {
				int i = this.getYWithOffset(5);
	            int j = this.getXWithOffset(4 + l, 5);
	            int k = this.getZWithOffset(4 + l, 5);

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
            
            this.hasFoodPrep2 = successes >= 2;
		}
		
		if (!this.hasChest) {
			int i = this.getYWithOffset(4);
            int j = this.getXWithOffset(3, 2);
            int k = this.getZWithOffset(3, 2);

            if (sbb.isVecInside(j, i, k)) {
                this.hasChest = true;
                this.generateStructureChestContents(world, sbb, rand, 3, 4, 2, ChestGenHooks.getItems(ItemListMF.BAKERY_CHEST, rand), ChestGenHooks.getCount(ItemListMF.BAKERY_CHEST, rand));
            }
		}
		
		if (this.getBlockIdAtCurrentPosition(world, 4, 0, -1, sbb) == 0) {
			this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 4, -1, 0, sbb);
			this.placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 4, 0, 1, sbb);
			this.placeBlockAtCurrentPosition(world, 0, 0, 4, 0, 0, sbb);
		} else {
			this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 4, 0, 0, sbb);
			this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 4, 0, 1, sbb);
		}
		
		for (int l = 2; l < 8; ++l) {
            for (int i1 = 1; i1 < 8; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, sbb);
            }
        }
		this.fillCurrentPositionBlocksDownwards(world, Block.cobblestone.blockID, 0, 4, -1, 0, sbb);
		this.fillCurrentPositionBlocksDownwards(world, Block.cobblestone.blockID, 0, 4, -1, 1, sbb);
		for (int l = 0; l < 9; ++l) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.clearCurrentPositionBlocksUpwards(world, i1, 13, l, sbb);
            }
        }
		
		this.spawnVillagers(world, sbb, 4, 1, 3, 1);
		
		return true;
	}
	
	@Override
	protected int getVillagerType(int par1) {
		return 78;
	}
	
	@Override
	protected boolean generateStructureChestContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8) {
        int i1 = this.getXWithOffset(par4, par6);
        int j1 = this.getYWithOffset(par5);
        int k1 = this.getZWithOffset(par4, par6);

        if (par2StructureBoundingBox.isVecInside(i1, j1, k1) && par1World.getBlockId(i1, j1, k1) != Block.chest.blockID)
        {
            par1World.setBlock(i1, j1, k1, Block.chest.blockID, this.getMetadataWithOffset(Block.chest.blockID, 2), 2);
            TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(i1, j1, k1);

            if (tileentitychest != null)
            {
                WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, tileentitychest, par8);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
	
	private static String[][][] blueprint = new String[][][] {
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone", "stone_brick", "stone_brick", "stone", "stone", "stone", "" },
			{ "", "stone_brick", "cobble_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone_brick", "cobble_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "" },
			{ "", "stone_brick", "cobble_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "" },
			{ "", "stone", "stone", "stone_brick", "plank_oak", "stone", "stone", "stone", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "stone_brick", "cobble", "cobble", "stone_brick", "stone_brick", "stone", "" },
			{ "", "stone_brick", "brick", "", "", "", "", "stone_brick", "" },
			{ "", "brick", "firepit", "", "spruce_stair_wu", "plank_spruce", "", "cobble", "" },
			{ "", "stone_brick", "brick", "", "", "spruce_slab_u", "", "stone_brick", "" },
			{ "", "stone_brick", "", "", "", "spruce_stair_su", "ladder_w", "stone_brick", "" },
			{ "", "stone", "stone_brick", "cobble", " ", "stone_brick", "stone", "stone", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "stone_brick", "cobble", "cobble", "cobble", "cobble", "stone_brick", "stone_brick", "" },
			{ "", "cobble", "brick_stair_s", "", "torch_s", "", "", "cobble", "" },
			{ "", "brick", "brick_slab_u", "", "benchtop", "benchtop", "", "cobble", "" },
			{ "", "cobble", "brick_stair_n", "", "", "benchtop", "", "cobble", "" },
			{ "", "stone_brick", "", "", "", "benchtop", "ladder_w", "stone_brick", "" },
			{ "", "stone_brick", "cobble", "cobble", " ", "cobble", "stone_brick", "stone_brick", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "" },
			{ "", "plank_spruce", "oak_slab_u", "oak_slab_u", "oak_slab_u", "oak_slab_u", "oak_slab_u", "plank_spruce", "" },
			{ "", "plank_spruce", "chimney", "oak_slab_u", "oak_slab_u", "oak_slab_u", "oak_slab_u", "plank_spruce", "" },
			{ "", "plank_spruce", "oak_slab_u", "oak_slab_u", "oak_slab_u", "oak_slab_u", "oak_slab_u", "plank_spruce", "" },
			{ "", "plank_spruce", "oak_slab_u", "oak_slab_u", "oak_slab_u", "oak_slab_u", "ladder_w", "plank_spruce", "" },
			{ "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "" },
			{ "", "spruce_stair_nu", "spruce_slab_u", "spruce_stair_nu", "spruce_slab_u", "spruce_stair_nu", "spruce_slab_u", "spruce_stair_nu", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "log_y", "clay_wall_cross", "clay_wall_cross", "clay_wall_cross", "clay_wall_cross", "clay_wall_cross", "log_y", "" },
			{ "", "clay_wall_cross", "", "", "", "spruce_stair_n", "", "clay_wall_cross", "" },
			{ "", "clay_wall_cross", "chimney", "", "fence", "fence", "", "clay_wall_cross", "" },
			{ "", "clay_wall_cross", "", "", "spruce_stair_s", "", "", "clay_wall_cross", "" },
			{ "", "clay_wall_cross", "hay_slab", "", "", "", "ladder_w", "clay_wall_cross", "" },
			{ "", "clay_wall_cross", "hay_slab", "chest", "", "", "", "clay_wall_cross", "" },
			{ "", "log_y", "clay_wall_cross", "clay_wall_cross", "clay_wall_cross", "clay_wall_cross", "clay_wall_cross", "log_y", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "log_y", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "clay_wall", "log_y", "" },
			{ "", "clay_wall", "", "", "", "", "", "clay_wall", "" },
			{ "", "clay_wall", "chimney", "", "benchtop", "benchtop", "", "clay_wall", "" },
			{ "", "glass_pane", "", "", "", "", "", "glass_pane", "" },
			{ "", "clay_wall", "", "", "", "", "", "clay_wall", "" },
			{ "", "clay_wall", "", "", "", "", "", "clay_wall", "" },
			{ "", "log_y", "clay_wall", "glass_pane", "clay_wall", "glass_pane", "clay_wall", "log_y", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "" },
			{ "", "clay_wall", "", "", "", "", "", "clay_wall", "" },
			{ "", "clay_wall", "chimney", "", "", "", "", "clay_wall", "" },
			{ "", "glass_pane", "", "", "lantern", "", "", "glass_pane", "" },
			{ "", "clay_wall", "", "", "", "", "", "clay_wall", "" },
			{ "", "clay_wall", "", "", "", "", "", "clay_wall", "" },
			{ "", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s", "oak_stair_s" },
			{ "slate_roof_nu", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_nu" },
			{ "", "plank_spruce", "", "log_z", "", "log_z", "", "plank_spruce", "" },
			{ "", "clay_wall", "chimney", "log_z", "", "log_z", "", "clay_wall", "" },
			{ "", "clay_wall", "log_x", "log_x", "log_x", "log_x", "log_x", "clay_wall", "" },
			{ "", "clay_wall", "", "log_z", "", "log_z", "", "clay_wall", "" },
			{ "", "plank_spruce", "", "log_z", "", "log_z", "", "plank_spruce", "" },
			{ "slate_roof_su", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_su" },
			{ "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n", "oak_stair_n" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s" },
			{ "slate_roof_nu", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_nu" },
			{ "", "plank_spruce", "chimney", "", "", "", "", "plank_spruce", "" },
			{ "", "clay_wall", "", "log_y", "", "log_y", "", "clay_wall", "" },
			{ "", "plank_spruce", "", "", "", "", "", "plank_spruce", "" },
			{ "slate_roof_su", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_su" },
			{ "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s" },
			{ "slate_roof_nu", "plank_spruce", "chimney", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_nu" },
			{ "", "plank_spruce", "", "log_y", "", "log_y", "", "plank_spruce", "" },
			{ "slate_roof_su", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_su" },
			{ "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "slate_roof_s", "slate_roof_s", "chimney", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s", "slate_roof_s" },
			{ "oak_stair_eu", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "plank_spruce", "oak_stair_wu" },
			{ "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n", "slate_roof_n" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "chimney", "", "", "", "", "", "" },
			{ "oak_stair_w", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_slab", "oak_stair_e" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "chimney", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		}
	};

}
