package net.kozibrodka.extra.blocksCosmetic;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.ArrayList;

public class IronFenceCross extends TemplateBlockBase {
    public IronFenceCross(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public void addIntersectingBoundingBox(World par1World, int par2, int par3, int par4, Box par5AxisAlignedBB, ArrayList par6List)
    {
        this.setBoundingBox(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
        super.addIntersectingBoundingBox(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
        this.setBoundingBox(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
        super.addIntersectingBoundingBox(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
    }

    public void updateBoundingBox(BlockView blockviev, int x, int y, int z)
    {
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isOpaque()
    {
        return false;
    }
}
