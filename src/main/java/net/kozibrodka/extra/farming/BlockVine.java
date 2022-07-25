package net.kozibrodka.extra.farming;


import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.level.BlockStateView;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.template.block.TemplatePlant;

import java.util.Random;

public class BlockVine extends TemplateBlockBase {

    public BlockVine(Identifier identifier, Material material) {
        super(identifier, material);
        this.setTicksRandomly(true);
    }

    public void updateBoundingBox(BlockView blockviev, int x, int y, int z)
    {
        if(blockviev.getTileId(x,y,z) == BlockListener.vine.id)
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

    public Box getCollisionShape(Level level, int par2, int par3, int par4)
    {
        return null;
    }

    public boolean canPlaceAt(Level par1World, int par2, int par3, int par4, int par5)
    {
        switch (par5)
        {
            case 1:
                return this.canBePlacedOn(par1World.getTileId(par2, par3 + 1, par4));

            case 2:
                return this.canBePlacedOn(par1World.getTileId(par2, par3, par4 + 1));

            case 3:
                return this.canBePlacedOn(par1World.getTileId(par2, par3, par4 - 1));

            case 4:
                return this.canBePlacedOn(par1World.getTileId(par2 + 1, par3, par4));

            case 5:
                return this.canBePlacedOn(par1World.getTileId(par2 - 1, par3, par4));

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
            BlockBase var2 = BlockBase.BY_ID[par1];
            return var2.isFullCube() && var2.material.blocksMovement();
        }
    }

    public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int par5)
    {
        int a = ((BlockStateView)level).getBlockState(x, y, z).get(DOUBLE);
        int b = ((BlockStateView)level).getBlockState(x, y, z).get(SIDE);

        if(a == 0 && level.getTileId(x,y+1,z) != 0 && canBePlacedOn(level.getTileId(x,y+1,z)))
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y , z, getDefaultState().with(DOUBLE, 1).with(SIDE, b));
        }
        if(a == 1 && (level.getTileId(x,y+1,z) == 0 || !canBePlacedOn(level.getTileId(x,y+1,z))))
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y , z, getDefaultState().with(DOUBLE, 0).with(SIDE, b));
        }
        breakIfIncorrect(level, x, y, z);
    }

    protected final void breakIfIncorrect(Level arg, int i, int j, int k) {
        if (!this.canVineStay(arg, i, j, k)) {
            this.drop(arg, i, j, k, arg.getTileMeta(i, j, k));
            arg.setTile(i, j, k, 0);
        }
    }


    public void onScheduledTick(Level level, int x, int y, int z, Random random)
    {
        int a = 0;
        if(level.getTileId(x, y - 1 , z) == 0 && level.placeTile(x, y, z) >= 9 && random.nextInt(32) == 0)
        {
            try{
                a = ((BlockStateView)level).getBlockState(x, y, z).get(SIDE);
                level.setTile(x,y - 1, z, this.id);
                ((BlockStateView)level).setBlockStateWithNotify(x, y - 1, z, getDefaultState().with(SIDE, a));

            }catch (Exception e){}
        }
    }


    public boolean canVineStay(Level level, int x, int y, int z)
    {
        int var5 = 0;
        int var6 = 0;
        int dach = 0;

        try{
            dach = level.getTileId(x, y + 1, z);
            if(dach == BlockListener.vine.id)
            {
                if(((BlockStateView)level).getBlockState(x, y + 1, z).get(SIDE) != ((BlockStateView)level).getBlockState(x, y, z).get(SIDE))
                {
                    return false;
                }
            }else
            {
                var5 = ((BlockStateView)level).getBlockState(x, y, z).get(SIDE);

                if(var5 == 0)
                {
                    z += 1;
                    var6 = level.getTileId(x, y, z);
                }
                if(var5 == 2)
                {
                    x += 1;
                    var6 = level.getTileId(x, y, z);
                }
                if(var5 == 1)
                {
                    z -= 1;
                    var6 = level.getTileId(x, y, z);
                }
                if(var5 == 3)
                {
                    x -= 1;
                    var6 = level.getTileId(x, y, z);
                }
                return canBePlacedOn(var6);
            }
        }catch (Exception exception) {}

        return true;
    }

    public static final IntProperty DOUBLE = IntProperty.of("double", 0, 1);
    public static final IntProperty SIDE = IntProperty.of("side", 0, 3);

    public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder){
        builder.add(DOUBLE);
        setDefaultState(DOUBLE, 0);
        builder.add(SIDE);
        setDefaultState(SIDE, 0);
    }

    private void setDefaultState(IntProperty intprop, int i) {
    }

    public void onBlockPlaced(Level level, int x, int y, int z, int side)
    {
        if(side == 2)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, 0));
        }
        if(side == 3)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, 1));
        }
        if(side == 4)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, 2));
        }
        if(side == 5)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, 3));
        }
        this.onAdjacentBlockUpdate(level,x,y,z,side);  ///DAJ POTEM!!!!!
    }

    public void onTreeGrowth(Level level, int x, int y, int z, int side)
    {
        if(level.getTileId(x,y,z) == this.id) {
            this.onBlockPlaced(level, x, y, z, side);
        }
//        ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, side));
//        this.onAdjacentBlockUpdate(level,x,y,z,side);
    }

    public void afterBreak(Level arg, PlayerBase arg2, int i, int j, int k, int l) {
        if (!arg.isServerSide && arg2.getHeldItem() != null && arg2.getHeldItem().itemId == ItemBase.shears.id) {
            arg2.increaseStat(Stats.mineBlock[this.id], 1);
            this.drop(arg, i, j, k, new ItemInstance(BlockListener.vine.id, 1, 0));
        } else {
            super.afterBreak(arg, arg2, i, j, k, l);
        }

    }

    public int getDropId(int par1, Random par2Random)
    {
        return 0;
    }

    public int getDropCount(Random par1Random)
    {
        return 0;
    }

    public void method_1605()
    {
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isFullOpaque()
    {
        return false;
    }
}
