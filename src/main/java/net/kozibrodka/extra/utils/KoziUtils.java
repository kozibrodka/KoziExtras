package net.kozibrodka.extra.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.extra.mixin.MinecraftAccessor;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Stairs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Minecart;
import net.minecraft.level.BlockView;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Vec3f;

public class KoziUtils {
    boolean flag1;
    boolean flag2;
    boolean flag3;
    boolean flag4;

//    public HitResult objectMouseOver = minecraft;
    public float giveCursorHeigh(int x, int y, int z){
        Minecraft mc = (Minecraft) FabricLoader.getInstance().getGameInstance();
        HitResult objectMouseOver = mc.hitResult;
//                this.minecraft.hitResult;
        Vec3f vektor = objectMouseOver.field_1988;
        float varX = (float)vektor.x - (float)x;
        float varY = (float)vektor.y - (float)y;
        float varZ = (float)vektor.z - (float)z;
        return varY;
    }

    public boolean areStairsConnected(BlockView blockView, int x, int y, int z, int meta){
        if(meta == 0 || meta == 4)
            flag1 = isBlockStairsID(blockView.getTileId(x,y,z + 1)) || isBlockStairsID(blockView.getTileId(x,y,z + 1));
//            flag2 = isBlockStairsID(blockView.getTileId(x + 1,y,z)) && blockView.getTileMeta(x,y,z) ==
//             && (isBlockStairsID(blockView.getTileId(x,y,z))))
        return true;
    }

    public boolean isBlockStairsID(int id)
    {
        return id > 0 && BlockBase.BY_ID[id] instanceof Stairs;
    }

//    public boolean isBlockStairsMETA(int meta)
//    {
//
//    }

}
