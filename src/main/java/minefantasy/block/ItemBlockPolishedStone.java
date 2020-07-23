package minefantasy.block;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockPolishedStone extends ItemBlock {
	
	public ItemBlockPolishedStone(int id) {
		super(id);
		this.setMaxDamage(0);
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 2));
	}

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack) {
        int i = itemstack.getItemDamage();
        if (i == 2 || i == 3 || i == 4)
        	return StatCollector.translateToLocal("tile.stonePillar.name");
        else
        	return StatCollector.translateToLocal("tile.polishedStone.name");
    }
	
}
