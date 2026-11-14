package net.kozibrodka.extra.placementAPI;

import net.kozibrodka.extra.mixin_interface.BlockItemExtraPlacementInterface;
import net.kozibrodka.extra.utils.LookDirectionUtils;
import net.minecraft.block.Block;
import net.minecraft.client.InteractionManager;
import net.minecraft.client.MultiplayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ExtraInteractionManager {

    public static boolean interactBlockExtra(InteractionManager interactionManager, PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side, Vec3d eyeVec){

        float xPosition = (float)eyeVec.x - (float)x;
        float yPosition = (float)eyeVec.y - (float)y;
        float zPosition = (float)eyeVec.z - (float)z;
        int lookingDirection = LookDirectionUtils.headYawToLookDirection(player);

        if(world.isRemote){ /// client na SMP
            ((MultiplayerInteractionManager)interactionManager).updateSelectedSlot();
            PlayerInteractBlockExtraPacket packet = new PlayerInteractBlockExtraPacket(x, y, z, side, player.inventory.getSelectedItem(), xPosition, yPosition, zPosition, lookingDirection);
            ((MultiplayerInteractionManager)interactionManager).networkHandler.sendPacket(packet);
        }

        int var8 = world.getBlockId(x, y, z);
        if (var8 > 0 && Block.BLOCKS[var8].onUse(world, x, y, z, player)) {
            return true;
        } else {
            boolean flagUB = ((BlockItemExtraPlacementInterface)itemStack.getItem()).useOnBlockExtra(itemStack, player, world, x, y, z, side, xPosition, yPosition, zPosition, lookingDirection);
            if (flagUB) {
                player.increaseStat(Stats.USED[itemStack.itemId], 1);
            }
            return flagUB;
        }
    }

    public static boolean interactBlockExtraMP(PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side, float offSetX, float offSetY, float offSetZ, int lookDirection) {
        int var8 = world.getBlockId(x, y, z);
        if (var8 > 0 && Block.BLOCKS[var8].onUse(world, x, y, z, player)) {
            return true;
        } else {
            boolean flagUB = ((BlockItemExtraPlacementInterface)itemStack.getItem()).useOnBlockExtra(itemStack, player, world, x, y, z, side, offSetX, offSetY, offSetZ, lookDirection);
            if (flagUB) {
                player.increaseStat(Stats.USED[itemStack.itemId], 1);
            }
            return flagUB;
        }
    }

}
