package net.kozibrodka.extra.cosmetic;

import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.ArrayList;

public class PaneCross extends TemplateBlockBase {
    public PaneCross(Identifier identifier, Material material) {
        super(identifier, material);
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void doesBoxCollide(Level par1World, int par2, int par3, int par4, Box par5AxisAlignedBB, ArrayList par6List)
    {
        this.setBoundingBox(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
        super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
        this.setBoundingBox(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
        super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
    }


    public void updateBoundingBox(BlockView blockviev, int x, int y, int z)
    {
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isFullOpaque()
    {
        return false;
    }
}
