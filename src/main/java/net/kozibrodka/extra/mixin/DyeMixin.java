package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.events.BlockListener;
import net.kozibrodka.extra.farming.BlockCocoa;
import net.kozibrodka.extra.farming.BlockJungleSapling;
import net.kozibrodka.extra.farming.BlockStem;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Crops;
import net.minecraft.block.Sapling;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.Dye;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Dye.class)
public class DyeMixin extends ItemBase {

    protected DyeMixin(int i) {
        super(i);
        this.setHasSubItems(true);
        this.setDurability(0);
    }

    @Inject(method = "useOnTile", at = @At("HEAD"), cancellable = true)
    private void injected(ItemInstance arg, PlayerBase arg2, Level arg3, int i, int j, int k, int l, CallbackInfoReturnable<Boolean> cir) {
        if(useOnTile2(arg, arg2, arg3, i, j, k, l))
            cir.setReturnValue(true);
    }

    public boolean useOnTile2(ItemInstance arg, PlayerBase arg2, Level arg3, int i, int j, int k, int l)
    {
        if (arg.getDamage() == 15) {
            int var8 = arg3.getTileId(i, j, k);

            if (var8 == BlockListener.watermelonsten.id) {
                if (!arg3.isServerSide) {
                    ((BlockStem)BlockListener.watermelonsten).fertilizeStem(arg3, i, j, k);
                    --arg.count;
                }

                return true;
            }
            if (var8 == BlockListener.pumpkinsten.id) {
                if (!arg3.isServerSide) {
                    ((BlockStem)BlockListener.pumpkinsten).fertilizeStem(arg3, i, j, k);
                    --arg.count;
                }

                return true;
            }
            if (var8 == BlockListener.cocoaplant.id) {
                if (!arg3.isServerSide) {
                    ((BlockCocoa)BlockListener.cocoaplant).fertilizeCocoa(arg3, i, j, k);
                    --arg.count;
                }

                return true;
            }

            if (var8 == BlockListener.junglesapling.id) {
                if (!arg3.isServerSide) {
                    ((BlockJungleSapling)BlockListener.junglesapling).growTree(arg3, i, j, k, arg3.rand);
                    --arg.count;
                }

                return true;
            }

        }
        else if (arg.getDamage() == 3)
        {
            int var11 = arg3.getTileId(i, j, k);

            if (var11 == BlockListener.junglewood.id)
            {
                if (l == 0)
                {
                    return false;
                }

                if (l == 1)
                {
                    return false;
                }

                if (l == 2)
                {
                    --k;
                }

                if (l == 3)
                {
                    ++k;
                }

                if (l == 4)
                {
                    --i;
                }

                if (l == 5)
                {
                    ++i;
                }

                if (arg3.isAir(i, j, k))
                {
                    arg3.setTile(i, j, k, BlockListener.cocoaplant.id);
                    ((BlockCocoa)BlockListener.cocoaplant).onBlockPlaced(arg3, i, j, k, l);
                    --arg.count;
                }

                return true;
            }
        }

        return false;
    }

//    @Override
//    public boolean useOnTile(ItemInstance arg, PlayerBase arg2, Level arg3, int i, int j, int k, int l) {
//        if (arg.getDamage() == 15) {
//            int var8 = arg3.getTileId(i, j, k);
//            if (var8 == BlockBase.SAPLING.id) {
//                if (!arg3.isServerSide) {
//                    ((Sapling)BlockBase.SAPLING).growTree(arg3, i, j, k, arg3.rand);
//                    --arg.count;
//                }
//
//                return true;
//            }
//
//            if (var8 == BlockBase.CROPS.id) {
//                if (!arg3.isServerSide) {
//                    ((Crops)BlockBase.CROPS).growCropInstantly(arg3, i, j, k);
//                    --arg.count;
//                }
//
//                return true;
//            }
//
//            if (var8 == BlockListener.watermelonsten.id) {
//                if (!arg3.isServerSide) {
//                    ((BlockStem)BlockListener.watermelonsten).fertilizeStem(arg3, i, j, k);
//                    --arg.count;
//                }
//
//                return true;
//            }
//            if (var8 == BlockListener.pumpkinsten.id) {
//                if (!arg3.isServerSide) {
//                    ((BlockStem)BlockListener.pumpkinsten).fertilizeStem(arg3, i, j, k);
//                    --arg.count;
//                }
//
//                return true;
//            }
//            if (var8 == BlockListener.cocoaplant.id) {
//                if (!arg3.isServerSide) {
//                    ((BlockCocoa)BlockListener.cocoaplant).fertilizeCocoa(arg3, i, j, k);
//                    --arg.count;
//                }
//
//                return true;
//            }
//
//            if (var8 == BlockBase.GRASS.id) {
//                if (!arg3.isServerSide) {
//                    --arg.count;
//
//                    label53:
//                    for(int var9 = 0; var9 < 128; ++var9) {
//                        int var10 = i;
//                        int var11 = j + 1;
//                        int var12 = k;
//
//                        for(int var13 = 0; var13 < var9 / 16; ++var13) {
//                            var10 += rand.nextInt(3) - 1;
//                            var11 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
//                            var12 += rand.nextInt(3) - 1;
//                            if (arg3.getTileId(var10, var11 - 1, var12) != BlockBase.GRASS.id || arg3.canSuffocate(var10, var11, var12)) {
//                                continue label53;
//                            }
//                        }
//
//                        if (arg3.getTileId(var10, var11, var12) == 0) {
//                            if (rand.nextInt(10) != 0) {
//                                arg3.placeBlockWithMetaData(var10, var11, var12, BlockBase.TALLGRASS.id, 1);
//                            } else if (rand.nextInt(3) != 0) {
//                                arg3.setTile(var10, var11, var12, BlockBase.DANDELION.id);
//                            } else {
//                                arg3.setTile(var10, var11, var12, BlockBase.ROSE.id);
//                            }
//                        }
//                    }
//                }
//
//                return true;
//            }
//        }
//        else if (arg.getDamage() == 3)
//        {
//            int var11 = arg3.getTileId(i, j, k);
//
//            if (var11 == BlockListener.junglewood.id)
//            {
//                if (l == 0)
//                {
//                    return false;
//                }
//
//                if (l == 1)
//                {
//                    return false;
//                }
//
//                if (l == 2)
//                {
//                    --k;
//                }
//
//                if (l == 3)
//                {
//                    ++k;
//                }
//
//                if (l == 4)
//                {
//                    --i;
//                }
//
//                if (l == 5)
//                {
//                    ++i;
//                }
//
//                if (arg3.isAir(i, j, k))
//                {
//                    arg3.setTile(i, j, k, BlockListener.cocoaplant.id);
//                    ((BlockCocoa)BlockListener.cocoaplant).onBlockPlaced(arg3, i, j, k, l);
//                        --arg.count;
//                }
//
//                return true;
//            }
//        }
//
//        return false;
//    }

}
