package net.kozibrodka.extra.mixin_interface;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface BlockItemExtraPlacementInterface {

    boolean useOnBlockExtra(ItemStack itemstack, PlayerEntity playerbase, World world, int x, int y, int z, int side, float offSetX, float offSetY, float offSetZ, int lookDirection);
}
