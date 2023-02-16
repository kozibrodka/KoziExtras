package net.kozibrodka.extra.blocksCosmetic;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.ArrayList;

public class PaneTintedCross extends TemplateBlockBase {
    public PaneTintedCross(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public void doesBoxCollide(Level par1World, int par2, int par3, int par4, Box par5AxisAlignedBB, ArrayList par6List)
    {
        this.setBoundingBox(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
        super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
        this.setBoundingBox(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
        super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
    }

    public int getRenderPass(){
        return 1;
    }

    public boolean isFullOpaque()
    {
        return false;
    }
}
