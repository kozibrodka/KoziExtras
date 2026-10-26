package net.kozibrodka.extra.mixin.piston;


import net.kozibrodka.extra.mixin_interface.INeedsStateForBoxUpdate;
import net.minecraft.block.Block;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.block.entity.StationFlatteningPistonBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonExtensionBlock.class)
public class PistonExtensionBlockMixin {

    @Shadow
    PistonBlockEntity getPistonBlockEntity(BlockView blockView, int x, int y, int z) {return null;}

    @Redirect(
            method = "updateBoundingBox(Lnet/minecraft/world/BlockView;III)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;updateBoundingBox(Lnet/minecraft/world/BlockView;III)V" // Klasa Block i jej metoda
            )
    )
    private void redirectUpdateBoundingBox(Block block, BlockView blockView, int x, int y, int z) {
        if(block instanceof INeedsStateForBoxUpdate){
            PistonBlockEntity blockEntity = this.getPistonBlockEntity(blockView, x, y, z);
            ((INeedsStateForBoxUpdate)block).updateBoundingBox(blockEntity.getPushedBlockState(), blockView, x, y, z);
        }else{
            block.updateBoundingBox(blockView, x, y, z);
        }

    }
}
