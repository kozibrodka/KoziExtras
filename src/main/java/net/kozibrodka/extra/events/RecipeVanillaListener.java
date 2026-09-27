package net.kozibrodka.extra.events;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;

public class RecipeVanillaListener {


    @EventListener(priority = ListenerPriority.HIGH)
    public void registerRecipes(RecipeRegisterEvent event) {
            RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);

            switch (type != null ? type : RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED) {
                case CRAFTING_SHAPED -> {
                    registerShapedRecipes();
                }

                case CRAFTING_SHAPELESS -> {
//                    registerShapelessRecipes();
                }

                case SMELTING -> {

                }
            }
    }

    public static void registerShapedRecipes() {
        /* Modern Recipes */
        // Slabs Craft 6 per Craft
        if (!FabricLoader.getInstance().isModLoaded("unitweaks")) {
//            CraftingHelper.removeRecipe(Block.SLAB);
//            CraftingHelper.addShapedRecipe(new ItemStack(Block.SLAB, 6, 0), "XXX", 'X', new ItemStack(Block.STONE, 1));
//            CraftingHelper.addShapedRecipe(new ItemStack(Block.SLAB, 6, 1), "XXX", 'X', new ItemStack(Block.SANDSTONE, 1));
//            CraftingHelper.addShapedRecipe(new ItemStack(Block.SLAB, 6, 2), "XXX", 'X', new ItemStack(Block.PLANKS, 1));
//            CraftingHelper.addShapedRecipe(new ItemStack(Block.SLAB, 6, 3), "XXX", 'X', new ItemStack(Block.COBBLESTONE, 1));
        }
    }

}
