package net.kozibrodka.extra.events;

import net.kozibrodka.extra.registryJson.TemplateModelRegistry;
import net.modificationstation.stationapi.api.util.Identifier;

public class JsonListener {

    public static void registerJsonStatesAndModels(){

        if(ConfigListener.exGlass.vanilla_block_enhancements.modern_stairs) {
            TemplateModelRegistry.registerModernVanillaStairsWithInvisibleVariants(Identifier.of("cobblestone_stairs"),
                    Identifier.of("block/cobblestone"), false);
            TemplateModelRegistry.registerModernVanillaStairsWithInvisibleVariants(Identifier.of("oak_stairs"),
                    Identifier.of("block/oak_planks"), true);

//            TemplateModelRegistry.registerStairs(Identifier.of("cobblestone_stairs"),
//                    Identifier.of("block/oak_planks"),
//                    Identifier.of("block/oak_planks"),
//                    Identifier.of("block/oak_planks"));
        }

//        TemplateModelRegistry.registerStairs(Identifier.of("cobblestone_stairs"), Identifier.of("oak_planks"));
//        TemplateModelRegistry.registerStairs(Identifier.of("cobblestone_stairs"), Identifier.of("block/wood_birch"));

    }
}
