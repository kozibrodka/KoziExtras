package net.kozibrodka.extra.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.modificationstation.stationapi.api.util.math.Direction;

public class LookDirectionUtils {

    public static int headYawToLookDirection(LivingEntity placer){
        return MathHelper.floor((double)(placer.yaw * 4.0F / 360.0F) + (double)0.5F) & 3;
    }

    public static int lookDirectionToMetaDirection(int lookDir){
        if(lookDir == 0){
            return 2;
        }
        if(lookDir == 1){
            return 1;
        }
        if(lookDir == 2){
            return 3;
        }
        if(lookDir == 3){
            return 0;
        }
        return lookDir;
    }

    public static Direction lookDirectionToGeographicDirection(int lookDir){
        if(lookDir == 0){
            return Direction.SOUTH;
        }
        if(lookDir == 1){
            return Direction.WEST;
        }
        if(lookDir == 2){
            return Direction.NORTH;
        }
        if(lookDir == 3){
            return Direction.EAST;
        }
        return Direction.NORTH;
    }

    // stare metadata facing na direction
    /// NORTH = 3 | Direction.NORTH
    /// SOUTH = 2 | Direction.SOUTH
    /// WEST = 1  | Direction.WEST
    /// EAST = 0  | Direction.EAST
}
