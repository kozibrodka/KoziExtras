package net.kozibrodka.extra.generate;

import net.kozibrodka.extra.events.BlockListener;
import net.kozibrodka.extra.farming.BlockVine;
import net.minecraft.block.BlockBase;
import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.minecraft.util.maths.MathHelper;

import java.util.Random;

public class HugeJungleTree extends Structure
{
    /** The base height of the tree */
    private final int baseHeight;

//    /** Sets the metadata for the wood blocks used */
//    private final int woodMetadata;
//
//    /** Sets the metadata for the leaves used in huge trees */
//    private final int leavesMetadata;

    public HugeJungleTree(boolean par1, int par2, int par3, int par4)
    {
//        super(par1);
        this.baseHeight = par2;
//        this.woodMetadata = par3;
//        this.leavesMetadata = par4;
    }

    public boolean generate(Level level, Random random, int x, int y, int z)
    {
        int var6 = random.nextInt(3) + this.baseHeight;
        boolean var7 = true;

        if (y >= 1 && y + var6 + 1 <= 256)
        {
            int var8;
            int var10;
            int var11;
            int var12;

            for (var8 = y; var8 <= y + 1 + var6; ++var8)
            {
                byte var9 = 2;

                if (var8 == y)
                {
                    var9 = 1;
                }

                if (var8 >= y + 1 + var6 - 2)
                {
                    var9 = 2;
                }

                for (var10 = x - var9; var10 <= x + var9 && var7; ++var10)
                {
                    for (var11 = z - var9; var11 <= z + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                            var12 = level.getTileId(var10, var8, var11);

                            if (var12 != 0 && var12 != BlockBase.LEAVES.id && var12 != BlockBase.GRASS.id && var12 != BlockBase.DIRT.id && var12 != BlockBase.LOG.id && var12 != BlockListener.junglesapling.id && var12 != BlockListener.junglewood.id && var12 != BlockListener.jungleleaves.id)
                            {
                                var7 = false;
                            }
                        }
                        else
                        {
                            var7 = false;
                        }
                    }
                }
            }

            if (!var7)
            {
                return false;
            }
            else
            {
                var8 = level.getTileId(x, y - 1, z);

                if ((var8 == BlockBase.GRASS.id || var8 == BlockBase.DIRT.id) && y < 256 - var6 - 1)
                {
                    level.setTileInChunk(x, y - 1, z, BlockBase.DIRT.id); // (0,2)?
                    level.setTileInChunk(x + 1, y - 1, z, BlockBase.DIRT.id);
                    level.setTileInChunk(x, y - 1, z + 1, BlockBase.DIRT.id);
                    level.setTileInChunk(x + 1, y - 1, z + 1, BlockBase.DIRT.id);
                    this.growLeaves(level, x, z, y + var6, 2, random);

                    for (int var14 = y + var6 - 2 - random.nextInt(4); var14 > y + var6 / 2; var14 -= 2 + random.nextInt(4))
                    {
                        float var15 = random.nextFloat() * (float)Math.PI * 2.0F;
                        var11 = x + (int)(0.5F + MathHelper.cos(var15) * 4.0F);
                        var12 = z + (int)(0.5F + MathHelper.sin(var15) * 4.0F);
                        this.growLeaves(level, var11, var12, var14, 0, random);

                        for (int var13 = 0; var13 < 5; ++var13)
                        {
                            var11 = x + (int)(1.5F + MathHelper.cos(var15) * (float)var13);
                            var12 = z + (int)(1.5F + MathHelper.sin(var15) * (float)var13);
                            level.setTile(var11, var14 - 3 + var13 / 2, var12, BlockListener.junglewood.id);
                        }
                    }

                    for (var10 = 0; var10 < var6; ++var10)
                    {
                        var11 = level.getTileId(x, y + var10, z);

                        if (var11 == 0 || var11 == BlockListener.jungleleaves.id)
                        {
                            level.setTile(x, y + var10, z, BlockListener.junglewood.id);

                            if (var10 > 0)
                            {
                                if (random.nextInt(3) > 0 && level.isAir(x - 1, y + var10, z))
                                {
//                                    level.setTile(x - 1, y + var10, z, BlockListener.vine.id, 8);   // 8 - 2 - 1 -4
                                    this.stawVines(level,x - 1, y + var10, z, 4);             // 4 - 5 - 2 - 3
                                }

                                if (random.nextInt(3) > 0 && level.isAir(x, y + var10, z - 1))
                                {
//                                    level.setTile(x, y + var10, z - 1, BlockListener.vine.id, 1);
                                    this.stawVines(level,x, y + var10, z - 1, 2);
                                }
                            }
                        }

                        if (var10 < var6 - 1)
                        {
                            var11 = level.getTileId(x + 1, y + var10, z);

                            if (var11 == 0 || var11 == BlockListener.jungleleaves.id)
                            {
                                level.setTile(x + 1, y + var10, z, BlockListener.junglewood.id);

                                if (var10 > 0)
                                {
                                    if (random.nextInt(3) > 0 && level.isAir(x + 2, y + var10, z))
                                    {
//                                        level.setTile(x + 2, y + var10, z, BlockListener.vine.id, 2);
                                        this.stawVines(level,x + 2, y + var10, z, 5);
                                    }

                                    if (random.nextInt(3) > 0 && level.isAir(x + 1, y + var10, z - 1))
                                    {
//                                        level.setTile(x + 1, y + var10, z - 1, BlockListener.vine.id, 1);
                                        this.stawVines(level,x + 1, y + var10, z - 1, 2);
                                    }
                                }
                            }

                            var11 = level.getTileId(x + 1, y + var10, z + 1);

                            if (var11 == 0 || var11 == BlockListener.jungleleaves.id)
                            {
                                level.setTile(x + 1, y + var10, z + 1, BlockListener.junglewood.id);

                                if (var10 > 0)
                                {
                                    if (random.nextInt(3) > 0 && level.isAir(x + 2, y + var10, z + 1))
                                    {
//                                        level.setTile(x + 2, y + var10, z + 1, BlockListener.vine.id, 2);
                                        this.stawVines(level,x + 2, y + var10, z + 1, 5);
                                    }

                                    if (random.nextInt(3) > 0 && level.isAir(x + 1, y + var10, z + 2))
                                    {
//                                        level.setTile(x + 1, y + var10, z + 2, BlockListener.vine.id, 4);
                                        this.stawVines(level,x + 1, y + var10, z + 2, 3);
                                    }
                                }
                            }

                            var11 = level.getTileId(x, y + var10, z + 1);

                            if (var11 == 0 || var11 == BlockListener.jungleleaves.id)
                            {
                                level.setTile(x, y + var10, z + 1, BlockListener.junglewood.id);

                                if (var10 > 0)
                                {
                                    if (random.nextInt(3) > 0 && level.isAir(x - 1, y + var10, z + 1))
                                    {
//                                        level.setTile(x - 1, y + var10, z + 1, BlockListener.vine.id, 8);
                                        this.stawVines(level,x - 1, y + var10, z + 1, 4);
                                    }

                                    if (random.nextInt(3) > 0 && level.isAir(x, y + var10, z + 2))
                                    {
//                                        level.setTile(x, y + var10, z + 2, BlockListener.vine.id, 4);
                                        this.stawVines(level,x, y + var10, z + 2, 3);
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }

    private void stawVines(Level level, int x, int y, int z, int meta)
    {
        level.setTile(x, y, z, BlockListener.vine.id);
        ((BlockVine)BlockListener.vine).onTreeGrowth(level, x, y, z, meta);
    }

    private void growLeaves(Level level, int x, int y, int z, int par5, Random random)
    {
        byte var7 = 2;

        for (int var8 = z - var7; var8 <= z; ++var8)
        {
            int var9 = var8 - z;
            int var10 = par5 + 1 - var9;

            for (int var11 = x - var10; var11 <= x + var10 + 1; ++var11)
            {
                int var12 = var11 - x;

                for (int var13 = y - var10; var13 <= y + var10 + 1; ++var13)
                {
                    int var14 = var13 - y;

                    if ((var12 >= 0 || var14 >= 0 || var12 * var12 + var14 * var14 <= var10 * var10) && (var12 <= 0 && var14 <= 0 || var12 * var12 + var14 * var14 <= (var10 + 1) * (var10 + 1)) && (random.nextInt(4) != 0 || var12 * var12 + var14 * var14 <= (var10 - 1) * (var10 - 1)))
                    {
                        int var15 = level.getTileId(var11, var8, var13);

                        if (var15 == 0 || var15 == BlockListener.jungleleaves.id)
                        {
                            level.setTile(var11, var8, var13, BlockListener.jungleleaves.id);
                        }
                    }
                }
            }
        }
    }
}

