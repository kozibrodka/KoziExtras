package net.kozibrodka.extra.registryJson;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.extra.utils.EnvTool;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.HashMap;

public class TemplateModelRegistry {

    public static HashMap<Identifier, Identifier> stairs = new HashMap<>(); /// nie wiem do czego to jest


    public static void registerModernVanillaStairsWithInvisibleVariants(Identifier blockIdentifier, Identifier texture, boolean isWooden) {
//        stairs.put(blockIdentifier, texture);
        if (EnvTool.isEnvServ()) {
            return;
        }

        String invisiblePath = "extra:block/stairs/cobblestone_invisible_stairs";
        if(isWooden){
            invisiblePath = "extra:block/stairs/oak_invisible_stairs";
        }

        /// Models
        String modernStairsBlock_straight = JsonStrings.stairs_straight_invisible;
        modernStairsBlock_straight = modernStairsBlock_straight.replace("INVIPATH", invisiblePath);
        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier + "_straight", modernStairsBlock_straight);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_straight", "bottom", texture);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_straight", "top", texture);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_straight", "side", texture);

        String modernStairsBlock_outer = JsonStrings.stairs_outer_invisible;
        modernStairsBlock_outer = modernStairsBlock_outer.replace("INVIPATH", invisiblePath);
        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier + "_outer", modernStairsBlock_outer);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_outer", "bottom", texture);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_outer", "top", texture);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_outer", "side", texture);

        String modernStairsBlock_inner = JsonStrings.stairs_inner_invisible;
        modernStairsBlock_inner = modernStairsBlock_inner.replace("INVIPATH", invisiblePath);
        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier + "_inner", modernStairsBlock_inner);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_inner", "bottom", texture);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_inner", "top", texture);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_inner", "side", texture);

        /// States
        String modernStairsState = JsonStrings.modernStairsStateJson; //
        modernStairsState = modernStairsState.replace("STRAIGHT", getBlockModelPath(blockIdentifier + "_straight"));
        modernStairsState = modernStairsState.replace("OUTER", getBlockModelPath(blockIdentifier + "_outer"));
        modernStairsState = modernStairsState.replace("INNER", getBlockModelPath(blockIdentifier + "_inner"));
        JsonOverrideRegistry.registerBlockstateOverride(blockIdentifier, modernStairsState);

    }

    public static void registerModernVanillaSlabs(Identifier blockIdentifier){
        if (EnvTool.isEnvServ()) {
            return;
        }
        /// States
        JsonOverrideRegistry.registerBlockstateOverride(blockIdentifier, JsonStrings.modernSlabStateJson);
    }




//    public static void registerStairs(Identifier blockIdentifier, Identifier bottom, Identifier top, Identifier side) {
////        stairs.put(blockIdentifier, texture);
//
//        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
//            return;
//        }
//
//        String modernStairsBlock = stairsJson;
//
//        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier + "_straight", stairsJson);
//        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_straight", "bottom", bottom);
//        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_straight", "top", top);
//        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_straight", "side", side);
//
//
////        String modernStairsState = stairsStateJson; //
//        String modernStairsState = JsonStrings.modernStairsStateJson; //
//        modernStairsState = modernStairsState.replace("STRAIGHT", getBlockModelPath(blockIdentifier + "_straight"));
//        modernStairsState = modernStairsState.replace("OUTER", getBlockModelPath(blockIdentifier + "_outer"));
//        modernStairsState = modernStairsState.replace("INNER", getBlockModelPath(blockIdentifier + "_inner"));
//        JsonOverrideRegistry.registerBlockstateOverride(blockIdentifier, modernStairsState);
//
//
////        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier, stairsJson);
////        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier, "all", texture);
//
////        JsonOverrideRegistry.registerItemModelOverride(blockIdentifier, itemJson.replace("PATH", getBlockModelPath(blockIdentifier)));
////
////        JsonOverrideRegistry.registerBlockstateOverride(blockIdentifier, stairsStateJson.replace("PATH", getBlockModelPath(blockIdentifier)));
//    }

    public static String getBlockModelPath(String blockIdentifier) {
        return getBlockModelPath(Identifier.of(blockIdentifier));
    }

    public static String getBlockModelPath(Identifier blockIdentifier) {
        return blockIdentifier.namespace + ":block/" + blockIdentifier.path;
    }

    /// STRAIGHT, OUTER, INNER

    // Stairs
    public static final String stairsJson = ("""
            {
              "parent": "extra:block/stairs/stairs",
              "textures": {
              }
            }"""
    );

    public static final String emptyJsonStates = ("""
            {
              "variants": {

              }
            }"""
    );

}
