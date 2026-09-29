package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.mixin_interface.BlockSlabInterface;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(SlabBlock.class)
public class SlabBlockMixin extends Block implements BlockSlabInterface {
    protected SlabBlockMixin(int i, Material arg) {
        super(i, arg);
    }

    @Shadow private boolean doubleSlab;

    @Unique
    private static final BooleanProperty UPPER = BooleanProperty.of("upper");

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(UPPER);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructorEnd(CallbackInfo ci) {
        setDefaultState(getDefaultState()
                .with(UPPER, false)
        );
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        /// Override w celu usunięcia vanilla-logic, która ustawia DOUBLE-SLAB blok niżej.
    }


    @Override
    public void onPlacedSlabExtra(World world, int i, int j, int k, boolean upper, int oldMeta){
        /// on ItemBlock use
        BlockState currentState = world.getBlockState(i, j, k);
        world.setBlockState(i, j, k, currentState.with(UPPER, upper));
        world.setBlockMeta(i, j, k, oldMeta);
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

    @Override
    public void addIntersectingBoundingBox(World world, int i, int j, int k, Box box, ArrayList list) {
        this.updateBoundingBox(world, i, j, k);
        super.addIntersectingBoundingBox(world, i, j, k, box, list);
    }

}
