package net.kozibrodka.extra.farming;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.extra.events.BlockListener;
import net.kozibrodka.extra.events.ColorListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplatePlant;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;


public class BlockStem extends TemplatePlant
{
    private final Block fruitType;

    public BlockStem(Identifier identifier, int texture, Block par2Block) {
        super(identifier, texture);
        this.fruitType = par2Block;
        this.setTickRandomly(true);
        float var3 = 0.125F;
        this.setBoundingBox(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
    }
    Random random = new Random();

    public void updateBoundingBox(BlockView arg, int par2, int par3, int par4)
    {
        try{
            this.maxY = (double)((float)(((BlockStateView)arg).getBlockState(par2, par3, par4).get(WZROST) * 2 + 2) / 16.0F);
        }catch (Exception exception){

        }
        float var5 = 0.125F;
        this.setBoundingBox(0.5F - var5, 0.0F, 0.5F - var5, 0.5F + var5, (float)this.maxY, 0.5F + var5);
    }

    public void neighborUpdate(World world, int x, int y, int z, int l) {
        super.neighborUpdate(world, x, y, z, l);
        if(world.getBlockId(x,y,z) != BlockListener.watermelonsten.id && world.getBlockId(x,y,z) != BlockListener.pumpkinsten.id)
        {
            return;
        }
        BlockState currentState = world.getBlockState(x, y, z);
        int stanik = currentState.get(WZROST);
        if(stanik > 7)
        {
            if(stanik == 8){
                if (world.getBlockId(x, y, z + 1) != this.fruitType.id)
                {
                    world.setBlockStateWithNotify(x,y,z,currentState.with(WZROST,7));
                }
            }
            if(stanik == 9){
                if (world.getBlockId(x - 1, y, z) != this.fruitType.id)
                {
                    world.setBlockStateWithNotify(x,y,z,currentState.with(WZROST,7));
                }
            }
            if(stanik == 10){
                if (world.getBlockId(x , y, z - 1) != this.fruitType.id)
                {
                    world.setBlockStateWithNotify(x,y,z,currentState.with(WZROST,7));
                }
            }
            if(stanik == 11){
                if (world.getBlockId(x + 1, y, z) != this.fruitType.id)
                {
                    world.setBlockStateWithNotify(x,y,z,currentState.with(WZROST,7));
                }
            }
        }
    }

    public void fertilizeStem(World world, int x, int y, int z)
    {
        int a = random.nextInt(2,5);
        BlockState currentState = world.getBlockState(x, y, z);
        int fert = currentState.get(WZROST);
        int e = fert + a;
        if(fert >= 7)
        {
            return;
        }
        if(fert < 7)
        {
            if(e <= 7){
                world.setBlockStateWithNotify(x,y,z,currentState.with(WZROST,e));
            }
            if(e > 7) {
                world.setBlockStateWithNotify(x,y,z,currentState.with(WZROST,7));
            }
        }
    }

    protected boolean canPlantOnTop(int i) {
        return i == Block.FARMLAND.id;
    }

    public static final IntProperty WZROST = IntProperty.of("wzrost", 0, 11);

    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(WZROST);
        setDefaultState(WZROST, 0);
    }

    private void setDefaultState(IntProperty WZROST, int i) {
    }


    public void onTick(World world, int x, int y, int z, Random random)
    {
        super.onTick(world, x, y, z, random);

        if (world.getLightLevel(x, y + 1, z) >= 9)
        {
            float var6 = this.getGrowthModifier(world, x, y, z);

            if (random.nextInt((int)(25.0F / var6) + 1) == 0)
            {
                BlockState currentState = world.getBlockState(x, y, z);
                int var7 = currentState.get(WZROST);

                if (var7 < 7)
                {
                    ++var7;
                    world.setBlockStateWithNotify(x,y,z,currentState.with(WZROST,var7));
                }
                else
                {
                    if (world.getBlockId(x - 1, y, z) == this.fruitType.id)
                    {
                        return;
                    }

                    if (world.getBlockId(x + 1, y, z) == this.fruitType.id)
                    {
                        return;
                    }

                    if (world.getBlockId(x, y, z - 1) == this.fruitType.id)
                    {
                        return;
                    }

                    if (world.getBlockId(x, y, z + 1) == this.fruitType.id)
                    {
                        return;
                    }

                    int var8 = random.nextInt(4);
                    int var9 = x;
                    int var10 = z;

                    if (var8 == 0)
                    {
                        var9 = x - 1;
                    }

                    if (var8 == 1)
                    {
                        ++var9;
                    }

                    if (var8 == 2)
                    {
                        var10 = z - 1;
                    }

                    if (var8 == 3)
                    {
                        ++var10;
                    }

                    int var11 = world.getBlockId(var9, y - 1, var10);

                    if (world.getBlockId(var9, y, var10) == 0 && (var11 == Block.FARMLAND.id || var11 == Block.DIRT.id || var11 == Block.GRASS_BLOCK.id))
                    {
                        world.setBlockWithoutNotifyingNeighbors(var9, y, var10, this.fruitType.id);
                        wybierzRotacje(var9, var10, x, z, y, world);

                    }
                }
            }
        }
    }

