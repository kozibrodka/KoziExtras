package net.kozibrodka.extra.generate;


import net.kozibrodka.extra.events.BlockListener;
import net.kozibrodka.extra.farming.BlockCocoa;
import net.kozibrodka.extra.farming.BlockVine;
import net.minecraft.block.BlockBase;
import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;

import java.util.Random;

public class JungleTree extends Structure
{
    /** The minimum height of a generated tree. */
    private final int minTreeHeight;

    /** True if this tree should grow Vines. */
    private final boolean vinesGrow;


    public JungleTree(boolean par1)
    {
        this(par1, 4, 0, 0, false);
    }

    public JungleTree(boolean par1, int par2, int par3, int par4, boolean par5)
    {
        this.minTreeHeight = par2;
        this.vinesGrow = par5;
    }

    public boolean generate(Level level, Random random, int x, int y, int z)
    {
        int var6 = random.nextInt(3) + this.minTreeHeight;
        boolean var7 = true;

        if (y >= 1 && y + var6 + 1 <= 256)
        {
            int var8;
            byte var9;
            int var11;
            int var12;

            for (var8 = y; var8 <= y + 1 + var6; ++var8)
            {
                var9 = 1;

                if (var8 == y)
                {
                    var9 = 0;
                }

                if (var8 >= y + 1 + var6 - 2)
                {
                    var9 = 2;
                }

                for (int var10 = x - var9; var10 <= x + var9 && var7; ++var10)
                {
                    for (var11 = z - var9; var11 <= z + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                            var12 = level.getTileId(var10, var8, var11);

                            if (var12 != 0 && var12 != BlockBase.LEAVES.id && var12 != BlockBase.GRASS.id && var12 != BlockBase.DIRT.id && var12 != BlockBase.LOG.id && var12 != BlockListener.junglewood.id && var12 != BlockListener.jungleleaves.id)
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
                    level.setTileInChunk(x, y - 1, z, BlockBase.DIRT.id);
                    var9 = 3;
                    byte var19 = 0;
                    int var13;
                    int var14;
                    int var15;

                    for (var11 = y - var9 + var6; var11 <= y + var6; ++var11)
                    {
                        var12 = var11 - (y + var6);
                        var13 = var19 + 1 - var12 / 2;

                        for (var14 = x - var13; var14 <= x + var13; ++var14)
                        {
                            var15 = var14 - x;

                            for (int var16 = z - var13; var16 <= z + var13; ++var16)
                            {
                                int var17 = var16 - z;

                                if (Math.abs(var15) != var13 || Math.abs(var17) != var13 || random.nextInt(2) != 0 && var12 != 0)
                                {
                                    int var18 = level.getTileId(var14, var11, var16);

                                    if (var18 == 0 || var18 == BlockBase.LEAVES.id || var18 == BlockListener.jungleleaves.id)
                                    {
                                        level.setTile(var14, var11, var16, BlockListener.jungleleaves.id);
                                    }
                                }
                            }
                        }
                    }

                    for (var11 = 0; var11 < var6; ++var11)
                    {
                        var12 = level.getTileId(x, y + var11, z);

                        if (var12 == 0 || var12 == BlockListener.jungleleaves.id) //if (var12 == 0 || var12 == BlockBase.LEAVES.id || var12 == BlockListener.jungleleaves.id)
                        {
                            level.setTile(x, y + var11, z, BlockListener.junglewood.id);

                            if (this.vinesGrow && var11 > 0)
                            {
                                if (random.nextInt(3) > 0 && level.isAir(x - 1, y + var11, z))
                                {
//                                    level.setTile(x - 1, y + var11, z, BlockListener.vine.id); //8
                                    this.stawVines(level, x - 1, y + var11, z, 4);
                                }

                                if (random.nextInt(3) > 0 && level.isAir(x + 1, y + var11, z))
                                {
//                                    level.setTile(x + 1, y + var11, z, BlockListener.vine.id); //2
                                    this.stawVines(level, x + 1, y + var11, z,5);
                                }

                                if (random.nextInt(3) > 0 && level.isAir(x, y + var11, z - 1))
                                {
//                                    level.setTile(x, y + var11, z - 1, BlockListener.vine.id); //1
                                    this.stawVines(level, x, y + var11, z - 1,2);
                                }

                                if (random.nextInt(3) > 0 && level.isAir(x, y + var11, z + 1))
                                {
//                                    level.setTile(x, y + var11, z + 1, BlockListener.vine.id); //4
                                    this.stawVines(level, x, y + var11, z + 1,3);
                                }
                            }
                        }
                    }

                    if (this.vinesGrow)
                    {
                        for (var11 = y - 3 + var6; var11 <= y + var6; ++var11)
                        {
                            var12 = var11 - (y + var6);
                            var13 = 2 - var12 / 2;

                            for (var14 = x - var13; var14 <= x + var13; ++var14)
                            {
                                for (var15 = z - var13; var15 <= z + var13; ++var15)
                                {
                                    if (level.getTileId(var14, var11, var15) == BlockListener.jungleleaves.id)
                                    {
                                        if (random.nextInt(4) == 0 && level.getTileId(var14 - 1, var11, var15) == 0)
                                        {
                                            this.growVines(level, var14 - 1, var11, var15, 4);
                                        }

                                        if (random.nextInt(4) == 0 && level.getTileId(var14 + 1, var11, var15) == 0)
                                        {
                                            this.growVines(level, var14 + 1, var11, var15, 5);
                                        }

                                        if (random.nextInt(4) == 0 && level.getTileId(var14, var11, var15 - 1) == 0)
                                        {
                                            this.growVines(level, var14, var11, var15 - 1, 2);
                                        }

                                        if (random.nextInt(4) == 0 && level.getTileId(var14, var11, var15 + 1) == 0)
                                        {
                                            this.growVines(level, var14, var11, var15 + 1, 3);
                                        }
                                    }
                                }
                            }
                        }

                        if (random.nextInt(5) == 0 && var6 > 5)
                        {
                            for (var11 = 0; var11 < 2; ++var11)
                            {
                                for (var12 = 0; var12 < 4; ++var12)
                                {
                                    if (random.nextInt(4 - var11) == 0)
                                    {
                                        var13 = random.nextInt(3);

                                        if(var12 == 0)
                                        {
//                                            z += 1;
                                            level.setTile(x, y + var6 - 5 + var11, z + 1, BlockListener.cocoaplant.id);
                                            ((BlockCocoa)BlockListener.cocoaplant).onTreeGrowth(level, x, y + var6 - 5 + var11, z + 1, 3, var13);
                                        }
                                        if(var12 == 2)
                                        {
//                                            x += 1;
                                            level.setTile(x + 1, y + var6 - 5 + var11, z, BlockListener.cocoaplant.id);
                                            ((BlockCocoa)BlockListener.cocoaplant).onTreeGrowth(level, x + 1, y + var6 - 5 + var11, z, 5, var13);
                                        }
                                        if(var12 == 1)
                                        {
//                                            z -= 1;
                                            level.setTile(x, y + var6 - 5 + var11, z - 1, BlockListener.cocoaplant.id);
                                            ((BlockCocoa)BlockListener.cocoaplant).onTreeGrowth(level, x, y + var6 - 5 + var11, z - 1, 2, var13);
                                        }
                                        if(var12 == 3)
                                        {
//                                            x -= 1;
                                            level.setTile(x - 1, y + var6 - 5 + var11, z, BlockListener.cocoaplant.id);
                                            ((BlockCocoa)BlockListener.cocoaplant).onTreeGrowth(level, x - 1, y + var6 - 5 + var11, z, 4, var13);
                                        }
//                                        level.setTile(level, x + Direction.offsetX[Direction.rotateOpposite[var12]], y + var6 - 5 + var11, z + Direction.offsetZ[Direction.rotateOpposite[var12]], Block.cocoaPlant.id, var13 << 2 | var12);
//                                        level.setTile(x , y , z , BlockListener.cocoaplant.id);
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

    /**
     * Grows vines downward from the given block for a given length. Args: World, x, starty, z, vine-length
     */
    private void growVines(Level level, int x, int y, int z, int meta)
    {
        level.setTile(x, y, z, BlockListener.vine.id);
        ((BlockVine)BlockListener.vine).onTreeGrowth(level, x, y, z, meta);
        int var6 = 4;

        while (true)
        {
            --y;

            if (level.getTileId(x, y, z) != 0 || var6 <= 0)
            {
                return;
            }

            level.setTile(x, y, z, BlockListener.vine.id);
            ((BlockVine)BlockListener.vine).onTreeGrowth(level, x, y, z, meta);
            --var6;
        }
    }

    private void stawVines(Level level, int x, int y, int z, int meta)
    {
        level.setTile(x, y, z, BlockListener.vine.id);
        ((BlockVine)BlockListener.vine).onTreeGrowth(level, x, y, z, meta);
    }
}

