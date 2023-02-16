package net.kozibrodka.extra.blocksSimple;

import net.kozibrodka.extra.events.TextureListener;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class BlockSprucePlanks extends TemplateBlockBase {

    public BlockSprucePlanks(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public int getTextureForSide(int side) {
        return TextureListener.planks_spruce;
    }

}
