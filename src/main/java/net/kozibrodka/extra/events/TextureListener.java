package net.kozibrodka.extra.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class TextureListener {

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    public static int planks_birch;
    public static int planks_spruce;
    public static int planks_jungle;
    public static int stone_bricks_beta;
    public static int log_spruce_top;
    public static int log_birch_top;
    public static int log_jungle_top;
    public static int log_jungle_side;
    public static int stone_brick;
    public static int stone_brick_mossy;
    public static int nether_brick;
    public static int quartz_top;
    public static int quartz_bottom;
    public static int quartz_side;


    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        planks_birch = registerBlockTexture("block/wood_birch");
        planks_spruce = registerBlockTexture("block/wood_spruce");
        planks_jungle = registerBlockTexture("block/wood_jungle");
        stone_bricks_beta = registerBlockTexture("block/stone_bricks");
        log_spruce_top = registerBlockTexture("block/log_spruce_top");
        log_birch_top = registerBlockTexture("block/log_birch_top");
        log_jungle_top = registerBlockTexture("block/log_jungle_top");
        log_jungle_side = registerBlockTexture("block/tree_jungle");
        stone_brick = registerBlockTexture("block/stonebrick");
        stone_brick_mossy = registerBlockTexture("block/stonebrick_mossy");
        nether_brick = registerBlockTexture("block/nether_brick");
        quartz_top = registerBlockTexture("block/quartz_block_top");
        quartz_bottom = registerBlockTexture("block/quartz_block_bottom");
        quartz_side = registerBlockTexture("block/quartz_block_side");
    }

    private int registerBlockTexture(String s) {
        if(s == null) {
            return 0;
        }
        return Atlases.getStationTerrain().addTexture(Identifier.of(MOD_ID, s)).index;
    }
}
