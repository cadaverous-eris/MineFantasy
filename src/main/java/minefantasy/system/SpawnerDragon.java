package minefantasy.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import minefantasy.entity.EntityDragonSmall;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.Event.Result;

public class SpawnerDragon implements ITickHandler {

	/** The 17x17 area around the player where mobs can spawn */
	private HashMap eligibleChunksForSpawning = new HashMap();

	/**
	 * Given a chunk, find a random position in it.
	 */
	protected static ChunkPosition getRandomSpawningPointInChunk(World par0World, int par1, int par2) {
		Chunk chunk = par0World.getChunkFromChunkCoords(par1, par2);
		int k = par1 * 16 + par0World.rand.nextInt(16);
		int l = par2 * 16 + par0World.rand.nextInt(16);
		int i1 = chunk == null ? par0World.getActualHeight() : par0World.getTopSolidOrLiquidBlock(k, l);
		return new ChunkPosition(k, i1, l);
	}

	/**
	 * adds all chunks within the spawn radius of the players to
	 * eligibleChunksForSpawning. pars: the world. returns number of eligible
	 * chunks.
	 */
	public int findChunksForSpawning(WorldServer par1WorldServer) {
		this.eligibleChunksForSpawning.clear();
		int i;
		int j;

		for (i = 0; i < par1WorldServer.playerEntities.size(); ++i) {
			EntityPlayer entityplayer = (EntityPlayer) par1WorldServer.playerEntities.get(i);
			int k = MathHelper.floor_double(entityplayer.posX / 16.0D);
			j = MathHelper.floor_double(entityplayer.posZ / 16.0D);
			byte b0 = 8;

			for (int l = -b0; l <= b0; ++l) {
				for (int i1 = -b0; i1 <= b0; ++i1) {
					boolean flag3 = l == -b0 || l == b0 || i1 == -b0 || i1 == b0;
					ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(l + k, i1 + j);
					
					if (!flag3 || par1WorldServer.getChunkFromChunkCoords(l + k, i1 + j).inhabitedTime > 6000) {
						this.eligibleChunksForSpawning.put(chunkcoordintpair, Boolean.valueOf(false));
					} else if (!this.eligibleChunksForSpawning.containsKey(chunkcoordintpair)) {
						this.eligibleChunksForSpawning.put(chunkcoordintpair, Boolean.valueOf(true));
					}
				}
			}
		}

		i = 0;
		ChunkCoordinates chunkcoordinates = par1WorldServer.getSpawnPoint();

		Iterator iterator = this.eligibleChunksForSpawning.keySet().iterator();
		ArrayList<ChunkCoordIntPair> tmp = new ArrayList(eligibleChunksForSpawning.keySet());
		Collections.shuffle(tmp);
		iterator = tmp.iterator();

		while (iterator.hasNext()) {
			ChunkCoordIntPair chunkcoordintpair1 = (ChunkCoordIntPair) iterator.next();

			if (!((Boolean) this.eligibleChunksForSpawning.get(chunkcoordintpair1)).booleanValue()) {
				ChunkPosition chunkposition = getRandomSpawningPointInChunk(par1WorldServer,
						chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos);
				int k1 = chunkposition.x;
				int l1 = chunkposition.y;
				int i2 = chunkposition.z;

				Block block = Block.blocksList[par1WorldServer.getBlockId(k1, l1, i2)];
				boolean nullBox = block == null || block.getBlockBoundsMinX() == block.getBlockBoundsMaxX()
						|| block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() <= 0.125
						|| block.getBlockBoundsMinZ() == block.getBlockBoundsMaxZ();

				if (!par1WorldServer.isBlockNormalCube(k1, l1, i2)
						&& (par1WorldServer.getBlockMaterial(k1, l1, i2) == Material.air || nullBox)) {
					int j2 = 0;
					int k2 = 0;

					while (k2 < 2) {
						int l2 = k1;
						int i3 = l1;
						int j3 = i2;
						byte b1 = 6;
						int k3 = 0;

						while (true) {
							if (k3 < 2) {
								l2 += par1WorldServer.rand.nextInt(b1) - par1WorldServer.rand.nextInt(b1);
								j3 += par1WorldServer.rand.nextInt(b1) - par1WorldServer.rand.nextInt(b1);
								i3 = par1WorldServer.getTopSolidOrLiquidBlock(l2, j3);

								if (canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, par1WorldServer, l2, i3,
										j3)) {
									float f = (float) l2 + 0.5F;
									float f1 = (float) i3;
									float f2 = (float) j3 + 0.5F;

									if (par1WorldServer.getClosestPlayer((double) f, (double) f1, (double) f2,
											32.0D) == null) {
										float f3 = f - (float) chunkcoordinates.posX;
										float f4 = f1 - (float) chunkcoordinates.posY;
										float f5 = f2 - (float) chunkcoordinates.posZ;
										float f6 = f3 * f3 + f4 * f4 + f5 * f5;

										if (f6 >= 1024.0F) {
											EntityDragonSmall entity = new EntityDragonSmall(par1WorldServer);

											entity.setLocationAndAngles((double) f, (double) f1, (double) f2,
													par1WorldServer.rand.nextFloat() * 360.0F, 0.0F);

											Result canSpawn = ForgeEventFactory.canEntitySpawn(entity, par1WorldServer,
													f, f1, f2);
											if (canSpawn == Result.ALLOW
													|| (canSpawn == Result.DEFAULT && entity.getCanSpawnHere())) {
												++j2;
												par1WorldServer.spawnEntityInWorld(entity);
											}

											i += j2;
										}
									}
								}

								++k3;
								continue;
							}

							++k2;
							break;
						}
					}
				}
			}
		}

