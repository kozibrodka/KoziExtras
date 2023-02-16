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

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/RecipeRegistry;addShapedRecipe(Lnet/minecraft/item/ItemInstance;[Ljava/lang/Object;)V", ordinal = 23))
    private void injected(RecipeRegistry instance, ItemInstance objects, Object[] objects1) {

    }

}
