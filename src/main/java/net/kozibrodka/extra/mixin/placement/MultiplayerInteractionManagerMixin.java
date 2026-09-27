package net.kozibrodka.extra.mixin.placement;

import net.kozibrodka.extra.mixin_interface.InteractPacketOffSetInterface;
import net.minecraft.client.MultiplayerInteractionManager;
import net.minecraft.client.network.ClientNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MultiplayerInteractionManager.class)
public class MultiplayerInteractionManagerMixin extends InteractionManagerMixin {

    @Shadow
    void updateSelectedSlot() {}

    @Shadow
    private ClientNetworkHandler networkHandler;

    @Override
    public boolean interactBlockExtra(PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side, Vec3d eyeVec) {
        this.updateSelectedSlot();
//        this.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(x, y, z, side, player.inventory.getSelectedItem()));

        float xPosition = (float)eyeVec.x - (float)x;
        float yPosition = (float)eyeVec.y - (float)y;
        float zPosition = (float)eyeVec.z - (float)z;

        PlayerInteractBlockC2SPacket packet = new PlayerInteractBlockC2SPacket(x, y, z, side, player.inventory.getSelectedItem());
        ((InteractPacketOffSetInterface)packet).setOffsets(xPosition, yPosition, zPosition);
        this.networkHandler.sendPacket(packet);

        boolean var8 = this.interactBlockExtraClient(player, world, itemStack, x, y, z, side, eyeVec);
        return var8;
    }

}
