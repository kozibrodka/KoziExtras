package net.kozibrodka.extra.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.extra.events.TextureListener;
import net.kozibrodka.extra.utils.KoziUtils;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.client.model.block.BlockWithWorldRenderer;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LogBlock.class)
public class LogMixin extends Block implements BlockWithWorldRenderer{

    protected LogMixin(int i, Material arg) {
        super(i, arg);
    }

    @Environment(EnvType.CLIENT)
    public void setupRenderBoundingBox() {
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public int getTexture(int i, int j) {
        if(j == 0){
            return i <= 1 ? 21 : 20;
        }else if(j == 4){
           return i == 4 || i == 5 ? 21 : 20;
        }else if(j == 8){
            return i == 2 || i == 3 ? 21 : 20;
        }else if(j == 1){
            return i <= 1 ? TextureListener.log_spruce_top : 116;
        }else if(j == 5){
            return i == 4 || i == 5 ? TextureListener.log_spruce_top : 116;
        }else if(j == 9){
            return i == 2 || i == 3 ? TextureListener.log_spruce_top : 116;
        }else if(j == 2){
            return i <= 1 ? TextureListener.log_birch_top : 117;
        }else if(j == 6){
            return i == 4 || i == 5 ? TextureListener.log_birch_top : 117;
        }else if(j == 10){
            return i == 2 || i == 3 ? TextureListener.log_birch_top : 117;
        }else if(j == 3){
            return i <= 1 ? TextureListener.log_jungle_top : TextureListener.log_jungle_side;
        }else if(j == 7){
            return i == 4 || i == 5 ? TextureListener.log_jungle_top : TextureListener.log_jungle_side;
        }else if(j == 11){
            return i == 2 || i == 3 ? TextureListener.log_jungle_top : TextureListener.log_jungle_side;
        }else{
            return 21;
        }
    }

    @Inject(method = "droppedMeta", at = @At("HEAD"), cancellable = true)
    private void injected(int i, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(i & 3);
    }

    public void onPlaced(World var1, int i, int j, int k, int l){
        int var6 = var1.getBlockMeta(i, j, k);
        int var10 = var6 & 3;
        byte var11 = 0;
        switch (l)
        {
            case 0:
            case 1:
                var11 = 0;
                break;

            case 2:
            case 3:
                var11 = 8;
                break;

            case 4:
            case 5:
                var11 = 4;
        }
        var1.setBlockMeta(i, j, k, var10 | var11);
    }

    public boolean renderWorld(BlockRenderManager tileRenderer, BlockView tileView, int x, int y, int z) {

        int var5 = tileView.getBlockMeta(x, y, z);
        int var6 = var5 & 12;

        if (var6 == 4)
        {
            ((RenderBlockAccessor)tileRenderer).setEastFaceRotation(1);
            ((RenderBlockAccessor)tileRenderer).setWestFaceRotation(1);
            ((RenderBlockAccessor)tileRenderer).setTopFaceRotation(1);
            ((RenderBlockAccessor)tileRenderer).setBottomFaceRotation(1);
//            this.eastFaceRotation = 1;
//            this.westFaceRotation = 1;
//            this.topFaceRotation = 1;
//            this.bottomFaceRotation = 1;
        }
        else if (var6 == 8)
        {
            ((RenderBlockAccessor)tileRenderer).setNorthFaceRotation(1);
            ((RenderBlockAccessor)tileRenderer).setSouthFaceRotation(1);
//            this.southFaceRotation = 1;
//            this.northFaceRotation = 1;
        }

        boolean var7 = tileRenderer.renderBlock(this, x, y, z);
        ((RenderBlockAccessor)tileRenderer).setEastFaceRotation(0);
        ((RenderBlockAccessor)tileRenderer).setWestFaceRotation(0);
        ((RenderBlockAccessor)tileRenderer).setTopFaceRotation(0);
        ((RenderBlockAccessor)tileRenderer).setBottomFaceRotation(0);
        ((RenderBlockAccessor)tileRenderer).setNorthFaceRotation(0);
        ((RenderBlockAccessor)tileRenderer).setSouthFaceRotation(0);
//        this.southFaceRotation = 0;
//        this.eastFaceRotation = 0;
//        this.westFaceRotation = 0;
//        this.northFaceRotation = 0;
//        this.topFaceRotation = 0;
//        this.bottomFaceRotation = 0;
        return var7;
    }
}
