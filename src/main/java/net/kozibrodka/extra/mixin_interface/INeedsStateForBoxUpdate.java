package net.kozibrodka.extra.mixin_interface;

import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.block.BlockState;

public interface INeedsStateForBoxUpdate {

    void updateBoundingBox(BlockState state, BlockView arg, int i, int j, int k);
}
