package net.kozibrodka.extra.blocksCosmetic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.extra.blockItem.ItemWoodenSlabExtra;
import net.kozibrodka.extra.events.BlockListener;
import net.kozibrodka.extra.events.TextureListener;
import net.kozibrodka.extra.utils.KoziFacing;
import net.kozibrodka.extra.utils.KoziUtils;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.HasCustomBlockItemFactory;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.Random;

@HasCustomBlockItemFactory(ItemWoodenSlabExtra.class)
public class BlockWoodenSlabExtra extends TemplateBlock {

    public static final String[] field_2323 = new String[]{"spruce", "birch", "jungle"};
    private boolean field_2324;

    public BlockWoodenSlabExtra(Identifier identifier, Material material, boolean bl) {
        super(identifier, material);
        this.field_2324 = bl;
        if (!bl) {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public void onPlaced(World var1, int i, int j, int k, int l){
        if(l == 0) {
            int var6 = var1.getBlockMeta(i, j, k);
            var1.setBlockMeta(i, j, k, var6 | 8);
        }else if(l != 1){
            KoziUtils kozi = new KoziUtils();
            float a = kozi.giveCursorHeigh(i,j,k);
            if((double)a >= 0.5D){
                int var6 = var1.getBlockMeta(i, j, k);
                var1.setBlockMeta(i, j, k, var6 | 8);
            }
        }
    }

    public void updateBoundingBox(BlockView arg, int i, int j, int k){
        if(field_2324){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            boolean flag = (arg.getBlockMeta(i, j, k) & 8) != 0;

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

    public void setupRenderBoundingBox() {
        if(field_2324){
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }else{
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public boolean isSideVisible(BlockView arg, int i, int j, int k, int l)
    {
        if(this.field_2324) {
            super.isSideVisible(arg, i, j, k, l);
        }

        if(l != 1 && l != 0 && !super.isSideVisible(arg, i, j, k, l)) {
            return false;
        } else {
            int var6 = i + KoziFacing.offsetsXForSide[KoziFacing.faceToSide[l]];
            int var7 = j + KoziFacing.offsetsYForSide[KoziFacing.faceToSide[l]];
            int var8 = k + KoziFacing.offsetsZForSide[KoziFacing.faceToSide[l]];
            boolean var9 = (arg.getBlockMeta(var6, var7, var8) & 8) != 0;
            return !var9 ? (l == 1 || (l == 0 && super.isSideVisible(arg, i, j, k, l) || arg.getBlockId(i, j, k) != this.id || (arg.getBlockMeta(i, j, k) & 8) != 0)) : (l == 0 || (l == 1 && super.isSideVisible(arg, i, j, k, l) || arg.getBlockId(i, j, k) != this.id || (arg.getBlockMeta(i, j, k) & 8) == 0));
        }
    }

    public void addIntersectingBoundingBox(World world, int i, int j, int k, Box box, ArrayList list) {
        this.updateBoundingBox(world, i, j, k);
        super.addIntersectingBoundingBox(world, i, j, k, box, list);
    }

    public int getTexture(int i, int j) {
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

    public int getTexture(int i) {
        return this.getTexture(i, 0);
    }

    public boolean isOpaque() {
        return this.field_2324;
    }

    public int getDroppedItemId(int i, Random random) {
        return BlockListener.wooden_slab_extra.id;
    }

    public int getDroppedItemCount(Random random) {
        return this.field_2324 ? 2 : 1;
    }

    protected int getDroppedItemMeta(int i) {
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
