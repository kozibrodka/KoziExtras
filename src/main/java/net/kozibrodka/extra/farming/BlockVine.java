package net.kozibrodka.extra.farming;


import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.template.block.TemplatePlant;
import net.modificationstation.stationapi.api.world.BlockStateView;

import java.util.Random;

public class BlockVine extends TemplateBlockBase {

    public BlockVine(Identifier identifier, Material material) {
        super(identifier, material);
        this.setTickRandomly(true);
    }

    public void updateBoundingBox(BlockView blockviev, int x, int y, int z)
    {
        if(blockviev.getBlockId(x,y,z) == BlockListener.vine.id)
        {
            int sajd = ((BlockStateView)blockviev).getBlockState(x, y, z).get(SIDE);
//            int dasz = ((BlockStateView)blockviev).getBlockState(x, y, z).get(DOUBLE);

            float var7 = 1.0F;
            float var8 = 1.0F;
            float var9 = 1.0F;
            float var10 = 0.0F;
            float var11 = 0.0F;
            float var12 = 0.0F;
//            boolean var13 = dasz > 0;

            if (sajd == 3)
            {
                var10 = Math.max(var10, 0.0625F);
                var7 = 0.0F;
                var8 = 0.0F;
                var11 = 1.0F;
                var9 = 0.0F;
                var12 = 1.0F;
            }

            if (sajd == 2)
            {
                var7 = Math.min(var7, 0.9375F);
                var10 = 1.0F;
                var8 = 0.0F;
                var11 = 1.0F;
                var9 = 0.0F;
                var12 = 1.0F;
            }

            if (sajd == 1)
            {
                var12 = Math.max(var12, 0.0625F);
                var9 = 0.0F;
                var7 = 0.0F;
                var10 = 1.0F;
                var8 = 0.0F;
                var11 = 1.0F;
            }

            if (sajd == 0)
            {
                var9 = Math.min(var9, 0.9375F);
                var12 = 1.0F;
                var7 = 0.0F;
                var10 = 1.0F;
                var8 = 0.0F;
                var11 = 1.0F;
            }

//            if (dasz == 1)
//            {
//                var8 = Math.min(var8, 0.9375F);
//                var11 = 1.0F;
//                var7 = 0.0F;
//                var10 = 1.0F;
//                var9 = 0.0F;
//                var12 = 1.0F;
//            }

            this.setBoundingBox(var7, var8, var9, var10, var11, var12);
        }
    }

    public Box getCollisionShape(World level, int par2, int par3, int par4)
    {
        return null;
    }

    public boolean canPlaceAt(World par1World, int par2, int par3, int par4, int par5)
    {
        switch (par5)
        {
            case 1:
                return this.canBePlacedOn(par1World.getBlockId(par2, par3 + 1, par4));

            case 2:
                return this.canBePlacedOn(par1World.getBlockId(par2, par3, par4 + 1));

            case 3:
                return this.canBePlacedOn(par1World.getBlockId(par2, par3, par4 - 1));

            case 4:
                return this.canBePlacedOn(par1World.getBlockId(par2 + 1, par3, par4));

            case 5:
                return this.canBePlacedOn(par1World.getBlockId(par2 - 1, par3, par4));

            default:
                return false;
        }
    }

    private boolean canBePlacedOn(int par1)
    {
        if (par1 == 0)
        {
            return false;
        }
        else
        {
            Block var2 = Block.BLOCKS[par1];
            return var2.isFullCube() && var2.material.blocksMovement();
        }
    }

    public void neighborUpdate(World level, int x, int y, int z, int par5)
    {
        BlockState currentState = level.getBlockState(x, y, z);
        int a = currentState.get(DOUBLE);
        int b = currentState.get(SIDE);

        if(a == 0 && level.getBlockId(x,y+1,z) != 0 && canBePlacedOn(level.getBlockId(x,y+1,z)))
        {
            level.setBlockStateWithNotify(x, y , z, currentState.with(DOUBLE, 1).with(SIDE, b));
        }
        if(a == 1 && (level.getBlockId(x,y+1,z) == 0 || !canBePlacedOn(level.getBlockId(x,y+1,z))))
        {
            level.setBlockStateWithNotify(x, y , z, currentState.with(DOUBLE, 0).with(SIDE, b));
        }
        breakIfIncorrect(level, x, y, z);
    }

