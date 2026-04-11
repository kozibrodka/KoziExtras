package net.kozibrodka.extra.events;

import net.glasslauncher.hmifabric.HowManyItems;
import net.kozibrodka.extra.blocksCosmetic.BlockFenceExtra;
import net.kozibrodka.extra.blocksCosmetic.BlockWoodenSlabExtra;
import net.kozibrodka.extra.blocksSimple.*;
import net.kozibrodka.extra.farming.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.template.block.*;
import net.modificationstation.stationapi.api.template.item.TemplateSeeds;
import net.modificationstation.stationapi.api.util.Null;

public class BlockListener {

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

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
        fence1337 = new BlockFenceExtra(Identifier.of(MOD_ID, "stoneslab1337"), 0, birchplanks).setTranslationKey(MOD_ID, "stoneslab1337");

//        stonebricks = new TemplateBlockBase(Identifier.of(MOD_ID, "stonebricks"), Material.STONE).setTranslationKey(MOD_ID, "stonebricks").setHardness(1.5F).setBlastResistance(10.0F).setSounds(BlockBase.STONE_SOUNDS);
        birchplanksjson = new TemplateBlockBase(Identifier.of(MOD_ID, "birchplanksjson"), Material.WOOD).setTranslationKey(MOD_ID, "birchplanksjson").setHardness(2.0F).setResistance(5.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
//        spruceplanks = new TemplateBlockBase(Identifier.of(MOD_ID, "spruceplanks"), Material.WOOD).setTranslationKey(MOD_ID, "spruceplanks").setHardness(2.0F).setBlastResistance(5.0F).setSounds(BlockBase.WOOD_SOUNDS);
//        jungleplanks = new TemplateBlockBase(Identifier.of(MOD_ID, "jungleplanks"), Material.WOOD).setTranslationKey(MOD_ID, "jungleplanks").setHardness(2.0F).setBlastResistance(5.0F).setSounds(BlockBase.WOOD_SOUNDS);
        watermelon = new TemplatePumpkin(Identifier.of(MOD_ID, "watermelon"), 1, false).setTranslationKey(MOD_ID, "watermelon").setHardness(1.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        junglewood = new BlockTreeJungle(Identifier.of(MOD_ID, "junglewood"), Material.WOOD).setTranslationKey(MOD_ID, "junglewood").setHardness(2.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        jungleleaves = new BlockLeavesJungle(Identifier.of(MOD_ID, "jungleleaves"), Material.LEAVES).setTranslationKey(MOD_ID, "jungleleaves").setHardness(0.2F).setOpacity(1).setSoundGroup(Block.DIRT_SOUND_GROUP);
        brickstairs = new TemplateStairs(Identifier.of(MOD_ID, "brickstairs"), jungleplanks).setTranslationKey(MOD_ID, "brickstairs");

        pumpkinsten = new BlockStem(Identifier.of(MOD_ID, "pumpkinsten"), 1, Block.PUMPKIN).setTranslationKey(MOD_ID, "pumpkinsten").setHardness(0.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        watermelonsten = new BlockStem(Identifier.of(MOD_ID, "watermelonsten"), 1, watermelon).setTranslationKey(MOD_ID, "watermelonsten").setHardness(0.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        pumpkinseeds = new TemplateSeeds(Identifier.of(MOD_ID, "pumpkinseeds"), pumpkinsten.id).setTranslationKey(MOD_ID, "pumpkinseeds");
        watermelonseeds = new TemplateSeeds(Identifier.of(MOD_ID, "watermelonseeds"), watermelonsten.id).setTranslationKey(MOD_ID, "watermelonseeds");
        junglesapling = new BlockJungleSapling(Identifier.of(MOD_ID, "junglesapling"), 1).setTranslationKey(MOD_ID, "junglesapling").setHardness(0.0F).setSoundGroup(Block.DIRT_SOUND_GROUP);
        cocoaplant = new BlockCocoa(Identifier.of(MOD_ID, "cocoaplant"), 1).setTranslationKey(MOD_ID, "cocoaplant").setHardness(0.2F).setResistance(5.0F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        waterlily = new BlockLilyPad(Identifier.of(MOD_ID, "waterlily"), 2).setTranslationKey(MOD_ID, "waterlily").setHardness(0.0F).setSoundGroup(Block.DIRT_SOUND_GROUP);
        vine = new BlockVine(Identifier.of(MOD_ID, "vine"), Material.PLANT).setTranslationKey(MOD_ID, "vine").setHardness(0.2F).setSoundGroup(Block.DIRT_SOUND_GROUP);

//        stained_glass_black = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_black"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_black").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_blue = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_blue"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_blue").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_brown = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_brown"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_brown").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_cyan = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_cyan"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_cyan").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_gray = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_gray"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_gray").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_green = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_green"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_green").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_light_blue = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_light_blue"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_light_blue").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_light_gray = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_light_gray"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_light_gray").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_lime = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_lime"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_lime").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_magenta = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_magenta"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_magenta").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_orange = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_orange"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_orange").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_pink = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_pink"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_pink").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_purple = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_purple"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_purple").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_red = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_red"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_red").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_white = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_white"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_white").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        stained_glass_yellow = new TintedGlass(Identifier.of(MOD_ID, "stained_glass_yellow"), Material.GLASS).setTranslationKey(MOD_ID, "stained_glass_yellow").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//
//        pane_stained_glass_black = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_black"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_black").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_blue = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_blue"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_blue").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_brown = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_brown"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_brown").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_cyan = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_cyan"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_cyan").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_gray = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_gray"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_gray").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_green = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_green"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_green").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_light_blue = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_light_blue"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_light_blue").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_light_gray = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_light_gray"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_light_gray").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_lime = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_lime"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_lime").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_magenta = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_magenta"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_magenta").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_orange = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_orange"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_orange").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_pink = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_pink"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_pink").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_purple = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_purple"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_purple").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_red = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_red"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_red").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_white = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_white"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_white").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        pane_stained_glass_yellow = new PaneTintedGlass(Identifier.of(MOD_ID, "pane_stained_glass_yellow"), Material.GLASS).setTranslationKey(MOD_ID, "pane_stained_glass_yellow").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//
//        glass_cross = new PaneCross(Identifier.of(MOD_ID, "glass_cross"), Material.GLASS).setTranslationKey(MOD_ID, "glass_cross").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        glass_pane = new PaneGlass(Identifier.of(MOD_ID, "glass_pane"), Material.GLASS).setTranslationKey(MOD_ID, "glass_pane").setHardness(0.3F).setSounds(BlockBase.GLASS_SOUNDS);
//        iron_bars = new IronFence(Identifier.of(MOD_ID, "iron_bars"), Material.GLASS).setTranslationKey(MOD_ID, "iron_bars").setHardness(5.0F).setBlastResistance(10.0F).setSounds(BlockBase.METAL_SOUNDS);
//        iron_bars_cross = new IronFence(Identifier.of(MOD_ID, "iron_bars_cross"), Material.METAL).setTranslationKey(MOD_ID, "iron_bars_cross").setHardness(5.0F).setBlastResistance(10.0F).setSounds(BlockBase.METAL_SOUNDS).mineableBy(Identifier.of("tools/pickaxes"), 0);
    }

    //TODO: Bloki:
    // 4 Drewna + Logi;
    // stonebrick (+mossy), netherbrick, quartz, smoothstone?


    public static TemplateStairs brickstairs;

    public static TemplateBlockBase birchplanks;
    public static TemplateBlockBase spruceplanks;
    public static TemplateBlockBase jungleplanks;

    public static TemplateBlockBase stonebricks;
    public static TemplateBlockBase stonemossybricks;
    public static TemplateBlockBase netherbricks;
    public static TemplateBlockBase quartzblock;

    public static TemplateBlockBase junglewood;
    public static TemplateBlockBase jungleleaves;
    public static TemplateBlockBase birchplanksjson;

    public static TemplateBlockBase wooden_slab_extra;
    public static TemplateBlockBase double_wooden_slab_extra;
    public static TemplateBlockBase stone_slab_extra;
    public static TemplateBlockBase double_stone_slab_extra;

    public static TemplateBlockBase stoneslab1337;
    public static TemplateFence fence1337;

    public static TemplateBlockBase glass_cross;
    public static TemplateBlockBase glass_pane;
    public static TemplateBlockBase iron_bars;
    public static TemplateBlockBase iron_bars_cross;

    public static TemplateBlockBase stained_glass_black;
    public static TemplateBlockBase stained_glass_blue;
    public static TemplateBlockBase stained_glass_brown;
    public static TemplateBlockBase stained_glass_cyan;
    public static TemplateBlockBase stained_glass_gray;
    public static TemplateBlockBase stained_glass_green;
    public static TemplateBlockBase stained_glass_light_blue;
    public static TemplateBlockBase stained_glass_light_gray;
    public static TemplateBlockBase stained_glass_lime;
    public static TemplateBlockBase stained_glass_magenta;
    public static TemplateBlockBase stained_glass_orange;
    public static TemplateBlockBase stained_glass_pink;
    public static TemplateBlockBase stained_glass_purple;
    public static TemplateBlockBase stained_glass_red;
    public static TemplateBlockBase stained_glass_white;
    public static TemplateBlockBase stained_glass_yellow;

    public static TemplateBlockBase pane_stained_glass_black;
    public static TemplateBlockBase pane_stained_glass_blue;
    public static TemplateBlockBase pane_stained_glass_brown;
    public static TemplateBlockBase pane_stained_glass_cyan;
    public static TemplateBlockBase pane_stained_glass_gray;
    public static TemplateBlockBase pane_stained_glass_green;
    public static TemplateBlockBase pane_stained_glass_light_blue;
    public static TemplateBlockBase pane_stained_glass_light_gray;
    public static TemplateBlockBase pane_stained_glass_lime;
    public static TemplateBlockBase pane_stained_glass_magenta;
    public static TemplateBlockBase pane_stained_glass_orange;
    public static TemplateBlockBase pane_stained_glass_pink;
    public static TemplateBlockBase pane_stained_glass_purple;
    public static TemplateBlockBase pane_stained_glass_red;
    public static TemplateBlockBase pane_stained_glass_white;
    public static TemplateBlockBase pane_stained_glass_yellow;

    public static TemplatePumpkin watermelon;
    public static TemplatePlant pumpkinsten;
    public static TemplatePlant watermelonsten;
    public static TemplateSeeds watermelonseeds;
    public static TemplateSeeds pumpkinseeds;
    public static TemplatePlant cocoaplant;
    public static TemplatePlant waterlily;
    public static TemplateBlockBase vine;
    public static TemplatePlant junglesapling;

}
