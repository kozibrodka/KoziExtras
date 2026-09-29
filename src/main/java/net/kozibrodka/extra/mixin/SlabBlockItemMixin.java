package net.kozibrodka.extra.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.extra.mixin_interface.BlockItemExtraPlacementInterface;
import net.kozibrodka.extra.mixin_interface.BlockSlabInterface;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SlabBlockItem;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlabBlockItem.class)
public class SlabBlockItemMixin extends BlockItem implements BlockItemExtraPlacementInterface {

    public SlabBlockItemMixin(int i) {
        super(i);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public boolean useOnBlockExtra(ItemStack itemstack, PlayerEntity playerbase, World level, int x, int y, int z, int site, float offSetX, float offSetY, float offSetZ, int lookDirection) {
        if(itemstack.count == 0) {
            return false;
        } else {
            int clickedID = level.getBlockId(x, y, z);
            int materialMeta = level.getBlockMeta(x, y, z);
            boolean var11 = false;

            boolean isClickedSlab = clickedID == Block.SLAB.id;
            if(isClickedSlab){
                var11 = level.getBlockState(x,y,z).get(BooleanProperty.of("upper"));
            }

            if((site == 1 && !var11 || site == 0 && var11) && isClickedSlab && materialMeta == itemstack.getDamage()) {
                if(level.canSpawnEntity(Block.DOUBLE_SLAB.getCollisionShape(level, x, y, z)) && level.setBlock(x, y, z, Block.DOUBLE_SLAB.id, materialMeta)) {
                    level.playSound((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, Block.DOUBLE_SLAB.soundGroup.getSound(), (Block.DOUBLE_SLAB.soundGroup.getVolume() + 1.0F) / 2.0F, Block.DOUBLE_SLAB.soundGroup.getPitch() * 0.8F);
                    --itemstack.count;
                }

                return true;
            } else {
                return attemptDoubleSlabPlaceWithOffset(itemstack, playerbase, level, x, y, z, site) || useOnBlockSuper(itemstack, playerbase, level, x, y, z, site, offSetX, offSetY, offSetZ);
            }
        }
    }

    private static boolean attemptDoubleSlabPlaceWithOffset(ItemStack itemstack, PlayerEntity playerbase, World level, int x, int y, int z, int side) {
        if(side == 0) {
            --y;
        }
        if(side == 1) {
            ++y;
        }
        if(side == 2) {
            --z;
        }
        if(side == 3) {
            ++z;
        }
        if(side == 4) {
            --x;
        }
        if(side == 5) {
            ++x;
        }

        int clickedID = level.getBlockId(x, y, z);
        int materialMeta = level.getBlockMeta(x, y, z);

        if(clickedID == Block.SLAB.id && materialMeta == itemstack.getDamage()) {
            if(level.canSpawnEntity(Block.DOUBLE_SLAB.getCollisionShape(level, x, y, z)) && level.setBlock(x, y, z, Block.DOUBLE_SLAB.id, materialMeta)) {
                level.playSound((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, Block.DOUBLE_SLAB.soundGroup.getSound(), (Block.DOUBLE_SLAB.soundGroup.getVolume() + 1.0F) / 2.0F, Block.DOUBLE_SLAB.soundGroup.getPitch() * 0.8F);
                --itemstack.count;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean useOnBlockSuper(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side, float offSetX, float offSetY, float offSetZ) {
        boolean isUpper = side >= 2 && offSetY >= 0.5F; /// upper Slab przy bocznych kliknięciach przy offSetY >=0.5F

        if (world.getBlockId(x, y, z) == Block.SNOW.id) {
            side = 0;
        } else {
            if (side == 0) {
                --y;
                isUpper = true; /// kliknięcie od spodu
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
        } else if (y == world.getHeight()-1 && Block.BLOCKS[this.blockId].material.isSolid()) {
            return false;
        } else if (world.canPlace(this.blockId, x, y, z, false, side)) {
            Block var8 = Block.BLOCKS[this.blockId];
            if (world.setBlock(x, y, z, this.blockId)) {
                ((BlockSlabInterface)Block.BLOCKS[this.blockId]).onPlacedSlabExtra(world, x, y, z, isUpper, this.getPlacementMetadata(stack.getDamage())); /// nowa metoda onPlace
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
    public void preventCrash(ItemStack stack, CallbackInfoReturnable<String> cir) { //TODO uniTwerks possible conflict
        if (stack.getDamage() >= SlabBlock.names.length) {
//            cir.setReturnValue(null);
            cir.setReturnValue(super.getTranslationKey() + ".crash");
        }
    }

}
