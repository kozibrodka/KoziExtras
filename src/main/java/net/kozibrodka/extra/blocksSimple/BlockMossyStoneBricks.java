package net.kozibrodka.extra.blocksSimple;

import net.kozibrodka.extra.events.TextureListener;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class BlockMossyStoneBricks extends TemplateBlockBase {

    public BlockMossyStoneBricks(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public int getTexture(int side) {
        return TextureListener.stone_brick_mossy;
    }

}
