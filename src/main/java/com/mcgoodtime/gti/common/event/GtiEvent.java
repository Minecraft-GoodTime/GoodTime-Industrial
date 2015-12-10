/*
 * This file is part of GoodTime-Industrial, licensed under MIT License (MIT).
 *
 * Copyright (c) 2015 GoodTime Studio <https://github.com/GoodTimeStudio>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mcgoodtime.gti.common.event;

import com.mcgoodtime.gti.common.GtiPotion;
import com.mcgoodtime.gti.common.entity.EntityThrowable;
import com.mcgoodtime.gti.common.init.GtiAchievement;
import com.mcgoodtime.gti.common.init.GtiBlocks;
import com.mcgoodtime.gti.common.init.GtiItems;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import ic2.core.IC2Potion;
import ic2.core.Ic2Items;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.player.FillBucketEvent;

/*
 * Created by suhao on 2015/5/17.
 */

public class GtiEvent {
    @SubscribeEvent
    public void onPlayerCrafting(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem().equals(GtiBlocks.carbonizeFurnace.getItem())) {
            event.player.addStat(GtiAchievement.getCarbonizeFurnace, 1);
        }
    }

    @SubscribeEvent
    public void onPlayerPickup(PlayerEvent.ItemPickupEvent event) {
        if (event.pickedUp.getEntityItem().isItemEqual(new ItemStack(GtiBlocks.oreIridium))) {
            event.player.addStat(GtiAchievement.getIrOre, 1);
        }
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        if (event.entityPlayer != null) {
            BiomeGenBase biome = event.world.getBiomeGenForCoords(event.target.blockX, event.target.blockZ);
            if (biome == BiomeGenBase.ocean || biome == BiomeGenBase.deepOcean || biome == BiomeGenBase.frozenOcean) {
                event.setResult(Event.Result.ALLOW);
                event.result = new ItemStack(GtiItems.saltWaterBucket);
            }
        }
    }

    @SubscribeEvent
    public void onEntityThrowableImpact(EntityThrowableImpactEvent event) {
        if (event.entityThrowable.getThrowItem().getItem().equals(GtiItems.packagedSalt)) {
            this.onImpact(event.entityThrowable, event.movingObjectPosition, new PotionEffect(GtiPotion.salty.id, 0, 3));
        }
        else if (event.entityThrowable.getThrowItem().isItemEqual(Ic2Items.Uran238)) {
            this.onImpact(event.entityThrowable, event.movingObjectPosition, new PotionEffect(IC2Potion.radiation.id, 200, 0));
        }
        else if (event.entityThrowable.getThrowItem().getItem().equals(GtiItems.gravityRay)) {
            this.onImpact(event.entityThrowable, event.movingObjectPosition);
        }
    }

    private void onImpact(EntityThrowable entity, MovingObjectPosition movingObjectPosition) {
        this.onImpact(entity, movingObjectPosition, null);
    }

    private void onImpact(EntityThrowable entity, MovingObjectPosition movingObjectPosition, PotionEffect potionEffect) {
        if (movingObjectPosition.entityHit != null) {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(entity, entity.getThrower()), 3F);
            if (movingObjectPosition.entityHit instanceof EntityLivingBase && potionEffect != null) {
                ((EntityLivingBase) movingObjectPosition.entityHit).addPotionEffect(potionEffect);
            }
        }

        for (int i = 0; i < 8; ++i) {
            float fmod = (float) (1.2 - (Math.random() * 2.4));
            float f1mod = (float) (0.5 - (Math.random() * 1.0));
            float f2mod = (float) (1.2 - (Math.random() * 2.4));

            entity.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(GtiItems.salt), entity.posX + fmod,
                    entity.posY + f1mod, entity.posZ + f2mod, 0.1D, 0.1D, 0.1D);

            if (!entity.worldObj.isRemote) {
                EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX + fmod, entity.posY + f1mod, entity.posZ + f2mod, new ItemStack(GtiItems.salt));
                entity.worldObj.spawnEntityInWorld(entityItem);
            }
        }

        if (!entity.worldObj.isRemote) {
            entity.setDead();
        }
    }
}
