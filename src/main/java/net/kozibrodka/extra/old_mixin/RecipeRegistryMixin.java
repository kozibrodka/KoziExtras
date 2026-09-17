package net.kozibrodka.extra.old_mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(CraftingRecipeManager.class)
public abstract class RecipeRegistryMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/CraftingRecipeManager;addShapedRecipe(Lnet/minecraft/item/ItemStack;[Ljava/lang/Object;)V", ordinal = 23))
    private void injected(CraftingRecipeManager instance, ItemStack objects, Object[] objects1) {

    }

}
