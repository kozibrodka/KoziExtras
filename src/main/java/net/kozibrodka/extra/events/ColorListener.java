package net.kozibrodka.extra.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.client.render.block.FoliageColour;
import net.minecraft.client.render.block.GrassColour;
import net.minecraft.level.BlockView;
import net.minecraft.util.maths.TilePos;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.client.colour.block.BlockColorProvider;
import net.modificationstation.stationapi.api.client.colour.world.BiomeColors;
import net.modificationstation.stationapi.api.client.event.colour.block.BlockColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.colour.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.model.LoadUnbakedModelEvent;
import net.modificationstation.stationapi.api.client.level.ColorResolver;
import net.modificationstation.stationapi.api.level.BlockStateView;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.Null;
import net.modificationstation.stationapi.mixin.render.client.WaterColourAccessor;
import org.jetbrains.annotations.Nullable;

public class ColorListener {

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    public static void colorForMelon(BlockColorsRegisterEvent event) {
        event.blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null)
                        return 0;
                    else {
                        int meta = state.get(IntProperty.of("wzrost", 0, 11));
                        return (meta) >= 7 ? 0x38B217 : 0xC9F891;
                    }
                },
                BlockListener.watermelonsten
        );

        event.blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null)
                        return 0;
                    else {
                        int meta = state.get(IntProperty.of("wzrost", 0, 11));
                        return (meta) >= 7 ? 0xFFBD00 : 0xFFE486;
                    }
                },
                BlockListener.pumpkinsten
        );

        event.blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null)
                        return 0;
                    else {
                        return 2129968;
                    }
                },
                BlockListener.waterlily
        );

        event.blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null)
                        return 0;
                    else {
                        return BiomeColors.getGrassColor(world, pos);
                    }
                },
                BlockListener.jungleleaves, BlockListener.vine
        );

        event.blockColors.registerColorProvider(
                (state, world, pos, tintIndex) -> world == null || pos == null ? -1 : BiomeColors.getWaterColor(world, pos),
                BlockBase.FLOWING_WATER, BlockBase.STILL_WATER
        );

    }

    @EventListener
    public static void colorForLily(ItemColorsRegisterEvent event) {
        event.blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null)
                        return 0;
                    else {
                        return 2129968;
                    }
                },
                BlockListener.waterlily
        );
    }

//    @EventListener
//    public static void modelowanie (LoadUnbakedModelEvent event)
//    {
//        event.model.
//    }


}
