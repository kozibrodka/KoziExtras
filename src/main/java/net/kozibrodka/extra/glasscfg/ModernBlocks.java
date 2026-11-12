package net.kozibrodka.extra.glasscfg;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class ModernBlocks {

    @ConfigEntry(name="§5Modern Stairs", description = "Stairs function as in modern minecraft, invisible stairs still work", requiresRestart = true)
    public Boolean modern_stairs = true;

    @ConfigEntry(name="§5Modern Slabs", description = "Slabs function as in modern minecraft, crash slabs still work", requiresRestart = true)
    public Boolean modern_slabs = true;
}
