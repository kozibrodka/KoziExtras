package net.kozibrodka.extra.registryJson;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.modificationstation.stationapi.api.util.Identifier;

public class JsonOverrideRegistry {

    public static final Object2ObjectOpenHashMap<Identifier, String> modelOverrides = new Object2ObjectOpenHashMap<>();

    public static final Object2ObjectOpenHashMap<Identifier, Object2ObjectOpenHashMap<String, Identifier>> modelTextureOverrides = new Object2ObjectOpenHashMap<>();

    public static final Object2ObjectOpenHashMap<Identifier, ObjectArrayList<String>> blockstateOverrides = new Object2ObjectOpenHashMap<>();

    // Block Model Override
    public static void registerBlockModelOverride(Identifier blockIdentifier, String model) {
        Identifier identifier = Identifier.of(blockIdentifier.namespace + ":block/" + blockIdentifier.path);
        modelOverrides.put(identifier, model);
    }

    public static void registerBlockModelOverride(String blockIdentifier, String model) {
        registerBlockModelOverride(Identifier.of(blockIdentifier), model);
    }

    // Block Model Texture Override
    public static void registerBlockModelTextureOverride(Identifier blockIdentifier, String texture, Identifier textureIdentifier) {
        Identifier identifier = Identifier.of(blockIdentifier.namespace + ":block/" + blockIdentifier.path);

        Object2ObjectOpenHashMap<String, Identifier> blockModelTextures = modelTextureOverrides.computeIfAbsent(identifier, (id) -> new Object2ObjectOpenHashMap<>());
        blockModelTextures.put(texture, textureIdentifier);
    }

    public static void registerBlockModelTextureOverride(String blockIdentifier, String texture, Identifier textureIdentifier) {
        registerBlockModelTextureOverride(Identifier.of(blockIdentifier), texture, textureIdentifier);
    }

    // Blockstate Override
    public static void registerBlockstateOverride(Identifier blockIdentifier, String blockstate) {
        ObjectArrayList<String> blockEntries = blockstateOverrides.computeIfAbsent(blockIdentifier, (id) -> new ObjectArrayList<>());
        blockEntries.add(blockstate);
    }

    public static void registerBlockstateOverride(String blockIdentifier, String blockstate) {
        registerBlockstateOverride(Identifier.of(blockIdentifier), blockstate);
    }
}
