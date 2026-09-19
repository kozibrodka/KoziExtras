package net.kozibrodka.extra.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
public class ItemMetaSlabMixin extends BlockItem {

    public ItemMetaSlabMixin(int i) {
        super(i);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public boolean useOnBlock(ItemStack itemstack, PlayerEntity playerbase, World level, int x, int y, int z, int site) {
        if(itemstack.count == 0) {
            return false;
        } else {
            int clickedID = level.getBlockId(x, y, z);
            int clickedMeta = level.getBlockMeta(x, y, z);
//            int materialMeta = clickedMeta & 3; /// OG
            int materialMeta = clickedMeta;
            boolean var11 = false;
//            boolean var11 = (clickedMeta & 4) != 0; /// old-meta logic, nie równa się Dolna plytka

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
                return attemptDoubleSlabPlaceWithOffset(itemstack, playerbase, level, x, y, z, site) || super.useOnBlock(itemstack, playerbase, level, x, y, z, site);
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
        int clickedMeta = level.getBlockMeta(x, y, z);

//        int materialMeta = clickedMeta & 3;
        int materialMeta = clickedMeta;

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

    @Environment(EnvType.CLIENT)
    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey() + "." + SlabBlock.names[getDamageForNewMeta(stack.getDamage())];
    }


    public int getDamageForNewMeta(int oldMeta){
        return oldMeta % 4;
    }


    @Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
    public void preventCrash(ItemStack stack, CallbackInfoReturnable<String> cir) { //TODO uniTwerks possible conflict
        if (stack.getDamage() >= SlabBlock.names.length) {
//            cir.setReturnValue(null);
            cir.setReturnValue(super.getTranslationKey() + ".crash");
        }
    }
}
