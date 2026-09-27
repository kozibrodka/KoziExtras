package net.kozibrodka.extra.utils;

import net.kozibrodka.extra.old_mixin.BlockSlabMixin;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.impl.world.chunk.ChunkSection;

public class ExtraBlockFixer {

    public static void fixChunkSection(ChunkSection section) {
        /// Pętla przechodząca przez wszystkie 4096 bloków w sekcji (16x16x16)
        for (short i = 0; i < 4096; i++) {
            byte dx = (byte) (i & 15);
            byte dy = (byte) ((i >> 4) & 15);
            byte dz = (byte) (i >> 8);

            BlockState state = section.getBlockState(dx, dy, dz);
            if (state == null) continue;

            /// PRZYKŁAD 1: Chcesz ustawić coś na podstawie metadanych Slauba (np. rodzaj materiału)
            if (state.getBlock() == Block.SLAB) {
                int meta = section.getMeta(dx, dy, dz); // Pobierasz starą vanillową metadaną (0-3)

                /// Warunek skipFix: upewniamy się, że modyfikujemy tylko "świeżo załadowany" domyślny blok
                if (state == state.getBlock().getDefaultState()) {
                    /// Twoja teoretyczna logika, np:
                    /// Jeśli stary świat zapisał metadaną 2 (Sandstone), ustawiasz dedykowany stan w swoim modzie
                    if (meta == 1) { //todo przykłąd tylko meta 1
                        BlockState fixedState = state.with(BooleanProperty.of("upper"), true); // Przykład użycia ///TODO do wyjebanie to jest "TEST"
                        section.setBlockState(dx, dy, dz, fixedState);
                    }
                }
            }

            /// PRZYKŁAD 2: Inny blok vanilli, który w b1.7.3 miał bogate metadane (np. Skrzynia/Chest i jej kierunek)
            else if (state.getBlock() == Block.CHEST) {
                int meta = section.getMeta(dx, dy, dz); // Meta określała kierunek (2, 3, 4, 5)

                if (state == state.getBlock().getDefaultState()) {
                    /// Mapujesz stare int meta na nowoczesne Property z kierunkiem świata (Direction)
                    /// i aplikujesz do bloku za pomocą section.setBlockState(...);
                }
            }
        }
    }
}
