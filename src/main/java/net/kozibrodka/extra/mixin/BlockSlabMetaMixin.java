package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.utils.KoziFacing;
import net.kozibrodka.extra.utils.KoziUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Properties;

@Mixin(SlabBlock.class)
public class BlockSlabMetaMixin extends Block {
    protected BlockSlabMetaMixin(int i, Material arg) {
        super(i, arg);
    }

    @Shadow private boolean doubleSlab;

    @Unique
    private static final BooleanProperty UPPER = BooleanProperty.of("upper");
    @Unique
    private static final IntProperty MATERIAL = IntProperty.of("material", 0, 3); /// {STONE, SANDSTONE, WOOD, COBBLESTONE}

    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(UPPER);
        builder.add(MATERIAL);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructorEnd(CallbackInfo ci) {
        setDefaultState(getDefaultState()
                .with(UPPER, false)
        );
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        System.out.println("GRAMY");
        BlockState currentState = world.getBlockState(x, y, z);
        int mat = currentState.get(MATERIAL);
//        world.setBlockState(x,y,z, currentState.with(MATERIAL, mat % 4));
        world.setBlockState(x,y,z, currentState.with(MATERIAL, 3));
        return false;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        /// Override w celu usunięcia vanilla-logic, która ustawia DOUBLE-SLAB blok niżej.
    }


    @Override
    public void onPlaced(World world, int i, int j, int k, int side){
        int currentMeta = world.getBlockMeta(i, j, k); /// Moja aktualna Meta
        BlockState currentState = world.getBlockState(i, j, k);
        int newMeta = currentMeta;
        boolean uppper = false;
        if(side == 0) { /// Góra
//            newMeta = currentMeta | 4; //todo wyeliminować nowe mety.
            uppper = true;

        }else if(side != 1){ /// Poza dołem
            if(KoziUtils.giveCursorHeigh(i,j,k) >= 0.5F){
//                newMeta = currentMeta | 4;
                uppper = true;
            }
        }
//        world.setBlockState(i, j, k, currentState.with(UPPER, uppper).with(MATERIAL, newMeta & 3));
        world.setBlockState(i, j, k, currentState.with(UPPER, uppper));
        world.setBlockMeta(i, j, k, newMeta);
    }

    @Override
    public void updateBoundingBox(BlockView arg, int i, int j, int k){
        if(doubleSlab){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            boolean flag = ((BlockStateView)arg).getBlockState(i,j,k).get(UPPER);
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
    public void setupRenderBoundingBox() { /// Iventory Render Box
        if(doubleSlab){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    ///
//    @Override
//    public boolean isSideVisible(BlockView arg, int i, int j, int k, int l)
//    {
//        if(this.doubleSlab) {
//            super.isSideVisible(arg, i, j, k, l);
//        }
//
//        if(l != 1 && l != 0 && !super.isSideVisible(arg, i, j, k, l)) {
//            return false;
//        } else {
//            int var6 = i + KoziFacing.offsetsXForSide[KoziFacing.faceToSide[l]];
//            int var7 = j + KoziFacing.offsetsYForSide[KoziFacing.faceToSide[l]];
//            int var8 = k + KoziFacing.offsetsZForSide[KoziFacing.faceToSide[l]];
//            boolean var9 = (arg.getBlockMeta(var6, var7, var8) & 4) != 0;
//            return !var9 ? (l == 1 || (l == 0 && super.isSideVisible(arg, i, j, k, l) || arg.getBlockId(i, j, k) != this.id || (arg.getBlockMeta(i, j, k) & 4) != 0)) : (l == 0 || (l == 1 && super.isSideVisible(arg, i, j, k, l) || arg.getBlockId(i, j, k) != this.id || (arg.getBlockMeta(i, j, k) & 4) == 0));
//        }
//    }

    @Override
    public void addIntersectingBoundingBox(World world, int i, int j, int k, Box box, ArrayList list) {
        this.updateBoundingBox(world, i, j, k);
        super.addIntersectingBoundingBox(world, i, j, k, box, list);
    }

//    @Inject(method = "getDroppedItemMeta", at = @At("HEAD"), cancellable = true)
//    private void injected(int i, CallbackInfoReturnable<Integer> cir) {
////        if(i < 4)
////            cir.setReturnValue(i);
////        else{
////            cir.setReturnValue(i - 4);
////        }
//        cir.setReturnValue(i & 3);
//
//    }

//    @Override
//    public int getTexture(int i, int j) {
//    /// REGULAR
////        if (j == 0 || j == 4) {
////            return i <= 1 ? 6 : 5;
////        } else if (j == 1 || j == 5) {
////            if (i == 0) {
////                return 208;
////            } else {
////                return i == 1 ? 176 : 192;
////            }
////        } else if (j == 2 || j == 6) {
////            return 4;
////        } else if (j == 3 || j == 7) {
////            return 16;
////        } else{
////            return 0;
////        }
//        /// DEV DEBUG
//        return 194;
//    }

}
