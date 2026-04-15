package net.kozibrodka.extra.blocksCosmetic;

import net.kozibrodka.extra.utils.FCUtilsMisc;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;

public class FenceGate extends TemplateBlock {

    public FenceGate(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public void onPlaced(World world, int i, int j, int k, LivingEntity entityLiving)
    {
        int iFacing = FCUtilsMisc.ConvertPlacingEntityOrientationToFlatBlockFacing(entityLiving);
        SetFacing(world, i, j, k, iFacing);
    }

    public void SetFacing(World world, int i, int j, int k, int iFacing)
    {
        BlockState currentState = world.getBlockState(i, j, k);
//        world.setBlockStateWithNotify(i,j,k, currentState.with(FACING, iFacing));
    }

    public void neighborUpdate(World world, int x, int y, int z, int l) {
    }

    public void addIntersectingBoundingBox(World par1World, int par2, int par3, int par4, Box par5AxisAlignedBB, ArrayList par6List)
    {

    }

    public void updateBoundingBox(BlockView blockviev, int x, int y, int z)
    {
    }

    public boolean onUse(World world, int i, int j, int k, PlayerEntity entityplayer)
    {
        if(world.isRemote)
        {
            return true;
        } else
        {
            int iFacing = FCUtilsMisc.ConvertPlacingEntityOrientationToFlatBlockFacing(entityplayer);

            return true;
        }
    }

    public boolean isOpaque() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    public void onBlockBreakStart(World arg, int i, int j, int k, PlayerEntity arg2) {
        this.onUse(arg, i, j, k, arg2);
    }

    /**
     * STATES
     */
    public static final IntProperty FACING = IntProperty.of("facing", 0, 2);
    public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FACING);
        builder.add(OPEN);
    }
}
