package com.mcgoodtime.gti.common;

import com.mcgoodtime.gti.common.entity.EntityThrowable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;

import java.util.Random;

/**
 * Created by BestOwl on 2015.11.2.0002.
 *
 * Gti Util
 * @author BestOwl
 */
public class GtiUtil {

    public static void throwItemByPlayer(net.minecraft.entity.projectile.EntityThrowable entity) {
        if (!((EntityPlayer) entity.getThrower()).capabilities.isCreativeMode) {
            --((EntityThrowable) entity).getThrowItem().stackSize;
        }
        entity.worldObj.playSoundAtEntity(entity.getThrower(), "random.bow", 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F));
        if (!entity.worldObj.isRemote) {
            entity.worldObj.spawnEntityInWorld(entity);
        }
    }

    public static void onThrowItemImpact(MovingObjectPosition objectPosition, net.minecraft.entity.projectile.EntityThrowable throwable, Item item, Potion potion, int potionTime, int level) {
        if (objectPosition.entityHit != null) {
            byte b0 = 0;

            if (objectPosition.entityHit instanceof EntityBlaze) {
                b0 = 3;
            }

            objectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(throwable, throwable.getThrower()), (float)b0);
            if (objectPosition.entityHit instanceof EntityLivingBase) {
                if (potion != null) {
                    ((EntityLivingBase) objectPosition.entityHit).addPotionEffect(new PotionEffect(potion.id, potionTime, level));
                }
            }
        }

        for (int i = 0; i < 8; ++i) {
            throwable.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(item), throwable.posX, throwable.posY, throwable.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!throwable.worldObj.isRemote) {
            throwable.setDead();
        }
    }
}
