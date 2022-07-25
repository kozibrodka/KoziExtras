package net.kozibrodka.extra.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.block.Log;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.level.BlockStateView;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Log.class)
public class LogMixin extends BlockBase implements BlockTemplate {


    protected LogMixin(int i, Material arg) {
        super(i, arg);
    }

//    @Override
//    public void afterPlaced(Level level, int i, int j, int k, Living arg2) {
//        int var6 = MathHelper.floor((double)(arg2.yaw * 4.0F / 360.0F) + 0.5D) & 3;
//        System.out.println("KĄT: " + var6);
//        if(var6 == 0 || var6 == 1) {
////            setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 0.5F, 1.0F);
////            ((BlockStateView)level).setBlockStateWithNotify(i, j, k, getDefaultState().with(ROTACJA, 1));
//        }
//        if(var6 == 2 || var6 == 3) {
////            setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
////            ((BlockStateView)level).setBlockStateWithNotify(i, j, k, getDefaultState().with(ROTACJA, 2));
//        }
//    }

    @Override
    public void onBlockPlaced(Level level, int x, int y, int z, int side) {


//        int korzen = level.getTileMeta(x,y,z);
////        System.out.println(korzen);
//        if(side == 1 || side == 0)
//        {
//            level.setTileMeta(x,y,z, korzen);
//        }
//        if(side == 4 || side == 5)
//        {
//            level.setTileMeta(x,y,z, korzen + 3);
//        }
//        if(side == 2 || side == 3)
//        {
//            level.setTileMeta(x,y,z, korzen+ 6);
//        }
    }
}
