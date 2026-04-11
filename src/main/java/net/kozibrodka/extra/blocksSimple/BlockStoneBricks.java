package net.kozibrodka.extra.blocksSimple;

import net.kozibrodka.extra.events.TextureListener;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class BlockStoneBricks extends TemplateBlockBase {

    public BlockStoneBricks(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public int getTexture(int side) {
        return TextureListener.stone_brick;
    }

}
