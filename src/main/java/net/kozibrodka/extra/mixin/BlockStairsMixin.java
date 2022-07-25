package net.kozibrodka.extra.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.block.Stairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;


@Mixin(Stairs.class)
public class BlockStairsMixin extends BlockBase {


    protected BlockStairsMixin(int i, Material arg) {
        super(i, arg);
    }

    @Override
    public void onBlockPlaced(Level var1, int var2, int var3, int var4, int var5) {
        if(var5 == 0) {
            int var6 = var1.getTileMeta(var2, var3, var4);
            var1.setTileMeta(var2, var3, var4, var6 | 4);
        }
    }

    @Override
    public void doesBoxCollide(Level arg, int i, int j, int k, Box arg2, ArrayList arraylist) {
        int var7 = arg.getTileMeta(i, j, k);
        int var8 = var7 & 3;
        float var9 = 0.0F;
        float var10 = 0.5F;
        float var11 = 0.5F;
        float var12 = 1.0F;
        if((var7 & 4) != 0) {
            var9 = 0.5F;
            var10 = 1.0F;
            var11 = 0.0F;
            var12 = 0.5F;
        }

        this.setBoundingBox(0.0F, var9, 0.0F, 1.0F, var10, 1.0F);
        super.doesBoxCollide(arg, i, j, k, arg2, arraylist);
        if(var8 == 0) {
            this.setBoundingBox(0.5F, var11, 0.0F, 1.0F, var12, 1.0F);
            super.doesBoxCollide(arg, i, j, k, arg2, arraylist);
        } else if(var8 == 1) {
            this.setBoundingBox(0.0F, var11, 0.0F, 0.5F, var12, 1.0F);
            super.doesBoxCollide(arg, i, j, k, arg2, arraylist);
        } else if(var8 == 2) {
            this.setBoundingBox(0.0F, var11, 0.5F, 1.0F, var12, 1.0F);
            super.doesBoxCollide(arg, i, j, k, arg2, arraylist);
        } else if(var8 == 3) {
            this.setBoundingBox(0.0F, var11, 0.0F, 1.0F, var12, 0.5F);
            super.doesBoxCollide(arg, i, j, k, arg2, arraylist);
        }

        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void afterPlaced(Level arg, int i, int j, int k, Living arg2) {
        int var6 = MathHelper.floor((double)(arg2.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        int var7 = arg.getTileMeta(i, j, k) & 4;
        if(var6 == 0) {
            arg.setTileMeta(i, j, k, 2 | var7);
        }

        if(var6 == 1) {
            arg.setTileMeta(i, j, k, 1 | var7);
        }

        if(var6 == 2) {

        }

        if(var6 == 3) {
            arg.setTileMeta(i, j, k, 0 | var7);
        }

    }
}
