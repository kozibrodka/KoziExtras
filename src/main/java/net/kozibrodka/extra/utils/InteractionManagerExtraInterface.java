package net.kozibrodka.extra.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface InteractionManagerExtraInterface {

    boolean interactBlockExtra(PlayerEntity player, World world, ItemStack item, int x, int y, int z, int side, Vec3d eyeVec);

//    boolean interactBlockExtraClient(PlayerEntity player, World world, ItemStack item, int x, int y, int z, int side, Vec3d eyeVec);

}
