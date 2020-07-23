package minefantasy.system.villagegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import minefantasy.block.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageMFPen1 extends ComponentVillageMF {

	private boolean hasPigs;
	
	public ComponentVillageMFPen1() {
	}

	public ComponentVillageMFPen1(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2,
			Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}

	public static ComponentVillageMFPen1 create(ComponentVillageMFStartPiece par0ComponentVillageStartPiece,
			List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5,
				0, 0, 0, 7, 5, 6, par6);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(par1List, structureboundingbox) == null
						? new ComponentVillageMFPen1(par0ComponentVillageStartPiece, par7, par2Random,
								structureboundingbox, par6)
						: null;
	}
	
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
        super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Pigs", this.hasPigs);
    }
	
	@Override
    protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
        super.func_143011_b(par1NBTTagCompound);
        this.hasPigs = par1NBTTagCompound.getBoolean("Pigs");
    }
	
	protected int getRoadLevel(World par1World, StructureBoundingBox par2StructureBoundingBox) {
		int max = 0;
		
		for (int i = 0; i < 7; i++) {
			int x = this.getXWithOffset(i, -1);
			int z = this.getZWithOffset(i,  -1);
			
			int level = par1World.getTopSolidOrLiquidBlock(x, z);
			
			if (level > max) {
				max = level;
			}
		}
		
		int x = this.getXWithOffset(3, -1);
		int z = this.getZWithOffset(3,  -1);
		
		int level = (par1World.getTopSolidOrLiquidBlock(x, z) + max) / 2;
		
		level = max;
		
		if (level <= 0) {
			return -1;
		}
		
		return level;
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		if (this.field_143015_k < 0) {
			this.field_143015_k = this.getRoadLevel(world, sbb) - 1;
			
            if (this.field_143015_k < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 5 - 1, 0);
        }
		
		Map<String, int[]> key = new HashMap<String, int[]>();
		key.put("stone", new int[] {BlockListMF.stoneDoubleSlab.blockID, 3});
		key.put("stone_brick", new int[] {Block.stoneBrick.blockID});
		key.put("cobble", new int[] {Block.cobblestone.blockID});
		key.put("plank_oak", new int[] {Block.planks.blockID});
		key.put("plank_spruce", new int[] {Block.planks.blockID, 1});
		key.put("fence", new int[] {Block.fence.blockID});
		key.put("lantern", new int[] {BlockListMF.lantern.blockID});
		key.put("log_x", new int[] {Block.wood.blockID, 4});
		key.put("log_y", new int[] {Block.wood.blockID, 0});
		key.put("log_z", new int[] {Block.wood.blockID, 8});
		key.put("hay_x", new int[] {Block.hay.blockID, 4});
		key.put("hay_y", new int[] {Block.hay.blockID, 0});
		key.put("hay_z", new int[] {Block.hay.blockID, 8});
		key.put("spruce_slab", new int[] {Block.woodSingleSlab.blockID, 1});
		key.put("spruce_slab_u", new int[] {Block.woodSingleSlab.blockID, 9});
		key.put("grass", new int[] {Block.grass.blockID});
		key.put("water", new int[] {Block.waterStill.blockID});
		key.put("farmland", new int[] {Block.tilledField.blockID, 7});
		
		this.fillWithBlocks(world, sbb, 0, 0, 0, 6, 4, 5, 0, 0, false);
		
		this.buildFromBlueprint(world, rand, sbb, blueprint, key);
		
		this.placeBlockAtCurrentPosition(world, Block.fenceGate.blockID, this.coordBaseMode, 3, 1, 0, sbb);
		
		if (!this.hasPigs) {
			this.hasPigs = true;
			int x = this.getXWithOffset(2, 2);
			int y = this.getYWithOffset(1);
			int z = this.getZWithOffset(2, 2);
			EntityPig pig1 = new EntityPig(world);
			pig1.setLocationAndAngles(x, y, z, rand.nextFloat() * 360.0F, 0.0F);
			world.spawnEntityInWorld(pig1);
		
			x = this.getXWithOffset(3, 2);
			z = this.getZWithOffset(3, 2);
			EntityPig pig2 = new EntityPig(world);
			pig2.setLocationAndAngles(x, y, z, rand.nextFloat() * 360.0F, 0.0F);
			world.spawnEntityInWorld(pig2);
		}
		
		for (int l = 0; l < 6; ++l) {
            for (int i1 = 0; i1 < 7; ++i1) {
                this.fillCurrentPositionBlocksDownwards(world, Block.dirt.blockID, 0, i1, -1, l, sbb);
                this.clearCurrentPositionBlocksUpwards(world, i1, 5, l, sbb);
            }
        }
		
		return true;
	}
	
	private static String[][][] blueprint = new String[][][] {
		{
			{ "grass", "grass", "grass", "grass", "grass", "grass", "grass" },
			{ "grass", "hay_z", "hay_x", "farmland", "farmland", "farmland", "grass" },
			{ "grass", "hay_z", "farmland", "water", "water", "farmland", "grass" },
			{ "grass", "grass", "grass", "farmland", "farmland", "grass", "grass" },
			{ "grass", "grass", "grass", "grass", "grass", "grass", "grass" },
			{ "grass", "grass", "grass", "grass", "grass", "grass", "grass" }
		},
		{
			{ "fence", "fence", "fence", "fence", "fence", "fence", "fence" },
			{ "fence", "", "", "", "", "", "fence" },
			{ "fence", "", "", "", "", "", "fence" },
			{ "fence", "", "", "", "", "", "fence" },
			{ "fence", "", "", "", "", "", "fence" },
			{ "fence", "fence", "fence", "fence_gate", "fence", "fence", "fence" }
		},
		{
			{ "fence", "", "", "", "", "", "fence" },
			{ "", "", "", "", "", "", "" },
			{ "fence", "", "", "", "", "", "fence" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" }
		},
		{
			{ "fence", "", "", "", "", "", "fence" },
			{ "spruce_slab_u", "spruce_slab_u", "spruce_slab_u", "spruce_slab_u", "spruce_slab_u", "spruce_slab_u", "spruce_slab_u" },
			{ "spruce_slab", "spruce_slab", "spruce_slab", "spruce_slab", "spruce_slab", "spruce_slab", "spruce_slab" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" }
		},
		{
			{ "spruce_slab", "spruce_slab", "spruce_slab", "spruce_slab", "spruce_slab", "spruce_slab", "spruce_slab" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" }
		}
	};

}
