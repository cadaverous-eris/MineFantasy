package minefantasy.system;

import java.util.Iterator;
import java.util.Random;

import biomesoplenty.worldgen.structure.BOPMapGenVillage;
import minefantasy.MineFantasyBase;
import minefantasy.api.MineFantasyAPI;
import minefantasy.api.anvil.ITongs;
import minefantasy.api.armour.EnumArmourClass;
import minefantasy.api.arrow.ISpecialBow;
import minefantasy.api.forge.TongsHelper;
import minefantasy.api.weapon.EnumWeaponType;
import minefantasy.api.weapon.WeaponClass;
import minefantasy.block.BlockListMF;
import minefantasy.block.BlockSaplingMF;
import minefantasy.compat.biomesoplenty.BOPMapGenVillageMF;
import minefantasy.entity.EntityArrowMF;
import minefantasy.entity.EntityDragonSmall;
import minefantasy.entity.EntityDrake;
import minefantasy.entity.EntityMinotaur;
import minefantasy.entity.EntitySkeletalKnight;
import minefantasy.item.ItemBloom;
import minefantasy.item.ItemHotItem;
import minefantasy.item.ItemListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.item.mabShield.EnumShieldDesign;
import minefantasy.item.mabShield.ItemShield;
import minefantasy.item.tool.ItemMedievalAxe;
import minefantasy.item.tool.ItemScytheMF;
import minefantasy.item.weapon.ItemBattleaxe;
import minefantasy.item.weapon.ItemBowMF;
import minefantasy.item.weapon.ItemBroadsword;
import minefantasy.item.weapon.ItemGreatsword;
import minefantasy.item.weapon.ItemSpearMF;
import minefantasy.item.weapon.ItemWaraxe;
import minefantasy.item.weapon.ItemWeaponMF;
import minefantasy.system.villagegen.MapGenVillageMF;
import minefantasy.system.villagegen.StructureVillageMFPieces;
import minefantasy.system.villagegen.StructureVillageMFStart;
import mods.battlegear2.api.PlayerEventChild;
import mods.battlegear2.api.PlayerEventChild.OffhandSwingEvent;
import mods.battlegear2.api.PlayerEventChild.QuiverArrowEvent;
import mods.battlegear2.api.core.IBattlePlayer;
import mods.battlegear2.api.core.InventoryPlayerBattle;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.StatCollector;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.ComponentVillageTorch;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillageStart;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventManagerMF {

	public static String nonTendon[] = { "MoCEntityEnt", "EntityOoze" };

	@ForgeSubscribe
	public void entityDrop(LivingDropsEvent event) {
		Class enClass = event.entityLiving.getClass();
		String name = "";
		if (enClass != null && EntityList.classToStringMapping.get(enClass) != null) {
			name = (String) EntityList.classToStringMapping.get(enClass);

		}

		if (event.entityLiving instanceof EntityAnimal && cfg.hardcoreCraft) {
			boolean primitive = false;
			if (event.source != null && event.source.getEntity() != null
					&& event.source.getEntity() instanceof EntityPlayer) {
				primitive = isPrimitive(((EntityPlayer) event.source.getEntity()).getHeldItem());
			}

			Random rand = event.entityLiving.getRNG();
			if (primitive) {
				for (String ent : nonTendon) {
					if (enClass.getName().endsWith(ent)) {
						break;
					} else {
						for (int a = 0; a < 1 + rand.nextInt(4); a++) {
							event.entityLiving.entityDropItem(new ItemStack(ItemListMF.misc, 1, ItemListMF.tendon),
									1.0F);
						}
					}
				}
			}
		}
		EntityLivingBase dropper = event.entityLiving;

		for (EntityItem item : event.drops) {
			if (item.getEntityItem().isItemEqual(new ItemStack(Item.leather))) {
				item.setDead();

				if (name.equals("EntityHorse") || enClass.getName().endsWith("GaiaCentaur")
						|| enClass.getName().endsWith("MoCEntityHorse")
						|| enClass.getName().endsWith("MoCEntityHorseMob")) {
					dropHide(event.lootingLevel, ItemListMF.hideHorse, dropper);
				} else if (enClass.getName().endsWith("EntityAtmosBison")) {
					dropHide(event.lootingLevel, ItemListMF.rawHide, dropper);
				} else if (enClass.getName().endsWith("GaiaHunter")) {
					dropHide(event.lootingLevel, ItemListMF.leatherRaw, dropper);
				} else if (enClass.getName().endsWith("MoCEntityFox")) {
					dropHide(event.lootingLevel, ItemListMF.hideHound, dropper);
				} else if (enClass.getName().endsWith("EntityTroll")) {
					dropHide(event.lootingLevel, ItemListMF.hideGeneric, dropper);
				} else if (name.equals("Cow")) {
					dropHide(event.lootingLevel, ItemListMF.rawHide, dropper);
				} else {
					if (!enClass.getName().startsWith("net.minecraft")
							&& !enClass.getName().startsWith("minefantasy")) {
						FMLLog.warning("[MineFantasy] " + enClass.getName()
								+ "is attempting to drop vanilla leather, report this to the developers");
						dropHide(event.lootingLevel, ItemListMF.hideGeneric, dropper);
					}
				}
			}
		}
		
		// chance of getting a head drop
		float headChance = 0;
		if (event.source != null && event.source.getEntity() != null
				&& event.source.getEntity() instanceof EntityPlayer) {
			ItemStack weapon = ((EntityPlayer) event.source.getEntity()).getHeldItem();
			if (weapon.getItem() instanceof ItemBattleaxe)
				headChance = 0.125f;
			else if (weapon.getItem() instanceof ItemWaraxe)
				headChance = 0.0625f;
			else if (weapon.getItem() instanceof ItemGreatsword)
				headChance = 0.0625f;
			else if (weapon.getItem() instanceof ItemBroadsword)
				headChance = 0.03125f;
			else if (weapon.getItem() instanceof ItemScytheMF)
				headChance = 0.125f;
		}
		headChance += headChance * event.lootingLevel * 0.5f;
		
		if (name.equals("Skeleton")) {
			final ItemStack witherSkullStack = new ItemStack(Item.skull.itemID, 1, 1);
			boolean droppedWitherSkull = false;
			for (EntityItem item : event.drops) {
				if (item.getEntityItem().isItemEqual(new ItemStack(Item.coal))) {
					item.setEntityItemStack(new ItemStack(ItemListMF.misc, 1, ItemListMF.HellCoal));
				} else if (item.getEntityItem().isItemEqual(new ItemStack(Item.bow))) {
					item.setEntityItemStack(new ItemStack(ItemListMF.shortbow));
				} else if (item.getEntityItem().isItemEqual(witherSkullStack)) {
					droppedWitherSkull = true;
				}
			}
			if ((headChance > 0.0f) && (dropper instanceof EntitySkeleton) && (((EntitySkeleton) dropper).getSkeletonType() == 1)) {
				if (!droppedWitherSkull && (dropper.getRNG().nextFloat() < headChance))
					dropper.entityDropItem(witherSkullStack, 0.0F);
			}
		} else if (name.equals("Sheep") || name.toLowerCase().contains("sheep") || name.toLowerCase().contains("goat")) {
			dropHide(event.lootingLevel, ItemListMF.hideSheep, dropper);

			if (cfg.dropMutton) {
				int amount = MineFantasyBase.isHOLoaded() ? 1 : dropper.getRNG().nextInt(3);

				if (event.lootingLevel > 0) {
					amount += dropper.getRNG().nextInt(event.lootingLevel);
				}

				ItemStack food = new ItemStack(dropper.isBurning() ? ItemListMF.muttonCooked : ItemListMF.muttonRaw);

				if (food.stackSize > 0) {
					for (int a = 0; a < amount; a++)
						dropper.entityDropItem(food, 0.0F);
				}
			}
		} else if (name.equals("Pig") || name.toLowerCase().contains("pig") || name.toLowerCase().contains("boar")) {
			dropHide(event.lootingLevel, ItemListMF.hidePig, dropper);
		} else if (name.equals("Chicken")) {
			int amount = dropper.getRNG().nextInt(2) + 1 + dropper.getRNG().nextInt(1 + event.lootingLevel);
			ItemStack featherdrop = new ItemStack(Item.feather);
			if (featherdrop.stackSize > 0)
				for (int a = 0; a < amount; a++)
					dropper.entityDropItem(featherdrop, 0.0F);
		} else if (name.equals("Blaze")) {
			int amount = 1 + dropper.getRNG().nextInt(1 + event.lootingLevel);
			ItemStack hellDrop = new ItemStack(ItemListMF.misc, 1, ItemListMF.HellCoal);
			if (hellDrop.stackSize > 0) {
				for (int a = 0; a < amount; a++)
					dropper.entityDropItem(hellDrop, 0.0F);
			}
		}

		if (enClass.getName().endsWith("MoCEntityBoar") || enClass.getName().endsWith("MoCEntityDeer")) {
			ItemStack drop = new ItemStack(Item.porkRaw);
			if (event.entityLiving.isBurning())
				drop = new ItemStack(Item.porkCooked);
			int num = event.lootingLevel + event.entityLiving.getRNG().nextInt(2);
			for (int a = 0; a < num; a++)
				event.entityLiving.entityDropItem(drop, 0.0F);
		} else if (enClass.getName().endsWith("MoCEntityFishy")) {
			ItemStack drop = new ItemStack(Item.fishRaw);
			if (event.entityLiving.isBurning())
				drop = new ItemStack(Item.fishCooked);
			event.entityLiving.entityDropItem(drop, 0.0F);
		}

		if (event.entityLiving instanceof EntityWolf) {
			dropHide(event.lootingLevel, ItemListMF.hideHound, dropper);
		}

	}

	private void dropHide(int loot, int hide, EntityLivingBase dropper) {
		int amount = 1 + dropper.getRNG().nextInt(1 + loot);

		ItemStack leatherdrop = new ItemStack(ItemListMF.misc, 1, hide);

		if (leatherdrop.stackSize > 0)
			for (int a = 0; a < amount; a++)
				dropper.entityDropItem(leatherdrop, 0.0F);
	}

	@ForgeSubscribe
	public void livingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
		if (cfg.hardcoreFurnace && event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;

			if (player.inventory.hasItem(61)) {
				for (int i = 0; i < player.inventory.mainInventory.length; i++) {
					ItemStack stack = player.inventory.mainInventory[i];
					if (stack != null && stack.itemID == 61) {
						stack.itemID = BlockListMF.smelter.blockID;
						stack.setItemDamage(0);
					}
				}
			}
		}
	}

	@ForgeSubscribe
	public void itemEvent(EntityItemPickupEvent event) {
		EntityPlayer player = event.entityPlayer;

		EntityItem drop = event.item;
		ItemStack item = drop.getEntityItem();
		ItemStack held = player.getHeldItem();

		if (held != null && held.getItem() instanceof ITongs) {
			if (!TongsHelper.hasHeldItem(held)) {
				if (isHotItem(item)) {
					if (TongsHelper.trySetHeldItem(held, item)) {
						drop.setDead();

						if (event.isCancelable()) {
							event.setCanceled(true);
						}
						return;
					}
				}
			}
		}
		{
			if (cfg.burnPlayer && item != null && isHotItem(item)) {
				if (event.isCancelable()) {
					event.setCanceled(true);
				}
			}
		}
	}

	private boolean isPrimitive(ItemStack heldItem) {
		if (heldItem == null)
			return true;

		if (heldItem.isItemEqual(ItemListMF.component(ItemListMF.rock))) {
			return true;
		}
		if (heldItem.itemID == Item.swordStone.itemID || heldItem.itemID == Item.swordWood.itemID) {
			return true;
		}
		if (heldItem.getItem() != null) {
			if (heldItem.getItem() instanceof IPublicMaterialItem) {
				EnumToolMaterial mat = ((IPublicMaterialItem) heldItem.getItem()).getMaterial();
				return mat == EnumToolMaterial.STONE || mat == EnumToolMaterial.WOOD
						|| mat == ToolMaterialMedieval.PRIMITIVE_COPPER || mat == ToolMaterialMedieval.PRIMITIVE_STONE;
			}
			if (heldItem.getItem() instanceof ItemWeaponMF) {
				return ((ItemWeaponMF) heldItem.getItem()).isPrimitive();
			}
		}
		return false;
	}

	@ForgeSubscribe
	public void setTarget(LivingSetAttackTargetEvent event) {
		if (event.entity != null && event.target != null) {
			if (event.entityLiving.canEntityBeSeen(event.target))
				if (!TacticalManager.isDetected(event.target, event.entityLiving)) {
					if (event.isCancelable())
						event.setCanceled(true);
					else {
						if (event.entityLiving instanceof EntityLiving) {
							((EntityLiving) event.entityLiving).setAttackTarget(null);
						}
					}
				}

			if (event.entityLiving instanceof EntityCreeper && !(event.target instanceof EntityPlayer))
				if (event.isCancelable())
					event.setCanceled(true);
				else {
					if (event.entityLiving instanceof EntityLiving) {
						((EntityLiving) event.entityLiving).setAttackTarget(null);
					}
				}
		}
	}

	private static String getType(String type, EnumWeaponType wC) {
		if (wC == EnumWeaponType.AXE || wC == EnumWeaponType.BIGAXE || wC == EnumWeaponType.BIGBLADE
				|| wC == EnumWeaponType.BIGPOLEARM || wC == EnumWeaponType.LONGBLADE || wC == EnumWeaponType.MEDBLADE
				|| wC == EnumWeaponType.POLEARM || wC == EnumWeaponType.SMLAXE || wC == EnumWeaponType.SMLBLADE
				|| wC == EnumWeaponType.STAFF) {
			return "blade";
		}
		return "blunt";
	}

	public static void makeHitSound(ItemStack weapon, Entity target) {
		if (!cfg.hitSound) {
			return;
		}
		if (weapon != null) {
			EnumWeaponType WC = WeaponClass.getClassFor(weapon);
			String material = "metal";
			String type = "blunt";

			material = getMaterial(material, weapon.getItem());
			if (WC != null) {
				type = getType(type, WC);
				String sndString = data_minefantasy.sound("Weapon.hit." + type + "." + material + "_");

				float volume = 1.0F;
				target.playSound(sndString, 1.0F, 1.0F);
			}
		}
	}

	private static String getMaterial(String material, Item item) {

		if (item instanceof ItemTool) {
			if (((ItemTool) item).getToolMaterialName().equalsIgnoreCase("WOOD")) {
				material = "wood";
			}
			if (((ItemTool) item).getToolMaterialName().equalsIgnoreCase("STONE")) {
				material = "stone";
			}
		}

		if (item instanceof ItemSword) {
			if (((ItemSword) item).getToolMaterialName().equalsIgnoreCase("WOOD")) {
				material = "wood";
			}
			if (((ItemSword) item).getToolMaterialName().equalsIgnoreCase("STONE")) {
				material = "stone";
			}
		}

		if (item instanceof IPublicMaterialItem) {
			EnumToolMaterial mat = ((IPublicMaterialItem) item).getMaterial();

			if (mat == ToolMaterialMedieval.PRIMITIVE_STONE) {
				material = "stone";
			}
		}
		return material;
	}

	public void readyBow(ArrowNockEvent event) {
		if (MineFantasyBase.isBGLoaded()) {
			return;
		}
		EntityPlayer player = event.entityPlayer;
		ItemStack bow = event.result;
		boolean infinite = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, bow) > 0;

		for (ItemStack arrow : ArrowsMF.useableArrows) {
			if (player.inventory.hasItemStack(arrow)) {
				player.setItemInUse(bow, bow.getMaxItemUseDuration());
				ItemBowMF.loadArrow(player, bow, arrow, true);
				event.setCanceled(true);
				return;
			}
		}

		if (infinite) {
			player.setItemInUse(bow, bow.getMaxItemUseDuration());
			ItemBowMF.loadArrow(player, bow, new ItemStack(Item.arrow), true);
			event.setCanceled(true);
			return;
		}
	}

	// ItemBow
	public void fireArrow(ArrowLooseEvent event) {
		if (MineFantasyBase.isBGLoaded()) {
			return;
		}

		ItemStack bow = event.bow;
		float charge = event.charge;
		EntityPlayer player = event.entityPlayer;
		World world = event.entity.worldObj;

		ItemBowMF.loadArrow(player, bow, null, true);

		for (ItemStack arrow : ArrowsMF.useableArrows) {
			if (shootSpecificArrow(bow, world, player, charge, arrow)) {
				event.setCanceled(true);
				return;
			}
		}
	}

	@ForgeSubscribe
	public void playWorldSound(PlaySoundAtEntityEvent event) {
		if (event != null && event.name != null) {
			if (event.name.equalsIgnoreCase("random.bow")) {
				event.name = "minefantasy:oldbow";
			}
		}
	}

	public boolean shootSpecificArrow(ItemStack item, World world, EntityPlayer player, float power, ItemStack ammo) {
		boolean infinite = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
		Random itemRand = new Random();
		if (player.inventory.hasItemStack(ammo)) {
			float charge = (float) power / 20.0F;
			charge = (charge * charge + charge * 2.0F) / 3.0F;

			if ((double) charge < 0.1D) {
				return false;
			}

			if (charge > 1.0F) {
				charge = 1.0F;
			}

			float force = 2.0F;
			EntityArrowMF arrow = new EntityArrowMF(world, player, charge * 2.0F, ammo.getItemDamage());

			if (charge == 1.0F) {
				arrow.setIsCritical(true);
			}

			int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, item);

			if (var9 > 0) {
				arrow.setDamage(arrow.getDamage() + (double) var9 * 0.5D + 0.5D);
			}

			int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, item);

			if (var10 > 0) {
				arrow.setKnockbackStrength(var10);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, item) > 0) {
				arrow.setFire(100);
			}

			if (!player.capabilities.isCreativeMode) {
				item.damageItem(1, player);
			}

			if (!infinite) {
				consumePlayerItem(player, ammo);
			} else {
				arrow.canBePickedUp = 2;
			}
			world.playSoundAtEntity(player, "random.bow", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

			if (!world.isRemote) {
				world.spawnEntityInWorld(arrow);
			}
			return true;
		}
		return false;
	}

	@ForgeSubscribe
	public void hurtEntity(LivingHurtEvent event) {
		CombatManager.onAttack(event);
	}

	@ForgeSubscribe
	public void onAttack(LivingAttackEvent event) {
		Entity source = event.source.getSourceOfDamage();

		if (source != null && source instanceof EntityLiving && !event.source.isProjectile()) {
			EntityLiving attacker = (EntityLiving) source;

			PotionEffect affliction = attacker.getActivePotionEffect(Potion.confusion);

			if (affliction != null) {
				Random rand = attacker.getRNG();
				int tier = affliction.getAmplifier();

				if (rand.nextInt(5) <= tier) {
					event.setCanceled(true);
				}
			}
		}

	}

	@ForgeSubscribe
	public void fertellise(BonemealEvent event) {
		World world = event.world;
		EntityPlayer player = event.entityPlayer;
		int x = event.X;
		int y = event.Y;
		int z = event.Z;
		int id = event.ID;
		ItemStack item = player.getHeldItem();
		int meta = world.getBlockMetadata(x, y, z);

		if (id == BlockListMF.sapling.blockID) {
			if (!world.isRemote) {
				if ((double) world.rand.nextFloat() < ((BlockSaplingMF) BlockListMF.sapling).getBonemealChance(meta)) {
					((BlockSaplingMF) BlockListMF.sapling).markOrGrowMarked(world, x, y, z, world.rand);
				}
			}

			event.setResult(Result.ALLOW);
		}
	}

	private boolean consumePlayerItem(EntityPlayer player, ItemStack item) {
		for (int a = 0; a < player.inventory.getSizeInventory(); a++) {
			ItemStack i = player.inventory.getStackInSlot(a);
			if (i != null && i.isItemEqual(item)) {
				player.inventory.decrStackSize(a, 1);
				return true;
			}
		}
		return false;
	}

	private boolean isHotItem(ItemStack item) {
		return item != null && (item.getItem() instanceof ItemHotItem || item.getItem() instanceof ItemBloom
				|| MineFantasyAPI.isHotToPickup(item));
	}

	/*
	 * @ForgeSubscribe public void loadChunk(ChunkDataEvent.Load event) {
	 * if(!cfg.regen)return;
	 * 
	 * NBTTagCompound nbt = event.getData(); int layer = cfg.regenLayer;
	 * 
	 * if(nbt.hasKey("MineFantasyWG_" + layer)) { return; }
	 * nbt.setBoolean("MineFantasyWG_" + layer, true);
	 * 
	 * World world = event.world; int chunkX = event.getChunk().xPosition; int
	 * chunkZ = event.getChunk().zPosition; Random rand = event.world.rand;
	 * 
	 * MineFantasyBase.generator.generate(rand, chunkX, chunkZ, world,
	 * world.getChunkProvider(), world.getChunkProvider()); }
	 */
	
	/*@ForgeSubscribe
	public void onOffhandSwing(OffhandSwingEvent event) {
		if (event.entityPlayer != null) {
			System.out.println(event.entityPlayer.worldObj.isRemote);
		}
	}*/

	@ForgeSubscribe
	public void fireMBArrow(QuiverArrowEvent.Firing event) {
		ItemStack bow = event.getBow();
		EntityArrow arrow = event.arrow;

		if (bow != null && bow.getItem() != null && bow.getItem() instanceof ISpecialBow) {
			arrow = (EntityArrow) ((ISpecialBow) bow.getItem()).modifyArrow(arrow);
		}
	}

	@ForgeSubscribe
	public void onArrowJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow) event.entity;
			if (arrow.shootingEntity != null && arrow.shootingEntity instanceof EntityLiving) {
				EntityLiving attacker = (EntityLiving) arrow.shootingEntity;

				PotionEffect affliction = attacker.getActivePotionEffect(Potion.confusion);

				if (affliction != null) {
					Random rand = attacker.getRNG();
					float tier = (float) affliction.getAmplifier() + 1 * 5F;

					arrow.motionX *= 1F + ((rand.nextFloat() * 0.2F - 0.1F) * tier);
					arrow.motionY *= 1F + ((rand.nextFloat() * 0.2F - 0.1F) * tier);
					arrow.motionZ *= 1F + ((rand.nextFloat() * 0.2F - 0.1F) * tier);
				}
			}
		}
	}

	@ForgeSubscribe
	public void blockWithShield(PlayerEventChild.ShieldBlockEvent event) {
		float damage = event.ammount;
		EntityLivingBase attacker = null;
		if (event.source.getSourceOfDamage() != null && event.source.getSourceOfDamage() instanceof EntityLivingBase) {
			attacker = (EntityLivingBase) event.source.getSourceOfDamage();
		}
		ItemStack shield = event.shield;

		if (shield != null && shield.getItem() instanceof ItemShield) {
			if (attacker != null) {
				event.damageShield = ((ItemShield) shield.getItem()).onHit(shield, event.entityPlayer, event.source,
						attacker, damage);
			}
		}

		if (shield != null && shield.getItem() instanceof ItemShield) {
			if (((ItemShield) shield.getItem()).material == ToolMaterialMedieval.IGNOTUMITE) {
				event.damageShield = false;
			}
		}

		if (event.damageShield && shield != null && shield.getItem() instanceof ItemShield
				&& ((ItemShield) shield.getItem()).design == EnumShieldDesign.TOWER) {
			event.damageShield = false;
			shield.damageItem(Math.round(1F), event.entityPlayer);
			if (shield.stackSize <= 0) {
				ForgeEventFactory.onPlayerDestroyItem(event.entityPlayer, shield);
				event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem + 3,
						null);
				// TODO Render item break
			}
			((InventoryPlayerBattle) event.entityPlayer.inventory).hasChanged = true;
		}
	}

	@ForgeSubscribe
	public void removeVanillaItemDrops(LivingDropsEvent event) {
		if (cfg.hardcoreCraft) {
			for (EntityItem itemEntity : event.drops) {
				if (itemEntity != null) {
					ItemStack stack = itemEntity.getEntityItem();

					if (stack != null && stack.getItem() != null
							&& (stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword
									|| stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemHoe
									|| stack.getItem() instanceof ItemShears)) {

						if (stack.getItem() == Item.shovelWood) {
							itemEntity.setEntityItemStack(
									new ItemStack(Item.stick, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.pickaxeWood) {
							itemEntity.setEntityItemStack(
									new ItemStack(Item.stick, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.axeWood) {
							itemEntity.setEntityItemStack(
									new ItemStack(Item.stick, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.hoeWood) {
							itemEntity.setEntityItemStack(
									new ItemStack(Item.stick, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.shovelStone) {
							itemEntity.setEntityItemStack(
									ItemListMF.component(ItemListMF.rock, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.pickaxeStone) {
							itemEntity.setEntityItemStack(
									ItemListMF.component(ItemListMF.rock, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.axeStone) {
							itemEntity.setEntityItemStack(
									ItemListMF.component(ItemListMF.rock, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.hoeStone) {
							itemEntity.setEntityItemStack(
									ItemListMF.component(ItemListMF.rock, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.shovelIron) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.spadeIronForged, 1,
									randDamage(ItemListMF.spadeIronForged)));
						} else if (stack.getItem() == Item.pickaxeIron) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.pickIronForged, 1, randDamage(ItemListMF.pickIronForged)));
						} else if (stack.getItem() == Item.axeIron) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.axeIronForged, 1, randDamage(ItemListMF.axeIronForged)));
						} else if (stack.getItem() == Item.hoeIron) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.hoeIronForged, 1, randDamage(ItemListMF.hoeIronForged)));
						} else if (stack.getItem() == Item.shovelGold) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.spadeBronze, 1, randDamage(ItemListMF.spadeBronze)));
						} else if (stack.getItem() == Item.pickaxeGold) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.pickBronze, 1, randDamage(ItemListMF.pickBronze)));
						} else if (stack.getItem() == Item.axeGold) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.axeBronze, 1, randDamage(ItemListMF.axeBronze)));
						} else if (stack.getItem() == Item.hoeGold) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.hoeBronze, 1, randDamage(ItemListMF.hoeBronze)));
						} else if (stack.getItem() == Item.shovelDiamond) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.spadeSteelForged, 1,
									randDamage(ItemListMF.spadeSteelForged)));
						} else if (stack.getItem() == Item.pickaxeDiamond) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.pickSteelForged, 1,
									randDamage(ItemListMF.pickSteelForged)));
						} else if (stack.getItem() == Item.axeDiamond) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.axeSteelForged, 1, randDamage(ItemListMF.axeSteelForged)));
						} else if (stack.getItem() == Item.hoeDiamond) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.hoeSteelForged, 1, randDamage(ItemListMF.hoeSteelForged)));
						} else if (stack.getItem() == Item.helmetIron) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.helmetIronHvyChain, 1,
									randDamage(ItemListMF.helmetIronHvyChain)));
						} else if (stack.getItem() == Item.plateIron) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.plateIronHvyChain, 1,
									randDamage(ItemListMF.plateIronHvyChain)));
						} else if (stack.getItem() == Item.legsIron) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.legsIronHvyChain, 1,
									randDamage(ItemListMF.legsIronHvyChain)));
						} else if (stack.getItem() == Item.bootsIron) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.bootsIronHvyChain, 1,
									randDamage(ItemListMF.bootsIronHvyChain)));
						} else if (stack.getItem() == Item.helmetGold) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.helmetGuildedHvyChain, 1,
									randDamage(ItemListMF.helmetGuildedHvyChain)));
						} else if (stack.getItem() == Item.plateGold) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.plateGuildedHvyChain, 1,
									randDamage(ItemListMF.plateGuildedHvyChain)));
						} else if (stack.getItem() == Item.legsGold) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.legsGuildedHvyChain, 1,
									randDamage(ItemListMF.legsGuildedHvyChain)));
						} else if (stack.getItem() == Item.bootsGold) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.bootsGuildedHvyChain, 1,
									randDamage(ItemListMF.bootsGuildedHvyChain)));
						} else if (stack.getItem() == Item.helmetDiamond) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.helmetSteelHvyChain, 1,
									randDamage(ItemListMF.helmetSteelHvyChain)));
						} else if (stack.getItem() == Item.plateDiamond) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.plateSteelHvyChain, 1,
									randDamage(ItemListMF.plateSteelHvyChain)));
						} else if (stack.getItem() == Item.legsDiamond) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.legsSteelHvyChain, 1,
									randDamage(ItemListMF.legsSteelHvyChain)));
						} else if (stack.getItem() == Item.bootsDiamond) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.bootsSteelHvyChain, 1,
									randDamage(ItemListMF.bootsSteelHvyChain)));
						} else if (stack.getItem() == Item.shears) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.shearsIron, 1, randDamage(ItemListMF.shearsIron)));
						} else if (stack.getItem() == Item.swordWood) {
							itemEntity.setEntityItemStack(
									new ItemStack(Item.stick, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.swordStone) {
							itemEntity.setEntityItemStack(
									ItemListMF.component(ItemListMF.rock, itemEntity.worldObj.rand.nextInt(3) + 1));
						} else if (stack.getItem() == Item.swordIron) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.swordIronForged, 1,
									randDamage(ItemListMF.swordIronForged)));
						} else if (stack.getItem() == Item.swordGold) {
							itemEntity.setEntityItemStack(
									new ItemStack(ItemListMF.swordOrnate, 1, randDamage(ItemListMF.swordOrnate)));
						} else if (stack.getItem() == Item.swordDiamond) {
							itemEntity.setEntityItemStack(new ItemStack(ItemListMF.swordSteelForged, 1,
									randDamage(ItemListMF.swordSteelForged)));
						}

					} else if (stack != null && stack.getItem() != null && stack.getItem() == Item.ingotIron) {
						itemEntity
								.setEntityItemStack(ItemListMF.component(ItemListMF.ingotWroughtIron, stack.stackSize));
					}
				}
			}
		}
	}

	private static Random rand = new Random();

	private int randDamage(Item item) {
		return rand.nextInt(item.getMaxDamage() / 2) + (item.getMaxDamage() / 4);
	}

	@ForgeSubscribe
	public void specialSpawn(LivingSpawnEvent.SpecialSpawn event) {
		if (event.entityLiving.getClass() == EntityZombie.class) {
			event.setCanceled(true);

			EntityZombie entity = (EntityZombie) event.entityLiving;

			entity.onSpawnWithEgg(null);

			for (int i = 0; i < 5; i++) {
				entity.setCurrentItemOrArmor(i, null);
				entity.setEquipmentDropChance(i, 0.01F);
			}
			if (shouldGetArmor(event.world, entity)) {
				int mat = getArmorMaterial(event.world, entity);
				int weight = getArmorWeight(event.world, entity);

				for (int i = 0; i < 4; i++) {
					if (entity.getRNG().nextFloat() < 0.85F) {
						entity.setCurrentItemOrArmor((4 - i), getArmorItem(mat, weight, i, entity.getRNG()));
					}
				}
			}
			if (shouldGetWeapon(event.world, entity)) {
				int mat = getWeaponMaterial(event.world, entity);
				entity.setCurrentItemOrArmor(0, getWeaponItem(mat, entity.getRNG()));
			}

		} else if (event.entityLiving.getClass() == EntitySkeleton.class) {
			event.setCanceled(true);

			EntitySkeleton entity = (EntitySkeleton) event.entityLiving;

			entity.onSpawnWithEgg(null);

			entity.setEquipmentDropChance(0, 0.01F);
			for (int i = 1; i < 5; i++) {
				entity.setCurrentItemOrArmor(i, null);
				entity.setEquipmentDropChance(i, 0.01F);
			}
			if (shouldGetArmor(event.world, entity)) {
				int mat = getArmorMaterial(event.world, entity);
				int weight = getArmorWeight(event.world, entity);

				for (int i = 0; i < 4; i++) {
					if (entity.getRNG().nextFloat() < 0.85F) {
						entity.setCurrentItemOrArmor((4 - i), getArmorItem(mat, weight, i, entity.getRNG()));
					}
				}
			}

		} else if (event.entityLiving.getClass() == EntityPigZombie.class) {
			event.setCanceled(true);

			EntityPigZombie entity = (EntityPigZombie) event.entityLiving;

			entity.onSpawnWithEgg(null);

			for (int i = 1; i < 5; i++) {
				entity.setCurrentItemOrArmor(i, null);
				entity.setEquipmentDropChance(i, 0.01F);
			}
			if (shouldGetArmor(event.world, entity)) {
				int mat = entity.getRNG().nextInt(8);
				int weight = getArmorWeight(event.world, entity);

				for (int i = 0; i < 4; i++) {
					if (entity.getRNG().nextFloat() < 0.85F) {
						entity.setCurrentItemOrArmor((4 - i), getArmorItem(mat, weight, i, entity.getRNG()));
					}
				}
			}
			int rand = entity.getRNG().nextInt(10);
			ItemStack stack;
			switch (rand) {
			case 0:
				stack = new ItemStack(ItemListMF.daggerOrnate);
				break;
			case 1:
				stack = new ItemStack(ItemListMF.broadOrnate);
				break;
			case 2:
				stack = new ItemStack(ItemListMF.waraxeOrnate);
				break;
			case 3:
				stack = new ItemStack(ItemListMF.maceOrnate);
				break;
			case 4:
				stack = new ItemStack(ItemListMF.warpickOrnate);
				break;
			case 5:
				stack = new ItemStack(ItemListMF.spearOrnate);
				break;
			default:
				stack = new ItemStack(ItemListMF.swordOrnate);
				break;
			}
			entity.setCurrentItemOrArmor(0, stack);

		}
	}

	private boolean shouldGetArmor(World world, EntityLivingBase entity) {
		ChunkCoordinates spawn = world.getSpawnPoint();

		double spawnDistance = entity.getDistance(spawn.posX, spawn.posY, spawn.posZ);
		float difficultyFactorGlobal = (world.difficultySetting + 1) / 2F;
		float difficultyFactorLocal = 0.5F
				+ (0.5F * (world.getLocationTensionFactor(entity.posX, entity.posY, entity.posZ) / 1.5F));

		if (world.provider.isHellWorld) {
			int chance = (int) (1F / (0.15F / difficultyFactorLocal));
			return entity.getRNG().nextInt(chance) == 0;
		}

		int lowerChance = (int) (1F / (0.01F + (0.1F / difficultyFactorLocal)));
		int higherChance = Math.max((int) ((100000 / difficultyFactorGlobal) / (1F + spawnDistance)), 4);
		int chance = Math.min(lowerChance, higherChance);
		return entity.getRNG().nextInt(chance) == 0;
	}

	/*
	 * 0 - rough leather 1 - bronze 2 - iron 3 - steel 4 - encrusted 5 -
	 * dragonforge 6 - deep iron 7 - mithril
	 */
	private int getArmorMaterial(World world, EntityLivingBase entity) {
		ChunkCoordinates spawn = world.getSpawnPoint();

		double spawnDistance = entity.getDistance(spawn.posX, spawn.posY, spawn.posZ);
		float difficultyFactorGlobal = world.difficultySetting / 3F;
		float difficultyFactorLocal = 0.5F
				+ (0.5F * (world.getLocationTensionFactor(entity.posX, entity.posY, entity.posZ) / 1.5F));

		int limit = Math.min((int) ((spawnDistance / 500F) * difficultyFactorGlobal * (difficultyFactorLocal)), 7);
		if (limit > 0) {
			return entity.getRNG().nextInt(limit);
		}
		return 0;
	}

	private int getArmorWeight(World world, EntityLivingBase entity) {
		ChunkCoordinates spawn = world.getSpawnPoint();
		double spawnDistance = entity.getDistance(spawn.posX, spawn.posY, spawn.posZ);
		float difficultyFactorGlobal = world.difficultySetting / 3F;
		float difficultyFactorLocal = 0.5F
				+ (0.5F * (world.getLocationTensionFactor(entity.posX, entity.posY, entity.posZ) / 1.5F));

		int limit = Math.min((int) ((spawnDistance / 400F) * difficultyFactorGlobal * (difficultyFactorLocal)), 3);
		if (limit > 0) {
			return entity.getRNG().nextInt(limit);
		}
		return 0;
	}

	private ItemStack getArmorItem(int material, int weight, int slot, Random rand) {
		switch (material) {
		case 1:
			switch (weight) {
			case 0:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetBronzeScale);
				case 1:
					return new ItemStack(ItemListMF.plateBronzeScale);
				case 2:
					return new ItemStack(ItemListMF.legsBronzeScale);
				case 3:
					return new ItemStack(ItemListMF.bootsBronzeScale);
				}
			case 1:
				if (rand.nextBoolean()) {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetBronzeSplint);
					case 1:
						return new ItemStack(ItemListMF.plateBronzeSplint);
					case 2:
						return new ItemStack(ItemListMF.legsBronzeSplint);
					case 3:
						return new ItemStack(ItemListMF.bootsBronzeSplint);
					}
				} else {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetBronzeChain);
					case 1:
						return new ItemStack(ItemListMF.plateBronzeChain);
					case 2:
						return new ItemStack(ItemListMF.legsBronzeChain);
					case 3:
						return new ItemStack(ItemListMF.bootsBronzeChain);
					}
				}
			case 2:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetBronzeHvyChain);
				case 1:
					return new ItemStack(ItemListMF.plateBronzeHvyChain);
				case 2:
					return new ItemStack(ItemListMF.legsBronzeHvyChain);
				case 3:
					return new ItemStack(ItemListMF.bootsBronzeHvyChain);
				}
			case 3:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetBronzePlate);
				case 1:
					return new ItemStack(ItemListMF.plateBronzePlate);
				case 2:
					return new ItemStack(ItemListMF.legsBronzePlate);
				case 3:
					return new ItemStack(ItemListMF.bootsBronzePlate);
				}
			}
		case 2:
			switch (weight) {
			case 0:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetIronScale);
				case 1:
					return new ItemStack(ItemListMF.plateIronScale);
				case 2:
					return new ItemStack(ItemListMF.legsIronScale);
				case 3:
					return new ItemStack(ItemListMF.bootsIronScale);
				}
			case 1:
				if (rand.nextBoolean()) {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetIronSplint);
					case 1:
						return new ItemStack(ItemListMF.plateIronSplint);
					case 2:
						return new ItemStack(ItemListMF.legsIronSplint);
					case 3:
						return new ItemStack(ItemListMF.bootsIronSplint);
					}
				} else {
					switch (slot) {
					case 0:
						return new ItemStack(Item.helmetChain);
					case 1:
						return new ItemStack(Item.plateChain);
					case 2:
						return new ItemStack(Item.legsChain);
					case 3:
						return new ItemStack(Item.bootsChain);
					}
				}
			case 2:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetIronHvyChain);
				case 1:
					return new ItemStack(ItemListMF.plateIronHvyChain);
				case 2:
					return new ItemStack(ItemListMF.legsIronHvyChain);
				case 3:
					return new ItemStack(ItemListMF.bootsIronHvyChain);
				}
			case 3:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetIronPlate);
				case 1:
					return new ItemStack(ItemListMF.plateIronPlate);
				case 2:
					return new ItemStack(ItemListMF.legsIronPlate);
				case 3:
					return new ItemStack(ItemListMF.bootsIronPlate);
				}
			}
		case 3:
			switch (weight) {
			case 0:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetSteelScale);
				case 1:
					return new ItemStack(ItemListMF.plateSteelScale);
				case 2:
					return new ItemStack(ItemListMF.legsSteelScale);
				case 3:
					return new ItemStack(ItemListMF.bootsSteelScale);
				}
			case 1:
				if (rand.nextBoolean()) {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetSteelSplint);
					case 1:
						return new ItemStack(ItemListMF.plateSteelSplint);
					case 2:
						return new ItemStack(ItemListMF.legsSteelSplint);
					case 3:
						return new ItemStack(ItemListMF.bootsSteelSplint);
					}
				} else {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetSteelChain);
					case 1:
						return new ItemStack(ItemListMF.plateSteelChain);
					case 2:
						return new ItemStack(ItemListMF.legsSteelChain);
					case 3:
						return new ItemStack(ItemListMF.bootsSteelChain);
					}
				}
			case 2:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetSteelHvyChain);
				case 1:
					return new ItemStack(ItemListMF.plateSteelHvyChain);
				case 2:
					return new ItemStack(ItemListMF.legsSteelHvyChain);
				case 3:
					return new ItemStack(ItemListMF.bootsSteelHvyChain);
				}
			case 3:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetSteelPlate);
				case 1:
					return new ItemStack(ItemListMF.plateSteelPlate);
				case 2:
					return new ItemStack(ItemListMF.legsSteelPlate);
				case 3:
					return new ItemStack(ItemListMF.bootsSteelPlate);
				}
			}
		case 4:
			switch (weight) {
			case 0:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetSteelScale);
				case 1:
					return new ItemStack(ItemListMF.plateSteelScale);
				case 2:
					return new ItemStack(ItemListMF.legsSteelScale);
				case 3:
					return new ItemStack(ItemListMF.bootsSteelScale);
				}
			case 1:
				if (rand.nextBoolean()) {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetSteelSplint);
					case 1:
						return new ItemStack(ItemListMF.plateSteelSplint);
					case 2:
						return new ItemStack(ItemListMF.legsSteelSplint);
					case 3:
						return new ItemStack(ItemListMF.bootsSteelSplint);
					}
				} else {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetSteelChain);
					case 1:
						return new ItemStack(ItemListMF.plateSteelChain);
					case 2:
						return new ItemStack(ItemListMF.legsSteelChain);
					case 3:
						return new ItemStack(ItemListMF.bootsSteelChain);
					}
				}
			case 2:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetSteelHvyChain);
				case 1:
					return new ItemStack(ItemListMF.plateSteelHvyChain);
				case 2:
					return new ItemStack(ItemListMF.legsSteelHvyChain);
				case 3:
					return new ItemStack(ItemListMF.bootsSteelHvyChain);
				}
			case 3:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetEncrustedPlate);
				case 1:
					return new ItemStack(ItemListMF.plateEncrustedPlate);
				case 2:
					return new ItemStack(ItemListMF.legsEncrustedPlate);
				case 3:
					return new ItemStack(ItemListMF.bootsEncrustedPlate);
				}
			}
		case 5:
			switch (weight) {
			case 0:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetDragonScale);
				case 1:
					return new ItemStack(ItemListMF.plateDragonScale);
				case 2:
					return new ItemStack(ItemListMF.legsDragonScale);
				case 3:
					return new ItemStack(ItemListMF.bootsDragonScale);
				}
			case 1:
				if (rand.nextBoolean()) {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetDragonSplint);
					case 1:
						return new ItemStack(ItemListMF.plateDragonSplint);
					case 2:
						return new ItemStack(ItemListMF.legsDragonSplint);
					case 3:
						return new ItemStack(ItemListMF.bootsDragonSplint);
					}
				} else {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetDragonChain);
					case 1:
						return new ItemStack(ItemListMF.plateDragonChain);
					case 2:
						return new ItemStack(ItemListMF.legsDragonChain);
					case 3:
						return new ItemStack(ItemListMF.bootsDragonChain);
					}
				}
			case 2:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetDragonHvyChain);
				case 1:
					return new ItemStack(ItemListMF.plateDragonHvyChain);
				case 2:
					return new ItemStack(ItemListMF.legsDragonHvyChain);
				case 3:
					return new ItemStack(ItemListMF.bootsDragonHvyChain);
				}
			case 3:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetDragonPlate);
				case 1:
					return new ItemStack(ItemListMF.plateDragonPlate);
				case 2:
					return new ItemStack(ItemListMF.legsDragonPlate);
				case 3:
					return new ItemStack(ItemListMF.bootsDragonPlate);
				}
			}
		case 6:
			switch (weight) {
			case 0:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetDeepIronScale);
				case 1:
					return new ItemStack(ItemListMF.plateDeepIronScale);
				case 2:
					return new ItemStack(ItemListMF.legsDeepIronScale);
				case 3:
					return new ItemStack(ItemListMF.bootsDeepIronScale);
				}
			case 1:
				if (rand.nextBoolean()) {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetDeepIronSplint);
					case 1:
						return new ItemStack(ItemListMF.plateDeepIronSplint);
					case 2:
						return new ItemStack(ItemListMF.legsDeepIronSplint);
					case 3:
						return new ItemStack(ItemListMF.bootsDeepIronSplint);
					}
				} else {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetDeepIronChain);
					case 1:
						return new ItemStack(ItemListMF.plateDeepIronChain);
					case 2:
						return new ItemStack(ItemListMF.legsDeepIronChain);
					case 3:
						return new ItemStack(ItemListMF.bootsDeepIronChain);
					}
				}
			case 2:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetDeepIronHvyChain);
				case 1:
					return new ItemStack(ItemListMF.plateDeepIronHvyChain);
				case 2:
					return new ItemStack(ItemListMF.legsDeepIronHvyChain);
				case 3:
					return new ItemStack(ItemListMF.bootsDeepIronHvyChain);
				}
			case 3:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetDeepIronPlate);
				case 1:
					return new ItemStack(ItemListMF.plateDeepIronPlate);
				case 2:
					return new ItemStack(ItemListMF.legsDeepIronPlate);
				case 3:
					return new ItemStack(ItemListMF.bootsDeepIronPlate);
				}
			}
		case 7:
			switch (weight) {
			case 0:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetMithrilScale);
				case 1:
					return new ItemStack(ItemListMF.plateMithrilScale);
				case 2:
					return new ItemStack(ItemListMF.legsMithrilScale);
				case 3:
					return new ItemStack(ItemListMF.bootsMithrilScale);
				}
			case 1:
				if (rand.nextBoolean()) {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetMithrilSplint);
					case 1:
						return new ItemStack(ItemListMF.plateMithrilSplint);
					case 2:
						return new ItemStack(ItemListMF.legsMithrilSplint);
					case 3:
						return new ItemStack(ItemListMF.bootsMithrilSplint);
					}
				} else {
					switch (slot) {
					case 0:
						return new ItemStack(ItemListMF.helmetMithrilChain);
					case 1:
						return new ItemStack(ItemListMF.plateMithrilChain);
					case 2:
						return new ItemStack(ItemListMF.legsMithrilChain);
					case 3:
						return new ItemStack(ItemListMF.bootsMithrilChain);
					}
				}
			case 2:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetMithrilHvyChain);
				case 1:
					return new ItemStack(ItemListMF.plateMithrilHvyChain);
				case 2:
					return new ItemStack(ItemListMF.legsMithrilHvyChain);
				case 3:
					return new ItemStack(ItemListMF.bootsMithrilHvyChain);
				}
			case 3:
				switch (slot) {
				case 0:
					return new ItemStack(ItemListMF.helmetMithrilPlate);
				case 1:
					return new ItemStack(ItemListMF.plateMithrilPlate);
				case 2:
					return new ItemStack(ItemListMF.legsMithrilPlate);
				case 3:
					return new ItemStack(ItemListMF.bootsMithrilPlate);
				}
			}
		default:
			switch (slot) {
			case 0:
				return new ItemStack(ItemListMF.helmetLeatherRough);
			case 1:
				return new ItemStack(ItemListMF.plateLeatherRough);
			case 2:
				return new ItemStack(ItemListMF.legsLeatherRough);
			case 3:
				return new ItemStack(ItemListMF.bootsLeatherRough);
			default:
				return null;
			}
		}
	}

	/*
	 * 0 - copper/tin 1 - bronze 2 - iron 3 - steel 4 - encrusted 5 -
	 * dragonforge 6 - deep iron 7 - mithril
	 */
	private int getWeaponMaterial(World world, EntityLivingBase entity) {
		ChunkCoordinates spawn = world.getSpawnPoint();

		double spawnDistance = entity.getDistance(spawn.posX, spawn.posY, spawn.posZ);
		float difficultyFactorGlobal = world.difficultySetting / 3F;
		float difficultyFactorLocal = 0.5F
				+ (0.5F * (world.getLocationTensionFactor(entity.posX, entity.posY, entity.posZ) / 1.5F));

		int limit = Math.min((int) ((spawnDistance / 500F) * difficultyFactorGlobal * (difficultyFactorLocal)), 7);
		if (limit > 0) {
			return entity.getRNG().nextInt(limit);
		}
		return 0;
	}

	private boolean shouldGetWeapon(World world, EntityLivingBase entity) {
		ChunkCoordinates spawn = world.getSpawnPoint();

		double spawnDistance = entity.getDistance(spawn.posX, spawn.posY, spawn.posZ);
		float difficultyFactorGlobal = (world.difficultySetting + 1) / 2F;
		float difficultyFactorLocal = 0.5F
				+ (0.5F * (world.getLocationTensionFactor(entity.posX, entity.posY, entity.posZ) / 1.5F));

		int lowerChance = (int) (1F / (0.01F + (0.15F / difficultyFactorLocal)));
		int higherChance = Math.max((int) ((75000 / difficultyFactorGlobal) / (1F + spawnDistance)), 2);
		int chance = Math.min(lowerChance, higherChance);
		return entity.getRNG().nextInt(chance) == 0;
	}

	private ItemStack getWeaponItem(int mat, Random rand) {
		switch (mat) {
		case 0:
			switch (rand.nextInt(3)) {
			case 0:
				return new ItemStack(rand.nextBoolean() ? ItemListMF.swordCopper : ItemListMF.swordTin);
			case 1:
				return new ItemStack(rand.nextBoolean() ? ItemListMF.waraxeCopper : ItemListMF.waraxeTin);
			case 2:
				return new ItemStack(rand.nextBoolean() ? ItemListMF.maceCopper : ItemListMF.maceTin);
			}
		case 1:
			switch (rand.nextInt(3)) {
			case 0:
				return new ItemStack(ItemListMF.swordBronze);
			case 1:
				return new ItemStack(ItemListMF.waraxeBronze);
			case 2:
				return new ItemStack(ItemListMF.maceBronze);
			}
		case 2:
			switch (rand.nextInt(3)) {
			case 0:
				return new ItemStack(ItemListMF.swordIronForged);
			case 1:
				return new ItemStack(ItemListMF.waraxeIron);
			case 2:
				return new ItemStack(ItemListMF.maceIron);
			}
		case 3:
			switch (rand.nextInt(3)) {
			case 0:
				return new ItemStack(ItemListMF.swordSteelForged);
			case 1:
				return new ItemStack(ItemListMF.waraxeSteel);
			case 2:
				return new ItemStack(ItemListMF.maceSteel);
			}
		case 4:
			switch (rand.nextInt(3)) {
			case 0:
				return new ItemStack(ItemListMF.swordEncrusted);
			case 1:
				return new ItemStack(ItemListMF.waraxeEncrusted);
			case 2:
				return new ItemStack(ItemListMF.maceEncrusted);
			}
		case 5:
			switch (rand.nextInt(3)) {
			case 0:
				return new ItemStack(ItemListMF.swordDragon);
			case 1:
				return new ItemStack(ItemListMF.waraxeDragon);
			case 2:
				return new ItemStack(ItemListMF.maceDragon);
			}
		case 6:
			switch (rand.nextInt(3)) {
			case 0:
				return new ItemStack(ItemListMF.swordDeepIron);
			case 1:
				return new ItemStack(ItemListMF.waraxeDeepIron);
			case 2:
				return new ItemStack(ItemListMF.maceDeepIron);
			}
		case 7:
			switch (rand.nextInt(3)) {
			case 0:
				return new ItemStack(ItemListMF.swordMithril);
			case 1:
				return new ItemStack(ItemListMF.waraxeMithril);
			case 2:
				return new ItemStack(ItemListMF.maceMithril);
			}
		default:
			return null;
		}
	}
	
	@ForgeSubscribe
	public void initMapGen(InitMapGenEvent event) {
		if (event.type == InitMapGenEvent.EventType.VILLAGE) {
			event.newGen = new MapGenVillageMF();
		}
	}
	
	@ForgeSubscribe
	public void villagerInteract(EntityInteractEvent event) {
		if (event.entityPlayer != null && event.target != null && event.target instanceof EntityVillager) {
			EntityVillager villager = (EntityVillager) event.target;
			
			int i = 0;
			for (Object o : villager.getRecipes(event.entityPlayer)) {
				MerchantRecipe recipe = (MerchantRecipe) o;
				
				if (recipe.getItemToSell() != null) {
					ItemStack selling = recipe.getItemToSell();
					if (selling.getItem() == Item.arrow) {
						villager.getRecipes(event.entityPlayer).set(i, new MerchantRecipe(recipe.getItemToBuy(), recipe.getSecondItemToBuy(), new ItemStack(ItemListMF.arrowMF, selling.stackSize, 1)));
					} else if (selling.getItem() == Item.flintAndSteel) {
						villager.getRecipes(event.entityPlayer).set(i, new MerchantRecipe(recipe.getItemToBuy(), recipe.getSecondItemToBuy(), new ItemStack(ItemListMF.tinderbox)));
					} else if (selling.getItem() == Item.shears) {
						villager.getRecipes(event.entityPlayer).set(i, new MerchantRecipe(recipe.getItemToBuy(), recipe.getSecondItemToBuy(), new ItemStack(ItemListMF.shearsBronze)));
					} else if (selling.getItem() instanceof ItemTool) {
						ItemStack replacement = null;
						
						if (selling.getItem() == Item.pickaxeIron) {
							replacement = new ItemStack(ItemListMF.pickBronze);
						} else if (selling.getItem() == Item.pickaxeDiamond) {
							replacement = new ItemStack(ItemListMF.pickIronForged);
						} else if (selling.getItem() == Item.axeIron) {
							replacement = new ItemStack(ItemListMF.axeBronze);
						} else if (selling.getItem() == Item.axeDiamond) {
							replacement = new ItemStack(ItemListMF.axeIronForged);
						} else if (selling.getItem() == Item.shovelIron) {
							replacement = new ItemStack(ItemListMF.spadeBronze);
						} else if (selling.getItem() == Item.shovelDiamond) {
							replacement = new ItemStack(ItemListMF.spadeIronForged);
						} else if (selling.getItem() == Item.hoeIron) {
							replacement = new ItemStack(ItemListMF.hoeBronze);
						} else if (selling.getItem() == Item.hoeDiamond) {
							replacement = new ItemStack(ItemListMF.hoeIronForged);
						}
						
						if (replacement != null) {
							ItemStack buying = recipe.getItemToBuy();
							ItemStack buying2 = recipe.getSecondItemToBuy();
							if (buying != null && buying.getItem() == selling.getItem()) {
								buying = replacement.copy();
							}
							ItemStack newSelling = replacement.copy();
							if (selling.stackTagCompound != null) {
								newSelling.stackTagCompound = selling.stackTagCompound;
							}
							villager.getRecipes(event.entityPlayer).set(i, new MerchantRecipe(buying, buying2, newSelling));
						}
					} else if (selling.getItem() instanceof ItemSword) {
						ItemStack replacement = null;
						
						if (selling.getItem() == Item.swordIron) {
							replacement = new ItemStack(ItemListMF.swordBronze);
						} else if (selling.getItem() == Item.swordDiamond) {
							replacement = new ItemStack(ItemListMF.swordIronForged);
						}
						
						if (replacement != null) {
							ItemStack buying = recipe.getItemToBuy();
							ItemStack buying2 = recipe.getSecondItemToBuy();
							if (buying != null && buying.getItem() == selling.getItem()) {
								buying = replacement.copy();
							}
							ItemStack newSelling = replacement.copy();
							if (selling.stackTagCompound != null) {
								newSelling.stackTagCompound = selling.stackTagCompound;
							}
							villager.getRecipes(event.entityPlayer).set(i, new MerchantRecipe(buying, buying2, newSelling));
						}
					} else if (selling.getItem() instanceof ItemArmor) {
						ItemStack replacement = null;
						
						if (selling.getItem() == Item.helmetLeather) {
							replacement = new ItemStack(ItemListMF.helmetLeatherRough);
						} else if (selling.getItem() == Item.helmetChain) {
							replacement = new ItemStack(ItemListMF.helmetBronzeChain);
						} else if (selling.getItem() == Item.helmetIron) {
							replacement = new ItemStack(ItemListMF.helmetBronzeSplint);
						} else if (selling.getItem() == Item.helmetDiamond) {
							replacement = new ItemStack(ItemListMF.helmetBronzeScale);
						} else if (selling.getItem() == Item.plateLeather) {
							replacement = new ItemStack(ItemListMF.plateLeatherRough);
						} else if (selling.getItem() == Item.plateChain) {
							replacement = new ItemStack(ItemListMF.plateBronzeChain);
						} else if (selling.getItem() == Item.plateIron) {
							replacement = new ItemStack(ItemListMF.plateBronzeSplint);
						} else if (selling.getItem() == Item.plateDiamond) {
							replacement = new ItemStack(ItemListMF.plateBronzeScale);
						} else if (selling.getItem() == Item.legsLeather) {
							replacement = new ItemStack(ItemListMF.legsLeatherRough);
						} else if (selling.getItem() == Item.legsChain) {
							replacement = new ItemStack(ItemListMF.legsBronzeChain);
						} else if (selling.getItem() == Item.legsIron) {
							replacement = new ItemStack(ItemListMF.legsBronzeSplint);
						} else if (selling.getItem() == Item.legsDiamond) {
							replacement = new ItemStack(ItemListMF.legsBronzeScale);
						} else if (selling.getItem() == Item.bootsLeather) {
							replacement = new ItemStack(ItemListMF.bootsLeatherRough);
						} else if (selling.getItem() == Item.bootsChain) {
							replacement = new ItemStack(ItemListMF.bootsBronzeChain);
						} else if (selling.getItem() == Item.bootsIron) {
							replacement = new ItemStack(ItemListMF.bootsBronzeSplint);
						} else if (selling.getItem() == Item.bootsDiamond) {
							replacement = new ItemStack(ItemListMF.bootsBronzeScale);
						}
						
						if (replacement != null) {
							ItemStack buying = recipe.getItemToBuy();
							ItemStack buying2 = recipe.getSecondItemToBuy();
							if (buying != null && buying.getItem() == selling.getItem()) {
								buying = replacement.copy();
							}
							ItemStack newSelling = replacement.copy();
							if (selling.stackTagCompound != null) {
								newSelling.stackTagCompound = selling.stackTagCompound;
							}
							villager.getRecipes(event.entityPlayer).set(i, new MerchantRecipe(buying, buying2, newSelling));
						}
					}
				}
				
				i++;
			}
		}
	}
	
	// @ForgeSubscribe
	// public void chunkLoad(ChunkDataEvent.Load event) {
		//System.out.println("(" + event.getChunk().xPosition + ", " + event.getChunk().zPosition + ")");
	// }
	
	@SideOnly(Side.CLIENT)
	@ForgeSubscribe
	public void itemTooltip(ItemTooltipEvent event) {
		if ((event.itemStack != null) && (event.itemStack.getItem() != null) && (event.itemStack.getItem() instanceof ItemArmor)) {
			final EnumArmourClass armorClass = TacticalManager.getClassFor(event.itemStack);
			if (armorClass != EnumArmourClass.UNARMOURED) {
				event.toolTip.add("");
				event.toolTip.add(StatCollector.translateToLocal("armorclass." + armorClass.getName()));
			}
		}
	}

}
