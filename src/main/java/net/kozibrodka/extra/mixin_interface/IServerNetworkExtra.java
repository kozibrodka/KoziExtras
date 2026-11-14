package net.kozibrodka.extra.mixin_interface;

import net.kozibrodka.extra.placementAPI.PlayerInteractBlockExtraPacket;

public interface IServerNetworkExtra {

    void onPlayerInteractBlockExtra(PlayerInteractBlockExtraPacket packet);
}
