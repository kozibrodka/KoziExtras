package net.kozibrodka.extra.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.extra.mixin.MinecraftAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;

public class KoziUtils {
    boolean flag1;
    boolean flag2;
    boolean flag3;
    boolean flag4;

//    public HitResult objectMouseOver = minecraft;
    public float giveCursorHeigh(int x, int y, int z){
        Minecraft mc = (Minecraft) FabricLoader.getInstance().getGameInstance();
        HitResult objectMouseOver = mc.crosshairTarget;
//                this.minecraft.hitResult;
        Vec3d vektor = objectMouseOver.pos;
        float varX = (float)vektor.x - (float)x;
        float varY = (float)vektor.y - (float)y;
        float varZ = (float)vektor.z - (float)z;
        return varY;
    }

    public boolean areStairsConnected(BlockView blockView, int x, int y, int z, int meta){
        if(meta == 0 || meta == 4)
            flag1 = isBlockStairsID(blockView.getBlockId(x,y,z + 1)) || isBlockStairsID(blockView.getBlockId(x,y,z + 1));
//            flag2 = isBlockStairsID(blockView.getTileId(x + 1,y,z)) && blockView.getTileMeta(x,y,z) ==
//             && (isBlockStairsID(blockView.getTileId(x,y,z))))
        return true;
    }

    public boolean isBlockStairsID(int id)
    {
        return id > 0 && Block.BLOCKS[id] instanceof StairsBlock;
    }

//    public boolean isBlockStairsMETA(int meta)
//    {
//
//    }

}
