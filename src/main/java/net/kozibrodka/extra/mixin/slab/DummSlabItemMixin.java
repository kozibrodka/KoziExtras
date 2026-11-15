package net.kozibrodka.extra.mixin.slab;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.extra.events.ConfigListener;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SlabBlockItem;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlabBlockItem.class)
public class DummSlabItemMixin extends BlockItem {
    public DummSlabItemMixin(int i) {
        super(i);
    }

    public boolean canDoublePlace(World world, int i, int j, int k)
    {
        Box box = Box.createCached((double)i + 0D, (double)j + 0.5D, (double)k + 0D, (double)i + 1D, (double)j + 1D, (double)k + 1D);
        if(box != null && !world.canSpawnEntity(box))
        {
            return false;
        }
        return true;
    }

    private boolean useOnBlockDouble(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side){
        if (side == 0) {
            --y;
        }
        if (side == 2) {
            --z;
        }
        if (side == 3) {
            ++z;
        }
        if (side == 4) {
            --x;
        }
        if (side == 5) {
            ++x;
        }
        if (stack.count == 0) {
            return false;
        }
        if(world.getBlockId(x, y, z) == Block.SLAB.id && world.getBlockMeta(x, y, z) == stack.getDamage()){
            if (canDoublePlace(world, x, y, z)) { //world.canPlace(Block.DOUBLE_SLAB.id, x, y, z, false, side
                Block var8 = Block.BLOCKS[Block.DOUBLE_SLAB.id];
                if (world.setBlock(x, y, z, Block.DOUBLE_SLAB.id, this.getPlacementMetadata(stack.getDamage()))) {
                    Block.BLOCKS[Block.DOUBLE_SLAB.id].onPlaced(world, x, y, z, side);
                    Block.BLOCKS[Block.DOUBLE_SLAB.id].onPlaced(world, x, y, z, user);
                    world.playSound((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, var8.soundGroup.getSound(), (var8.soundGroup.getVolume() + 1.0F) / 2.0F, var8.soundGroup.getPitch() * 0.8F);
                    --stack.count;
                }
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        if(ConfigListener.exGlass.vanilla_block_enhancements.slab_placement_vanilla_fix){
            if(useOnBlockDouble(stack, user, world, x, y, z, side)){
                return true;
            }
        }

        if (world.getBlockId(x, y, z) == Block.SNOW.id) {
            side = 0;
        } else {
            if (side == 0) {
                --y;
            }

            if (side == 1) {
                ++y;
            }

            if (side == 2) {
                --z;
            }

            if (side == 3) {
                ++z;
            }

            if (side == 4) {
                --x;
            }

            if (side == 5) {
                ++x;
            }
        }

        if (stack.count == 0) {
            return false;
        } else if (y == world.getTopY()-1 && Block.BLOCKS[this.blockId].material.isSolid()) {
            return false;
        } else if (world.canPlace(this.blockId, x, y, z, false, side)) {
            Block var8 = Block.BLOCKS[this.blockId];
            if (world.setBlock(x, y, z, this.blockId, this.getPlacementMetadata(stack.getDamage()))) {
                Block.BLOCKS[this.blockId].onPlaced(world, x, y, z, side);
                Block.BLOCKS[this.blockId].onPlaced(world, x, y, z, user);
                world.playSound((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, var8.soundGroup.getSound(), (var8.soundGroup.getVolume() + 1.0F) / 2.0F, var8.soundGroup.getPitch() * 0.8F);
                --stack.count;
            }

            return true;
        } else {
            return false;
        }
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
    public void preventCrash(ItemStack stack, CallbackInfoReturnable<String> cir) {
        if (stack.getDamage() >= SlabBlock.names.length) {
//            cir.setReturnValue(super.getTranslationKey() + ".transmutation");
            cir.setReturnValue(super.getTranslationKey() + ".crash");
        }
    }


}
