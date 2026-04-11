package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.utils.BlockStairsInterface;
import net.kozibrodka.extra.utils.KoziUtils;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;


@Mixin(StairsBlock.class)
public class BlockStairsMixin extends Block implements BlockStairsInterface {

    protected BlockStairsMixin(int i, Material arg) {
        super(i, arg);
    }


    @Override
    public void addIntersectingBoundingBox(World world, int i, int j, int k, Box box, ArrayList list)
    {
        this.updateBoundingBox1(world, i, j, k);
        super.addIntersectingBoundingBox(world, i, j, k, box, list);

        boolean var8 = this.doesStairsCollide1(world, i, j, k);
        super.addIntersectingBoundingBox(world, i, j, k, box, list);

        if (var8 && this.doesStairsCollide2(world, i, j, k))
        {
            super.addIntersectingBoundingBox(world, i, j, k, box, list);
        }

        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);


//        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
//        super.doesBoxCollide(world, par2, par3, par4, box, list);
//        this.setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
//        super.doesBoxCollide(world, par2, par3, par4, box, list);
    }

//    @Override
//    public void updateBoundingBox(BlockView arg, int i, int j, int k) {
////        int meta = arg.getTileMeta(i, j, k);
////        if ((meta & 4) != 0)
////        {
////            this.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
////        }
////        else
////        {
////            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
////        }
//
//
//        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
////        this.setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
//    }

    public void updateBoundingBox1(BlockView arg, int i, int j, int k){
                int meta = arg.getBlockMeta(i, j, k);
        if ((meta & 4) != 0)
        {
            this.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }


    @Override
    public void onPlaced(World arg, int i, int j, int k, LivingEntity arg2) {
        int var6 = MathHelper.floor((double)(arg2.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        int var7 = arg.getBlockMeta(i, j, k) & 4;
//        System.out.println(arg.getTileMeta(i, j, k));
//        System.out.println(var7);
//        System.out.println("XD");
        if(var6 == 0) {
            arg.setBlockMeta(i, j, k, 2 | var7);
        }

        if(var6 == 1) {
            arg.setBlockMeta(i, j, k, 1 | var7);
        }

        if(var6 == 2) {
            arg.setBlockMeta(i, j, k, 3 | var7);
        }

        if(var6 == 3) {
            arg.setBlockMeta(i, j, k, 0 | var7);
        }

    }

    public void onPlaced(World var1, int i, int j, int k, int l){
        if(l == 0) {
            int var6 = var1.getBlockMeta(i, j, k);
            var1.setBlockMeta(i, j, k, var6 | 4);
        }else if(l != 1){
            KoziUtils kozi = new KoziUtils();
            float a = kozi.giveCursorHeigh(i,j,k);
            if((double)a >= 0.5D){
                int var6 = var1.getBlockMeta(i, j, k);
                var1.setBlockMeta(i, j, k, var6 | 4);
            }
        }
    }

    private boolean isStairsConnected(BlockView blockviev, int x, int y, int z, int par5)
    {
        int var6 = blockviev.getBlockId(x, y, z);
        return isBlockStairsID(var6) && blockviev.getBlockMeta(x, y, z) == par5;
    }

    public boolean isBlockStairsID(int par0)
    {
        return par0 > 0 && Block.BLOCKS[par0] instanceof StairsBlock;
    }

    @Override
    public boolean doesStairsCollide1(BlockView par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMeta(par2, par3, par4);
        int var6 = var5 & 3;
        float var7 = 0.5F;
        float var8 = 1.0F;

        if ((var5 & 4) != 0)
        {
            var7 = 0.0F;
            var8 = 0.5F;
        }

        float var9 = 0.0F;
        float var10 = 1.0F;
        float var11 = 0.0F;
        float var12 = 0.5F;
        boolean var13 = true;
        int var14;
        int var15;
        int var16;

        if (var6 == 0)
        {
            var9 = 0.5F;
            var12 = 1.0F;
            var14 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
            var15 = par1IBlockAccess.getBlockMeta(par2 + 1, par3, par4);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.isStairsConnected(par1IBlockAccess, par2, par3, par4 + 1, var5))
                {
                    var12 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 2 && !this.isStairsConnected(par1IBlockAccess, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.5F;
                    var13 = false;
                }
            }
        }
        else if (var6 == 1)
        {
            var10 = 0.5F;
            var12 = 1.0F;
            var14 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
            var15 = par1IBlockAccess.getBlockMeta(par2 - 1, par3, par4);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.isStairsConnected(par1IBlockAccess, par2, par3, par4 + 1, var5))
                {
                    var12 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 2 && !this.isStairsConnected(par1IBlockAccess, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.5F;
                    var13 = false;
                }
            }
        }
        else if (var6 == 2)
        {
            var11 = 0.5F;
            var12 = 1.0F;
            var14 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
            var15 = par1IBlockAccess.getBlockMeta(par2, par3, par4 + 1);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.isStairsConnected(par1IBlockAccess, par2 + 1, par3, par4, var5))
                {
                    var10 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 0 && !this.isStairsConnected(par1IBlockAccess, par2 - 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var13 = false;
                }
            }
        }
        else if (var6 == 3)
        {
            var14 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
            var15 = par1IBlockAccess.getBlockMeta(par2, par3, par4 - 1);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.isStairsConnected(par1IBlockAccess, par2 + 1, par3, par4, var5))
                {
                    var10 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 0 && !this.isStairsConnected(par1IBlockAccess, par2 - 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var13 = false;
                }
            }
        }

        this.setBoundingBox(var9, var7, var11, var10, var8, var12);
        return var13;
    }

    @Override
    public boolean doesStairsCollide2(BlockView par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMeta(par2, par3, par4);
        int var6 = var5 & 3;
        float var7 = 0.5F;
        float var8 = 1.0F;

        if ((var5 & 4) != 0)
        {
            var7 = 0.0F;
            var8 = 0.5F;
        }

        float var9 = 0.0F;
        float var10 = 0.5F;
        float var11 = 0.5F;
        float var12 = 1.0F;
        boolean var13 = false;
        int var14;
        int var15;
        int var16;

        if (var6 == 0)
        {
            var14 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
            var15 = par1IBlockAccess.getBlockMeta(par2 - 1, par3, par4);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.isStairsConnected(par1IBlockAccess, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.0F;
                    var12 = 0.5F;
                    var13 = true;
                }
                else if (var16 == 2 && !this.isStairsConnected(par1IBlockAccess, par2, par3, par4 + 1, var5))
                {
                    var11 = 0.5F;
                    var12 = 1.0F;
                    var13 = true;
                }
            }
        }
        else if (var6 == 1)
        {
            var14 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
            var15 = par1IBlockAccess.getBlockMeta(par2 + 1, par3, par4);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var9 = 0.5F;
                var10 = 1.0F;
                var16 = var15 & 3;

                if (var16 == 3 && !this.isStairsConnected(par1IBlockAccess, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.0F;
                    var12 = 0.5F;
                    var13 = true;
                }
                else if (var16 == 2 && !this.isStairsConnected(par1IBlockAccess, par2, par3, par4 + 1, var5))
                {
                    var11 = 0.5F;
                    var12 = 1.0F;
                    var13 = true;
                }
            }
        }
        else if (var6 == 2)
        {
            var14 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
            var15 = par1IBlockAccess.getBlockMeta(par2, par3, par4 - 1);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var11 = 0.0F;
                var12 = 0.5F;
                var16 = var15 & 3;

                if (var16 == 1 && !this.isStairsConnected(par1IBlockAccess, par2 - 1, par3, par4, var5))
                {
                    var13 = true;
                }
                else if (var16 == 0 && !this.isStairsConnected(par1IBlockAccess, par2 + 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var10 = 1.0F;
                    var13 = true;
                }
            }
        }
        else if (var6 == 3)
        {
            var14 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
            var15 = par1IBlockAccess.getBlockMeta(par2, par3, par4 + 1);

            if (isBlockStairsID(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.isStairsConnected(par1IBlockAccess, par2 - 1, par3, par4, var5))
                {
                    var13 = true;
                }
                else if (var16 == 0 && !this.isStairsConnected(par1IBlockAccess, par2 + 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var10 = 1.0F;
                    var13 = true;
                }
            }
        }

        if (var13)
        {
            this.setBoundingBox(var9, var7, var11, var10, var8, var12);
        }

        return var13;
    }

}
