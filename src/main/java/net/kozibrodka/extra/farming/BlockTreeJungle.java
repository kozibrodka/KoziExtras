package net.kozibrodka.extra.farming;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BlockTreeJungle extends TemplateBlock {


    public BlockTreeJungle(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public static final IntProperty ROTACJA = IntProperty.of("rotacja", 0, 2);

    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(ROTACJA);
        setDefaultState(ROTACJA, 0);
    }

    private void setDefaultState(IntProperty WZROST, int i) {
    }

//        @Override
//        public void afterPlaced(Level level, int i, int j, int k, Living arg2){
//            int a = MathHelper.floor((double) (arg2.yaw * 4.0F / 360.0F) + 0.5D) & 3;
//            System.out.println(a);
//            if (a == 0) {
//                ((BlockStateView)level).setBlockStateWithNotify(i, j, k, getDefaultState().with(ROTACJA, 0));
//            }
//            if(a == 1){
//                ((BlockStateView)level).setBlockStateWithNotify(i, j, k, getDefaultState().with(ROTACJA, 1));
//            }
//            if(a == 2){
//                ((BlockStateView)level).setBlockStateWithNotify(i, j, k, getDefaultState().with(ROTACJA, 1));
//            }
//            if (a == 3)  {
//                ((BlockStateView)level).setBlockStateWithNotify(i, j, k, getDefaultState().with(ROTACJA, 2));
//            }
//        }

    @Override
    public void onPlaced(World level, int x, int y, int z, int side) {


//        int korzen = level.getTileMeta(x,y,z);
//        System.out.println(korzen);
        BlockState currentState = level.getBlockState(x, y, z);
        if(side == 1 || side == 0)
        {
            level.setBlockStateWithNotify(x,y,z,currentState.with(ROTACJA,0));
        }
        if(side == 4 || side == 5)
        {
            level.setBlockStateWithNotify(x,y,z,currentState.with(ROTACJA,1));
        }
        if(side == 2 || side == 3)
        {
            level.setBlockStateWithNotify(x,y,z,currentState.with(ROTACJA,2));
        }
    }

}


