package net.kozibrodka.extra.registryJson;

public class JsonStrings {
    /// Stairs
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
}
