package net.kozibrodka.extra.blocksSimple;

import net.kozibrodka.extra.events.TextureListener;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class BlockQuartz extends TemplateBlockBase {

    public BlockQuartz(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public int getTextureForSide(int side) {
        if(side == 0){
            return TextureListener.quartz_bottom;
        }else
        return side == 1 ? TextureListener.quartz_top : TextureListener.quartz_side;
    }

}
