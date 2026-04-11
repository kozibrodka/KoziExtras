package net.kozibrodka.extra.farming;

import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.DropListProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplatePlant;
import net.modificationstation.stationapi.api.world.BlockStateView;

import java.util.List;
import java.util.Random;

public class BlockCocoa extends TemplatePlant implements DropListProvider {

    public BlockCocoa(Identifier identifier, int texture) {
        super(identifier, texture);
        this.setTickRandomly(true);
    }

    public void onTick(World level, int x, int y, int z, Random random)
    {
        if (!this.canGrow(level, x, y, z))
        {
//            int a = ((BlockStateView)level).getBlockState(x, y, z).get(WZROST);
//            this.drop(level, x, y, z, 0);
//            level.setBlockToAir(x, y, z);
        }
        else if (level.random.nextInt(5) == 0)
        {
            BlockState currentState = level.getBlockState(x, y, z);
            int var7 = currentState.get(WZROST);
            if (var7 < 2)
            {
                ++var7;
                level.setBlockStateWithNotify(x,y,z, currentState.with(WZROST, var7));
            }
        }
    }

    public void dropWithChance(World level, int i, int j, int k, BlockState state, int l, float f) {
        int c = state.get(WZROST);
        byte b = 1;
        if(c >= 2)
        {
            b = 3;
        }
        for (int a = 0; a < b; ++a)
        {
            this.dropStack(level, i, j, k, new ItemStack(Item.DYE, 1, 3));
        }
    }


    public List<ItemStack> getDropList(World level, int x, int y, int z, BlockState blockState, int meta) {
        int c = blockState.get(WZROST);
        byte b = 0;
        if(c >= 2)
        {
            b = 2;
        }
        for (int a = 0; a < b; ++a)
        {
            this.dropStack(level, x, y, z, new ItemStack(Item.DYE, 1, 3));
        }
        return List.of(new ItemStack(Item.DYE, 1, 3));
    }

    public void fertilizeCocoa(World world, int x, int y, int z)
    {
        BlockState currentState = world.getBlockState(x, y, z);
        int fert = currentState.get(WZROST);
        if(fert < 2)
        {
            world.setBlockStateWithNotify(x,y,z, currentState.with(WZROST, fert +1));
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

    public void onTreeGrowth(World level, int x, int y, int z, int side, int kokos)
    {
        if(level.getBlockId(x,y,z) == this.id) {
            this.onBlockPlaced2(level, x, y, z, side, kokos);
        }
    }

    public void onPlaced(World level, int x, int y, int z, int side) {
        BlockState currentState = level.getBlockState(x, y, z);
        if(side == 2)
        {
            level.setBlockStateWithNotify(x,y,z, currentState.with(SIDE, 0));
        }
        if(side == 3)
        {
            level.setBlockStateWithNotify(x,y,z, currentState.with(SIDE, 1));
        }
        if(side == 4)
        {
            level.setBlockStateWithNotify(x,y,z, currentState.with(SIDE, 2));
        }
        if(side == 5)
        {
            level.setBlockStateWithNotify(x,y,z, currentState.with(SIDE, 3));
        }
    }

    public void onBlockPlaced2(World level, int x, int y, int z, int side, int kokos) {
        BlockState currentState = level.getBlockState(x, y, z);
        if(side == 2)
        {
            level.setBlockStateWithNotify(x,y,z, currentState.with(SIDE, 0).with(WZROST, kokos));
        }
        if(side == 3)
        {
            level.setBlockStateWithNotify(x,y,z, currentState.with(SIDE, 1).with(WZROST, kokos));
        }
        if(side == 4)
        {
            level.setBlockStateWithNotify(x,y,z, currentState.with(SIDE, 2).with(WZROST, kokos));
        }
        if(side == 5)
        {
            level.setBlockStateWithNotify(x,y,z, currentState.with(SIDE, 3).with(WZROST, kokos));
        }
    }

    public boolean canGrow(World level, int x, int y, int z)
    {
    int var5 = 0;
    int var6 = 0;
        try{
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
            return var6 == BlockListener.junglewood.id;
        }catch (Exception exception) {}

        return true;
    }

    public static final IntProperty WZROST = IntProperty.of("wzrost", 0, 2);
    public static final IntProperty SIDE = IntProperty.of("side", 0, 3);

    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(WZROST);
        setDefaultState(WZROST, 0);
        builder.add(SIDE);
        setDefaultState(SIDE, 0);
    }

    private void setDefaultState(IntProperty intprop, int i) {
    }

}
