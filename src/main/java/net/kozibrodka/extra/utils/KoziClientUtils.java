package net.kozibrodka.extra.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class KoziClientUtils {

    public static Minecraft minecraft = (Minecraft) FabricLoader.getInstance().getGameInstance();
}
