package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Living.class)
public class LivingMixin extends EntityBase {


    public LivingMixin(Level arg) {
        super(arg);
    }

    public void initDataTracker() {
    }

    public void readCustomDataFromTag(CompoundTag arg) {
    }

    public void writeCustomDataToTag(CompoundTag arg) {
    }


//    @Inject(method = "method_932", at = @At("RETURN"), cancellable = true)
//    private void injected(CallbackInfoReturnable cir) {
//        int var1 = MathHelper.floor(this.x);
//        int var2 = MathHelper.floor(this.boundingBox.minY);
//        int var3 = MathHelper.floor(this.z);
//        cir.setReturnValue(this.level.getTileId(var1, var2, var3) == BlockBase.LADDER.id || this.level.getTileId(var1, var2, var3) == BlockListener.vine.id);
//    }


}
