package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.Item;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.recipe.*;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Comparator;

@Mixin(RecipeRegistry.class)
public abstract class RecipeRegistryMixin {

    @Shadow abstract void addShapelessRecipe(ItemInstance arg, Object... objects);

    @Shadow abstract void addShapedRecipe(ItemInstance arg, Object... objects);

//    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/RecipeRegistry;addShapedRecipe(Lnet/minecraft/item/ItemInstance;[Ljava/lang/Object;)V", ordinal = 23))
//    private void injected(RecipeRegistry instance, ItemInstance objects, Object[] objects1) {
//        addShapedRecipe(new ItemInstance(BlockBase.WOOD, 4), "#", '#', new ItemInstance(BlockBase.LOG, 1, 0));
//    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/RecipeRegistry;addShapedRecipe(Lnet/minecraft/item/ItemInstance;[Ljava/lang/Object;)V", ordinal = 23))
    private void injected(RecipeRegistry instance, ItemInstance objects, Object[] objects1) {

    }

//    @ModifyVariable(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/RecipeRegistry;addShapedRecipe(Lnet/minecraft/item/ItemInstance;[Ljava/lang/Object;)V", ordinal = 0))
//    private BlockBase injected(BlockBase blockbase) {
//        return BlockBase.SNOW;
//    }

//    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockBase;LOG:Lnet/minecraft/block/BlockBase;", ordinal = 0))
//    private BlockBase injected(BlockBase blockbase) {
//
//    }

//    @ModifyArg(method = "<init>", at = @At(value = "INVOKE",target = "Lnet/minecraft/recipe/RecipeRegistry;addShapedRecipe(Lnet/minecraft/item/ItemInstance;[Ljava/lang/Object;)V", ordinal = 10))
//    private static ItemInstance injected(ItemInstance x) {
//        return new ItemInstance(BlockBase.LOG, 1, 0);
//    }

//    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 0, ordinal=0))
//    private int injected(int value) {
//        return ++value;
//    }

//    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/RecipeRegistry;addShapedRecipe(Lnet/minecraft/item/ItemInstance;[Ljava/lang/Object;)V", ordinal = 1))
//    private void injected(CallbackInfo ci) {
//        this.addShapedRecipe(new ItemInstance(BlockBase.WOOD, 4), "#", '#', new ItemInstance(BlockBase.LOG, 1, 0));
//        this.addShapedRecipe(new ItemInstance(BlockListener.birchplanks, 4), "#", '#', new ItemInstance(BlockBase.LOG, 1, 1));
//        this.addShapedRecipe(new ItemInstance(BlockListener.birchplanks, 4), "#", '#', new ItemInstance(BlockBase.LOG, 1, 2));
//    }


}
