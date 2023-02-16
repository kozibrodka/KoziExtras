package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.events.BlockListener;
import net.kozibrodka.extra.utils.BlockFenceInterface;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Fence;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(Fence.class)
public class BlockFenceMixin extends BlockBase implements BlockFenceInterface {

    protected BlockFenceMixin(int i, Material arg) {
        super(i, arg);
    }

    public void doesBoxCollide(Level level, int x, int y, int z, Box box, ArrayList list)
    {
        boolean var8 = this.canConnectFenceTo(level, x, y, z - 1);
        boolean var9 = this.canConnectFenceTo(level, x, y, z + 1);
        boolean var10 = this.canConnectFenceTo(level, x - 1, y, z);
        boolean var11 = this.canConnectFenceTo(level, x + 1, y, z);
        float var12 = 0.375F;
        float var13 = 0.625F;
        float var14 = 0.375F;
        float var15 = 0.625F;

        if (var8)
        {
            var14 = 0.0F;
        }

        if (var9)
        {
            var15 = 1.0F;
        }

        if (var8 || var9)
        {
            this.setBoundingBox(var12, 0.0F, var14, var13, 1.5F, var15);
            super.doesBoxCollide(level, x, y, z, box, list);
        }

        var14 = 0.375F;
        var15 = 0.625F;

        if (var10)
        {
            var12 = 0.0F;
        }

        if (var11)
        {
            var13 = 1.0F;
        }

        if (var10 || var11 || !var8 && !var9)
        {
            this.setBoundingBox(var12, 0.0F, var14, var13, 1.5F, var15);
            super.doesBoxCollide(level, x, y, z, box, list);
        }

        if (var8)
        {
            var14 = 0.0F;
        }

        if (var9)
        {
            var15 = 1.0F;
        }

        this.setBoundingBox(var12, 0.0F, var14, var13, 1.0F, var15);
    }

    public void updateBoundingBox(BlockView blockView, int x, int y, int z)
    {
        boolean var5 = this.canConnectFenceTo(blockView, x, y, z - 1);
        boolean var6 = this.canConnectFenceTo(blockView, x, y, z + 1);
        boolean var7 = this.canConnectFenceTo(blockView, x - 1, y, z);
        boolean var8 = this.canConnectFenceTo(blockView, x + 1, y, z);
        float var9 = 0.375F;
        float var10 = 0.625F;
        float var11 = 0.375F;
        float var12 = 0.625F;

        if (var5)
        {
            var11 = 0.0F;
        }

        if (var6)
        {
            var12 = 1.0F;
        }

        if (var7)
        {
            var9 = 0.0F;
        }

        if (var8)
        {
            var10 = 1.0F;
        }

        this.setBoundingBox(var9, 0.0F, var11, var10, 1.0F, var12);
    }

    public boolean canConnectFenceTo(BlockView blockView, int par2, int par3, int par4)
    {
        int var5 = blockView.getTileId(par2, par3, par4);

        if (!isIdAFence(var5)) // && var5 != Block.fenceGate.blockID
        {
            BlockBase var6 = BlockBase.BY_ID[var5];
            return var6 != null && var6.material.hasNoSuffocation() && var6.isFullCube() && var6.material != Material.PUMPKIN;
        }
        else
        {
            return true;
        }
    }

    public boolean isIdAFence(int par0)
    {
        return par0 == BlockBase.FENCE.id || par0 == BlockListener.fence1337.id; //TO BE DONE
    }

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void injected(Level arg, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canPlaceAt(arg,i,j,k));
    }

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void injected1(Level arg, int i, int j, int k, CallbackInfoReturnable<Box> cir) {
        cir.setReturnValue(super.getCollisionShape(arg,i,j,k));
    }

}
