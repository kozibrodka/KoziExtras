package net.kozibrodka.extra.blocksCosmetic;

import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.template.block.TemplateFenceBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BlockFenceExtra extends TemplateFenceBlock {

    public BlockFenceExtra(Identifier identifier, int j, Block arg) {
        super(identifier, j);
        this.template = arg;
    }

    private Block template;

    public int getTexture(int i, int j) {
        return this.template.getTexture(i, j);
    }

    public int getTexture(int i) {
        return this.template.getTexture(i);
    }
}
