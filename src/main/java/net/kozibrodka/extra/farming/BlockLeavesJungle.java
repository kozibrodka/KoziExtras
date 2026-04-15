package net.kozibrodka.extra.farming;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BlockLeavesJungle extends TemplateBlock {

    public BlockLeavesJungle(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public boolean isOpaque() {
        return false;
    }
}
