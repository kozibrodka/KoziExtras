package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.block.item.BlockItemStairs;
import net.kozibrodka.extra.mixin_interface.BlockStairsInterface;
import net.kozibrodka.extra.utils.StairShapeEnum;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
        super(i, 8, arg);
    }

    @Unique
    private static final BooleanProperty UPPER = BooleanProperty.of("upper");
    @Unique
    private static final DirectionProperty FACING = DirectionProperty.of("facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    @Unique
    private static final EnumProperty<StairShapeEnum> SHAPE = EnumProperty.of("shape", StairShapeEnum.class);

    ///  ENUM PROPERRY? ^^ zrób swój Enum Stairs Shape.

//    @Override
//    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
//        BlockState currentS = world.getBlockState(x,y,z);
//        world.setBlockState(x,y,z, currentS.with(SHAPE, StairShapeEnum.INNER_LEFT));
////        currentS.with(SHAPE, StairShapeEnum.INNER_LEFT);
//        /// DEBUG DEV
//        return false;
//    }

//    @Override
//    public int getTexture(int side) {
//        return 8;
//    }

    @Override
    public int getTexture(int side, int meta) {
        return 8;
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(UPPER);
        builder.add(FACING);
        builder.add(SHAPE);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructorEnd(CallbackInfo ci) {
        setDefaultState(getDefaultState()
                .with(UPPER, false)
                .with(FACING, Direction.NORTH)
                .with(SHAPE, StairShapeEnum.STRAIGHT)
        );
    }

    @Override
    public void onPlacedStairsExtra(World world, int i, int j, int k, boolean upper, Direction geoFacing, int oldMeta) {
        /// on ItemBlock use
        BlockState currentState = world.getBlockState(i, j, k);
        world.setBlockStateWithoutNotifyingNeighbors(i, j, k, currentState.with(FACING, geoFacing).with(UPPER, upper));
        world.setBlockMeta(i, j, k, oldMeta); //TODO tutaj jedyne wywołanie updateNeighbors

//        this.neighborUpdate(world, i, j, k, this.id);
        this.investigateStairShape(world, i, j, k);
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        if(id == 0 || Block.BLOCKS[id] instanceof StairsBlock){
            investigateStairShape(world, x, y, z);
            /// tylko w tym przypadku będę sprawdzał raczej.
        }
        System.out.println("SĄSIEDNI_UPDATE " + id);
//        investigateStairShape(world, x, y, z); //todo
        //TODO crash PISTON - ale w Render.
    }

    @Unique
    public void investigateStairShape(World world, int i, int j, int k)
    {
        int currentMeta = world.getBlockMeta(i, j, k);

        boolean flagS = true;

        boolean naroznik_zewnetrzny = false;
        boolean naroznik_wewnetrzny = false;
//        boolean naroznik_zewnetrzny = determineOuterShape(world, i, j, k); /// false = niestworzony.

        int naro_zewn_info = determineOuterShape(world, i, j, k); /// 0 - BRAK KSZTAŁTU,  1 - UTRWALENIE KSZTAŁTU,  2 - NOWY KSZTAŁT
        if(naro_zewn_info > 0){
            flagS = false;
            if(naro_zewn_info > 1){
                naroznik_zewnetrzny = true;
            }
        }

        if (!naroznik_zewnetrzny)
        {
            naroznik_wewnetrzny = determineInnerShape(world, i, j, k, flagS); /// false = niestworzony.
        }

        if(naroznik_wewnetrzny || naroznik_zewnetrzny){ /// Gdy SHAPE został zmieniony.
            world.setBlockMetaWithoutNotifyingNeighbors(i, j, k, currentMeta);
        }

        //TODO stworzyć metody dla ustawiania blockStatesów ^^ osobne.   investigateStairShape ma być odpalane tylko na neighborupdate.
    }

    public boolean tryUpdateShapeState(World world, BlockState currentState, BlockPos pos, StairShapeEnum newShape){ /// TRUE - gdy rzeczywiście potrzeba update.
        if(currentState.get(SHAPE) == newShape){
            return false;
        }else{
            world.setBlockStateWithoutNotifyingNeighbors(pos, currentState.with(SHAPE, newShape));
            return true;
        }
    }

    @Override
    public void addIntersectingBoundingBox(World world, int i, int j, int k, Box box, ArrayList list)
    {
        this.updateBoundingBox1(world, i, j, k);
        super.addIntersectingBoundingBox(world, i, j, k, box, list);

        boolean var8 = this.createOuterCorners(world, i, j, k);
        super.addIntersectingBoundingBox(world, i, j, k, box, list);

        if (var8 && this.createInnerCorners(world, i, j, k))
        {
            super.addIntersectingBoundingBox(world, i, j, k, box, list);
        }

        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void updateBoundingBox1(BlockView arg, int i, int j, int k){
        boolean flag = ((BlockStateView)arg).getBlockState(i,j,k).get(UPPER);
//        int meta = arg.getBlockMeta(i, j, k);
//        if ((meta & 4) != 0)
        if (flag)
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

    private boolean isStairsIdentical(BlockView blockviev, int x, int y, int z, int par5)
    {
        int var6 = blockviev.getBlockId(x, y, z);
        return isBlockStairsID(var6) && blockviev.getBlockMeta(x, y, z) == par5;
    }

    public boolean isBlockStairsID(int par0)
    {
        return par0 > 0 && Block.BLOCKS[par0] instanceof StairsBlock;
    }

    //TODO getRid of BlockVievs...

    public boolean isBlockPosStairs(BlockView blockViev, BlockPos pos){
        return Block.BLOCKS[blockViev.getBlockId(pos.x, pos.y, pos.z)] instanceof StairsBlock;
    }

    public boolean isBlockPosUpperStairs(BlockView blockView, BlockPos pos){
        return ((BlockStateView)blockView).getBlockState(pos).get(UPPER);
    }

    public Direction getBlockPosGeographicDir(BlockView blockView, BlockPos pos){
        return ((BlockStateView)blockView).getBlockState(pos).get(FACING);
    }

    public boolean isStairsIdentical(BlockView blockView, BlockState currentState, BlockPos pos){ /// Sprawdzam FACING & UPPER
        if(isBlockPosStairs(blockView, pos)) {
            BlockState neighborState = ((BlockStateView) blockView).getBlockState(pos);
            return neighborState.get(UPPER) == currentState.get(UPPER) && neighborState.get(FACING) == currentState.get(FACING);
        }else
        {
            return false;
        }
    }

    //TODO!!! get rid of blockViev kurwa... jak ogarne render...

    @Override
    public boolean createOuterCorners(BlockView world, int x, int y, int z) /// narożnik zewnętrzny - 3/4 przestrzeni to puste powietrze. NADPISUJĄCA METODA
    {
        BlockPos currentPos = new BlockPos(x, y, z);
        BlockState currentState = ((BlockStateView)world).getBlockState(currentPos);
        Direction geographicDir = currentState.get(FACING);
        boolean upperStairs = currentState.get(UPPER);
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
    public boolean createInnerCorners(BlockView world, int x, int y, int z) /// narożnik wewnętrzny - 1/4 przestrzeni to puste powietrze.
    {
        BlockPos currentPos = new BlockPos(x, y, z);
        BlockState currentState = ((BlockStateView)world).getBlockState(currentPos);
        Direction geographicDir = currentState.get(FACING);
        boolean upperStairs = currentState.get(UPPER);
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
    public int determineOuterShape(World world, int x, int y, int z) /// narożnik zewnętrzny - 3/4 przestrzeni to puste powietrze. NADPISUJĄCA METODA
    {
        BlockPos currentPos = new BlockPos(x, y, z);
        BlockState currentState = world.getBlockState(currentPos);
        Direction geographicDir = currentState.get(FACING);
        boolean upperStairs = currentState.get(UPPER);
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
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.OUTER_LEFT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_LEFT);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.OUTER_RIGHT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_RIGHT);
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
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.OUTER_RIGHT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_RIGHT);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.NORTH.getVector())))
                {
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.OUTER_LEFT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_LEFT);
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
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.OUTER_RIGHT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_RIGHT);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.OUTER_LEFT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_LEFT);
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
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.OUTER_LEFT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_LEFT);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.WEST.getVector())))
                {
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.OUTER_RIGHT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.OUTER_RIGHT);
                    flagStraighten = false;
                }
            }
        }

        if(flagStraighten && !flag){ /// TODO żeby ładnie było...
            return 0;
        }else if(flagStraighten == false && !flag){
            return 1;
        }else if(flagStraighten == false && flag){
            return 2;
        }
        return 0;
    }

    public boolean determineInnerShape(World world, int x, int y, int z, boolean flagS) /// narożnik zewnętrzny - 3/4 przestrzeni to puste powietrze.
    {
        BlockPos currentPos = new BlockPos(x, y, z);
        BlockState currentState = world.getBlockState(currentPos);
        Direction geographicDir = currentState.get(FACING);
        boolean upperStairs = currentState.get(UPPER);
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
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.INNER_LEFT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_LEFT);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.INNER_RIGHT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_RIGHT);
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
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.INNER_RIGHT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_RIGHT);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.SOUTH && !isStairsIdentical(world, currentState, currentPos.add(Direction.SOUTH.getVector())))
                {
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.INNER_LEFT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_LEFT);
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
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.INNER_RIGHT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_RIGHT);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.INNER_LEFT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_LEFT);
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
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.INNER_LEFT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_LEFT);
                    flagStraighten = false;
                }
                else if (neighborFACING == Direction.EAST && !isStairsIdentical(world, currentState, currentPos.add(Direction.EAST.getVector())))
                {
//                    world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.INNER_RIGHT));
//                    flag = true;
                    flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.INNER_RIGHT);
                    flagStraighten = false;
                }
            }
        }
        if (flagStraighten)
        {
//            world.setBlockStateWithoutNotifyingNeighbors(currentPos, currentState.with(SHAPE, StairShapeEnum.STRAIGHT));
//            flag = true;
            /// Shape == Straight na pewno.
            flag = tryUpdateShapeState(world, currentState, currentPos, StairShapeEnum.STRAIGHT);
        }
        return flag;
    }

}
