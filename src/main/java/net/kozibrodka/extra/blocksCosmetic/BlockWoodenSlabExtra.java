package net.kozibrodka.extra.blocksCosmetic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.extra.blockItem.ItemWoodenSlabExtra;
import net.kozibrodka.extra.events.BlockListener;
import net.kozibrodka.extra.events.TextureListener;
import net.kozibrodka.extra.utils.KoziFacing;
import net.kozibrodka.extra.utils.KoziUtils;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.HasCustomBlockItemFactory;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.ArrayList;
import java.util.Random;

@HasCustomBlockItemFactory(ItemWoodenSlabExtra.class)
public class BlockWoodenSlabExtra extends TemplateBlockBase {

    public static final String[] field_2323 = new String[]{"spruce", "birch", "jungle"};
    private boolean field_2324;

    public BlockWoodenSlabExtra(Identifier identifier, Material material, boolean bl) {
        super(identifier, material);
        this.field_2324 = bl;
        if (!bl) {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public void onBlockPlaced(Level var1, int i, int j, int k, int l){
        if(l == 0) {
            int var6 = var1.getTileMeta(i, j, k);
            var1.setTileMeta(i, j, k, var6 | 8);
        }else if(l != 1){
            KoziUtils kozi = new KoziUtils();
            float a = kozi.giveCursorHeigh(i,j,k);
            if((double)a >= 0.5D){
                int var6 = var1.getTileMeta(i, j, k);
                var1.setTileMeta(i, j, k, var6 | 8);
            }
        }
    }

    public void updateBoundingBox(BlockView arg, int i, int j, int k){
        if(field_2324){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            boolean flag = (arg.getTileMeta(i, j, k) & 8) != 0;

            if (flag)
            {
                this.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
        }
    }

    public void method_1605() {
        if(field_2324){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public boolean isSideRendered(BlockView arg, int i, int j, int k, int l)
    {
        if(this.field_2324) {
            super.isSideRendered(arg, i, j, k, l);
        }

        if(l != 1 && l != 0 && !super.isSideRendered(arg, i, j, k, l)) {
            return false;
        } else {
            int var6 = i + KoziFacing.offsetsXForSide[KoziFacing.faceToSide[l]];
            int var7 = j + KoziFacing.offsetsYForSide[KoziFacing.faceToSide[l]];
            int var8 = k + KoziFacing.offsetsZForSide[KoziFacing.faceToSide[l]];
            boolean var9 = (arg.getTileMeta(var6, var7, var8) & 8) != 0;
            return !var9 ? (l == 1 || (l == 0 && super.isSideRendered(arg, i, j, k, l) || arg.getTileId(i, j, k) != this.id || (arg.getTileMeta(i, j, k) & 8) != 0)) : (l == 0 || (l == 1 && super.isSideRendered(arg, i, j, k, l) || arg.getTileId(i, j, k) != this.id || (arg.getTileMeta(i, j, k) & 8) == 0));
        }
    }

    public void doesBoxCollide(Level world, int i, int j, int k, Box box, ArrayList list) {
        this.updateBoundingBox(world, i, j, k);
        super.doesBoxCollide(world, i, j, k, box, list);
    }

    public int getTextureForSide(int i, int j) {
        if (j == 0 || j == 8) {
            return TextureListener.planks_spruce;
        } else if (j == 1 || j == 9) {
            return TextureListener.planks_birch;
        } else if (j == 2 || j == 10) {
            return TextureListener.planks_jungle;
        } else{
            return 0;
        }
    }

    public int getTextureForSide(int i) {
        return this.getTextureForSide(i, 0);
    }

    public boolean isFullOpaque() {
        return this.field_2324;
    }

    public int getDropId(int i, Random random) {
        return BlockListener.wooden_slab_extra.id;
    }

    public int getDropCount(Random random) {
        return this.field_2324 ? 2 : 1;
    }

    protected int droppedMeta(int i) {
//        if(i < 8)
//            return i;
//        else{
//            return(i - 8);
//        }
        return i & 7;
    }

    public boolean isFullCube() {
        return this.field_2324;
    }

}
