package net.kozibrodka.extra.events;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.kozibrodka.extra.glasscfg.ExtrasCFG;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class ConfigListener {

    @ConfigRoot(value = "ExtrasCFG", visibleName = "Kozi Extras Config")
    public static final ExtrasCFG exGlass = new ExtrasCFG();

    @Entrypoint.Namespace
    public static Namespace MOD_ID;
}
