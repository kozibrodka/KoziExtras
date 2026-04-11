package net.kozibrodka.extra.blocksCosmetic;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class TintedGlass extends TemplateBlockBase {

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
