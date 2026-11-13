package net.kozibrodka.extra.mixin.slab;

import net.kozibrodka.extra.events.ConfigListener;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlabBlock.class)
public class DummySlabMixin extends Block {
    public DummySlabMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.BOTTOM);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructorEnd(CallbackInfo ci) {
        setDefaultState(getDefaultState()
                .with(Properties.BOTTOM, true)
        );
    }

    @Inject(method = "onPlaced", at = @At("HEAD"), cancellable = true)
    private void onPlacedInject(World world, int x, int y, int z, CallbackInfo ci) {
        if(ConfigListener.exGlass.vanilla_block_enhancements.slab_placement_vanilla_fix){
            ci.cancel();
        }
    }

}
