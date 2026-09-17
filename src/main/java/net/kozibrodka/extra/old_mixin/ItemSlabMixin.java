package net.kozibrodka.extra.old_mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SlabBlockItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SlabBlockItem.class)
public class ItemSlabMixin extends BlockItem {

    public ItemSlabMixin(int i) {
        super(i);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public boolean useOnBlock(ItemStack itemstack, PlayerEntity playerbase, World level, int x, int y, int z, int site) {
        if(itemstack.count == 0) {
            return false;
        }
//        else if(!playerbase.canPlayerEdit(x, y, z)) {
//            return false;
//        }
        else {
            int var8 = level.getBlockId(x, y, z);
            int var9 = level.getBlockMeta(x, y, z);
            int var10 = var9 & 3; // 3
            boolean var11 = (var9 & 4) != 0; // 4
            if((site == 1 && !var11 || site == 0 && var11) && var8 == Block.SLAB.id && var10 == itemstack.getDamage()) {
                if(level.canSpawnEntity(Block.DOUBLE_SLAB.getCollisionShape(level, x, y, z)) && level.setBlock(x, y, z, Block.DOUBLE_SLAB.id, var10)) {
                    level.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), Block.DOUBLE_SLAB.soundGroup.getSound(), (Block.DOUBLE_SLAB.soundGroup.getVolume() + 1.0F) / 2.0F, Block.DOUBLE_SLAB.soundGroup.getPitch() * 0.8F);
                    --itemstack.count;
                }

                return true;
            } else {
                return func_50087_b(itemstack, playerbase, level, x, y, z, site) || super.useOnBlock(itemstack, playerbase, level, x, y, z, site);
            }
        }
    }

    private static boolean func_50087_b(ItemStack itemstack, PlayerEntity playerbase, World level, int x, int y, int z, int side) {
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

        int var7 = level.getBlockId(x, y, z);
        int var8 = level.getBlockMeta(x, y, z);
        int var9 = var8 & 3; // 3
        if(var7 == Block.SLAB.id && var9 == itemstack.getDamage()) {
            if(level.canSpawnEntity(Block.DOUBLE_SLAB.getCollisionShape(level, x, y, z)) && level.setBlock(x, y, z, Block.DOUBLE_SLAB.id, var9)) {
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
}
