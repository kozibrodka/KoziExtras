package net.kozibrodka.extra.registryJson.init;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kozibrodka.extra.events.JsonListener;
import net.kozibrodka.extra.registryJson.JsonOverrideRegistry;
import net.kozibrodka.extra.registryJson.TemplateModelRegistry;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.render.model.ModelLoader;
import net.modificationstation.stationapi.api.event.resource.RuntimeResourcesEvent;
import net.modificationstation.stationapi.api.resource.RuntimeResourcePack;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Map;

public class RuntimeResourcesListener {
    @EventListener
    public void registerTemplateBlockstates(RuntimeResourcesEvent.Assets event) {

//        TemplateModelRegistry.registerStairs(Identifier.of("cobblestone_stairs"), Identifier.of("block/oak_planks"));
        JsonListener.registerJsonStatesAndModels();

        RuntimeResourcePack.Scope blockState = event.with(ModelLoader.BLOCK_STATES_FINDER);
        for (Map.Entry<Identifier, ObjectArrayList<String>> entry : JsonOverrideRegistry.blockstateOverrides.entrySet()) {
            for (String jsonString : entry.getValue()) {
                blockState.add(entry.getKey(), jsonString);
            }
        }
    }
}
