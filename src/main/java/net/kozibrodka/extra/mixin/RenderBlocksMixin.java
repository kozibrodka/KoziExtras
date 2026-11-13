package net.kozibrodka.extra.mixin;


import net.kozibrodka.extra.mixin_interface.BlockStairsInterface;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRenderManager.class)
public abstract class RenderBlocksMixin {

    /// Potrzebny aby w razie potrzeby "ZASŁONIĆ" Vanilla render na potrzeby DEBUG - aby nie zmylało że renderuje coś.
    /// MIXIN dla starej logiki vanilla render, prawdopodobnie do wyjebania

    @Shadow
    public boolean renderBlock(Block arg, int i, int j, int k) {
        return false;
    }
    @Shadow
    private BlockView blockView;
    @Shadow
    private int eastFaceRotation;
    @Shadow
    private int westFaceRotation;
    @Shadow
    private int southFaceRotation;
    @Shadow
    private int northFaceRotation;
    @Shadow
    private int topFaceRotation;
    @Shadow
    private int bottomFaceRotation;

    @Shadow public abstract boolean renderCactus(Block arg, int i, int j, int k);

//    @Inject(method = "renderStairs", at = @At("HEAD"), cancellable = true)
//    private void injectedStairs(Block arg, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
//
////            cir.setReturnValue(true); //TODO zamazywacz
//
//    }

//    @Inject(method = "renderFence", at = @At("HEAD"), cancellable = true)
//    private void injectedFence(Block arg, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
//        boolean var5 = false;
//        float var6 = 0.375F;
//        float var7 = 0.625F;
//        arg.setBoundingBox(var6, 0.0F, var6, var7, 1.0F, var7);
//        this.renderBlock(arg, i, j, k);
//        var5 = true;
//        boolean var8 = false;
//        boolean var9 = false;
//        if (((BlockFenceInterface)arg).canConnectFenceTo(blockView, i - 1, j, k) || ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i + 1, j, k)) {
//            var8 = true;
//        }
//
//        if (((BlockFenceInterface)arg).canConnectFenceTo(blockView, i , j, k - 1) || ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i , j, k + 1)) {
//            var9 = true;
//        }
//
//        boolean var10 = ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i - 1, j, k);
//        boolean var11 = ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i + 1, j, k);
//        boolean var12 = ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i, j, k - 1);
//        boolean var13 = ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i, j, k + 1);
//        if (!var8 && !var9) {
//            var8 = true;
//        }
//
//        var6 = 0.4375F;
//        var7 = 0.5625F;
//        float var14 = 0.75F;
//        float var15 = 0.9375F;
//        float var16 = var10 ? 0.0F : var6;
//        float var17 = var11 ? 1.0F : var7;
//        float var18 = var12 ? 0.0F : var6;
//        float var19 = var13 ? 1.0F : var7;
//        if (var8) {
//            arg.setBoundingBox(var16, var14, var6, var17, var15, var7);
//            this.renderBlock(arg, i, j, k);
//            var5 = true;
//        }
//
//        if (var9) {
//            arg.setBoundingBox(var6, var14, var18, var7, var15, var19);
//            this.renderBlock(arg, i, j, k);
//            var5 = true;
//        }
//
//        var14 = 0.375F;
//        var15 = 0.5625F;
//        if (var8) {
//            arg.setBoundingBox(var16, var14, var6, var17, var15, var7);
//            this.renderBlock(arg, i, j, k);
//            var5 = true;
//        }
//
//        if (var9) {
//            arg.setBoundingBox(var6, var14, var18, var7, var15, var19);
//            this.renderBlock(arg, i, j, k);
//            var5 = true;
//        }
//
//        arg.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//        cir.setReturnValue(var5);
//    }


}
