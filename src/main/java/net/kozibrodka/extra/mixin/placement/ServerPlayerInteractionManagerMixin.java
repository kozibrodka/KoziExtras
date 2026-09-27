package net.kozibrodka.extra.mixin.placement;

import net.kozibrodka.extra.mixin_interface.BlockItemExtraPlacementInterface;
import net.kozibrodka.extra.mixin_interface.ServerInteractionManagerExtraInterface;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin implements ServerInteractionManagerExtraInterface {

    @Override
    public boolean interactBlockExtra(PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side, float offSetX, float offSetY, float offSetZ) {
        int var8 = world.getBlockId(x, y, z);
        if (var8 > 0 && Block.BLOCKS[var8].onUse(world, x, y, z, player)) {
            return true;
        } else {
            if(itemStack != null){
                if(itemStack.getItem() instanceof BlockItemExtraPlacementInterface){
                    return  ((BlockItemExtraPlacementInterface)itemStack.getItem()).useOnBlockExtra(itemStack, player, world, x, y, z, side, offSetX, offSetY, offSetZ);
                }else{
                    return itemStack.useOnBlock(player, world, x, y, z, side);
                }
            }
            return false;
        }
    }
}
