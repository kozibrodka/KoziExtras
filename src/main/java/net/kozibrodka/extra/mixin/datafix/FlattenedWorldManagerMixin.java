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
        /// Jeśli sekcja nie posiada zapisanego kontenera stanów, pomiń
        if (!sectionTag.contains("block_states")) return;

        NbtCompound states = sectionTag.getCompound("block_states");
        NbtList palette = states.getList("palette");

        /// Chunk Palette
        for (short i = 0; i < palette.size(); i++) { //TODO dodać flagi. + Jedynie uzupełnienie propsów dla poszczególnych bloków.
            NbtCompound tag = (NbtCompound) palette.get(i);

            /// Jeśli wpis posiada już Properties (czyli świat był już zapisany z Twoim modem), nie rób nic
            if (tag.contains("Properties")) continue;

            Identifier id = Identifier.of(tag.getString("Name"));
            Block block = BlockRegistry.INSTANCE.get(id);
            if (block == null) continue;

            /// Interesują nas tylko bloki, do których Twój mod dodał nowe właściwości (np. Slab)
            if (block.getStateManager().getProperties().isEmpty()) continue;

            /// Pobieramy domyślny stan (w którym Twój onConstructorEnd ustawił już upper = false)
            BlockState defaultState = block.getDefaultState();
            NbtCompound propertiesTag = new NbtCompound();

            /// Wstrzykujemy brakujące "Properties" bezpośrednio do NBT palety
            tag.put("Properties", propertiesTag);
            for (Property<?> property : defaultState.getProperties()) {
                propertiesTag.putString(property.getName(), defaultState.get(property).toString());
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
        ExtraBlockFixer.fixChunkSection(chunkSection);
    }

}
