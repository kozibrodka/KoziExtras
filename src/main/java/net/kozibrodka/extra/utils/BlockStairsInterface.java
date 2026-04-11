package net.kozibrodka.extra.utils;

import net.minecraft.world.BlockView;

public interface BlockStairsInterface {
    boolean doesStairsCollide1(BlockView par1IBlockAccess, int par2, int par3, int par4);
    boolean doesStairsCollide2(BlockView par1IBlockAccess, int par2, int par3, int par4);
    void updateBoundingBox1(BlockView arg, int i, int j, int k);
}
