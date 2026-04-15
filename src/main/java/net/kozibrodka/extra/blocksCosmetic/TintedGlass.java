package net.kozibrodka.extra.blocksCosmetic;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class TintedGlass extends TemplateBlock {

    public TintedGlass(Identifier identifier, Material material) {
        super(identifier, material);

    }

    public boolean isOpaque()
    {
        return false;
    }

    public int getRenderLayer(){
        return 1;
    }

}
