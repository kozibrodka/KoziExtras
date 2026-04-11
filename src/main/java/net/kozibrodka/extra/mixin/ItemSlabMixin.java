package net.kozibrodka.extra.mixin;

import net.minecraft.block.Block;
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
    public boolean useOnBlock(ItemStack itemstack, PlayerEntity playerbase, World level, int var4, int var5, int var6, int var7) {
        if(itemstack.count == 0) {
            return false;
        }
//        else if(!playerbase.canPlayerEdit(var4, var5, var6)) {
//            return false;
//        }
        else {
            int var8 = level.getBlockId(var4, var5, var6);
            int var9 = level.getBlockMeta(var4, var5, var6);
            int var10 = var9 & 3; // 3
            boolean var11 = (var9 & 4) != 0; // 4
            if((var7 == 1 && !var11 || var7 == 0 && var11) && var8 == Block.SLAB.id && var10 == itemstack.getDamage()) {
                if(level.canSpawnEntity(Block.DOUBLE_SLAB.getCollisionShape(level, var4, var5, var6)) && level.setBlock(var4, var5, var6, Block.DOUBLE_SLAB.id, var10)) {
                    level.playSound((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), Block.DOUBLE_SLAB.soundGroup.getSound(), (Block.DOUBLE_SLAB.soundGroup.getVolume() + 1.0F) / 2.0F, Block.DOUBLE_SLAB.soundGroup.getPitch() * 0.8F);
                    --itemstack.count;
                }

                return true;
            } else {
                return func_50087_b(itemstack, playerbase, level, var4, var5, var6, var7) || super.useOnBlock(itemstack, playerbase, level, var4, var5, var6, var7);
            }
        }
    }

    private static boolean func_50087_b(ItemStack itemstack, PlayerEntity playerbase, World level, int var3, int var4, int var5, int var6) {
        if(var6 == 0) {
            --var4;
        }

        if(var6 == 1) {
            ++var4;
        }

        if(var6 == 2) {
            --var5;
        }

        if(var6 == 3) {
            ++var5;
        }

        if(var6 == 4) {
            --var3;
        }

        if(var6 == 5) {
            ++var3;
        }

        int var7 = level.getBlockId(var3, var4, var5);
        int var8 = level.getBlockMeta(var3, var4, var5);
        int var9 = var8 & 3; // 3
        if(var7 == Block.SLAB.id && var9 == itemstack.getDamage()) {
            if(level.canSpawnEntity(Block.DOUBLE_SLAB.getCollisionShape(level, var3, var4, var5)) && level.setBlock(var3, var4, var5, Block.DOUBLE_SLAB.id, var9)) {
                level.playSound((double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), Block.DOUBLE_SLAB.soundGroup.getSound(), (Block.DOUBLE_SLAB.soundGroup.getVolume() + 1.0F) / 2.0F, Block.DOUBLE_SLAB.soundGroup.getPitch() * 0.8F);
                --itemstack.count;
            }

            return true;
        } else {
            return false;
        }
    }
}
