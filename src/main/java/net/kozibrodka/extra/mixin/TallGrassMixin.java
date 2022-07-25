package net.kozibrodka.extra.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.block.Plant;
import net.minecraft.block.TallGrass;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TallGrass.class)
public class TallGrassMixin extends Plant {
    protected TallGrassMixin(int i, int j) {
        super(i, j);
    }

    @Override
    public void afterBreak(Level arg, PlayerBase arg2, int i, int j, int k, int l) {
        if (!arg.isServerSide && arg2.getHeldItem() != null && arg2.getHeldItem().itemId == ItemBase.shears.id) {
            arg2.increaseStat(Stats.mineBlock[this.id], 1);
            this.drop(arg, i, j, k, new ItemInstance(BlockBase.TALLGRASS.id, 1, l));
        } else {
            super.afterBreak(arg, arg2, i, j, k, l);
        }
    }
}
