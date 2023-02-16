package net.kozibrodka.extra.blockItem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.template.item.TemplateBlock;

public class ItemWoodenSlabExtra extends TemplateBlock {
    public ItemWoodenSlabExtra(int i) {
        super(i);
        this.setDurability(0);
        this.setHasSubItems(true);
    }

    @Environment(EnvType.CLIENT)
    public int getTexturePosition(int i) {
        return BlockListener.wooden_slab_extra.getTextureForSide(2, i);
    }

    public int getMetaData(int i) {
        return i;
    }

    @Environment(EnvType.CLIENT)
    public String getTranslationKey(ItemInstance arg) {
        return super.getTranslationKey() + "." + net.kozibrodka.extra.blocksCosmetic.BlockWoodenSlabExtra.field_2323[arg.getDamage()];
    }

    public boolean useOnTile(ItemInstance itemstack, PlayerBase playerbase, Level level, int var4, int var5, int var6, int var7) {
        if(itemstack.count == 0) {
            return false;
        }
        else {
            int var8 = level.getTileId(var4, var5, var6);
            int var9 = level.getTileMeta(var4, var5, var6);
            int var10 = var9 & 7; 
            boolean var11 = (var9 & 8) != 0;
            if((var7 == 1 && !var11 || var7 == 0 && var11) && var8 == BlockListener.wooden_slab_extra.id && var10 == itemstack.getDamage()) {
                if(level.canSpawnEntity(BlockListener.double_wooden_slab_extra.getCollisionShape(level, var4, var5, var6)) && level.placeBlockWithMetaData(var4, var5, var6, BlockListener.double_wooden_slab_extra.id, var10)) {
                    level.playSound((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), BlockListener.double_wooden_slab_extra.sounds.getWalkSound(), (BlockListener.double_wooden_slab_extra.sounds.getVolume() + 1.0F) / 2.0F, BlockListener.double_wooden_slab_extra.sounds.getPitch() * 0.8F);
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
        int var9 = var8 & 7;
        if(var7 == BlockListener.wooden_slab_extra.id && var9 == itemstack.getDamage()) {
            if(level.canSpawnEntity(BlockListener.double_wooden_slab_extra.getCollisionShape(level, var3, var4, var5)) && level.placeBlockWithMetaData(var3, var4, var5, BlockListener.double_wooden_slab_extra.id, var9)) {
                level.playSound((double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), BlockListener.double_wooden_slab_extra.sounds.getWalkSound(), (BlockListener.double_wooden_slab_extra.sounds.getVolume() + 1.0F) / 2.0F, BlockListener.double_wooden_slab_extra.sounds.getPitch() * 0.8F);
                --itemstack.count;
            }

            return true;
        } else {
            return false;
        }
    }
}
