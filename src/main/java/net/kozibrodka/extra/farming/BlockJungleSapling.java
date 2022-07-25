package net.kozibrodka.extra.farming;

import net.kozibrodka.extra.generate.HugeJungleTree;
import net.kozibrodka.extra.generate.JungleTree;
import net.minecraft.level.Level;
import net.minecraft.level.structure.*;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplatePlant;

import java.util.Random;

public class BlockJungleSapling extends TemplatePlant {

    public BlockJungleSapling(Identifier identifier, int texture) {
        super(identifier, texture);
        float var3 = 0.4F;
        this.setBoundingBox(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
    }

    public void onScheduledTick(Level arg, int i, int j, int k, Random random) {
        if (!arg.isServerSide) {
            super.onScheduledTick(arg, i, j, k, random);
            if (arg.placeTile(i, j + 1, k) >= 9 && random.nextInt(30) == 0) {
                int var6 = arg.getTileMeta(i, j, k);
                if ((var6 & 8) == 0) {
                    arg.setTileMeta(i, j, k, var6 | 8);
                } else {
                    this.growTree(arg, i, j, k, random);
                }
            }

        }
    }

    public void growTree(Level arg, int i, int j, int k, Random random) {
//        arg.setTileInChunk(i, j, k, 0);
        Object var7 = null;
        boolean var10 = false;
        int var8 = 0;
        int var9 = 0;
        for (var8 = 0; var8 >= -1; --var8)
        {
            for (var9 = 0; var9 >= -1; --var9)
            {
//                System.out.println(var8 + " " + var9);
                if (this.isSameSapling(arg, i + var8, j, k + var9) && this.isSameSapling(arg, i + var8 + 1, j, k + var9) && this.isSameSapling(arg, i + var8, j, k + var9 + 1) && this.isSameSapling(arg, i + var8 + 1, j, k + var9 + 1))
                {
                    System.out.println("JESTEM");
                    var7 = new HugeJungleTree(true, 10 + random.nextInt(20), 3, 3);
                    var10 = true;
                    break;
                }
            }
            if (var7 != null)
            {
                break;
            }
        }

        if (var7 == null)
        {
            var9 = 0;
            var8 = 0;
            var7 = new JungleTree(true, 4 + random.nextInt(7), 3, 3, true);
        }

        if (var10)
        {
            arg.setTile(i + var8, j, k + var9, 0);
            arg.setTile(i + var8 + 1, j, k + var9, 0);
            arg.setTile(i + var8, j, k + var9 + 1, 0);
            arg.setTile(i + var8 + 1, j, k + var9 + 1, 0);
        }
        else
        {
            arg.placeBlockWithMetaData(i, j, k, 0, 4);
        }

        if (!((Structure)var7).generate(arg, random, i + var8, j, k + var9))
        {
            if (var10)
            {
                arg.setTile(i + var8, j, k + var9, this.id);
                arg.setTile(i + var8 + 1, j, k + var9, this.id);
                arg.setTile(i + var8, j, k + var9 + 1, this.id);
                arg.setTile(i + var8 + 1, j, k + var9 + 1, this.id);
            }
            else
            {
                arg.setTile(i, j, k, this.id);
            }
        }
//        if (!((Structure)var7).generate(arg, random, i, j, k)) {
//            arg.setTileWithMetadata(i, j, k, this.id, var6);
//        }
    }

    public boolean isSameSapling(Level par1World, int par2, int par3, int par4)
    {
//        return par1World.getTileId(par2, par3, par4) == this.id && (par1World.getTileMeta(par2, par3, par4) & 3) == par5;
//        System.out.println(par2 + " " +  par3 + " " + par4);
//        System.out.println(par1World.getTileId(par2, par3, par4));
        return par1World.getTileId(par2, par3, par4) == this.id;
    }

    protected int droppedMeta(int i) {
        return 0;
    }
}
