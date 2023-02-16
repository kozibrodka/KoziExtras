package net.kozibrodka.extra.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.Block;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.StoneSlab;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StoneSlab.class)
public class ItemSlabMixin extends Block {

    public ItemSlabMixin(int i) {
        super(i);
        this.setDurability(0);
        this.setHasSubItems(true);
    }

    @Override
    public boolean useOnTile(ItemInstance itemstack, PlayerBase playerbase, Level level, int var4, int var5, int var6, int var7) {
        if(itemstack.count == 0) {
            return false;
        }
//        else if(!playerbase.canPlayerEdit(var4, var5, var6)) {
//            return false;
//        }
        else {
            int var8 = level.getTileId(var4, var5, var6);
            int var9 = level.getTileMeta(var4, var5, var6);
            int var10 = var9 & 3; // 3
            boolean var11 = (var9 & 4) != 0; // 4
            if((var7 == 1 && !var11 || var7 == 0 && var11) && var8 == BlockBase.STONE_SLAB.id && var10 == itemstack.getDamage()) {
                if(level.canSpawnEntity(BlockBase.DOUBLE_STONE_SLAB.getCollisionShape(level, var4, var5, var6)) && level.placeBlockWithMetaData(var4, var5, var6, BlockBase.DOUBLE_STONE_SLAB.id, var10)) {
                    level.playSound((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), BlockBase.DOUBLE_STONE_SLAB.sounds.getWalkSound(), (BlockBase.DOUBLE_STONE_SLAB.sounds.getVolume() + 1.0F) / 2.0F, BlockBase.DOUBLE_STONE_SLAB.sounds.getPitch() * 0.8F);
                    --itemstack.count;
                }

                return true;
            } else {
                return func_50087_b(itemstack, playerbase, level, var4, var5, var6, var7) || super.useOnTile(itemstack, playerbase, level, var4, var5, var6, var7);
            }
        }
    }

    private static boolean func_50087_b(ItemInstance itemstack, PlayerBase playerbase, Level level, int var3, int var4, int var5, int var6) {
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

        int var7 = level.getTileId(var3, var4, var5);
        int var8 = level.getTileMeta(var3, var4, var5);
        int var9 = var8 & 3; // 3
        if(var7 == BlockBase.STONE_SLAB.id && var9 == itemstack.getDamage()) {
            if(level.canSpawnEntity(BlockBase.DOUBLE_STONE_SLAB.getCollisionShape(level, var3, var4, var5)) && level.placeBlockWithMetaData(var3, var4, var5, BlockBase.DOUBLE_STONE_SLAB.id, var9)) {
                level.playSound((double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), BlockBase.DOUBLE_STONE_SLAB.sounds.getWalkSound(), (BlockBase.DOUBLE_STONE_SLAB.sounds.getVolume() + 1.0F) / 2.0F, BlockBase.DOUBLE_STONE_SLAB.sounds.getPitch() * 0.8F);
                --itemstack.count;
            }

            return true;
        } else {
            return false;
        }
    }
}
