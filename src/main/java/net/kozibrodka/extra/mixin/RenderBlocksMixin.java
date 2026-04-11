package net.kozibrodka.extra.mixin;


import net.kozibrodka.extra.utils.BlockFenceInterface;
import net.kozibrodka.extra.utils.BlockStairsInterface;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockRenderManager.class)
public abstract class RenderBlocksMixin {

    @Shadow
    public boolean renderStandardBlock(Block arg, int i, int j, int k) {
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

    @Inject(method = "renderStairs", at = @At("HEAD"), cancellable = true)
    private void injectedStairs(Block arg, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        ((BlockStairsInterface)arg).updateBoundingBox1(blockView,i,j,k);
        arg.setBoundingBox((float)arg.minX, (float)arg.minY, (float)arg.minZ, (float)arg.maxX, (float)arg.maxY, (float)arg.maxZ);
        this.renderStandardBlock(arg, i, j, k);

        boolean flag = ((BlockStairsInterface)arg).doesStairsCollide1(blockView,i,j,k);
        arg.setBoundingBox((float)arg.minX, (float)arg.minY, (float)arg.minZ, (float)arg.maxX, (float)arg.maxY, (float)arg.maxZ);
        this.renderStandardBlock(arg, i, j, k);

        if (flag && ((BlockStairsInterface)arg).doesStairsCollide2(blockView,i,j,k))
        {
            arg.setBoundingBox((float)arg.minX, (float)arg.minY, (float)arg.minZ, (float)arg.maxX, (float)arg.maxY, (float)arg.maxZ);
            this.renderStandardBlock(arg, i, j, k);
        }
            cir.setReturnValue(true);
    }

    @Inject(method = "renderFence", at = @At("HEAD"), cancellable = true)
    private void injectedFence(Block arg, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        boolean var5 = false;
        float var6 = 0.375F;
        float var7 = 0.625F;
        arg.setBoundingBox(var6, 0.0F, var6, var7, 1.0F, var7);
        this.renderStandardBlock(arg, i, j, k);
        var5 = true;
        boolean var8 = false;
        boolean var9 = false;
        if (((BlockFenceInterface)arg).canConnectFenceTo(blockView, i - 1, j, k) || ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i + 1, j, k)) {
            var8 = true;
        }

        if (((BlockFenceInterface)arg).canConnectFenceTo(blockView, i , j, k - 1) || ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i , j, k + 1)) {
            var9 = true;
        }

        boolean var10 = ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i - 1, j, k);
        boolean var11 = ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i + 1, j, k);
        boolean var12 = ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i, j, k - 1);
        boolean var13 = ((BlockFenceInterface)arg).canConnectFenceTo(blockView, i, j, k + 1);
        if (!var8 && !var9) {
            var8 = true;
        }

        var6 = 0.4375F;
        var7 = 0.5625F;
        float var14 = 0.75F;
        float var15 = 0.9375F;
        float var16 = var10 ? 0.0F : var6;
        float var17 = var11 ? 1.0F : var7;
        float var18 = var12 ? 0.0F : var6;
        float var19 = var13 ? 1.0F : var7;
        if (var8) {
            arg.setBoundingBox(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(arg, i, j, k);
            var5 = true;
        }

        if (var9) {
            arg.setBoundingBox(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(arg, i, j, k);
            var5 = true;
        }

        var14 = 0.375F;
        var15 = 0.5625F;
        if (var8) {
            arg.setBoundingBox(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(arg, i, j, k);
            var5 = true;
        }

        if (var9) {
            arg.setBoundingBox(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(arg, i, j, k);
            var5 = true;
        }

        arg.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        cir.setReturnValue(var5);
    }

//    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockBase;updateBoundingBox(Lnet/minecraft/level/BlockView;III)V", shift = At.Shift.AFTER), locals  = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
//    private void injected1(BlockBase arg, int i, int j, int k, CallbackInfoReturnable<Boolean> cir, int var5){
//        if (var5 == 18) {
//            cir.setReturnValue(this.renderBlockLog(arg, i, j, k));
//        }
//    }


}
