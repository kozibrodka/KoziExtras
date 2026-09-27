package net.kozibrodka.extra.mixin.placement;

import net.kozibrodka.extra.mixin_interface.InteractPacketOffSetInterface;
import net.kozibrodka.extra.mixin_interface.InteractionManagerExtraInterface;
import net.kozibrodka.extra.mixin_interface.ServerInteractionManagerExtraInterface;
import net.minecraft.client.InteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Redirect(
            method = "onPlayerInteractBlock(Lnet/minecraft/network/packet/c2s/play/PlayerInteractBlockC2SPacket;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;interactBlock(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)Z"
            )
    )
    private boolean redirectInteractBlock(
            ServerPlayerInteractionManager instance,
            PlayerEntity player,
            World world,
            ItemStack itemStack,
            int x, int y, int z,
            int side, PlayerInteractBlockC2SPacket packet) {

        return  ((ServerInteractionManagerExtraInterface)instance).interactBlockExtra(player, world, itemStack, x, y, z, side,
                ((InteractPacketOffSetInterface)packet).getXOffset(),
                ((InteractPacketOffSetInterface)packet).getYOffset(),
                ((InteractPacketOffSetInterface)packet).getZOffset());

    }
}
