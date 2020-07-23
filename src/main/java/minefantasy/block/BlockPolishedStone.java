package minefantasy.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlockPolishedStone extends BlockMedieval {
	
    @SideOnly(Side.CLIENT)
    private Icon polished;
    @SideOnly(Side.CLIENT)
    private Icon pillar_side;
	
	public BlockPolishedStone(int id) {
		super(id, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        if (meta != 2 && meta != 3 && meta != 4) {
            return this.polished;
        } else {
        	if (meta == 2 && (side == 1 || side == 0))
        		return this.polished;
        	else if (meta == 3 && (side == 5 || side == 4))
        		return this.polished;
        	else if (meta == 4 && (side == 2 || side == 3))
        		return this.polished;
        	else
        		return this.pillar_side;
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	protected String getTextureName() {
		return "minefantasy:Basic/stone";
	}
	
	@Override
	public int damageDropped(int meta) {
        return ((meta == 3) || (meta == 4)) ? 2 : meta;
    }
	
	@Override
	protected ItemStack createStackedBlock(int meta) {
		return ((meta == 3) || (meta == 4)) ? new ItemStack(this.blockID, 1, 2) : super.createStackedBlock(meta);
    }
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        if (meta == 2) {
            switch (side) {
                case 0:
                case 1:
                	meta = 2;
                    break;
                case 2:
                case 3:
                	meta = 4;
                    break;
                case 4:
                case 5:
                	meta = 3;
            }
        }
        return meta;
    }

	@Override
	public int getRenderType() {
        return 39;
    }
	
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(int id, CreativeTabs creativeTab, List itemList) {
		itemList.add(new ItemStack(id, 1, 0));
		itemList.add(new ItemStack(id, 1, 2));
    }
	
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.polished = par1IconRegister.registerIcon(this.getTextureName() + "_polished");
        this.pillar_side = par1IconRegister.registerIcon(this.getTextureName() + "_pillar");
    }

}
