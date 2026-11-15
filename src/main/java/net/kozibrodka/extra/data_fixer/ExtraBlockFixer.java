package net.kozibrodka.extra.data_fixer;

import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.impl.world.chunk.ChunkSection;

public class ExtraBlockFixer {

    public static void fixChunkSection(ChunkSection section, boolean flag_stairs) {
        /// Pętla przechodząca przez wszystkie 4096 bloków w sekcji (16x16x16)
        for (int i = 0; i < 4096; i++) {
            int dx = i & 15;
            int dy = (i >> 4) & 15;
            int dz = i >> 8;

            BlockState state = section.getBlockState(dx, dy, dz);
            if (state == null) continue;

            /// STAIRS
            if (flag_stairs && (state.getBlock() == Block.WOODEN_STAIRS || state.getBlock() == Block.COBBLESTONE_STAIRS)) {
                if (state == state.getBlock().getDefaultState()) { /// Upewnienie się, blok jest ładowany pierwszy raz - w sumie nie wiem czy konieczne
                    int meta = section.getMeta(dx, dy, dz); //

                    Direction dir = switch (meta & 3) { /// w teorii można by nawet dać support dla jakiegoś moda z modloadera który robi upside-down stairs za pomocą metadata
                        case 0 -> Direction.EAST;
                        case 1 -> Direction.WEST;
                        case 2 -> Direction.SOUTH;
                        default -> Direction.NORTH; /// case 3
                    };

//                    @SuppressWarnings("unchecked") //Logika Gdybym używał swojego customego blockState (np. StairShape)
//                    Property<Direction> facingProperty = (Property<Direction>) state.getBlock().getStateManager().getProperty("facing");
//                    if (facingProperty != null) {
//                        BlockState fixedState = state.with(facingProperty, dir);
//                        section.setBlockState(dx, dy, dz, fixedState);
//                    }

                    BlockState fixedState = state.with(Properties.HORIZONTAL_FACING, dir);
                    section.setBlockState(dx, dy, dz, fixedState);
                    /// oryginalna meta zostaje
                }
            }
        }
    }



}
