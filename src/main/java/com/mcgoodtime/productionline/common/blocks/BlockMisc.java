/*
 * This file is part of GoodTime-Industrial, licensed under MIT License (MIT).
 *
 * Copyright (c) 2015 Minecraft-GoodTime <http://github.com/Minecraft-GoodTime>
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
package com.mcgoodtime.productionline.common.blocks;

import com.mcgoodtime.productionline.common.init.PLBlocks;
import com.mcgoodtime.productionline.common.items.ItemBlockPL;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.mcgoodtime.productionline.common.core.ProductionLine.*;

/**
 * Created by suhao on 2015.10.18.0018.
 *
 * @author suhao
 */
public class BlockMisc extends BlockPL {

    public static List<String> internalNameList = new ArrayList<String>();

    static {
        internalNameList.add("CompressedWaterHyacinth");
        internalNameList.add("DehydratedWaterHyacinthBlock");
    }

    public BlockMisc() {
        super(Material.ROCK, "BlockMisc");
        this.setHardness(1.0F);
        PLBlocks.compressedWaterHyacinth = new ItemStack(this, 1, 0);
        PLBlocks.dehydratedWaterHyacinthblock = new ItemStack(this, 1, 1);
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockPL.class;
    }

    public int getMaxMeta() {
        return internalNameList.size();
    }

    /**
     * Returns the unlocalized name of this block. This version accepts an ItemStack so different stacks can have
     * different names based on their meta or NBT.
    */
    public String getBlockName(ItemStack itemStack) {
        return "tile." + MOD_ID + ".block." + this.getInternalName(itemStack.getItemDamage());
    }

    public String getInternalName(int meta) {
        return internalNameList.get(meta);
    }

//    /**
//     * Determines the damage on the item the block drops. Used in cloth and wood.
//     */
//    @Override
//    public int damageDropped(int meta) {
//        return meta;
//    }
}