    protected final void breakIfIncorrect(World arg, int i, int j, int k) {
        if (!this.canVineStay(arg, i, j, k)) {
            this.dropStacks(arg, i, j, k, arg.getBlockMeta(i, j, k));
            arg.setBlock(i, j, k, 0);
        }
    }


    public void onTick(World level, int x, int y, int z, Random random)
    {
        int a = 0;
        if(level.getBlockId(x, y - 1 , z) == 0 && level.getLightLevel(x, y, z) >= 9 && random.nextInt(32) == 0)
        {
            try{
                BlockState currentState = level.getBlockState(x, y, z);
                a = currentState.get(SIDE);
                level.setBlock(x,y - 1, z, this.id);
                level.setBlockStateWithNotify(x, y - 1, z, currentState.with(SIDE, a));

            }catch (Exception e){}
        }
    }


    public boolean canVineStay(World level, int x, int y, int z)
    {
        int var5 = 0;
        int var6 = 0;
        int dach = 0;

        try{
            dach = level.getBlockId(x, y + 1, z);
            if(dach == BlockListener.vine.id)
            {
                if(level.getBlockState(x, y + 1, z).get(SIDE) != level.getBlockState(x, y, z).get(SIDE))
                {
                    return false;
                }
            }else
            {
                var5 = level.getBlockState(x, y, z).get(SIDE);

                if(var5 == 0)
                {
                    z += 1;
                    var6 = level.getBlockId(x, y, z);
                }
                if(var5 == 2)
                {
                    x += 1;
                    var6 = level.getBlockId(x, y, z);
                }
                if(var5 == 1)
                {
                    z -= 1;
                    var6 = level.getBlockId(x, y, z);
                }
                if(var5 == 3)
                {
                    x -= 1;
                    var6 = level.getBlockId(x, y, z);
                }
                return canBePlacedOn(var6);
            }
        }catch (Exception exception) {}

        return true;
    }

    public static final IntProperty DOUBLE = IntProperty.of("double", 0, 1);
    public static final IntProperty SIDE = IntProperty.of("side", 0, 3);

    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(DOUBLE);
        setDefaultState(DOUBLE, 0);
        builder.add(SIDE);
        setDefaultState(SIDE, 0);
    }

    private void setDefaultState(IntProperty intprop, int i) {
    }

    public void onPlaced(World level, int x, int y, int z, int side)
    {
        BlockState currentState = level.getBlockState(x, y, z);
        if(side == 2)
        {
            level.setBlockStateWithNotify(x, y, z, currentState.with(SIDE, 0));
        }
        if(side == 3)
        {
            level.setBlockStateWithNotify(x, y, z, currentState.with(SIDE, 1));
        }
        if(side == 4)
        {
            level.setBlockStateWithNotify(x, y, z, currentState.with(SIDE, 2));
        }
        if(side == 5)
        {
            level.setBlockStateWithNotify(x, y, z, currentState.with(SIDE, 3));
        }
        this.neighborUpdate(level,x,y,z,side);  ///DAJ POTEM!!!!!
    }

    public void onTreeGrowth(World level, int x, int y, int z, int side)
    {
        if(level.getBlockId(x,y,z) == this.id) {
            this.onPlaced(level, x, y, z, side);
        }
//        ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, side));
//        this.onAdjacentBlockUpdate(level,x,y,z,side);
    }

    public void afterBreak(World arg, PlayerEntity arg2, int i, int j, int k, int l) {
        if (!arg.isRemote && arg2.getHand() != null && arg2.getHand().itemId == Item.SHEARS.id) {
            arg2.increaseStat(Stats.MINE_BLOCK[this.id], 1);
            this.dropStack(arg, i, j, k, new ItemStack(BlockListener.vine.id, 1, 0));
        } else {
            super.afterBreak(arg, arg2, i, j, k, l);
        }

    }

    public int getDroppedItemId(int par1, Random par2Random)
    {
        return 0;
    }

    public int getDroppedItemCount(Random par1Random)
    {
        return 0;
    }

    public void setupRenderBoundingBox()
    {
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isOpaque()
    {
        return false;
    }
}
