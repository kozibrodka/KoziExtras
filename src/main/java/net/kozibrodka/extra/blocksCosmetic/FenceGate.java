package net.kozibrodka.extra.blocksCosmetic;

import net.kozibrodka.extra.utils.FCUtilsMisc;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.ArrayList;

public class FenceGate extends TemplateBlockBase {

    public FenceGate(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public void afterPlaced(Level world, int i, int j, int k, Living entityLiving)
    {
        int iFacing = FCUtilsMisc.ConvertPlacingEntityOrientationToFlatBlockFacing(entityLiving);
        SetFacing(world, i, j, k, iFacing);
    }

    public void SetFacing(Level world, int i, int j, int k, int iFacing)
    {
        BlockState currentState = world.getBlockState(i, j, k);
//        world.setBlockStateWithNotify(i,j,k, currentState.with(FACING, iFacing));
    }

    public void onAdjacentBlockUpdate(Level world, int x, int y, int z, int l) {
    }

    public void doesBoxCollide(Level par1World, int par2, int par3, int par4, Box par5AxisAlignedBB, ArrayList par6List)
    {

    }

    public void updateBoundingBox(BlockView blockviev, int x, int y, int z)
    {
    }

    public boolean canUse(Level world, int i, int j, int k, PlayerBase entityplayer)
    {
        if(world.isServerSide)
        {
            return true;
        } else
        {
            int iFacing = FCUtilsMisc.ConvertPlacingEntityOrientationToFlatBlockFacing(entityplayer);

            return true;
        }
    }

    public boolean isFullOpaque() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    public void activate(Level arg, int i, int j, int k, PlayerBase arg2) {
        this.canUse(arg, i, j, k, arg2);
    }

    /**
     * STATES
     */
    public static final IntProperty FACING = IntProperty.of("facing", 0, 2);
    public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder){
        builder.add(FACING);
        builder.add(OPEN);
    }
}
