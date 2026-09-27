package net.kozibrodka.extra.mixin.placement;

import net.kozibrodka.extra.mixin_interface.InteractPacketOffSetInterface;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


@Mixin(PlayerInteractBlockC2SPacket.class)
public class PlayerInteractBlockC2SPacketMixin implements InteractPacketOffSetInterface {


    /** The offset from xPosition where the actual click took place */
    @Unique
    private float xOffset;

    /** The offset from yPosition where the actual click took place */
    @Unique
    private float yOffset;

    /** The offset from zPosition where the actual click took place */
    @Unique
    private float zOffset;


    @Inject(method = "write", at = @At("RETURN"))
    private void write(DataOutputStream stream, CallbackInfo ci) throws IOException {
        stream.writeFloat(this.xOffset);
        stream.writeFloat(this.yOffset);
        stream.writeFloat(this.zOffset);
    }

    @Inject(method = "read", at = @At("RETURN"))
    private void onRead(DataInputStream stream, CallbackInfo ci) throws IOException {
        this.xOffset = stream.readFloat();
        this.yOffset = stream.readFloat();
        this.zOffset = stream.readFloat();
    }

    @Override
    public void setOffsets(float xOffset, float yOffset, float zOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }

    @Override
    public float getXOffset() { return this.xOffset; }
    @Override
    public float getYOffset() { return this.yOffset; }
    @Override
    public float getZOffset() { return this.zOffset; }
}
