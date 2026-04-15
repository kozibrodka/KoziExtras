package net.kozibrodka.extra.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;


public class RecipeListener {

    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        CraftingRegistry.addShapelessRecipe(new ItemStack(Block.PLANKS, 4), new ItemStack(Block.LOG,1,0));
        CraftingRegistry.addShapelessRecipe(new ItemStack(BlockListener.spruceplanks, 4), new ItemStack(Block.LOG,1,1));
        CraftingRegistry.addShapelessRecipe(new ItemStack(BlockListener.birchplanks, 4), new ItemStack(Block.LOG,1,2));
        CraftingRegistry.addShapelessRecipe(new ItemStack(BlockListener.jungleplanks, 4), new ItemStack(Block.LOG,1,3));
    }
}
