package net.kozibrodka.extra.events;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.extra.glasscfg.ExtrasCFG;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class LoadingLogicMixinPlugin implements IMixinConfigPlugin {


    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        if (mixinClassName.equals("net.kozibrodka.extra.mixin.stairs.BlockStairsMixin")) {
            return isConfigBooleanTrue("modern_stairs");
        } else if(mixinClassName.equals("net.kozibrodka.extra.mixin.stairs.DummyStairsMixin")){
            return !isConfigBooleanTrue("modern_stairs");
        } else if(mixinClassName.equals("net.kozibrodka.extra.mixin.slab.SlabBlockItemMixin") || mixinClassName.equals("net.kozibrodka.extra.mixin.slab.SlabBlockMixin")){
            return isConfigBooleanTrue("modern_slabs");
        }else if(mixinClassName.equals("net.kozibrodka.extra.mixin.slab.DummySlabMixin") || mixinClassName.equals("net.kozibrodka.extra.mixin.slab.DummSlabItemMixin")){
            return !isConfigBooleanTrue("modern_slabs");
        }else
//        if(){
//
//        }else
        {
            return true;
        }
    }


    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }



    private boolean isConfigBooleanTrue(String entryName) {
        Path configPath = FabricLoader.getInstance()
                .getConfigDir()
                .resolve("extra")
                .resolve("ExtrasCFG.yml");

        if (!Files.exists(configPath)) {
            return true;
        }

        try {
            List<String> lines = Files.readAllLines(configPath);
            for (String line : lines) {
                String trimmed = line.trim();

                if (trimmed.startsWith(entryName+":")) {
                    String value = trimmed.substring((entryName+":").length()).trim();
                    return Boolean.parseBoolean(value);
                }
            }
        } catch (Exception e) {
            System.err.println("[Release Tweaks] Unable to load ExtrasCFG.yml: " + e.getMessage());
        }

        return true;
    }


}

