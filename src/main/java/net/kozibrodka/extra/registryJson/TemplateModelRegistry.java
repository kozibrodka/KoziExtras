package net.kozibrodka.extra.registryJson;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.extra.utils.EnvTool;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.HashMap;

public class TemplateModelRegistry {

    public static HashMap<Identifier, Identifier> stairs = new HashMap<>();


    public static void registerModernVanillaStairsWithInvisibleVariants(Identifier blockIdentifier, Identifier texture, boolean isWooden) {
//        stairs.put(blockIdentifier, texture);
        if (EnvTool.isEnvServ()) {
            return;
        }

        String invisiblePath = "extra:block/stairs/cobblestone_invisible_stairs";
        if(isWooden){
            invisiblePath = "extra:block/stairs/oak_invisible_stairs";
        }

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


//        String modernStairsState = stairsStateJson; //
        String modernStairsState = modernStairsStateJson; //
        modernStairsState = modernStairsState.replace("STRAIGHT", getBlockModelPath(blockIdentifier + "_straight"));
        modernStairsState = modernStairsState.replace("OUTER", getBlockModelPath(blockIdentifier + "_outer"));
        modernStairsState = modernStairsState.replace("INNER", getBlockModelPath(blockIdentifier + "_inner"));
        JsonOverrideRegistry.registerBlockstateOverride(blockIdentifier, modernStairsState);

    }


    // Template Blocks
    public static void registerStairs(Identifier blockIdentifier, Identifier bottom, Identifier top, Identifier side) {
//        stairs.put(blockIdentifier, texture);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            return;
        }

        String modernStairsBlock = stairsJson;

        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier + "_straight", stairsJson);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_straight", "bottom", bottom);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_straight", "top", top);
        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier + "_straight", "side", side);



//        String modernStairsState = stairsStateJson; //
        String modernStairsState = modernStairsStateJson; //
        modernStairsState = modernStairsState.replace("STRAIGHT", getBlockModelPath(blockIdentifier + "_straight"));
        modernStairsState = modernStairsState.replace("OUTER", getBlockModelPath(blockIdentifier + "_outer"));
        modernStairsState = modernStairsState.replace("INNER", getBlockModelPath(blockIdentifier + "_inner"));
        JsonOverrideRegistry.registerBlockstateOverride(blockIdentifier, modernStairsState);




//        JsonOverrideRegistry.registerBlockModelOverride(blockIdentifier, stairsJson);
//        JsonOverrideRegistry.registerBlockModelTextureOverride(blockIdentifier, "all", texture);

