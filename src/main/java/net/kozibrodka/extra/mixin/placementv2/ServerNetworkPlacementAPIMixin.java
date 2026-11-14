package net.kozibrodka.extra.mixin.placementv2;

import net.kozibrodka.extra.mixin_interface.BlockItemExtraPlacementInterface;
import net.kozibrodka.extra.mixin_interface.IServerNetworkExtra;
import net.kozibrodka.extra.mixin_interface.InteractPacketOffSetInterface;
import net.kozibrodka.extra.placementAPI.ExtraInteractionManager;
import net.kozibrodka.extra.placementAPI.PlayerInteractBlockExtraPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.modificationstation.stationapi.impl.packet.FlattenedBlockChangeS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerNetworkPlacementAPIMixin implements IServerNetworkExtra {

    @Shadow
    private MinecraftServer server;
    @Shadow
    private ServerPlayerEntity player;
    @Shadow
    private boolean teleported;
    @Shadow
    public void sendPacket(Packet packet) {}


    @Override
    @Unique
    public void onPlayerInteractBlockExtra(PlayerInteractBlockExtraPacket packet) {
        ServerWorld var2 = this.server.getWorld(this.player.dimensionId);
        ItemStack var3 = this.player.inventory.getSelectedItem();
        boolean var4 = var2.bypassSpawnProtection = var2.dimension.id != 0 || this.server.playerManager.isOperator(this.player.name);
        if (packet.side == 255) {
            if (var3 == null) {
                return;
            }

            this.player.interactionManager.interactItem(this.player, var2, var3);
        } else {
            int clientX = packet.x;
            int clientY = packet.y;
            int clientZ = packet.z;
            int clientSide = packet.side;
            float mouseX = packet.xOffset;
            float mouseY = packet.yOffset;
            float mouseZ = packet.zOffset;
            int lookDirection = packet.lookDir;

            Vec3i var9 = var2.getSpawnPos();
            int var10 = (int) MathHelper.abs((float)(clientX - var9.x));
            int var11 = (int)MathHelper.abs((float)(clientZ - var9.z));
            if (var10 > var11) {
                var11 = var10;
            }

            if (this.teleported && this.player.getSquaredDistance((double)clientX + (double)0.5F, (double)clientY + (double)0.5F, (double)clientZ + (double)0.5F) < (double)64.0F && (var11 > 16 || var4)) {
//                this.player.interactionManager.interactBlock(this.player, var2, var3, clientX, clientY, clientZ, clientSide);
                ExtraInteractionManager.interactBlockExtraMP(this.player, var2, var3, clientX, clientY, clientZ, clientSide, mouseX, mouseY, mouseZ, lookDirection);

            }

            this.player.networkHandler.sendPacket(new FlattenedBlockChangeS2CPacket(clientX, clientY, clientZ, var2)); /// STAPI packet instead of Vanilla's BlockUpdateS2CPacket
            if (clientSide == 0) {
                --clientY;
            }

            if (clientSide == 1) {
                ++clientY;
            }

            if (clientSide == 2) {
                --clientZ;
            }

            if (clientSide == 3) {
                ++clientZ;
            }

            if (clientSide == 4) {
                --clientX;
            }

            if (clientSide == 5) {
                ++clientX;
            }

            this.player.networkHandler.sendPacket(new FlattenedBlockChangeS2CPacket(clientX, clientY, clientZ, var2)); /// STAPI packet instead of Vanilla's BlockUpdateS2CPacket
        }

        var3 = this.player.inventory.getSelectedItem();
        if (var3 != null && var3.count == 0) {
            this.player.inventory.main[this.player.inventory.selectedSlot] = null;
        }

        this.player.skipPacketSlotUpdates = true;
        this.player.inventory.main[this.player.inventory.selectedSlot] = ItemStack.clone(this.player.inventory.main[this.player.inventory.selectedSlot]);
        Slot var13 = this.player.currentScreenHandler.getSlot(this.player.inventory, this.player.inventory.selectedSlot);
        this.player.currentScreenHandler.sendContentUpdates();
        this.player.skipPacketSlotUpdates = false;
        if (!ItemStack.areEqual(this.player.inventory.getSelectedItem(), packet.stack)) {
            this.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(this.player.currentScreenHandler.syncId, var13.id, this.player.inventory.getSelectedItem()));
        }

        var2.bypassSpawnProtection = false;
    }
}
