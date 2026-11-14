package net.kozibrodka.extra.mixin.stairs;

import net.kozibrodka.extra.block.item.BlockItemStairs;
import net.kozibrodka.extra.mixin_interface.BlockStairsInterface;
import net.kozibrodka.extra.utils.StairShapeEnum;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.HasCustomBlockItemFactory;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.*;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;

@Mixin(StairsBlock.class)
@HasCustomBlockItemFactory(BlockItemStairs.class)
public class BlockStairsMixin extends Block implements BlockStairsInterface {

    protected BlockStairsMixin(int i, Material arg) {
        super(i, arg);
    }

    @Unique
    private static final EnumProperty<StairShapeEnum> SHAPE = EnumProperty.of("shape", StairShapeEnum.class);

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.BOTTOM);
        builder.add(Properties.HORIZONTAL_FACING);
        builder.add(SHAPE);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructorEnd(CallbackInfo ci) {
        setDefaultState(getDefaultState()
                .with(Properties.BOTTOM, true)
                .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                .with(SHAPE, StairShapeEnum.STRAIGHT)
        );
    }

    @Override
    public void onPlacedStairsExtra(World world, int i, int j, int k, boolean bottom, Direction geoFacing, int oldMeta) {
        /// on ItemBlock use
        BlockState currentState = world.getBlockState(i, j, k);
        world.setBlockState(i, j, k, currentState.with(Properties.HORIZONTAL_FACING, geoFacing).with(Properties.BOTTOM, bottom), oldMeta); /// tutaj jedyne wywołanie updateNeighbors (bez cichego - WithoutNotifyingNeighbors)
        this.investigateStairShape(world, i, j, k, false);
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        if(id == 0 || Block.BLOCKS[id] instanceof StairsBlock){ /// gdyby ID piston-heada przeszło to byłby crash (wiadomo o co chodzi)
            investigateStairShape(world, x, y, z, true);
            /// AIR, STAIRS - jedyne odpalają investigation --- ewentualnie dodać black-liste ID's
        }
//        System.out.println("SĄSIEDNI_UPDATE " + id);
    }

    @Unique
    public void investigateStairShape(World world, int i, int j, int k, boolean flagUpdateMP)
    {
        int currentMeta = world.getBlockMeta(i, j, k);
        boolean flagS = true;

        boolean naroznik_zewnetrzny = false; /// czy narożnik został zmieniony
        boolean naroznik_wewnetrzny = false;

        int naro_zewn_info = determineOuterShape(world, i, j, k, currentMeta); /// 0 - BRAK KSZTAŁTU,  1 - UTRWALENIE KSZTAŁTU,  2 - NOWY KSZTAŁT
        if(naro_zewn_info > 0){
            flagS = false;
            if(naro_zewn_info > 1){
                naroznik_zewnetrzny = true;
            }
        }

        if (!naroznik_zewnetrzny)
        {
            naroznik_wewnetrzny = determineInnerShape(world, i, j, k, flagS, currentMeta); /// false = niestworzony.
        }

        if((naroznik_wewnetrzny || naroznik_zewnetrzny) && flagUpdateMP){ /// Gdy SHAPE został zmieniony.
            world.blockUpdateEvent(i, j, k); /// SMP update.
        }
    }

    public boolean tryUpdateShapeState(World world, BlockState currentState, BlockPos pos, StairShapeEnum newShape, int currentMetaData){ /// TRUE - gdy rzeczywiście potrzeba update.
        if(currentState.get(SHAPE) == newShape){
            return false;
        }else{
            world.setBlockStateWithoutNotifyingNeighbors(pos.x, pos.y, pos.z, currentState.with(SHAPE, newShape), currentMetaData);
            return true;
        }
    }

    @Override
    public void addIntersectingBoundingBox(World world, int i, int j, int k, Box box, ArrayList list)
    {
        if(world.getBlockMeta(i, j, k) < 4) {
            this.updateBoundingBoxExtra(world, i, j, k);
            super.addIntersectingBoundingBox(world, i, j, k, box, list);

            boolean var8 = this.createOuterCorners(world, i, j, k);
            super.addIntersectingBoundingBox(world, i, j, k, box, list);

            if (var8 && this.createInnerCorners(world, i, j, k)) {
                super.addIntersectingBoundingBox(world, i, j, k, box, list);
            }
        }

        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void updateBoundingBoxExtra(BlockView arg, int i, int j, int k){
        boolean flag = ((BlockStateView)arg).getBlockState(i,j,k).get(Properties.BOTTOM);
        if (!flag)
        {
            this.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }


    @Override
    public void onPlaced(World arg, int i, int j, int k, LivingEntity placer) {
        /// no vanilla logic
    }

    @Override
    public void onPlaced(World var1, int i, int j, int k, int l){
        /// no vanilla logic
    }

    public boolean isBlockPosStairs(World blockViev, BlockPos pos){
        return Block.BLOCKS[blockViev.getBlockId(pos.x, pos.y, pos.z)] instanceof StairsBlock && blockViev.getBlockMeta(pos.x, pos.y, pos.z) < 4;
    }

    public boolean isBlockPosUpperStairs(World blockView, BlockPos pos){
        return !blockView.getBlockState(pos).get(Properties.BOTTOM);
    }

    public Direction getBlockPosGeographicDir(World blockView, BlockPos pos){
        return blockView.getBlockState(pos).get(Properties.HORIZONTAL_FACING);
    }

    public boolean isStairsIdentical(World blockView, BlockState currentState, BlockPos pos){ /// Sprawdzam FACING & UPPER
        if(isBlockPosStairs(blockView, pos)) {
            BlockState neighborState = blockView.getBlockState(pos);
            return neighborState.get(Properties.BOTTOM) == currentState.get(Properties.BOTTOM) && neighborState.get(Properties.HORIZONTAL_FACING) == currentState.get(Properties.HORIZONTAL_FACING);
        }else{
            return false;
        }
    }

    @Override
    public boolean createOuterCorners(World world, int x, int y, int z) /// narożnik zewnętrzny - 3/4 przestrzeni to puste powietrze. NADPISUJĄCA METODA
    {
        BlockPos currentPos = new BlockPos(x, y, z);
        BlockState currentState = world.getBlockState(currentPos);
        Direction geographicDir = currentState.get(Properties.HORIZONTAL_FACING);
        boolean upperStairs = !currentState.get(Properties.BOTTOM);
        float minY = 0.5F;
        float maxY = 1.0F;
        if (upperStairs) /// Stopień jest na dole, dla górnych schodów
        {
            minY = 0.0F;
            maxY = 0.5F;
        }
        float minX = 0.0F;
        float maxX = 1.0F;
        float minZ = 0.0F;
        float maxZ = 0.5F;
        boolean flag = true;
        Direction neighborFACING;
        BlockPos neighborPos;
        if (geographicDir == Direction.EAST)
        {
            minX = 0.5F;
            maxZ = 1.0F;
            neighborPos = currentPos.add(Direction.EAST.getVector());

            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);

                if (neighborFACING == Direction.NORTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
                    maxZ = 0.5F;
                    flag = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
                    minZ = 0.5F;
                    flag = false;
                }
            }
        }
        else if (geographicDir == Direction.WEST)
        {
            maxX = 0.5F;
            maxZ = 1.0F;
            neighborPos = currentPos.add(Direction.WEST.getVector());

            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);

                if (neighborFACING == Direction.NORTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
                    maxZ = 0.5F;
                    flag = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
                    minZ = 0.5F;
                    flag = false;
                }
            }
        }
        else if (geographicDir == Direction.SOUTH)
        {
            minZ = 0.5F;
            maxZ = 1.0F;
            neighborPos = currentPos.add(Direction.SOUTH.getVector());

            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);

                if (neighborFACING == Direction.WEST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
                    maxX = 0.5F;
                    flag = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
                    minX = 0.5F;
                    flag = false;
                }
            }
        }
        else if (geographicDir == Direction.NORTH)
        {
            neighborPos = currentPos.add(Direction.NORTH.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);

                if (neighborFACING == Direction.WEST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
                    maxX = 0.5F;
                    flag = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
                    minX = 0.5F;
                    flag = false;
                }
            }
        }

        this.setBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
        return flag;
    }

    @Override
    public boolean createInnerCorners(World world, int x, int y, int z) /// narożnik wewnętrzny - 1/4 przestrzeni to puste powietrze.
    {
        BlockPos currentPos = new BlockPos(x, y, z);
        BlockState currentState = world.getBlockState(currentPos);
        Direction geographicDir = currentState.get(Properties.HORIZONTAL_FACING);
        boolean upperStairs = !currentState.get(Properties.BOTTOM);
        float minY = 0.5F;
        float maxY = 1.0F;
        if (upperStairs)  /// Stopień jest na dole, dla górnych schodów
        {
            minY = 0.0F;
            maxY = 0.5F;
        }
        float minX = 0.0F;
        float maxX = 0.5F;
        float minZ = 0.5F;
        float maxZ = 1.0F;
        boolean flag = false;
        Direction neighborFACING;
        BlockPos neighborPos;
        if (geographicDir == Direction.EAST)
        {
            neighborPos = currentPos.add(Direction.WEST.getVector());

            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);

                if (neighborFACING == Direction.NORTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
                    minZ = 0.0F;
                    maxZ = 0.5F;
                    flag = true;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
                    minZ = 0.5F;
                    maxZ = 1.0F;
                    flag = true;
                }
            }
        }
        else if (geographicDir == Direction.WEST)
        {
            neighborPos = currentPos.add(Direction.EAST.getVector());

            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                minX = 0.5F;
                maxX = 1.0F;
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);

                if (neighborFACING == Direction.NORTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
                    minZ = 0.0F;
                    maxZ = 0.5F;
                    flag = true;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
                    minZ = 0.5F;
                    maxZ = 1.0F;
                    flag = true;
                }
            }
        }
        else if (geographicDir == Direction.SOUTH)
        {
            neighborPos = currentPos.add(Direction.NORTH.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                minZ = 0.0F;
                maxZ = 0.5F;
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);

                if (neighborFACING == Direction.WEST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
                    flag = true;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
                    minX = 0.5F;
                    maxX = 1.0F;
                    flag = true;
                }
            }
        }
        else if (geographicDir == Direction.NORTH)
        {
            neighborPos = currentPos.add(Direction.SOUTH.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);
                if (neighborFACING == Direction.WEST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
                    flag = true;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
                    minX = 0.5F;
                    maxX = 1.0F;
                    flag = true;
                }
            }
        }
        if (flag)
        {
            this.setBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
        }
        return flag;
    }

    /// --- Investigate Stair Shape (blockState) section ---
    public int determineOuterShape(World world, int x, int y, int z, int meta) /// narożnik zewnętrzny - 3/4 przestrzeni to puste powietrze. NADPISUJĄCA METODA
    {
        BlockPos currentPos = new BlockPos(x, y, z);
        BlockState currentState = world.getBlockState(currentPos);
        Direction geographicDir = currentState.get(Properties.HORIZONTAL_FACING);
        boolean upperStairs = !currentState.get(Properties.BOTTOM);
        boolean flag = false;
        boolean flagStraighten = true;
        Direction neighborFACING;
        BlockPos neighborPos;
        if (geographicDir == Direction.EAST)
        {
            neighborPos = currentPos.add(Direction.EAST.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);
                if (neighborFACING == Direction.NORTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_LEFT, meta);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_RIGHT, meta);
                    flagStraighten = false;
                }
            }
        }
        else if (geographicDir == Direction.WEST)
        {
            neighborPos = currentPos.add(Direction.WEST.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);
                if (neighborFACING == Direction.NORTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_RIGHT, meta);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_LEFT, meta);
                    flagStraighten = false;
                }
            }
        }
        else if (geographicDir == Direction.SOUTH)
        {
            neighborPos = currentPos.add(Direction.SOUTH.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);
                if (neighborFACING == Direction.WEST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_RIGHT, meta);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_LEFT, meta);
                    flagStraighten = false;
                }
            }
        }
        else if (geographicDir == Direction.NORTH)
        {
            neighborPos = currentPos.add(Direction.NORTH.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);
                if (neighborFACING == Direction.WEST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_LEFT, meta);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_RIGHT, meta);
                    flagStraighten = false;
                }
            }
        }

        if (flagStraighten) return 0;
        return flag ? 2 : 1;
    }

    public boolean determineInnerShape(World world, int x, int y, int z, boolean flagS, int meta) /// narożnik zewnętrzny - 3/4 przestrzeni to puste powietrze.
    {
        BlockPos currentPos = new BlockPos(x, y, z);
        BlockState currentState = world.getBlockState(currentPos);
        Direction geographicDir = currentState.get(Properties.HORIZONTAL_FACING);
        boolean upperStairs = !currentState.get(Properties.BOTTOM);
        boolean flag = false;
        boolean flagStraighten = flagS;
        Direction neighborFACING;
        BlockPos neighborPos;
        if (geographicDir == Direction.EAST)
        {
            neighborPos = currentPos.add(Direction.WEST.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);
                if (neighborFACING == Direction.NORTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_LEFT, meta);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_RIGHT, meta);
                    flagStraighten = false;
                }
            }
        }
        else if (geographicDir == Direction.WEST)
        {
            neighborPos = currentPos.add(Direction.EAST.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);
                if (neighborFACING == Direction.NORTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_RIGHT, meta);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_LEFT, meta);
                    flagStraighten = false;
                }
            }
        }
        else if (geographicDir == Direction.SOUTH)
        {
            neighborPos = currentPos.add(Direction.NORTH.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);
                if (neighborFACING == Direction.WEST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_RIGHT, meta);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_LEFT, meta);
                    flagStraighten = false;
                }
            }
        }
        else if (geographicDir == Direction.NORTH)
        {
            neighborPos = currentPos.add(Direction.SOUTH.getVector());
            if (isBlockPosStairs(world, neighborPos) && upperStairs == isBlockPosUpperStairs(world, neighborPos))
            {
                neighborFACING = getBlockPosGeographicDir(world, neighborPos);
                if (neighborFACING == Direction.WEST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_LEFT, meta);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_RIGHT, meta);
                    flagStraighten = false;
                }
            }
        }
        if (flagStraighten)
        {
            /// Shape == Straight na pewno.
            flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.STRAIGHT, meta);
        }
        return flag;
    }

    @Inject(method = "dropStacks", at = @At("HEAD"), cancellable = true)
    public void dropStacks(World world, int x, int y, int z, int meta, float f, CallbackInfo ci) {
        if (!world.isRemote) {
            int droppedItemCount = this.getDroppedItemCount(world.random);

            for (int i = 0; i < droppedItemCount; ++i) {
                this.dropStack(world, x, y, z, new ItemStack(id, 1, this.getDroppedItemMeta(meta)));
            }
            ci.cancel();
        }
    }

}
