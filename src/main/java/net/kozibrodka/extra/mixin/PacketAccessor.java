package net.kozibrodka.extra.mixin;

import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Mixin(Packet.class)
public interface PacketAccessor{

        @Accessor("creationTime")
        @Mutable
        void setCreationTime(long time);

}
