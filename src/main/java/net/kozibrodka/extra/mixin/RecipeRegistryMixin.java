package net.kozibrodka.extra.mixin;

import net.kozibrodka.extra.events.BlockListener;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Comparator;

@Mixin(CraftingRecipeManager.class)
public abstract class RecipeRegistryMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/CraftingRecipeManager;addShapedRecipe(Lnet/minecraft/item/ItemStack;[Ljava/lang/Object;)V", ordinal = 23))
    private void injected(CraftingRecipeManager instance, ItemStack objects, Object[] objects1) {

    }

}
