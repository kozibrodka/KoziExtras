package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingMixin extends Entity {


    public LivingMixin(World arg) {
        super(arg);
    }

    public void initDataTracker() {
    }

    public void readNbt(NbtCompound arg) {
    }

    public void writeNbt(NbtCompound arg) {
    }


//    @Inject(method = "method_932", at = @At("RETURN"), cancellable = true)
//    private void injected(CallbackInfoReturnable cir) {
//        int var1 = MathHelper.floor(this.x);
//        int var2 = MathHelper.floor(this.boundingBox.minY);
//        int var3 = MathHelper.floor(this.z);
//        cir.setReturnValue(this.level.getTileId(var1, var2, var3) == BlockBase.LADDER.id || this.level.getTileId(var1, var2, var3) == BlockListener.vine.id);
//    }


}
