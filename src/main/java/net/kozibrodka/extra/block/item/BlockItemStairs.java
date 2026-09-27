package net.kozibrodka.extra.block.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockItemStairs extends BlockItem {
    public BlockItemStairs(int i) {
        super(i);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        System.out.println("O KURWA BlockITEM");
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
        } else if (y == 127 && Block.BLOCKS[this.blockId].material.isSolid()) {
            return false;
        } else if (world.canPlace(this.blockId, x, y, z, false, side)) {
            Block var8 = Block.BLOCKS[this.blockId];
            if (world.setBlock(x, y, z, this.blockId, this.getPlacementMetadata(stack.getDamage()))) {
                Block.BLOCKS[this.blockId].onPlaced(world, x, y, z, side);
                Block.BLOCKS[this.blockId].onPlaced(world, x, y, z, user);
                world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), var8.soundGroup.getSound(), (var8.soundGroup.getVolume() + 1.0F) / 2.0F, var8.soundGroup.getPitch() * 0.8F);
                --stack.count;
            }

            return true;
        } else {
            return false;
        }
    }
}
