package net.kozibrodka.extra.mixin.datafix;

import com.llamalad7.mixinextras.sugar.Local;
import net.kozibrodka.extra.utils.ExtraBlockFixer;
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

@Mixin(value = FlattenedWorldManager.class, remap = false)
public class FlattenedWorldManagerMixin {

    /// same thing that VBE does.
    @Inject(method = "loadChunk", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;sectionCoordToIndex(I)I",
            shift = At.Shift.AFTER
    ))
    private static void extra_fixSlabsWithoutProperties(
            World world, NbtCompound chunkTag, CallbackInfoReturnable<Chunk> info,
            @Local(name = "sectionTag") NbtCompound sectionTag
    ) {
        if (!sectionTag.contains("block_states")) return;  /// Jeśli sekcja nie posiada zapisanego kontenera stanów, pomiń
        NbtCompound states = sectionTag.getCompound("block_states");
        NbtList palette = states.getList("palette");
        extra_stairsNeedsConversion = false; /// reset Flag

        /// Chunk Palette
        for (short i = 0; i < palette.size(); i++) {
            NbtCompound tag = (NbtCompound) palette.get(i);
            if (tag.contains("Properties")) continue; /// Jeśli wpis posiada już Properties (czyli świat był już zapisany z Modem), nie rób nic
            String blockName = tag.getString("Name");
            Identifier id = Identifier.of(blockName);
            Block block = BlockRegistry.INSTANCE.get(id);
            if (block == null) continue;

            /// Half-Slabs
            if (blockName.equals("minecraft:slab")) {
                NbtCompound propertiesTag = new NbtCompound();
                tag.put("Properties", propertiesTag);
                BlockState defaultState = block.getDefaultState();
                for (Property<?> property : defaultState.getProperties()) {
                    propertiesTag.putString(property.getName(), defaultState.get(property).toString()); /// Default State Apply
                }
            }

            /// Stairs
            else if (block instanceof net.minecraft.block.StairsBlock) {
                extra_stairsNeedsConversion = true;
                NbtCompound propertiesTag = new NbtCompound();
                tag.put("Properties", propertiesTag);
                BlockState defaultState = block.getDefaultState();
                for (Property<?> property : defaultState.getProperties()) {
                    propertiesTag.putString(property.getName(), defaultState.get(property).toString());
                }
            }
        }
    }


    @Inject(method = "loadChunk", at = @At(
            value = "INVOKE",
            target = "Lnet/modificationstation/stationapi/impl/world/chunk/ChunkSection;getLightArray(Lnet/minecraft/world/LightType;)Lnet/modificationstation/stationapi/impl/world/chunk/NibbleArray;",
            ordinal = 1,
            shift = At.Shift.AFTER
    ))
    private static void vbe_fixLoadedBlocks(
            World level, NbtCompound chunkTag, CallbackInfoReturnable<Chunk> info,
            @Local ChunkSection chunkSection
    ) {
        //todo Fixery - odpalane tylko przy pierwszy załadowaniu chunku na podstawie flag: np. extra_stairsNeedsConversion
//        ExtraBlockFixer.fixChunkSection(chunkSection);
    }

    @Unique
    private static boolean extra_stairsNeedsConversion = false;

}