    public void wybierzRotacje(int melonX, int melonZ, int stenX, int stenZ, int stenY, World level)
    {
        BlockState currentState = level.getBlockState(stenX, stenY, stenZ);
        if(melonX == stenX)
        {
            if(melonZ > stenZ)
            {
                level.setBlockStateWithNotify(stenX, stenY, stenZ, currentState.with(WZROST,8));
            }
            if(melonZ < stenZ)
            {
                level.setBlockStateWithNotify(stenX, stenY, stenZ, currentState.with(WZROST,10));
            }
        }
        if(melonZ == stenZ)
        {
            if(melonX > stenX)
            {
                level.setBlockStateWithNotify(stenX, stenY, stenZ, currentState.with(WZROST,11));
            }
            if(melonX < stenX)
            {
                level.setBlockStateWithNotify(stenX, stenY, stenZ, currentState.with(WZROST,9));
            }
        }
    }

    private float getGrowthModifier(World world, int x, int y, int z)
    {
        float var5 = 1.0F;
        int var6 = world.getBlockId(x, y, z - 1);
        int var7 = world.getBlockId(x, y, z + 1);
        int var8 = world.getBlockId(x - 1, y, z);
        int var9 = world.getBlockId(x + 1, y, z);
        int var10 = world.getBlockId(x - 1, y, z - 1);
        int var11 = world.getBlockId(x + 1, y, z - 1);
        int var12 = world.getBlockId(x + 1, y, z + 1);
        int var13 = world.getBlockId(x - 1, y, z + 1);
        boolean var14 = var8 == this.id || var9 == this.id;
        boolean var15 = var6 == this.id || var7 == this.id;
        boolean var16 = var10 == this.id || var11 == this.id || var12 == this.id || var13 == this.id;

        for (int var17 = x - 1; var17 <= x + 1; ++var17)
        {
            for (int var18 = z - 1; var18 <= z + 1; ++var18)
            {
                int var19 = world.getBlockId(var17, y - 1, var18);
                float var20 = 0.0F;

                if (var19 == Block.FARMLAND.id)
                {
                    var20 = 1.0F;

                    if (world.getBlockMeta(var17, y - 1, var18) > 0)
                    {
                        var20 = 3.0F;
                    }
                }

                if (var17 != x || var18 != z)
                {
                    var20 /= 4.0F;
                }

                var5 += var20;
            }
        }

        if (var16 || var14 && var15)
        {
            var5 /= 2.0F;
        }

        return var5;
    }

//    public void beforeDestroyedByExplosion(Level par1World, int par2, int par3, int par4, int par5, float par7)
//    {
//        super.beforeDestroyedByExplosion(par1World, par2, par3, par4, par5, par7);
//
//        if (!par1World.isServerSide)
//        {
//            ItemBase var8 = null;
//
//            if (this.fruitType == BlockBase.PUMPKIN)
//            {
//                var8 = BlockListener.pumpkinseeds;
//            }
//
//            if (this.fruitType == BlockListener.watermelon)
//            {
//                var8 = BlockListener.watermelonseeds;
//            }
//
//            for (int var9 = 0; var9 < 3; ++var9)
//            {
//                if (par1World.rand.nextInt(15) <= par5)
//                {
//                    this.drop(par1World, par2, par3, par4, new ItemInstance(var8));
//                }
//            }
//        }
//    }

    public void dropWithChance(World level, int i, int j, int k, BlockState state, int l, float f) {
        if (!level.isRemote)
        {
            Item var8 = null;
            int par5 = state.get(WZROST);
            if (this.fruitType == Block.PUMPKIN)
            {
                var8 = BlockListener.pumpkinseeds;
            }

            if (this.fruitType == BlockListener.watermelon)
            {
                var8 = BlockListener.watermelonseeds;
            }

            for (int var9 = 0; var9 < 3; ++var9)
            {
                if (level.random.nextInt(15) <= par5)
                {
                    this.dropStack(level, i, j, k, new ItemStack(var8));
                }
            }
        }
    }

    public List<ItemStack> getDropList(World level, int x, int y, int z, BlockState blockState, int meta) {

        Item var8 = null;
        int par5 = blockState.get(WZROST);
        if (this.fruitType == Block.PUMPKIN)
        {
            var8 = BlockListener.pumpkinseeds;
        }
        if (this.fruitType == BlockListener.watermelon)
        {
            var8 = BlockListener.watermelonseeds;
        }

        if (!level.isRemote)
        {
            for (int var9 = 0; var9 < 3; ++var9)
            {
                if (level.random.nextInt(15) <= par5)
                {
                    this.dropStack(level, x, y, z, new ItemStack(var8));
                }
            }
        }
//        return List.of(new ItemInstance(var8,0));
        return null;
    }

//    public int getDropId(int par1, Random par2Random)
//    {
//        return -1;
//    }
//
//    public int getDropCount(Random par1Random)
//    {
//        return 1;
//    }

}

