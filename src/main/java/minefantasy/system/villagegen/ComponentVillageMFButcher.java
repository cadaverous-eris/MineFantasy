package minefantasy.system.villagegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import minefantasy.block.BlockListMF;
import minefantasy.block.tileentity.TileEntityFirepit;
import minefantasy.block.tileentity.TileEntityPrepBlock;
import minefantasy.block.tileentity.TileEntityRoast;
import minefantasy.block.tileentity.TileEntityWeaponRack;
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

public class ComponentVillageMFButcher extends ComponentVillageMF {
	
	private boolean hasFirepit;
	private boolean hasFoodPrep;
	private boolean hasFoodPrep2;
	private boolean hasChest;
	private boolean hasToolRack;

	public ComponentVillageMFButcher() {
	}

	public ComponentVillageMFButcher(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFButcher create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 9, 9, 12, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFButcher(par0ComponentVillageStartPiece, par7, par2Random,
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
        par1NBTTagCompound.setBoolean("ToolRack", this.hasToolRack);
    }
	
	@Override
    protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
        super.func_143011_b(par1NBTTagCompound);
        this.hasFirepit = par1NBTTagCompound.getBoolean("Firepit");
        this.hasFoodPrep = par1NBTTagCompound.getBoolean("FoodPrep");
        this.hasFoodPrep2 = par1NBTTagCompound.getBoolean("FoodPrep2");
        this.hasChest = par1NBTTagCompound.getBoolean("Chest");
        this.hasToolRack = par1NBTTagCompound.getBoolean("ToolRack");
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

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
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
		
		this.fillWithBlocks(world, sbb, 0, 1, 0, 8, 8, 11, 0, 0, false);
		
		this.buildFromBlueprint(world, rand, sbb, blueprint, key);
		
		if (!this.hasFirepit) {
			int successes = 0;
			
			for (int l = 0; l < 2; l++) {
				int i = this.getYWithOffset(1);
				int j = this.getXWithOffset(6, 8 + l);
				int k = this.getZWithOffset(6, 8 + l);

				if (sbb.isVecInside(j, i, k) && sbb.isVecInside(j, i + 1, k)) {
					successes++;
					world.setBlock(j, i, k, BlockListMF.firepit.blockID, 0, 2);
					world.setBlock(j, i + 1, k, BlockListMF.roast.blockID, 0, 2);
					TileEntityFirepit teFirepit = (TileEntityFirepit) world.getBlockTileEntity(j, i, k);
					TileEntityRoast teRoast = (TileEntityRoast) world.getBlockTileEntity(j, i + 1, k);
					if (teFirepit != null) {
						teFirepit.fuel = rand.nextInt(2400) + 1200;
					}
					if (teRoast != null) {
						teRoast.direction = (this.coordBaseMode + 3) & 3;
					}
				}
			}
			
			this.hasFirepit = successes >= 2;
		}
		
		if (!this.hasFoodPrep) {
			int successes = 0;
			
			for (int l = 0; l < 4; l++) {
				int i = this.getYWithOffset(2);
				int j = this.getXWithOffset(2 + l, 3);
				int k = this.getZWithOffset(2 + l, 3);

	            if (sbb.isVecInside(j, i, k)) {
	                successes++;
	                world.setBlock(j, i, k, BlockListMF.foodPrep.blockID, 0, 2);
	                TileEntityPrepBlock tePrep = (TileEntityPrepBlock) world.getBlockTileEntity(j, i, k);
	                if (tePrep != null) {
	                	tePrep.direction = (this.coordBaseMode) & 3;
	                	tePrep.setInventorySlotContents(1, new ItemStack(Block.planks.blockID, 1, 1));
	                	int r = rand.nextInt(7);
	                	if (r == 0) {
	                		tePrep.setInventorySlotContents(0, new ItemStack(ItemListMF.miscFood.itemID, 1, 1));
	                	} else if (r == 1) {
	                		tePrep.setInventorySlotContents(0, new ItemStack(ItemListMF.miscFood.itemID, 1, 3));
	                	} else if (r == 2) {
	                		tePrep.setInventorySlotContents(0, new ItemStack(ItemListMF.miscFood.itemID, 1, 5));
	                	} else if (r == 3) {
	                		tePrep.setInventorySlotContents(0, new ItemStack(ItemListMF.miscFood.itemID, 1, 25));
	                	}
	                }
	            }
			}
            
            this.hasFoodPrep = successes >= 4;
		}
		
		if (!this.hasFoodPrep2) {
			int successes = 0;
			
			for (int l = 0; l < 2; l++) {
				int i = this.getYWithOffset(2);
	            int j = this.getXWithOffset(3, 7 + l);
	            int k = this.getZWithOffset(3, 7 + l);

	            if (sbb.isVecInside(j, i, k)) {
	                successes++;
	                world.setBlock(j, i, k, BlockListMF.foodPrep.blockID, 0, 2);
	                TileEntityPrepBlock tePrep = (TileEntityPrepBlock) world.getBlockTileEntity(j, i, k);
	                if (tePrep != null) {
	                	tePrep.direction = (this.coordBaseMode + 1) & 3;
	                	tePrep.setInventorySlotContents(1, new ItemStack(Block.planks.blockID, 1, 1));
	                	if (rand.nextBoolean()) {
	                		tePrep.setInventorySlotContents(0, new ItemStack(Item.porkRaw.itemID, 1, 0));
	                	}
	                }
	            }
			}
            
            this.hasFoodPrep2 = successes >= 2;
		}
		
		if (!this.hasToolRack) {
			int i = this.getYWithOffset(2);
            int j = this.getXWithOffset(6, 6);
            int k = this.getZWithOffset(6, 6);

            if (sbb.isVecInside(j, i, k)) {
                this.hasToolRack = true;
                int dir = (this.coordBaseMode + 3) & 3;
                if (coordBaseMode == 2 || coordBaseMode == 3) {
                	dir = (this.coordBaseMode + 1) & 3;
                }
                int meta = 0;
                if (dir == 0) {
                	meta = 2;
                } else if (dir == 1) {
                	meta = 5;
                } else if (dir == 2) {
                	meta = 3;
                } else if (dir == 3) {
                	meta = 4;
                }
                
                world.setBlock(j, i, k, BlockListMF.weaponRack.blockID, meta, 2);
                TileEntityWeaponRack teRack = (TileEntityWeaponRack) world.getBlockTileEntity(j, i, k);
                if (teRack != null) {
                	teRack.direction = dir;
                	int r = rand.nextInt(7);
                	if (r == 0) {
                		teRack.setInventorySlotContents(rand.nextInt(4), new ItemStack(ItemListMF.knifeTin));
                	} else if (r == 1) {
                		teRack.setInventorySlotContents(rand.nextInt(4), new ItemStack(ItemListMF.knifeCopper));
                	} else {
                		teRack.setInventorySlotContents(rand.nextInt(4), new ItemStack(ItemListMF.knifeStone));
                	}
                }
            }
		}
		
		if (!this.hasChest) {
			int i = this.getYWithOffset(1);
            int j = this.getXWithOffset(6, 6);
            int k = this.getZWithOffset(6, 6);

            if (sbb.isVecInside(j, i, k)) {
                this.hasChest = true;
                this.generateStructureChestContents(world, sbb, rand, 6, 1, 6, ChestGenHooks.getItems(ItemListMF.BUTCHER_CHEST, rand), ChestGenHooks.getCount(ItemListMF.BUTCHER_CHEST, rand));
            }
		}
		
		this.placeDoorAtCurrentPosition(world, sbb, rand, 4, 1, 1, this.getMetadataWithOffset(Block.doorWood.blockID, 3));
		
		if (this.getBlockIdAtCurrentPosition(world, 4, 0, -1, sbb) == 0) {
			this.placeBlockAtCurrentPosition(world, Block.stairsCobblestone.blockID, this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 4, 0, 0, sbb);
		} else {
			this.placeBlockAtCurrentPosition(world, Block.cobblestone.blockID, 0, 4, 0, 0, sbb);
		}
		
		for (int l = 1; l < 11; ++l) {
            for (int i1 = 1; i1 < 8; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.stone.blockID, 0, i1, -1, l, sbb);
            }
        }
		this.fillCurrentPositionBlocksDownwards(world, Block.cobblestone.blockID, 0, 4, -1, 0, sbb);
		for (int l = 0; l < 12; ++l) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.clearCurrentPositionBlocksUpwards(world, i1, 9, l, sbb);
            }
        }
		
		this.spawnVillagers(world, sbb, 4, 1, 5, 1);
		
		return true;
	}
	
	@Override
	protected int getVillagerType(int par1) {
		return 4;
	}
	
	@Override
	protected boolean generateStructureChestContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8) {
        int i1 = this.getXWithOffset(par4, par6);
        int j1 = this.getYWithOffset(par5);
        int k1 = this.getZWithOffset(par4, par6);

        if (par2StructureBoundingBox.isVecInside(i1, j1, k1) && par1World.getBlockId(i1, j1, k1) != Block.chest.blockID) {
            par1World.setBlock(i1, j1, k1, Block.chest.blockID, this.getMetadataWithOffset(Block.chest.blockID, 4), 2);
            TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(i1, j1, k1);

            if (tileentitychest != null) {
                WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, tileentitychest, par8);
            }

            return true;
        } else {
            return false;
        }
    }
	
	private static String[][][] blueprint = new String[][][] {
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone_brick", "stone", "stone_brick", "stone_brick", "stone", "stone", "" },
			{ "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone", "" },
			{ "", "stone_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "" },
			{ "", "stone_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone_brick", "" },
			{ "", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "cobble_brick", "stone", "" },
			{ "", "stone", "stone", "cobble_brick", "cobble_brick", "cobble_brick", "stone", "stone", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "" },
			{ "", "stone_brick", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone_brick", "" },
			{ "", "stone", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "plank_oak", "stone", "" },
			{ "", "stone", "stone_brick", "stone_brick", "plank_oak", "stone", "stone", "stone", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "stone", "stone_brick", "stone_brick", "cobble", "cobble", "stone_brick", "stone", "" },
			{ "", "stone_brick", "", "", "", "", "firepit", "stone_brick", "" },
			{ "", "cobble", "", "fence", "", "", "firepit", "stone_brick", "" },
			{ "", "stone_brick", "", "fence", "", "", "", "cobble", "" },
			{ "", "stone_brick", "", "", "", "", "chest", "stone_brick", "" },
			{ "", "stone", "stone_brick", "", "", "", "stone_brick", "stone", "" },
			{ "", "stone_brick", "", "", "", "", "", "stone_brick", "" },
			{ "", "stone_brick", "spruce_stair_wu", "spruce_slab_u", "spruce_slab_u", "spruce_stair_eu", "", "cobble", "" },
			{ "", "stone_brick", "", "", "", "", "", "stone_brick", "" },
			{ "", "stone", "stone_brick", "cobble", " ", "stone_brick", "stone", "stone", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "hay_roof_s", "oak_stair_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "hay_roof_s", "oak_stair_s", "hay_roof_s" },
			{ "hay_slab_u", "stone_brick", "cobble", "cobble", "cobble", "cobble", "cobble", "stone_brick", "hay_slab_u" },
			{ "", "cobble", "", "", "", "", "spit_roast", "cobble", "" },
			{ "", "cobble", "", "food_prep", "", "", "spit_roast", "cobble", "" },
			{ "", "cobble", "", "food_prep", "", "", "", "cobble", "" },
			{ "", "cobble", "", "", "", "", "tool_rack", "cobble", "" },
			{ "", "stone_brick", "cobble", "", "", "", "cobble", "stone_brick", "" },
			{ "", "cobble", "", "", "", "", "", "cobble", "" },
			{ "", "cobble", "food_prep", "food_prep", "food_prep", "food_prep", "", "cobble", "" },
			{ "", "cobble", "", "", "", "", "", "cobble", "" },
			{ "", "stone_brick", "cobble", "cobble", " ", "cobble", "stone_brick", "stone_brick", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "hay_slab", "oak_slab", "hay_slab", "hay_slab", "hay_slab", "hay_slab", "hay_slab", "oak_slab", "hay_slab" },
			{ "hay_slab_d", "plank_oak", "hay_slab_d", "hay_slab_d", "hay_slab_d", "hay_slab_d", "hay_slab_d", "plank_oak", "hay_slab_d" },
			{ "hay_slab_u", "plank_spruce", "hay_slab_u", "hay_slab_u", "hay_slab_u", "hay_slab_u", "hay_slab_u", "plank_spruce", "hay_slab_u" },
			{ "", "plank_spruce", "lantern", "", "", "", "lantern", "plank_spruce", "" },
			{ "", "plank_spruce", "", "", "", "", "", "plank_spruce", "" },
			{ "", "plank_spruce", "cobble", "cobble_stair_wu", "cobble_slab_u", "cobble_stair_eu", "cobble", "plank_spruce", "" },
			{ "", "plank_spruce", "", "", "", "", "", "plank_spruce", "" },
			{ "", "plank_spruce", "", "", "lantern", "", "", "plank_spruce", "" },
			{ "", "plank_spruce", "", "", "", "", "", "plank_spruce", "" },
			{ "", "plank_spruce", "cobble", "cobble", "cobble", "cobble", "cobble", "plank_spruce", "" },
			{ "", "", "", "", "", "", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "hay_slab", "oak_slab", "hay_slab", "hay_slab", "hay_slab", "hay_slab", "hay_slab", "oak_slab", "hay_slab" },
			{ "hay_roof_e", "plank_oak", "hay_slab_d", "hay_slab_d", "hay_slab_d", "hay_slab_d", "hay_slab_d", "plank_oak", "hay_roof_w" },
			{ "oak_stair_e", "plank_spruce", "hay_slab_u", "hay_slab_u", "hay_slab_u", "hay_slab_u", "hay_slab_u", "plank_spruce", "oak_stair_w" },
			{ "oak_stair_e", "plank_spruce", "plank_spruce", "cobble", "cobble", "cobble", "plank_spruce", "plank_spruce", "oak_stair_w" },
			{ "oak_stair_e", "plank_spruce", "", "", "log_z", "", "", "plank_spruce", "oak_stair_w" },
			{ "oak_stair_e", "plank_spruce", "log_x", "log_x", "log_z", "log_x", "log_x", "plank_spruce", "oak_stair_w" },
			{ "oak_stair_e", "plank_spruce", "", "", "log_z", "", "", "plank_spruce", "oak_stair_w" },
			{ "oak_stair_e", "plank_spruce", "plank_spruce", "cobble", "cobble", "cobble", "plank_spruce", "plank_spruce", "oak_stair_w" },
			{ "oak_stair_e", "slate_roof_wu", "", "", "", "", "", "slate_roof_eu", "oak_stair_w" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "slate_roof_e", "slate_roof_wu", "hay_slab", "hay_slab", "hay_slab", "slate_roof_eu", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "plank_spruce", "cobble", "plank_spruce", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "log_y", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "", "", "", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "plank_spruce", "plank_spruce", "cobble", "plank_spruce", "plank_spruce", "slate_roof_w", "" },
			{ "", "slate_roof_e", "slate_roof_wu", "", "", "", "slate_roof_eu", "slate_roof_w", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "slate_roof_e", "slate_roof_wu", "", "slate_roof_eu", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "log_y", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "plank_spruce", "plank_spruce", "plank_spruce", "slate_roof_w", "", "" },
			{ "", "", "slate_roof_e", "slate_roof_wu", "", "slate_roof_eu", "slate_roof_w", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "slate_roof_e", "oak_stair_su", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "plank_spruce", "slate_roof_w", "", "", "" },
			{ "", "", "", "slate_roof_e", "oak_stair_nu", "slate_roof_w", "", "", "" }
		},
		{
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "oak_stair_n", "", "", "", "" },
			{ "", "", "", "", "oak_slab", "", "", "", "" },
			{ "", "", "", "", "oak_slab", "", "", "", "" },
			{ "", "", "", "", "oak_slab", "", "", "", "" },
			{ "", "", "", "", "oak_slab", "", "", "", "" },
			{ "", "", "", "", "oak_slab", "", "", "", "" },
			{ "", "", "", "", "oak_stair_s", "", "", "", "" }
		}
	};

}
