package net.kozibrodka.extra.util_vanilla;


import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class BlockHorizontal extends TemplateBlock
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
