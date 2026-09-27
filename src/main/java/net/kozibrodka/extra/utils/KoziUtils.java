package net.kozibrodka.extra.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class KoziUtils {
    boolean flag1;
    boolean flag2;
    boolean flag3;
    boolean flag4;

//    public HitResult objectMouseOver = minecraft;
    public static float giveCursorHeigh(int x, int y, int z){
        HitResult objectMouseOver = KoziClientUtils.minecraft.crosshairTarget;
        Vec3d vektor = objectMouseOver.pos;
        float varX = (float)vektor.x - (float)x;
        float varY = (float)vektor.y - (float)y;
        float varZ = (float)vektor.z - (float)z;
        return varY;
    }

    public static boolean getCursorHeightRaycast(LivingEntity player, World world, int i, int j, int k) {
        Vec3d eye = player.getPosition(1.0F);
        Vec3d look = player.getLookVector(1.0F);
        Vec3d target = eye.add(look.x * 5.0, look.y * 5.0, look.z * 5.0);

        HitResult mop = world.raycast(eye, target);
        boolean upper = false;
        if (mop != null && mop.blockX == i && mop.blockY == j && mop.blockZ == k) {
            float hitY = (float)(mop.pos.y - j);
            upper = hitY >= 0.5F;
            System.out.println(upper + "  " + hitY);
        }

        return upper;
    }


    public static boolean getCursorHeight2D(LivingEntity player, World world, int i, int j, int k, int side) {
        boolean upper = false;


        // ściana boczna: policz przecięcie promienia TYLKO z tą jedną, znaną płaszczyzną
        float serverY = 0F;
        if(EnvTool.isEnvServ()){
//            serverY = player.standingEyeHeight;
            serverY = 1.62F;
        }


        Vec3d eye  = Vec3d.createCached(player.x, player.y + serverY, player.z);
        Vec3d look = player.getLookVector(1.0F);

//        if(EnvTool.isEnvServ()){
//            eye.add(0, player.standingEyeHeight,0);
//        }
//        System.out.println("TEST " + eye.y + " " + look.y);

        /// Algebra trójkąta
        double t;
        if (side == 4 || side == 5) {          // płaszczyzna pionowa X = const
            double planeX = (side == 4) ? i : i + 1;
            t = (planeX - eye.x) / look.x;
        } else {                                // side == 2 || side == 3, płaszczyzna Z = const
            double planeZ = (side == 2) ? k : k + 1;
            t = (planeZ - eye.z) / look.z;
        }
        double hitYAbs = eye.y + look.y * t;
        float hitY = (float) (hitYAbs - j);

        upper = hitY >= 0.5F;

        return upper;
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
