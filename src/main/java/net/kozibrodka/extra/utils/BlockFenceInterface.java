package net.kozibrodka.extra.utils;

import net.minecraft.level.BlockView;

public interface BlockFenceInterface {
     boolean canConnectFenceTo(BlockView blockView, int par2, int par3, int par4);
}
