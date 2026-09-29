package net.kozibrodka.extra.utils;

import com.google.gson.annotations.SerializedName;
import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum StairShapeEnum implements StringIdentifiable {

    STRAIGHT(0, "straight"),
    OUTER_LEFT(1, "outer_left"),
    OUTER_RIGHT(2, "outer_right"),
    INNER_LEFT(3, "inner_left"),
    INNER_RIGHT(4, "inner_right");

    StairShapeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }

    private final int id;
    private final String name;
}
