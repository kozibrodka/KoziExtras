package net.kozibrodka.extra.farming;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.level.BlockStateView;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.template.item.TemplateLog;

public class BlockTreeJungle extends TemplateBlockBase {


    public BlockTreeJungle(Identifier identifier, Material material) {
        super(identifier, material);
    }

    public static final IntProperty ROTACJA = IntProperty.of("rotacja", 0, 2);

    public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder){
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
    public void onBlockPlaced(Level level, int x, int y, int z, int side) {


//        int korzen = level.getTileMeta(x,y,z);
//        System.out.println(korzen);
        if(side == 1 || side == 0)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(ROTACJA, 0));
        }
        if(side == 4 || side == 5)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(ROTACJA, 1));
        }
        if(side == 2 || side == 3)
        {
            ((BlockStateView)level).setBlockStateWithNotify(x, y, z, getDefaultState().with(ROTACJA, 2));
        }
    }

}


