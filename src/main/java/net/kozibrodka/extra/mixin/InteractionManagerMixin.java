package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.utils.InteractionManagerExtraInterface;
import net.minecraft.block.Block;
import net.minecraft.client.InteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SlabBlockItem;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InteractionManager.class)
public class InteractionManagerMixin implements InteractionManagerExtraInterface {

    @Override
    public boolean interactBlockExtra(PlayerEntity player, World world, ItemStack item, int x, int y, int z, int side, Vec3d eyeVec) {
        int var8 = world.getBlockId(x, y, z);
        if (var8 > 0 && Block.BLOCKS[var8].onUse(world, x, y, z, player)) {
            return true;
        } else {
            if(item != null){
                if(item.getItem() instanceof SlabBlockItem){
                    return item.useOnBlock(player, world, x, y, z, side); /// TODO mój osobny interface
                }else{
                    return item.useOnBlock(player, world, x, y, z, side);
                }
            }
            return false;
        }
    }

//    @Override
    public boolean interactBlockExtraClient(PlayerEntity player, World world, ItemStack item, int x, int y, int z, int side, Vec3d eyeVec) {
        return this.interactBlockExtra(player, world, item, x, y, z, side, eyeVec);
    }
}
