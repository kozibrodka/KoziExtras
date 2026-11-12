package net.kozibrodka.extra.mixin.stairs;

import net.kozibrodka.extra.utils.LookDirectionUtils;
import net.kozibrodka.extra.utils.StairShapeEnum;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StairsBlock.class)
public class DummyStairsMixin extends Block{

    public DummyStairsMixin(int id, Material material) {
        super(id, material);
    }

    @Unique
    private static final EnumProperty<StairShapeEnum> SHAPE = EnumProperty.of("shape", StairShapeEnum.class);

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.BOTTOM);
        builder.add(Properties.HORIZONTAL_FACING);
        builder.add(SHAPE);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructorEnd(CallbackInfo ci) {
        setDefaultState(getDefaultState()
                .with(Properties.BOTTOM, true)
                .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                .with(SHAPE, StairShapeEnum.STRAIGHT)
        );
    }

    @Inject(method = "onPlaced(Lnet/minecraft/world/World;IIILnet/minecraft/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    private void injectedStairs(World world, int x, int y, int z, LivingEntity placer, CallbackInfo cir) {
        int headAngleIndex = LookDirectionUtils.headYawToLookDirection(placer);
        int metaFacing = LookDirectionUtils.lookDirectionToMetaDirection(headAngleIndex);
//        System.out.println("METAFACING + " + metaFacing);
//        world.setBlockMeta(x, y, z, metaFacing);

        BlockState currentState = world.getBlockState(x,y,z);
        world.setBlockState(x,y,z, currentState.with(Properties.HORIZONTAL_FACING, LookDirectionUtils.lookDirectionToGeographicDirection(metaFacing)), metaFacing);
        cir.cancel();
    }

}
