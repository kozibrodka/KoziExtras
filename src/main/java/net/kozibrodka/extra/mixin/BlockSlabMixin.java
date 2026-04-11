package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.utils.KoziFacing;
import net.kozibrodka.extra.utils.KoziUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(SlabBlock.class)
public class BlockSlabMixin extends Block {
    protected BlockSlabMixin(int i, Material arg) {
        super(i, arg);
    }

    @Shadow private boolean field_2324;

    @Override
    public void onPlaced(World arg, int i, int j, int k){

    }

    @Override
    public void onPlaced(World var1, int i, int j, int k, int l){
        if(l == 0) {
            int var6 = var1.getBlockMeta(i, j, k);
            var1.setBlockMeta(i, j, k, var6 | 4);
        }else if(l != 1){
            KoziUtils kozi = new KoziUtils();
            float a = kozi.giveCursorHeigh(i,j,k);
            if((double)a >= 0.5D){
                int var6 = var1.getBlockMeta(i, j, k);
                var1.setBlockMeta(i, j, k, var6 | 4);
            }
        }
    }

    @Override
    public void updateBoundingBox(BlockView arg, int i, int j, int k){
        if(field_2324){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            boolean flag = (arg.getBlockMeta(i, j, k) & 4) != 0;

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
    public void setupRenderBoundingBox() {
        if(field_2324){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSideVisible(BlockView arg, int i, int j, int k, int l)
    {
        if(this.field_2324) {
            super.isSideVisible(arg, i, j, k, l);
        }

        if(l != 1 && l != 0 && !super.isSideVisible(arg, i, j, k, l)) {
            return false;
        } else {
            int var6 = i + KoziFacing.offsetsXForSide[KoziFacing.faceToSide[l]];
            int var7 = j + KoziFacing.offsetsYForSide[KoziFacing.faceToSide[l]];
            int var8 = k + KoziFacing.offsetsZForSide[KoziFacing.faceToSide[l]];
            boolean var9 = (arg.getBlockMeta(var6, var7, var8) & 4) != 0;
            return !var9 ? (l == 1 || (l == 0 && super.isSideVisible(arg, i, j, k, l) || arg.getBlockId(i, j, k) != this.id || (arg.getBlockMeta(i, j, k) & 4) != 0)) : (l == 0 || (l == 1 && super.isSideVisible(arg, i, j, k, l) || arg.getBlockId(i, j, k) != this.id || (arg.getBlockMeta(i, j, k) & 4) == 0));
        }
    }

    @Override
    public void addIntersectingBoundingBox(World world, int i, int j, int k, Box box, ArrayList list) {
        this.updateBoundingBox(world, i, j, k);
        super.addIntersectingBoundingBox(world, i, j, k, box, list);
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
    public int getTexture(int i, int j) {
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
