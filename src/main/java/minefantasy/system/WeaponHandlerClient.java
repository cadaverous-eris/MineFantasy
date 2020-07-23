package minefantasy.system;

import java.util.EnumSet;
import java.util.Random;

import minefantasy.api.weapon.IExtendedReachItem;
import minefantasy.api.weapon.IWeightedWeapon;
import minefantasy.item.weapon.ItemWeaponMF;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WeaponHandlerClient implements ITickHandler {
	
	private float offsetX = 0;
	private float offsetY = 0;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (!type.contains(TickType.PLAYER))
			return;
		EntityPlayer entityPlayer = (EntityPlayer) tickData[0];
		ItemStack weapon = entityPlayer.getHeldItem();
		float balance = 0.0F;

		if (weapon != null && weapon.getItem() instanceof IWeightedWeapon) {
			balance = ((IWeightedWeapon) weapon.getItem()).getBalance();
		}

		if (cfg.useBalance && balance > 0 && entityPlayer != null) {
			// If we JUST swung an Item
			/*
			if (entityPlayer.swingProgressInt == -1) {
				float amplify = 30.0F;

				float offsetX = entityPlayer.getRNG().nextFloat() * balance - (balance / 2);
				float offsetY = entityPlayer.getRNG().nextFloat() * balance;

				entityPlayer.rotationYaw += offsetX * amplify;
				entityPlayer.rotationPitch += offsetY * amplify;
				entityPlayer.moveStrafing += offsetX;
				if (offsetY > 0) {
					entityPlayer.moveForward += offsetY;
				}
			}
			*/
			if (entityPlayer.swingProgressInt == -1) {
				offsetX = entityPlayer.getRNG().nextFloat() * balance - (balance / 2);
				offsetY = highRandFloat(entityPlayer.getRNG()) * balance;
			}
			
			if (entityPlayer.isSwingInProgress) {
				// System.out.println(entityPlayer.swingProgressInt);
				float amplify = 30.0F;
				float timeScale = (entityPlayer.swingProgress / entityPlayer.swingProgressInt) * 1.25F;
				float maxTime = entityPlayer.swingProgressInt / entityPlayer.swingProgress;
				
				if (timeScale > 0 && entityPlayer.swingProgressInt <= maxTime / 2) {
					// System.out.println(maxTime);
					entityPlayer.rotationYaw += offsetX * amplify * timeScale * 2;
					entityPlayer.rotationPitch += offsetY * amplify * timeScale * 2;
					entityPlayer.moveStrafing += offsetX * timeScale * 2;
					if (offsetY > 0) {
						entityPlayer.moveForward += offsetY * timeScale * 2;
					}
				}
			} else {
				offsetX = 0;
				offsetY = 0;
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (!type.contains(TickType.PLAYER))
			return;

		EntityPlayer entityPlayer = (EntityPlayer) tickData[0];

		if (entityPlayer != null) {
			// If we JUST swung an Item
			if (entityPlayer.swingProgressInt == 1) {
				ItemStack mainhand = entityPlayer.getCurrentEquippedItem();
				if (mainhand != null && mainhand.getItem() instanceof IExtendedReachItem) {
					float extendedReach = ((IExtendedReachItem) mainhand.getItem()).getReachModifierInBlocks(mainhand);
					if (extendedReach > 0) {
						MovingObjectPosition mouseOver = ExtendedReachHelper.getMouseOver(0, extendedReach + 4);
						if (mouseOver != null && mouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
							Entity target = mouseOver.entityHit;
							if (target instanceof EntityLiving && target != entityPlayer) {
								if (target.hurtResistantTime != ((EntityLiving) target).maxHurtResistantTime) {
									FMLClientHandler.instance().getClient().playerController.attackEntity(entityPlayer,
											target);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "Minefantasy Weapon Ticker";
	}
	
	private float highRandFloat(Random rand) {
		return (rand.nextFloat() * 0.6F) + 0.4F;
	}

}