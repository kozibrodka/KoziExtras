package net.kozibrodka.extra.registryJson;

public class JsonStrings {
    /// Models
    // Stairs
    public static final String stairs_straight = ("""
            {
              "parent": "extra:block/stairs/stairs",
              "textures": {
              }
            }"""
    );

    public static final String stairs_straight_invisible = ("""
            {
                "parent": "extra:block/stairs/stairs",
                "textures": {
                },
                "overrides": [
                    {
                        "predicate": {"meta": 4},
                        "model": "INVIPATH"
                    }
                ]
            }"""
    );

    public static final String stairs_outer_invisible = ("""
            {
                "parent": "extra:block/stairs/outer_stairs",
                "textures": {
                },
                "overrides": [
                    {
                        "predicate": {"meta": 4},
                        "model": "INVIPATH"
                    }
                ]
            }"""
    );

    public static final String stairs_inner_invisible = ("""
            {
                "parent": "extra:block/stairs/inner_stairs",
                "textures": {
                },
                "overrides": [
                    {
                        "predicate": {"meta": 4},
                        "model": "INVIPATH"
                    }
                ]
            }"""
    );


    /// States

    //Stairs
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

    public static final String modernSlabStateJson = ("""
            {
              "variants": {
                  "bottom=true": { "model": "extra:block/slab/slab_vanilla_bottom" },
                  "bottom=false": { "model": "extra:block/slab/slab_vanilla_upper" }
              }
            }"""
    );
}
