package net.kozibrodka.extra.farming;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.template.block.TemplateLeaves;

public class BlockLeavesJungle extends TemplateBlockBase {

    public BlockLeavesJungle(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public boolean isFullOpaque() {
        return false;
    }
}
