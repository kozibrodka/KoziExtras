package net.kozibrodka.extra.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class RecipeListener {

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        CraftingRegistry.addShapelessRecipe(new ItemInstance(BlockListener.birchplanks, 4), new ItemInstance(BlockBase.LOG, 1,2));
        CraftingRegistry.addShapelessRecipe(new ItemInstance(BlockListener.spruceplanks, 4), new ItemInstance(BlockBase.LOG, 1,1));
        CraftingRegistry.addShapelessRecipe(new ItemInstance(BlockBase.WOOD, 4), new ItemInstance(BlockBase.LOG, 1,0));
    }
}