		return i;
	}

	public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType par0EnumCreatureType, World par1World,
			int par2, int par3, int par4) {
		if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) {
			return false;
		} else {
			int l = par1World.getBlockId(par2, par3 - 1, par4);
			boolean spawnBlock = (Block.blocksList[l] != null
					&& Block.blocksList[l].canCreatureSpawn(par0EnumCreatureType, par1World, par2, par3 - 1, par4));
			return spawnBlock && l != Block.bedrock.blockID && !par1World.isBlockNormalCube(par2, par3, par4)
					&& !par1World.getBlockMaterial(par2, par3, par4).isLiquid()
					&& !par1World.isBlockNormalCube(par2, par3 + 1, par4);
		}
	}

	/**
	 * Called during chunk generation to spawn initial dragons.
	 */
	public static void performWorldGenSpawning(World par0World, BiomeGenBase par1BiomeGenBase, int par2, int par3,
			int par4, int par5, Random par6Random) {

		if (par0World.provider.dimensionId == 0 || par0World.provider.dimensionId == -37) {
			int j1 = par2 + par6Random.nextInt(par4);
			int k1 = par3 + par6Random.nextInt(par5);
			int l1 = j1;
			int i2 = k1;

			boolean flag = false;

			for (int k2 = 0; !flag && k2 < 4; ++k2) {
				int l2 = par0World.getTopSolidOrLiquidBlock(j1, k1);

				if (canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, par0World, j1, l2, k1)) {
					float f = (float) j1 + 0.5F;
					float f1 = (float) l2;
					float f2 = (float) k1 + 0.5F;
					EntityDragonSmall entity = new EntityDragonSmall(par0World);

					entity.setLocationAndAngles((double) f, (double) f1, (double) f2,
							par0World.rand.nextFloat() * 360.0F, 0.0F);

					Result canSpawn = ForgeEventFactory.canEntitySpawn(entity, par0World, f, f1, f2);
					if (canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT && entity.getCanWorldGenSpawnHere())) {
						par0World.spawnEntityInWorld(entity);
						flag = true;
					}
				}

				j1 += par6Random.nextInt(5) - par6Random.nextInt(5);

				for (k1 += par6Random.nextInt(5) - par6Random.nextInt(5); j1 < par2 || j1 >= par2 + par4 || k1 < par3
						|| k1 >= par3 + par4; k1 = i2 + par6Random.nextInt(5) - par6Random.nextInt(5)) {
					j1 = l1 + par6Random.nextInt(5) - par6Random.nextInt(5);
				}
			}
		}
	}
	
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void populateChunk(PopulateChunkEvent.Populate event) {
		if (event.getResult() != Result.DENY && !event.hasVillageGenerated) {
			BiomeGenBase biome = event.world.getBiomeGenForCoords(event.chunkX * 16, event.chunkZ * 16);
			int x = (event.chunkX * 16) + 8;
			int z = (event.chunkZ * 16) + 8;
			performWorldGenSpawning(event.world, biome, x, z, 16, 16, event.rand);
		}
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (!type.contains(TickType.WORLD)) {
			return;
		}

		if (tickData[0] != null && tickData[0] instanceof WorldServer) {
			WorldServer world = (WorldServer) tickData[0];

			if (world.getTotalWorldTime() % 400 == 0L
					&& (world.provider.dimensionId == 0 || world.provider.dimensionId == -37)
					&& world.getGameRules().getGameRuleBooleanValue("doMobSpawning") && world.difficultySetting > 0) {
				findChunksForSpawning(world);
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.WORLD);
	}

	@Override
	public String getLabel() {
		return "Dragon Spawner Tick Handler";
	}

}
