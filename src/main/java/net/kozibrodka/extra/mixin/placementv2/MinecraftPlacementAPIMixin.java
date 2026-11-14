package net.kozibrodka.extra.mixin.placementv2;

import net.kozibrodka.extra.mixin_interface.BlockItemExtraPlacementInterface;
import net.kozibrodka.extra.placementAPI.ExtraInteractionManager;
import net.minecraft.client.InteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MultiplayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.ModdedPacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftPlacementAPIMixin {

    @Shadow
    public InteractionManager interactionManager;
    @Shadow public HitResult crosshairTarget = null;

    @Redirect(
            method = "handleMouseClick(I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/InteractionManager;interactBlock(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)Z"
            )
    )
    private boolean redirectInteractBlock(InteractionManager instance, PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side) {

        boolean smpHasRT = true;
        if(world.isRemote) {
            smpHasRT = ((ModdedPacketHandler) ((MultiplayerInteractionManager) instance).networkHandler).getMods().containsKey("extra");
        }

        if(itemStack != null && itemStack.getItem() instanceof BlockItemExtraPlacementInterface && smpHasRT){ //todo nowy packecik + NBT data stapi Mixin w pakiecie zawrzeć
            return  ExtraInteractionManager.interactBlockExtra(instance, player, world, itemStack, x, y, z, side, this.crosshairTarget.pos);
        }else{
            return  instance.interactBlock(player, world, itemStack, x, y, z, side);
        }



            //((ModdedPacketHandler)networkHandler).isModded()   //((MultiplayerInteractionManager)instance).networkHandler


//        System.out.println("WYSYŁAM INTERAKCJE BLOKU");
//        return  ((InteractionManagerExtraInterface)instance).interactBlockExtra(player, world, itemStack, x, y, z, side, this.crosshairTarget.pos);

    }
}
