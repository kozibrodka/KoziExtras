package net.kozibrodka.extra.mixin.smp_fixes;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperMixin extends MobEntity {

    public CreeperMixin(World world) {
        super(world);
    }

    /// Fix Creeper Animation on Server. Client-side only Mixin.
    @Inject(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/MonsterEntity;tick()V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void stopAfterSuperTick(CallbackInfo ci) {
        if(world.isRemote){ /// Jedynie jako klient na serwerze.
            ci.cancel();
        }
    }


}
