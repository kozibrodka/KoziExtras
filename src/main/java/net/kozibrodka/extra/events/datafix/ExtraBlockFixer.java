package net.kozibrodka.extra.events.datafix;

import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.DirectionProperty;
import net.modificationstation.stationapi.api.state.property.Property;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.impl.world.chunk.ChunkSection;

public class ExtraBlockFixer {

    public static void fixChunkSection(ChunkSection section, boolean flag_stairs) {
        /// Pętla przechodząca przez wszystkie 4096 bloków w sekcji (16x16x16)
        for (short i = 0; i < 4096; i++) {
            byte dx = (byte) (i & 15);
            byte dy = (byte) ((i >> 4) & 15);
            byte dz = (byte) (i >> 8);

            BlockState state = section.getBlockState(dx, dy, dz);
            if (state == null) continue;

            /// PRZYKŁAD 1: Chcesz ustawić coś na podstawie metadanych Slauba (np. rodzaj materiału)
            if (false && state.getBlock() == Block.SLAB) {
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

            /// STAIRS
            else if (flag_stairs && (state.getBlock() == Block.WOODEN_STAIRS || state.getBlock() == Block.COBBLESTONE_STAIRS)) {
                if (state == state.getBlock().getDefaultState()) { /// Upewnienie się, blok jest ładowany pierwszy raz - w sumie nie wiem czy konieczne
                    int meta = section.getMeta(dx, dy, dz); //

                    Direction dir = switch (meta & 3) { /// w teorii można by nawet dać support dla jakiegoś moda z modloadera który robi upside-down stairs za pomocą metadata
                        case 0 -> Direction.EAST;
                        case 1 -> Direction.WEST;
                        case 2 -> Direction.SOUTH;
                        default -> Direction.NORTH; /// case 3
                    };

                    @SuppressWarnings("unchecked") //todo tymczasowe
                    Property<Direction> facingProperty = (Property<Direction>) state.getBlock().getStateManager().getProperty("facing");

                    if (facingProperty != null) {
                        // Teraz 'with' nie wygeneruje błędu, ponieważ używasz oryginalnej instancji właściwości
                        BlockState fixedState = state.with(facingProperty, dir);
                        section.setBlockState(dx, dy, dz, fixedState);
                    }

//                    BlockState fixedState = state.with(DirectionProperty.of("facing"), dir);
////                    BlockState fixedState = state.with(DirectionProperty.of("facing"), 1);
////                    BlockState fixedState = state.with(BooleanProperty.of("upper"), true);
//                    section.setBlockState(dx, dy, dz, fixedState);
                    /// oryginalna meta powinna zostać
                }
            }
        }
    }



}
