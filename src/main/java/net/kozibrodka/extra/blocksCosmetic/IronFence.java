package net.kozibrodka.extra.blocksCosmetic;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.ArrayList;

public class IronFence extends TemplateBlockBase {

    public IronFence(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public void onBlockPlaced(Level level, int x, int y, int z, int side) {
        onAdjacentBlockUpdate(level, x, y, z, side);
    }

    public void onAdjacentBlockUpdate(Level world, int x, int y, int z, int l) {
        boolean side_N = this.canConnect(world.getTileId(x - 1, y, z));
        boolean side_S = this.canConnect(world.getTileId(x + 1, y, z));
        boolean side_W = this.canConnect(world.getTileId(x , y, z + 1));
        boolean side_E = this.canConnect(world.getTileId(x, y, z - 1));
        BlockState currentState = world.getBlockState(x, y, z);
        world.setBlockStateWithNotify(x, y, z, currentState.with(NORTH, side_N).with(SOUTH, side_S).with(EAST, side_E).with(WEST, side_W));    }

    public void doesBoxCollide(Level par1World, int par2, int par3, int par4, Box par5AxisAlignedBB, ArrayList par6List)
    {
        boolean var8 = this.canConnect(par1World.getTileId(par2, par3, par4 - 1));
        boolean var9 = this.canConnect(par1World.getTileId(par2, par3, par4 + 1));
        boolean var10 = this.canConnect(par1World.getTileId(par2 - 1, par3, par4));
        boolean var11 = this.canConnect(par1World.getTileId(par2 + 1, par3, par4));
        boolean alone = (!var9 && !var10 && !var11 && !var8);

        if(alone)
        {
            this.setBoundingBox(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
            super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
        }else {

            if ((!var10 || !var11) && (var10 || var11 || var8 || var9)) {
                if (var10 && !var11) {
                    this.setBoundingBox(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
                    super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
                } else if (!var10 && var11) {
                    this.setBoundingBox(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                    super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
                }
            } else {
                this.setBoundingBox(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
            }

            if ((!var8 || !var9) && (var10 || var11 || var8 || var9)) {
                if (var8 && !var9) {
                    this.setBoundingBox(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
                    super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
                } else if (!var8 && var9) {
                    this.setBoundingBox(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
                    super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
                }
            } else {
                this.setBoundingBox(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
                super.doesBoxCollide(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
            }
        }
    }

    public void updateBoundingBox(BlockView blockviev, int x, int y, int z)
    {
        float var5 = 0.4375F;
        float var6 = 0.5625F;
        float var7 = 0.4375F;
        float var8 = 0.5625F;
        boolean var9 = this.canConnect(blockviev.getTileId(x, y, z - 1));
        boolean var10 = this.canConnect(blockviev.getTileId(x, y, z + 1));
        boolean var11 = this.canConnect(blockviev.getTileId(x - 1, y, z));
        boolean var12 = this.canConnect(blockviev.getTileId(x + 1, y, z));

        if ((!var11 || !var12) && (var11 || var12 || var9 || var10))
        {
            if (var11 && !var12)
            {
                var5 = 0.0F;
            }
            else if (!var11 && var12)
            {
                var6 = 1.0F;
            }
        }
        else
        {
            var5 = 0.0F;
            var6 = 1.0F;
        }

        if ((!var9 || !var10) && (var11 || var12 || var9 || var10))
        {
            if (var9 && !var10)
            {
                var7 = 0.0F;
            }
            else if (!var9 && var10)
            {
                var8 = 1.0F;
            }
        }
        else
        {
            var7 = 0.0F;
            var8 = 1.0F;
        }

        if(!var9 && !var10 && !var11 && !var12)
        {
            var5 = 0.4375F;
            var6 = 0.5625F;
            var7 = 0.4375F;
            var8 = 0.5625F;
        }

        this.setBoundingBox(var5, 0.0F, var7, var6, 1.0F, var8);
    }


    public final boolean canConnect(int i)
    {
        boolean flag1 = false;
        BlockBase kloc = BlockBase.BY_ID[i];
        if(kloc instanceof IronFence ||kloc instanceof IronFenceCross || BlockBase.FULL_OPAQUE[i])
        {
            flag1 = true;
        }
        return flag1;
    }

    public boolean isFullOpaque()
    {
        return false;
    }

    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty WEST = BooleanProperty.of("west");

    public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder){
        builder.add(NORTH);
        setDefaultState(NORTH, false);
        builder.add(SOUTH);
        setDefaultState(SOUTH, false);
        builder.add(EAST);
        setDefaultState(EAST, false);
        builder.add(WEST);
        setDefaultState(WEST, false);
    }

    private void setDefaultState(BooleanProperty boolprop, boolean flag) {
    }

}
