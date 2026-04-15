package net.kozibrodka.extra.events;

import net.kozibrodka.extra.blocksCosmetic.*;
import net.kozibrodka.extra.blocksSimple.*;
import net.kozibrodka.extra.farming.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateSeedsItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.block.*;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class BlockListener {

    @Entrypoint.Namespace
    public static  Namespace MOD_ID = Null.get();

    @EventListener
    public void registerBlocks(BlockRegistryEvent event){

        spruceplanks = new BlockSprucePlanks(Identifier.of(MOD_ID, "spruceplanks"), Material.WOOD).setTranslationKey(MOD_ID, "spruceplanks").setHardness(2.0F).setResistance(5.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        birchplanks = new BlockBirchPlanks(Identifier.of(MOD_ID, "birchplanks"), Material.WOOD).setTranslationKey(MOD_ID, "birchplanks").setHardness(2.0F).setResistance(5.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        jungleplanks = new BlockJunglePlanks(Identifier.of(MOD_ID, "jungleplanks"), Material.WOOD).setTranslationKey(MOD_ID, "jungleplanks").setHardness(2.0F).setResistance(5.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        stonebricks = new BlockStoneBricks(Identifier.of(MOD_ID, "stonebricks"), Material.STONE).setTranslationKey(MOD_ID, "stonebricks").setHardness(1.5F).setResistance(10.0F).setSoundGroup(Block.DEFAULT_SOUND_GROUP);
        stonemossybricks = new BlockMossyStoneBricks(Identifier.of(MOD_ID, "stonemossybricks"), Material.STONE).setTranslationKey(MOD_ID, "stonemossybricks").setHardness(1.5F).setResistance(10.0F).setSoundGroup(Block.DEFAULT_SOUND_GROUP);
        netherbricks = new BlockNetherBricks(Identifier.of(MOD_ID, "netherbricks"), Material.STONE).setTranslationKey(MOD_ID, "netherbricks").setHardness(2.0F).setResistance(10.0F).setSoundGroup(Block.DEFAULT_SOUND_GROUP);
        quartzblock = new BlockQuartz(Identifier.of(MOD_ID, "quartzblock"), Material.STONE).setTranslationKey(MOD_ID, "quartzblock").setHardness(0.8F).setSoundGroup(Block.DEFAULT_SOUND_GROUP);

        wooden_slab_extra = new BlockWoodenSlabExtra(Identifier.of(MOD_ID, "wooden_slab_extra"), Material.WOOD, false).setTranslationKey(MOD_ID, "wooden_slab_extra").setHardness(2.0F).setResistance(10.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        double_wooden_slab_extra = new BlockWoodenSlabExtra(Identifier.of(MOD_ID, "double_wooden_slab_extra"), Material.WOOD, true).setTranslationKey(MOD_ID, "double_wooden_slab_extra").setHardness(2.0F).setResistance(10.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
//        stoneslab1337 = new BlockSlabExtra(Identifier.of(MOD_ID, "stoneslab1337"), Material.STONE, birchplanks).setTranslationKey(MOD_ID, "stoneslab1337");
        fence1337 = (TemplateFenceBlock) new BlockFenceExtra(Identifier.of(MOD_ID, "stoneslab1337"), 0, birchplanks).setTranslationKey(MOD_ID, "stoneslab1337");

//        stonebricks = new TemplateBlock(Identifier.of(MOD_ID, "stonebricks"), Material.STONE).setTranslationKey(MOD_ID, "stonebricks").setHardness(1.5F).setBlastResistance(10.0F).setSoundGroup(Block.STONE_SOUNDS);
//        birchplanksjson = new TemplateBlock (Identifier.of(MOD_ID, "birchplanksjson")).setTranslationKey(MOD_ID, "birchplanksjson").setHardness(2.0F).setResistance(5.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
//        spruceplanks = new TemplateBlock(Identifier.of(MOD_ID, "spruceplanks"), Material.WOOD).setTranslationKey(MOD_ID, "spruceplanks").setHardness(2.0F).setBlastResistance(5.0F).setSoundGroup(Block.WOOD_SOUNDS);
//        jungleplanks = new TemplateBlock(Identifier.of(MOD_ID, "jungleplanks"), Material.WOOD).setTranslationKey(MOD_ID, "jungleplanks").setHardness(2.0F).setBlastResistance(5.0F).setSoundGroup(Block.WOOD_SOUNDS);
        watermelon = (TemplatePumpkinBlock) new TemplatePumpkinBlock(Identifier.of(MOD_ID, "watermelon"), 1, false).setTranslationKey(MOD_ID, "watermelon").setHardness(1.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        junglewood = new BlockTreeJungle(Identifier.of(MOD_ID, "junglewood"), Material.WOOD).setTranslationKey(MOD_ID, "junglewood").setHardness(2.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        jungleleaves = new BlockLeavesJungle(Identifier.of(MOD_ID, "jungleleaves"), Material.LEAVES).setTranslationKey(MOD_ID, "jungleleaves").setHardness(0.2F).setOpacity(1).setSoundGroup(Block.DIRT_SOUND_GROUP);
        brickstairs = (TemplateStairsBlock) new TemplateStairsBlock(Identifier.of(MOD_ID, "brickstairs"), jungleplanks).setTranslationKey(MOD_ID, "brickstairs");

        pumpkinsten = (TemplatePlantBlock) new BlockStem(Identifier.of(MOD_ID, "pumpkinsten"), 1, Block.PUMPKIN).setTranslationKey(MOD_ID, "pumpkinsten").setHardness(0.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        watermelonsten = (TemplatePlantBlock) new BlockStem(Identifier.of(MOD_ID, "watermelonsten"), 1, watermelon).setTranslationKey(MOD_ID, "watermelonsten").setHardness(0.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        pumpkinseeds = (TemplateSeedsItem) new TemplateSeedsItem(Identifier.of(MOD_ID, "pumpkinseeds"), pumpkinsten.id).setTranslationKey(MOD_ID, "pumpkinseeds");
        watermelonseeds = (TemplateSeedsItem) new TemplateSeedsItem(Identifier.of(MOD_ID, "watermelonseeds"), watermelonsten.id).setTranslationKey(MOD_ID, "watermelonseeds");
        junglesapling = (TemplatePlantBlock) new BlockJungleSapling(Identifier.of(MOD_ID, "junglesapling"), 1).setTranslationKey(MOD_ID, "junglesapling").setHardness(0.0F).setSoundGroup(Block.DIRT_SOUND_GROUP);
        cocoaplant = (TemplatePlantBlock) new BlockCocoa(Identifier.of(MOD_ID, "cocoaplant"), 1).setTranslationKey(MOD_ID, "cocoaplant").setHardness(0.2F).setResistance(5.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        waterlily = (TemplatePlantBlock) new BlockLilyPad(Identifier.of(MOD_ID, "waterlily"), 2).setTranslationKey(MOD_ID, "waterlily").setHardness(0.0F).setSoundGroup(Block.DIRT_SOUND_GROUP);
        vine = new BlockVine(Identifier.of(MOD_ID, "vine"), Material.PLANT).setTranslationKey(MOD_ID, "vine").setHardness(0.2F).setSoundGroup(Block.DIRT_SOUND_GROUP);

        stained_glass_black = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_black"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_black").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_blue = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_blue"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_blue").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_brown = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_brown"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_brown").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_cyan = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_cyan"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_cyan").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_gray = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_gray"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_gray").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_green = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_green"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_green").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_light_blue = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_light_blue"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_light_blue").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_light_gray = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_light_gray"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_light_gray").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_lime = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_lime"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_lime").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_magenta = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_magenta"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_magenta").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_orange = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_orange"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_orange").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_pink = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_pink"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_pink").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_purple = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_purple"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_purple").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_red = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_red"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_red").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_white = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_white"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_white").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        stained_glass_yellow = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_yellow"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_yellow").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
