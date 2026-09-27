package net.kozibrodka.extra.mixin_interface;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface InteractionManagerExtraInterface {

    boolean interactBlockExtra(PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side, Vec3d eyeVec);

//    boolean interactBlockExtraClient(PlayerEntity player, World world, ItemStack item, int x, int y, int z, int side, Vec3d eyeVec);

}
