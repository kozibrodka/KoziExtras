package net.kozibrodka.extra.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TallPlantBlock.class)
public class TallGrassMixin extends PlantBlock {
    protected TallGrassMixin(int i, int j) {
        super(i, j);
    }

    @Override
    public void afterBreak(World arg, PlayerEntity arg2, int i, int j, int k, int l) {
        if (!arg.isRemote && arg2.getHand() != null && arg2.getHand().itemId == Item.SHEARS.id) {
            arg2.increaseStat(Stats.MINE_BLOCK[this.id], 1);
            this.dropStack(arg, i, j, k, new ItemStack(Block.GRASS.id, 1, l));
        } else {
            super.afterBreak(arg, arg2, i, j, k, l);
        }
    }
}
