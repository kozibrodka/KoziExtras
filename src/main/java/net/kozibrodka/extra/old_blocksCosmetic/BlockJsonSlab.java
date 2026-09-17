package net.kozibrodka.extra.old_blocksCosmetic;

import net.kozibrodka.extra.utils.KoziFacing;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BlockJsonSlab extends TemplateBlock {
    public BlockJsonSlab(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public void updateBoundingBox(BlockView arg, int i, int j, int k){
        this.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isSideVisible(BlockView arg, int i, int j, int k, int l)
    {

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

    public void onPlaced(World var1, int i, int j, int k, int l){
                int var6 = var1.getBlockMeta(i, j, k);
                var1.setBlockMeta(i, j, k, var6 | 8);
    }

    public boolean isOpaque() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }



}
