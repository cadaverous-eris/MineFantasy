package minefantasy.system.villagegen;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentVillage;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

public abstract class ComponentVillageMF extends ComponentVillage {

	public ComponentVillageMF() {
	}

	protected ComponentVillageMF(ComponentVillageMFStartPiece par1ComponentVillageStartPiece, int par2) {
		super(par1ComponentVillageStartPiece, par2);
	}

	@Override
	protected StructureComponent getNextComponentNN(ComponentVillageStartPiece par1ComponentVillageStartPiece,
			List par2List, Random par3Random, int par4, int par5) {
		if (par1ComponentVillageStartPiece instanceof ComponentVillageMFStartPiece) {
			ComponentVillageMFStartPiece startPiece = (ComponentVillageMFStartPiece) par1ComponentVillageStartPiece;
			switch (this.coordBaseMode) {
			case 0:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1,
						this.getComponentType());
			case 1:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2,
						this.getComponentType());
			case 2:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1,
						this.getComponentType());
			case 3:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2,
						this.getComponentType());
			default:
				return null;
			}
		}
		return null;
	}

	@Override
	protected StructureComponent getNextComponentPP(ComponentVillageStartPiece par1ComponentVillageStartPiece,
			List par2List, Random par3Random, int par4, int par5) {
		if (par1ComponentVillageStartPiece instanceof ComponentVillageMFStartPiece) {
			ComponentVillageMFStartPiece startPiece = (ComponentVillageMFStartPiece) par1ComponentVillageStartPiece;
			switch (this.coordBaseMode) {
			case 0:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3,
						this.getComponentType());
			case 1:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0,
						this.getComponentType());
			case 2:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3,
						this.getComponentType());
			case 3:
				return StructureVillageMFPieces.getNextStructureComponent(startPiece, par2List, par3Random,
						this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0,
						this.getComponentType());
			default:
				return null;
			}
		}
		return null;
	}

	/**
	 * Gets the replacement block for the current biome
	 */
	@Override
	protected int getBiomeSpecificBlock(int par1, int par2) {
		return par1;
	}

	/**
	 * Gets the replacement block metadata for the current biome
	 */
	@Override
	protected int getBiomeSpecificBlockMetadata(int par1, int par2) {
		return par2;
	}

	@Override
	protected int getMetadataWithOffset(int id, int meta) {
		if (id > 0 && id < Block.blocksList.length && Block.blocksList[id] != null) {
			Block block = Block.blocksList[id];

			if (block instanceof BlockStairs) {
				int dirMeta = meta & 3;
				boolean upsidedown = (meta & 4) == 4;

				if (this.coordBaseMode == 0) {
					int retMeta = dirMeta;
					if (dirMeta == 2) {
						retMeta = 3;
					} else if (dirMeta == 3) {
						retMeta = 2;
					}
					if (upsidedown) {
						retMeta |= 4;
					}
					return retMeta;
				} else if (this.coordBaseMode == 1) {
					int retMeta = dirMeta;
					if (dirMeta == 0) {
						retMeta = 2;
					} else if (dirMeta == 1) {
						retMeta = 3;
					} else if (dirMeta == 2) {
						retMeta = 0;
					} else if (dirMeta == 3) {
						retMeta = 1;
					}
					if (upsidedown) {
						retMeta |= 4;
					}
					return retMeta;
				} else if (this.coordBaseMode == 2) {
					return meta;
				} else if (this.coordBaseMode == 3) {
					int retMeta = dirMeta;
					if (dirMeta == 0) {
						retMeta = 2;
					} else if (dirMeta == 1) {
						retMeta = 3;
					} else if (dirMeta == 2) {
						retMeta = 1;
					} else if (dirMeta == 3) {
						retMeta = 0;
					}
					if (upsidedown) {
						retMeta |= 4;
					}
					return retMeta;
				}
			} else if (block instanceof BlockDoor) {
				int dirMeta = meta & 3;
				boolean open = (meta & 4) == 4;

				if (this.coordBaseMode == 0) {
					int retMeta = dirMeta;
					if (dirMeta != 0 && dirMeta != 2) {
						retMeta = (dirMeta + 2) & 3;
					}
					if (open) {
						retMeta |= 4;
					}
					return retMeta;
				} else if (this.coordBaseMode == 1) {
					int retMeta = dirMeta;
					if (dirMeta != 1 && dirMeta != 3) {
						retMeta = (dirMeta + 1) & 3;
					} else {
						retMeta = (dirMeta + 3) & 3;
					}
					if (open) {
						retMeta |= 4;
					}
					return retMeta;
				} else if (this.coordBaseMode == 2) {
					return meta;
				} else if (this.coordBaseMode == 3) {
					int retMeta = (dirMeta + 1) & 3;
					if (open) {
						retMeta |= 4;
					}
					return retMeta;
				}
			} else if (block == Block.wood) {
				if (this.coordBaseMode == 1 || this.coordBaseMode == 3) {
					int dirMeta = meta >> 2;
					int typeMeta = meta & 3;
					int retMeta = dirMeta;
					if (dirMeta == 1) {
						retMeta = 2;
					} else if (dirMeta == 2) {
						retMeta = 1;
					}
					retMeta = retMeta << 2;
					retMeta |= typeMeta;
					return retMeta;
				} else {
					return meta;
				}
			} else if (block == Block.hay) {
				if (this.coordBaseMode == 1 || this.coordBaseMode == 3) {
					int dirMeta = meta >> 2;
					int typeMeta = meta & 3;
					int retMeta = dirMeta;
					if (dirMeta == 1) {
						retMeta = 2;
					} else if (dirMeta == 2) {
						retMeta = 1;
					}
					retMeta = retMeta << 2;
					retMeta |= typeMeta;
					return retMeta;
				} else {
					return meta;
				}
			} else if (block instanceof BlockTorch) {
				if (this.coordBaseMode == 0) {
					if (meta == 3) {
						return 4;
					} else if (meta == 4) {
						return 3;
					}
				} else if (this.coordBaseMode == 1) {
					if (meta == 1) {
						return 3;
					} else if (meta == 2) {
						return 4;
					} else if (meta == 3) {
						return 1;
					} else if (meta == 4) {
						return 2;
					}
				} else if (this.coordBaseMode == 3) {
					if (meta == 1) {
						return 3;
					} else if (meta == 2) {
						return 4;
					} else if (meta == 3) {
						return 2;
					} else if (meta == 4) {
						return 3;
					}
				}
				return meta;
			}
		}
		return super.getMetadataWithOffset(id, meta);
	}

	protected void buildFromBlueprint(World world, Random rand, StructureBoundingBox sbb, String[][][] bp,
			Map<String, int[]> key) {

		if (bp.length < 1 || bp[0].length < 1 || bp[0][0].length < 1) {
			return;
		}
		int width = bp[0][0].length;
		int height = bp.length;
		int depth = bp[0].length;

		for (int z = 0; z < depth; z++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (!bp[y][(depth - 1) - z][x].isEmpty()) {
						int id = 0;
						int meta = 0;
						if (key.get(bp[y][(depth - 1) - z][x]) != null) {
							int[] data = key.get(bp[y][(depth - 1) - z][x]);
							if (data.length > 0) {
								id = data[0];
							}
							if (data.length > 1) {
								meta = data[1];
							}
						}
						this.placeBlockAtCurrentPosition(world, id, this.getMetadataWithOffset(id, meta), x, y, z, sbb);
					}
				}
			}
		}

	}
	
	@Override
	protected void fillCurrentPositionBlocksDownwards(World world, int id, int meta, int x, int y, int z, StructureBoundingBox sbb) {
		int j1 = this.getXWithOffset(x, z);
        int k1 = this.getYWithOffset(y);
        int l1 = this.getZWithOffset(x, z);

        if (sbb.isVecInside(j1, k1, l1)) {
            while ((world.isAirBlock(j1, k1, l1) || world.getBlockMaterial(j1, k1, l1).isLiquid() || world.getBlockMaterial(j1, k1, l1).isReplaceable()) && k1 > 1) {
                world.setBlock(j1, k1, l1, id, meta, 2);
                --k1;
            }
        }
    }

}
