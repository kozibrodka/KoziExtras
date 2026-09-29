package net.kozibrodka.extra.mixin.placement;

import net.kozibrodka.extra.mixin_interface.BlockItemExtraPlacementInterface;
import net.kozibrodka.extra.mixin_interface.InteractionManagerExtraInterface;
import net.kozibrodka.extra.utils.LookDirectionUtils;
import net.minecraft.block.Block;
import net.minecraft.client.InteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(InteractionManager.class)
public class InteractionManagerMixin implements InteractionManagerExtraInterface {

    /// Singleplayer method
    @Override
    public boolean interactBlockExtra(PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side, Vec3d eyeVec) {
        int var8 = world.getBlockId(x, y, z);
        if (var8 > 0 && Block.BLOCKS[var8].onUse(world, x, y, z, player)) {
            return true;
        } else {
            if(itemStack != null){
                if(itemStack.getItem() instanceof BlockItemExtraPlacementInterface){
                    float xPosition = (float)eyeVec.x - (float)x;
                    float yPosition = (float)eyeVec.y - (float)y;
                    float zPosition = (float)eyeVec.z - (float)z;

                    return  ((BlockItemExtraPlacementInterface)itemStack.getItem()).useOnBlockExtra(itemStack, player, world, x, y, z, side, xPosition, yPosition, zPosition, LookDirectionUtils.headYawToLookDirection(player));
                }else{
                    return itemStack.useOnBlock(player, world, x, y, z, side);
                }
            }
            return false;
        }
    }

    /// Client on server method
    @Unique
    public boolean interactBlockExtraClient(PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side, Vec3d eyeVec, int lookDirection) {
        /// Aby uniknąć Overflow crash - zapętlenia.
        int var8 = world.getBlockId(x, y, z);
        if (var8 > 0 && Block.BLOCKS[var8].onUse(world, x, y, z, player)) {
            return true;
        } else {
            if(itemStack != null){
                if(itemStack.getItem() instanceof BlockItemExtraPlacementInterface){
                    float xPosition = (float)eyeVec.x - (float)x;
                    float yPosition = (float)eyeVec.y - (float)y;
                    float zPosition = (float)eyeVec.z - (float)z;

                    return  ((BlockItemExtraPlacementInterface)itemStack.getItem()).useOnBlockExtra(itemStack, player, world, x, y, z, side, xPosition, yPosition, zPosition, lookDirection);
                }else{
                    return itemStack.useOnBlock(player, world, x, y, z, side);
                }
            }
            return false;
        }
    }
}
