package net.kozibrodka.extra.util_vanilla;


import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public abstract class BlockHorizontal extends TemplateBlockBase
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockHorizontal(Identifier identifier, Material material) {
        super(identifier, material);
    }


//    protected BlockHorizontal(Material materialIn, MapColor colorIn)
//    {
//        super(materialIn, colorIn);
//    }
}
