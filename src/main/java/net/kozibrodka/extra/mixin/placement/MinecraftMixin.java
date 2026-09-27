package net.kozibrodka.extra.mixin.placement;

import net.kozibrodka.extra.mixin_interface.InteractionManagerExtraInterface;
import net.minecraft.client.InteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow public InteractionManager interactionManager;
    @Shadow public HitResult crosshairTarget = null;

    @Redirect(
            method = "handleMouseClick(I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/InteractionManager;interactBlock(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)Z"
            )
    )
    private boolean redirectInteractBlock(InteractionManager instance, PlayerEntity player, World world, ItemStack itemStack, int x, int y, int z, int side) {
//        return  instance.interactBlock(player, world, itemStack, x, y, z, side);
        return  ((InteractionManagerExtraInterface)instance).interactBlockExtra(player, world, itemStack, x, y, z, side, this.crosshairTarget.pos);

    }
}
