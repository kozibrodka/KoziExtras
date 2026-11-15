package net.kozibrodka.extra.xold_mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(MobSpawnerBlockEntity.class)
public class DevSpawnerMixin extends BlockEntity {

    @Shadow
    String spawnedEntityId = "Pig";

    @Unique
    public int ticking = 0;

    @Inject(
            method = "tick()V",
            at = @At(
                    value = "HEAD"
            )
    )
    private void stopAfterSuperTick(CallbackInfo ci) {
        ticking++;
        if(Objects.equals(this.spawnedEntityId, "Spider") && ticking % 100 == 0){
            Minecraft mc = (Minecraft) FabricLoader.getInstance().getGameInstance();
            int distanceX = (int) Math.abs(mc.player.x - this.x);
            int distanceY = (int) Math.abs(mc.player.y - this.y);
            int distanceZ = (int) Math.abs(mc.player.z - this.z);
            System.out.println(this.spawnedEntityId + " " + this.x + " " + this.y + " " + this.z);
            System.out.println("DYSTANS: " + distanceX + " " + distanceY + " " + distanceZ);
        }
    }
}
