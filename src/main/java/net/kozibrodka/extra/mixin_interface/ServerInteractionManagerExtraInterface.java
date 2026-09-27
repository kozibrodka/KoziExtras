package net.kozibrodka.extra.mixin_interface;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface ServerInteractionManagerExtraInterface {

    boolean interactBlockExtra(PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side, float offSetX, float offSetY, float offSetZ);

}
