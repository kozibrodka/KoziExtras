package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.blocksCosmetic.BlockWoodenSlabExtra;
import net.kozibrodka.extra.utils.KoziUtils;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Stairs;
import net.minecraft.block.StoneSlab;
import net.minecraft.block.Trapdoor;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Trapdoor.class)
public class BlockTrapdoorMixin extends BlockBase {

    protected BlockTrapdoorMixin(int i, Material arg) {
        super(i, arg);
    }
    KoziUtils kozi = new KoziUtils();
    @Shadow
    public void method_1059(Level arg, int i, int j, int k, boolean bl) {}
    @Shadow
    public static boolean method_1061(int i) {
        return (i & 4) != 0;
    }

//    @Redirect(method = "onAdjacentBlockUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/Level;canSuffocate(III)Z"))
//    private void injected3(Level instance, int j, int k, int i) {
//       if(!isValidSupportBlock(instance.getTileId(j, k, i)));
//    }


    @Inject(method = "method_1060", at = @At("HEAD"), cancellable = true)
    private void injectedStairs(int par1, CallbackInfo ci) {
        float var2 = 0.1875F;

        if ((par1 & 8) != 0)
        {
            this.setBoundingBox(0.0F, 1.0F - var2, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, var2, 1.0F);
        }
//        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, var2, 1.0F);
        if (this.method_1061(par1)) {
            if ((par1 & 3) == 0) {
                this.setBoundingBox(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }

            if ((par1 & 3) == 1) {
                this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }

            if ((par1 & 3) == 2) {
                this.setBoundingBox(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if ((par1 & 3) == 3) {
                this.setBoundingBox(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
        }
        ci.cancel();
    }


    @Override
    public void onAdjacentBlockUpdate(Level arg, int i, int j, int k, int l) {
        if (!arg.isServerSide) {
            int var6 = arg.getTileMeta(i, j, k);
            int var7 = i;
            int var8 = k;
            if ((var6 & 3) == 0) {
                var8 = k + 1;
            }

            if ((var6 & 3) == 1) {
                --var8;
            }

            if ((var6 & 3) == 2) {
                var7 = i + 1;
            }

            if ((var6 & 3) == 3) {
                --var7;
            }

            if (!isValidSupportBlock(arg.getTileId(var7, j, var8))) {
                arg.setTile(i, j, k, 0);
                this.drop(arg, i, j, k, var6);
            }

            if (l > 0 && BlockBase.BY_ID[l].getEmitsRedstonePower()) {
                boolean var9 = arg.hasRedstonePower(i, j, k);
                this.method_1059(arg, i, j, k, var9);
            }

        }
    }

    @Override
    public void onBlockPlaced(Level arg, int i, int j, int k, int l){
        int var10 = 0;

        if (l == 2)
        {
            var10 = 0;
        }

        if (l == 3)
        {
            var10 = 1;
        }

        if (l == 4)
        {
            var10 = 2;
        }

        if (l == 5)
        {
            var10 = 3;
        }
        if (l != 1 && l != 0 && kozi.giveCursorHeigh(i,j,k) > 0.5F)
        {
            var10 |= 8;
        }
        arg.setTileMeta(i, j, k, var10);
    }

    @Inject(method = "canPlaceAt", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
    private void injected(Level level, int j, int k, int l, int par5, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(isValidSupportBlock(level.getTileId(j,k,l)));
//        cir.setReturnValue(level.canSuffocate(j,k,l));
    }

    private static boolean isValidSupportBlock(int par0)
    {
        if (par0 <= 0)
        {
            return false;
        }
        else
        {
            BlockBase var1 = BlockBase.BY_ID[par0];
            return var1 != null && var1.material.hasNoSuffocation() && var1.isFullCube() || var1 == BlockBase.GLOWSTONE || var1 instanceof StoneSlab || var1 instanceof Stairs|| var1 instanceof BlockWoodenSlabExtra; //DODAWAC SLABY
        }
    }

}
