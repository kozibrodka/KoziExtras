package net.kozibrodka.extra.farming;

import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.DropListProvider;
import net.modificationstation.stationapi.api.level.BlockStateView;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplatePlant;

import java.util.List;
import java.util.Random;

public class BlockCocoa extends TemplatePlant implements DropListProvider {

    public BlockCocoa(Identifier identifier, int texture) {
        super(identifier, texture);
        this.setTicksRandomly(true);
    }

    public void onScheduledTick(Level level, int x, int y, int z, Random random)
    {
        if (!this.canGrow(level, x, y, z))
        {
//            int a = ((BlockStateView)level).getBlockState(x, y, z).get(WZROST);
//            this.drop(level, x, y, z, 0);
//            level.setBlockToAir(x, y, z);
        }
        else if (level.rand.nextInt(5) == 0)
        {
            int var6 = ((BlockStateView)level).getBlockState(x, y, z).get(WZROST);
            int sidecz = ((BlockStateView)level).getBlockState(x, y, z).get(SIDE);
            int var7 = var6;

            if (var7 < 2)
            {
                ++var7;
                ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(WZROST, var7).with(SIDE, sidecz));
            }
        }
    }

    public void dropWithChance(Level level, int i, int j, int k, BlockState state, int l, float f) {
        int c = state.get(WZROST);
        byte b = 1;
        if(c >= 2)
        {
            b = 3;
        }
        for (int a = 0; a < b; ++a)
        {
            this.drop(level, i, j, k, new ItemInstance(ItemBase.dyePowder, 1, 3));
        }
    }


    public List<ItemInstance> getDropList(Level level, int x, int y, int z, BlockState blockState, int meta) {
        int c = blockState.get(WZROST);
        byte b = 0;
        if(c >= 2)
        {
            b = 2;
        }
        for (int a = 0; a < b; ++a)
        {
            this.drop(level, x, y, z, new ItemInstance(ItemBase.dyePowder, 1, 3));
        }
        return List.of(new ItemInstance(ItemBase.dyePowder, 1, 3));
    }

    public void fertilizeCocoa(Level world, int x, int y, int z)
    {
        int fert = ((BlockStateView)world).getBlockState(x, y, z).get(WZROST);
        int sidecz = ((BlockStateView)world).getBlockState(x, y, z).get(SIDE);
        if(fert < 2)
        {
            ((BlockStateView)world).setBlockStateWithNotify(x, y, z, getDefaultState().with(WZROST, fert + 1).with(SIDE, sidecz));
        }
    }

    public void updateBoundingBox(BlockView world, int x, int y, int z)
    {
        int var5 = 0;
        int var6 = 0;
        try{
            var5 = ((BlockStateView)world).getBlockState(x, y, z).get(WZROST);
            var6 = ((BlockStateView)world).getBlockState(x, y, z).get(SIDE);
        }catch (Exception exception){

        }

        int var7 = var5;
        int var8 = 4 + var7 * 2;
        int var9 = 5 + var7 * 2;
        float var10 = (float)var8 / 2.0F;

        switch (var6)
        {
            case 0:
                this.setBoundingBox((8.0F - var10) / 16.0F, (12.0F - (float)var9) / 16.0F, (15.0F - (float)var8) / 16.0F, (8.0F + var10) / 16.0F, 0.75F, 0.9375F);
                break;

            case 3:
                this.setBoundingBox(0.0625F, (12.0F - (float)var9) / 16.0F, (8.0F - var10) / 16.0F, (1.0F + (float)var8) / 16.0F, 0.75F, (8.0F + var10) / 16.0F);
                break;

            case 1:
                this.setBoundingBox((8.0F - var10) / 16.0F, (12.0F - (float)var9) / 16.0F, 0.0625F, (8.0F + var10) / 16.0F, 0.75F, (1.0F + (float)var8) / 16.0F);
                break;

            case 2:
                this.setBoundingBox((15.0F - (float)var8) / 16.0F, (12.0F - (float)var9) / 16.0F, (8.0F - var10) / 16.0F, 0.9375F, 0.75F, (8.0F + var10) / 16.0F);
        }
    }

    public void onTreeGrowth(Level level, int x, int y, int z, int side, int kokos)
    {
        if(level.getTileId(x,y,z) == this.id) {
            this.onBlockPlaced2(level, x, y, z, side, kokos);
        }
    }

    public void onBlockPlaced(Level level, int x, int y, int z, int side) {
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
    }

    public void onBlockPlaced2(Level level, int x, int y, int z, int side, int kokos) {
        if(side == 2)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, 0).with(WZROST, kokos));
        }
        if(side == 3)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, 1).with(WZROST, kokos));
        }
        if(side == 4)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, 2).with(WZROST, kokos));
        }
        if(side == 5)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(SIDE, 3).with(WZROST, kokos));
        }
    }

    public boolean canGrow(Level level, int x, int y, int z)
    {
    int var5 = 0;
    int var6 = 0;
        try{
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
            return var6 == BlockListener.junglewood.id;
        }catch (Exception exception) {}

        return true;
    }

    public static final IntProperty WZROST = IntProperty.of("wzrost", 0, 2);
    public static final IntProperty SIDE = IntProperty.of("side", 0, 3);

    public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder){
        builder.add(WZROST);
        setDefaultState(WZROST, 0);
        builder.add(SIDE);
        setDefaultState(SIDE, 0);
    }

    private void setDefaultState(IntProperty intprop, int i) {
    }

}
