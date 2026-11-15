package net.kozibrodka.extra.mixin.datafix;

import com.llamalad7.mixinextras.sugar.Local;
import net.kozibrodka.extra.data_fixer.ExtraBlockFixer;
import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.state.property.Property;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.impl.world.FlattenedWorldManager;
import net.modificationstation.stationapi.impl.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FlattenedWorldManager.class)
public class FlattenedWorldManagerMixin {

    /// same thing that VBE does.
    @Inject(method = "loadChunk", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;sectionCoordToIndex(I)I",
            shift = At.Shift.AFTER
    ))
    private static void extra_fixBlocksWithoutProperties(
            World world, NbtCompound chunkTag, CallbackInfoReturnable<Chunk> info,
            @Local(ordinal = 1) NbtCompound sectionTag
    ) {
//        /// Przy wchodzeniu na serwa Vanilla to w ogóle nie ODPALA.
//        if(world.isRemote &&  !((ModdedPacketHandler) ((MultiplayerClientPlayerEntity)KoziClientUtils.minecraft.player).networkHandler).isModded() ){
//            System.out.println("KLIENT NA SERWIE VANILLA");
//            return;
//        }

        if (!sectionTag.contains("block_states")) return;  /// Jeśli sekcja nie posiada zapisanego kontenera stanów, pomiń
        NbtCompound states = sectionTag.getCompound("block_states");
        NbtList palette = states.getList("palette");
        extra_stairsNeedsConversion = false; /// reset Flag

        /// Chunk Palette
        for (short i = 0; i < palette.size(); i++) {
            NbtCompound tag = (NbtCompound) palette.get(i);
            if (tag.contains("Properties")) continue; /// Jeśli wpis posiada już Properties (czyli świat był już zapisany z Modem), pomiń
            String blockName = tag.getString("Name");
            Identifier id = Identifier.of(blockName);
            Block block = BlockRegistry.INSTANCE.get(id);
            if (block == null) continue;

            /// Half-Slabs & Double-Slabs
            if (blockName.equals("minecraft:slab") || blockName.equals("minecraft:double_slab")) {
                NbtCompound propertiesTag = new NbtCompound();
                tag.put("Properties", propertiesTag);
                BlockState defaultState = block.getDefaultState();
                for (Property<?> property : defaultState.getProperties()) { /// Default State Apply - dla każdego state.
                    propertiesTag.putString(property.getName(), defaultState.get(property).toString());
                }
            }

            /// Stairs
            else if (block instanceof net.minecraft.block.StairsBlock) { //todo być może lepiej uspecyfikować tak jak w Slabsach? (jeżeli jakiś dziwny modded używający States ale innych)
                System.out.println("mod_ReleaseTweaks: Vanilla Stairs Detected, activating DataFixer");
                extra_stairsNeedsConversion = true;
                NbtCompound propertiesTag = new NbtCompound();
                tag.put("Properties", propertiesTag);
                BlockState defaultState = block.getDefaultState();
                for (Property<?> property : defaultState.getProperties()) {
                    propertiesTag.putString(property.getName(), defaultState.get(property).toString()); /// Default States Apply
                }
            }
        }
    }


    @Inject(method = "loadChunk", at = @At(
            value = "INVOKE",
            target = "Lnet/modificationstation/stationapi/impl/world/chunk/ChunkSection;getLightArray(Lnet/minecraft/world/LightType;)Lnet/modificationstation/stationapi/impl/world/chunk/NibbleArray;",
            shift = At.Shift.AFTER
    ))
    private static void extra_fixLoadedBlocks(
            World world, NbtCompound chunkTag, CallbackInfoReturnable<Chunk> info,
            @Local ChunkSection chunkSection
    ) {
        if(extra_stairsNeedsConversion){
            ExtraBlockFixer.fixChunkSection(chunkSection, extra_stairsNeedsConversion);
        }
    }

    @Unique
    private static boolean extra_stairsNeedsConversion = false;

}
