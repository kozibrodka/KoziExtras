package net.kozibrodka.extra.placementAPI;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.extra.mixin_interface.IServerNetworkExtra;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import net.modificationstation.stationapi.impl.item.StationNBTSetter;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerInteractBlockExtraPacket extends Packet implements ManagedPacket<PlayerInteractBlockExtraPacket> {

    public static final PacketType<PlayerInteractBlockExtraPacket> TYPE = PacketType.builder(true, true, PlayerInteractBlockExtraPacket::new).build();


    public int x;
    public int y;
    public int z;
    public int side;
    public ItemStack stack;

    /** The offset from xPosition where the actual click took place */
    public float xOffset;

    /** The offset from yPosition where the actual click took place */
    public float yOffset;

    /** The offset from zPosition where the actual click took place */
    public float zOffset;

    /** The geographical looking direction of placing entity */
    public int lookDir;

    public PlayerInteractBlockExtraPacket() {
    }

    @Environment(EnvType.CLIENT)
    public PlayerInteractBlockExtraPacket(int x, int y, int z, int side, ItemStack stack, float xOffset, float yOffset, float zOffset, int lookDirection) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.side = side;
        this.stack = stack;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.lookDir = lookDirection;
    }

    @Override
    public void read(DataInputStream stream) {
        try {

        this.x = stream.readInt();
        this.y = stream.read();
        this.z = stream.readInt();
        this.side = stream.read();
        short var2 = stream.readShort();
        if (var2 >= 0) {
            byte var3 = stream.readByte();
            short var4 = stream.readShort();
            this.stack = new ItemStack(var2, var3, var4);
        } else {
            this.stack = null;
        }
        this.xOffset = stream.readFloat();
        this.yOffset = stream.readFloat();
        this.zOffset = stream.readFloat();
        this.lookDir = stream.readByte();
        /// stapi-addon
        if (this.stack != null) {
            if (!stream.readBoolean()) {
                StationNBTSetter.cast(this.stack).setStationNbt((NbtCompound) NbtElement.readTag(stream));
            }
        }
        ///
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {

        stream.writeInt(this.x);
        stream.write(this.y);
        stream.writeInt(this.z);
        stream.write(this.side);
        if (this.stack == null) {
            stream.writeShort(-1);
        } else {
            stream.writeShort(this.stack.itemId);
            stream.writeByte(this.stack.count);
            stream.writeShort(this.stack.getDamage());
        }
        stream.writeFloat(this.xOffset);
        stream.writeFloat(this.yOffset);
        stream.writeFloat(this.zOffset);
        stream.writeByte(this.lookDir);
        /// stapi-addon
        if (this.stack != null) {
            boolean empty = this.stack.getStationNbt().values().isEmpty();
            stream.writeBoolean(empty);
            if (!empty) {
                NbtElement.writeTag(this.stack.getStationNbt(), stream);
            }
        }
        ///

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        switch (FabricLoader.INSTANCE.getEnvironmentType()) {
            case CLIENT -> handleClient(networkHandler);
            case SERVER -> handleServer(networkHandler);
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleClient(NetworkHandler networkHandler) {

    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        ((IServerNetworkExtra)networkHandler).onPlayerInteractBlockExtra(this);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<PlayerInteractBlockExtraPacket> getType() {
        return TYPE;
    }
}
