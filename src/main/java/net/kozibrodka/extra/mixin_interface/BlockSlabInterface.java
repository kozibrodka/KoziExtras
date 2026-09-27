package net.kozibrodka.extra.mixin_interface;

import net.minecraft.world.World;

public interface BlockSlabInterface {

    void onPlacedSlabExtra(World world, int i, int j, int k, boolean upper);
}