//        JsonOverrideRegistry.registerItemModelOverride(blockIdentifier, itemJson.replace("PATH", getBlockModelPath(blockIdentifier)));
//
//        JsonOverrideRegistry.registerBlockstateOverride(blockIdentifier, stairsStateJson.replace("PATH", getBlockModelPath(blockIdentifier)));
    }

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

    public static final String stairsStateJson = ("""
            {
              "variants": {
                "facing=east": {
                  "model": "extra:block/stairs/oak_stairs"
                },
                "facing=south": {
                  "model": "extra:block/stairs/oak_stairs",
                  "y": 90
                },
                "facing=west": {
                  "model": "extra:block/stairs/oak_stairs",
                  "y": 180
                },
                "facing=north": {
                  "model": "extra:block/stairs/oak_stairs",
                  "y": 270
                }
              }
            }"""
    );

    public static final String modernStairsStateJson = ("""
            {
                "variants": {
                    "facing=east,bottom=true,shape=straight":  { "model": "STRAIGHT" },
                    "facing=west,bottom=true,shape=straight":  { "model": "STRAIGHT", "y": 180, "uvlock": true },
                    "facing=south,bottom=true,shape=straight": { "model": "STRAIGHT", "y": 90, "uvlock": true },
                    "facing=north,bottom=true,shape=straight": { "model": "STRAIGHT", "y": 270, "uvlock": true },
                    "facing=east,bottom=true,shape=outer_right":  { "model": "OUTER" },
                    "facing=west,bottom=true,shape=outer_right":  { "model": "OUTER", "y": 180, "uvlock": true },
                    "facing=south,bottom=true,shape=outer_right": { "model": "OUTER", "y": 90, "uvlock": true },
                    "facing=north,bottom=true,shape=outer_right": { "model": "OUTER", "y": 270, "uvlock": true },
                    "facing=east,bottom=true,shape=outer_left":  { "model": "OUTER", "y": 270, "uvlock": true },
                    "facing=west,bottom=true,shape=outer_left":  { "model": "OUTER", "y": 90, "uvlock": true },
                    "facing=south,bottom=true,shape=outer_left": { "model": "OUTER" },
                    "facing=north,bottom=true,shape=outer_left": { "model": "OUTER", "y": 180, "uvlock": true },
                    "facing=east,bottom=true,shape=inner_right":  { "model": "INNER" },
                    "facing=west,bottom=true,shape=inner_right":  { "model": "INNER", "y": 180, "uvlock": true },
                    "facing=south,bottom=true,shape=inner_right": { "model": "INNER", "y": 90, "uvlock": true },
                    "facing=north,bottom=true,shape=inner_right": { "model": "INNER", "y": 270, "uvlock": true },
                    "facing=east,bottom=true,shape=inner_left":  { "model": "INNER", "y": 270, "uvlock": true },
                    "facing=west,bottom=true,shape=inner_left":  { "model": "INNER", "y": 90, "uvlock": true },
                    "facing=south,bottom=true,shape=inner_left": { "model": "INNER" },
                    "facing=north,bottom=true,shape=inner_left": { "model": "INNER", "y": 180, "uvlock": true },
                    "facing=east,bottom=false,shape=straight":  { "model": "STRAIGHT", "x": 180, "uvlock": true },
                    "facing=west,bottom=false,shape=straight":  { "model": "STRAIGHT", "x": 180, "y": 180, "uvlock": true },
                    "facing=south,bottom=false,shape=straight": { "model": "STRAIGHT", "x": 180, "y": 90, "uvlock": true },
                    "facing=north,bottom=false,shape=straight": { "model": "STRAIGHT", "x": 180, "y": 270, "uvlock": true },
                    "facing=east,bottom=false,shape=outer_right":  { "model": "OUTER", "x": 180, "y": 90, "uvlock": true },
                    "facing=west,bottom=false,shape=outer_right":  { "model": "OUTER", "x": 180, "y": 270, "uvlock": true },
                    "facing=south,bottom=false,shape=outer_right": { "model": "OUTER", "x": 180, "y": 180, "uvlock": true },
                    "facing=north,bottom=false,shape=outer_right": { "model": "OUTER", "x": 180, "uvlock": true },
                    "facing=east,bottom=false,shape=outer_left":  { "model": "OUTER", "x": 180, "uvlock": true },
                    "facing=west,bottom=false,shape=outer_left":  { "model": "OUTER", "x": 180, "y": 180, "uvlock": true },
                    "facing=south,bottom=false,shape=outer_left": { "model": "OUTER", "x": 180, "y": 90, "uvlock": true },
                    "facing=north,bottom=false,shape=outer_left": { "model": "OUTER", "x": 180, "y": 270, "uvlock": true },
                    "facing=east,bottom=false,shape=inner_right":  { "model": "INNER", "x": 180, "y": 90, "uvlock": true },
                    "facing=west,bottom=false,shape=inner_right":  { "model": "INNER", "x": 180, "y": 270, "uvlock": true },
                    "facing=south,bottom=false,shape=inner_right": { "model": "INNER", "x": 180, "y": 180, "uvlock": true },
                    "facing=north,bottom=false,shape=inner_right": { "model": "INNER", "x": 180, "uvlock": true },
                    "facing=east,bottom=false,shape=inner_left":  { "model": "INNER", "x": 180, "uvlock": true },
                    "facing=west,bottom=false,shape=inner_left":  { "model": "INNER", "x": 180, "y": 180, "uvlock": true },
                    "facing=south,bottom=false,shape=inner_left": { "model": "INNER", "x": 180, "y": 90, "uvlock": true },
                    "facing=north,bottom=false,shape=inner_left": { "model": "INNER", "x": 180, "y": 270, "uvlock": true }
                }
            }""");
}
