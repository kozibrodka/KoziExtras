package net.kozibrodka.extra.mixin_interface;

import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.Direction;

public interface BlockStairsInterface {
    boolean createOuterCorners(World world, int x, int y, int z);
    boolean createInnerCorners(World world, int x, int y, int z);
    void updateBoundingBox1(BlockView arg, int i, int j, int k);
    void onPlacedStairsExtra(World world, int i, int j, int k, boolean upper, Direction geoFacing, int oldMeta);
}
