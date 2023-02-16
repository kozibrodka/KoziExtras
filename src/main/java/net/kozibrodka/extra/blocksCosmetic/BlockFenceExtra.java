package net.kozibrodka.extra.blocksCosmetic;

import net.minecraft.block.BlockBase;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateFence;

public class BlockFenceExtra extends TemplateFence {

    public BlockFenceExtra(Identifier identifier, int j,  BlockBase arg) {
        super(identifier, j);
        this.template = arg;
    }

    private BlockBase template;

    public int getTextureForSide(int i, int j) {
        return this.template.getTextureForSide(i, j);
    }

    public int getTextureForSide(int i) {
        return this.template.getTextureForSide(i);
    }
}