//
        pane_stained_glass_black = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_black"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_black").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_blue = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_blue"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_blue").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_brown = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_brown"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_brown").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_cyan = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_cyan"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_cyan").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_gray = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_gray"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_gray").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_green = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_green"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_green").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_light_blue = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_light_blue"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_light_blue").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_light_gray = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_light_gray"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_light_gray").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_lime = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_lime"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_lime").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_magenta = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_magenta"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_magenta").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_orange = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_orange"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_orange").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_pink = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_pink"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_pink").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_purple = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_purple"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_purple").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_red = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_red"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_red").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_white = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_white"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_white").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pane_stained_glass_yellow = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_yellow"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_yellow").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);

        glass_cross = new PaneCross(Identifier.of(MOD_ID, "glass_cross"), Material.GLASS).setTranslationKey(MOD_ID, "glass_cross").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        glass_pane = new PaneGlass(Identifier.of(MOD_ID, "glass_pane"), Material.GLASS).setTranslationKey(MOD_ID, "glass_pane").setHardness(0.3F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        iron_bars = new IronFence(Identifier.of(MOD_ID, "iron_bars"), Material.GLASS).setTranslationKey(MOD_ID, "iron_bars").setHardness(5.0F).setResistance(10.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
        iron_bars_cross = new IronFence(Identifier.of(MOD_ID, "iron_bars_cross"), Material.METAL).setTranslationKey(MOD_ID, "iron_bars_cross").setHardness(5.0F).setResistance(10.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
//        iron_bars_cross = new IronFence(Identifier.of(MOD_ID, "iron_bars_cross"), Material.METAL).setTranslationKey(MOD_ID, "iron_bars_cross").setHardness(5.0F).setResistance(10.0F).setSoundGroup(Block.METAL_SOUND_GROUP).mineableBy(Identifier.of("tools/pickaxes"), 0);
    }

    //TODO: Bloki:
    // 4 Drewna + Logi;
    // stonebrick (+mossy), netherbrick, quartz, smoothstone?


    public static TemplateStairsBlock brickstairs;

    public static Block birchplanks;
    public static Block spruceplanks;
    public static Block jungleplanks;

    public static Block stonebricks;
    public static Block stonemossybricks;
    public static Block netherbricks;
    public static Block quartzblock;

    public static Block junglewood;
    public static Block jungleleaves;
    public static Block birchplanksjson;

    public static Block wooden_slab_extra;
    public static Block double_wooden_slab_extra;
    public static Block stone_slab_extra;
    public static Block double_stone_slab_extra;

    public static Block stoneslab1337;
    public static TemplateFenceBlock fence1337;

    public static Block glass_cross;
    public static Block glass_pane;
    public static Block iron_bars;
    public static Block iron_bars_cross;

    public static Block stained_glass_black;
    public static Block stained_glass_blue;
    public static Block stained_glass_brown;
    public static Block stained_glass_cyan;
    public static Block stained_glass_gray;
    public static Block stained_glass_green;
    public static Block stained_glass_light_blue;
    public static Block stained_glass_light_gray;
    public static Block stained_glass_lime;
    public static Block stained_glass_magenta;
    public static Block stained_glass_orange;
    public static Block stained_glass_pink;
    public static Block stained_glass_purple;
    public static Block stained_glass_red;
    public static Block stained_glass_white;
    public static Block stained_glass_yellow;

    public static Block pane_stained_glass_black;
    public static Block pane_stained_glass_blue;
    public static Block pane_stained_glass_brown;
    public static Block pane_stained_glass_cyan;
    public static Block pane_stained_glass_gray;
    public static Block pane_stained_glass_green;
    public static Block pane_stained_glass_light_blue;
    public static Block pane_stained_glass_light_gray;
    public static Block pane_stained_glass_lime;
    public static Block pane_stained_glass_magenta;
    public static Block pane_stained_glass_orange;
    public static Block pane_stained_glass_pink;
    public static Block pane_stained_glass_purple;
    public static Block pane_stained_glass_red;
    public static Block pane_stained_glass_white;
    public static Block pane_stained_glass_yellow;

    public static TemplatePumpkinBlock watermelon;
    public static TemplatePlantBlock pumpkinsten;
    public static TemplatePlantBlock watermelonsten;
    public static TemplateSeedsItem watermelonseeds;
    public static TemplateSeedsItem pumpkinseeds;
    public static TemplatePlantBlock cocoaplant;
    public static TemplatePlantBlock waterlily;
    public static Block vine;
    public static TemplatePlantBlock junglesapling;

}
