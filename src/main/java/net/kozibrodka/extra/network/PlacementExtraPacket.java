package net.kozibrodka.extra.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.extra.mixin.PacketAccessor;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlacementExtraPacket extends Packet implements ManagedPacket<PlacementExtraPacket> {

    public static final PacketType<PlacementExtraPacket> TYPE = PacketType.builder(true, true, PlacementExtraPacket::new).build();

    private int PosX;
    private int PosZ;
    private int PosY;
    private int blockID;

    public PlacementExtraPacket() {
    }

    public PlacementExtraPacket(int x, int y, int z, int id) {
        this.PosX = x;
        this.PosZ = y;
        this.PosY = z;
        this.blockID = id;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.PosX = stream.readInt();
            this.PosZ = stream.readInt();
            this.PosY = stream.readInt();
            this.blockID = stream.readInt();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.PosX);
            stream.writeInt(this.PosZ);
            stream.writeInt(this.PosY);
            stream.writeInt(this.blockID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apply(NetworkHandler arg) {
        switch (FabricLoader.INSTANCE.getEnvironmentType()) {
            case CLIENT -> handleClient(arg);
            case SERVER -> handleServer(arg);
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleClient(NetworkHandler networkHandler) {

    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        ServerPlayerEntity player = (ServerPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        int newID = player.world.getBlockId(this.PosX,this.PosY,this.PosZ);
        System.out.println(newID);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<PlacementExtraPacket> getType() {
        return TYPE;
    }
}
