package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.utils.KoziFacing;
import net.kozibrodka.extra.utils.KoziUtils;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Stairs;
import net.minecraft.block.StoneSlab;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(StoneSlab.class)
public class BlockSlabMixin extends BlockBase {
    protected BlockSlabMixin(int i, Material arg) {
        super(i, arg);
    }

    @Shadow private boolean field_2324;

    @Override
    public void onBlockPlaced(Level arg, int i, int j, int k){

    }

    @Override
    public void onBlockPlaced(Level var1, int i, int j, int k, int l){
        if(l == 0) {
            int var6 = var1.getTileMeta(i, j, k);
            var1.setTileMeta(i, j, k, var6 | 4);
        }else if(l != 1){
            KoziUtils kozi = new KoziUtils();
            float a = kozi.giveCursorHeigh(i,j,k);
            if((double)a >= 0.5D){
                int var6 = var1.getTileMeta(i, j, k);
                var1.setTileMeta(i, j, k, var6 | 4);
            }
        }
    }

    @Override
    public void updateBoundingBox(BlockView arg, int i, int j, int k){
        if(field_2324){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            boolean flag = (arg.getTileMeta(i, j, k) & 4) != 0;

            if (flag)
            {
                this.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
        }
    }

    @Override
    public void method_1605() {
        if(field_2324){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSideRendered(BlockView arg, int i, int j, int k, int l)
    {
        if(this.field_2324) {
            super.isSideRendered(arg, i, j, k, l);
        }

        if(l != 1 && l != 0 && !super.isSideRendered(arg, i, j, k, l)) {
            return false;
        } else {
            int var6 = i + KoziFacing.offsetsXForSide[KoziFacing.faceToSide[l]];
            int var7 = j + KoziFacing.offsetsYForSide[KoziFacing.faceToSide[l]];
            int var8 = k + KoziFacing.offsetsZForSide[KoziFacing.faceToSide[l]];
            boolean var9 = (arg.getTileMeta(var6, var7, var8) & 4) != 0;
            return !var9 ? (l == 1 || (l == 0 && super.isSideRendered(arg, i, j, k, l) || arg.getTileId(i, j, k) != this.id || (arg.getTileMeta(i, j, k) & 4) != 0)) : (l == 0 || (l == 1 && super.isSideRendered(arg, i, j, k, l) || arg.getTileId(i, j, k) != this.id || (arg.getTileMeta(i, j, k) & 4) == 0));
        }
    }

    @Override
    public void doesBoxCollide(Level world, int i, int j, int k, Box box, ArrayList list) {
        this.updateBoundingBox(world, i, j, k);
        super.doesBoxCollide(world, i, j, k, box, list);
    }

    @Inject(method = "droppedMeta", at = @At("HEAD"), cancellable = true)
    private void injected(int i, CallbackInfoReturnable<Integer> cir) {
//        if(i < 4)
//            cir.setReturnValue(i);
//        else{
//            cir.setReturnValue(i - 4);
//        }
        cir.setReturnValue(i & 3);

    }

    @Override
    public int getTextureForSide(int i, int j) {
        if (j == 0 || j == 4) {
            return i <= 1 ? 6 : 5;
        } else if (j == 1 || j == 5) {
            if (i == 0) {
                return 208;
            } else {
                return i == 1 ? 176 : 192;
            }
        } else if (j == 2 || j == 6) {
            return 4;
        } else if (j == 3 || j == 7) {
            return 16;
        } else{
            return 0;
        }
    }

}
