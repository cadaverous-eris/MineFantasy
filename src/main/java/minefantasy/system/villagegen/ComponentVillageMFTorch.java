package minefantasy.system.villagegen;

import java.util.List;
import java.util.Random;

import minefantasy.block.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentVillage;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageMFTorch extends ComponentVillage {

	public ComponentVillageMFTorch() {}

    public ComponentVillageMFTorch(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
        super(par1ComponentVillageStartPiece, par2);
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
    }

    public static StructureBoundingBox func_74904_a(ComponentVillageMFStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6) {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 2, 3, 1, par6);
        return StructureComponent.findIntersecting(par1List, structureboundingbox) != null ? null : structureboundingbox;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
    	if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);

            if (this.field_143015_k < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3 - 1, 0);
        }

        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 1, 2, 0, 0, 0, false);
        this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 0, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 1, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, BlockListMF.lantern.blockID, 0, 1, 2, 0, par3StructureBoundingBox);
        
        this.fillCurrentPositionBlocksDownwards(par1World, Block.fence.blockID, 0, 1, -1, 0, par3StructureBoundingBox);
        
        return true;
    }

}
