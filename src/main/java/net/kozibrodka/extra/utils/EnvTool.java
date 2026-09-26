package net.kozibrodka.extra.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class EnvTool {
    public static boolean isEnvServ(){
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    public static boolean isEnvClient(){
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
