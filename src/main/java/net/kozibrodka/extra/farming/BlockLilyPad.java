package net.kozibrodka.extra.farming;
import java.util.List;


import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplatePlant;

public class BlockLilyPad extends TemplatePlant {

    public BlockLilyPad(Identifier identifier, int texture) {
        super(identifier, texture);
        float var2 = 0.5F;
        float var3 = 0.015625F;
        this.setBoundingBox(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var3, 0.5F + var2);
    }


//    /**
//     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
//     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
//     */
//    public void addCollisionBoxesToList(Level par1World, int par2, int par3, int par4, Box par5AxisAlignedBB, List par6List, EntityBase par7Entity)
//    {
//        if (par7Entity == null || !(par7Entity instanceof Boat))
//        {
//            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
//        }
//    }

    public Box getCollisionShape(World par1World, int par2, int par3, int par4)
    {
        return Box.createCached((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)par3 + this.maxY, (double)par4 + this.maxZ);
    }


//    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
//    {
//        return 2129968;
//    }

    public void onPlaced(World arg, int i, int j, int k, LivingEntity arg2) {
        int var6 = MathHelper.floor((double)(arg2.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        BlockState currentState = arg.getBlockState(i, j, k);
        if(var6 == 0) {
            arg.setBlockStateWithNotify(i,j,k, currentState.with(SIDE,0));
        }
        if(var6 == 1) {
            arg.setBlockStateWithNotify(i,j,k, currentState.with(SIDE,1));
        }
        if(var6 == 2) {
            arg.setBlockStateWithNotify(i,j,k, currentState.with(SIDE,2));
        }
        if(var6 == 3) {
            arg.setBlockStateWithNotify(i,j,k, currentState.with(SIDE,3));
        }

    }

    public void afterBreak(World arg, PlayerEntity arg2, int i, int j, int k, int l) {
        if (!arg.isRemote && arg2.getHand() != null && arg2.getHand().itemId == Item.SHEARS.id) {
            arg2.increaseStat(Stats.MINE_BLOCK[this.id], 1);
            this.dropStack(arg, i, j, k, new ItemStack(BlockListener.waterlily.id, 1, l));
        } else {
            super.afterBreak(arg, arg2, i, j, k, l);
        }
    }

    public boolean canPlaceAt(World arg, int i, int j, int k) {
        return this.canPlantOnTop(arg.getBlockId(i, j - 1, k));
    }

    protected boolean canPlantOnTop(int par1)
    {
        return par1 == Block.WATER.id;
    }

    public boolean canGrow(World level, int par2, int par3, int par4)
    {
        return par3 >= 0 && par3 < 256 && level.getMaterial(par2, par3 - 1, par4) == Material.WATER && level.getBlockMeta(par2, par3 - 1, par4) == 0;
    }

    public static final IntProperty SIDE = IntProperty.of("side", 0, 3);

    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(SIDE);
        setDefaultState(SIDE, 0);
    }

    private void setDefaultState(IntProperty intprop, int i) {
    }

}
