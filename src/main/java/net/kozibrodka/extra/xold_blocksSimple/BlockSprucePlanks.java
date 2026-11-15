package net.kozibrodka.extra.xold_blocksSimple;

import net.kozibrodka.extra.events.TextureListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BlockSprucePlanks extends TemplateBlock {

    public BlockSprucePlanks(Identifier identifier, Material material) {
        super(identifier, material);
        setDefaultState(getDefaultState()
                .with(UPPER, false)
        );
    }

    private static final BooleanProperty UPPER = BooleanProperty.of("upper");

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(UPPER);
    }

    @Override
    public int getTexture(int side) {
        return TextureListener.planks_spruce;
    }

}
