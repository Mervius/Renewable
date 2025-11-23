package top.yuhh.renewables;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import top.yuhh.renewables.block.ModBlocks;
import top.yuhh.renewables.item.ModItems;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Renewables.MOD_ID)
public class Renewables {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "vanilla_renewables";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Renewables(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> FluidInteractionRegistry.addInteraction(NeoForgeMod.LAVA_TYPE.value(),
                new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> level.getBlockState(currentPos.below()).is(Blocks.DEEPSLATE) && level.getBlockState(relativePos).is(Blocks.COAL_BLOCK),
                ModBlocks.GRAPHITE_BLOCK.get().defaultBlockState()))
        );

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> key = event.getTabKey();
        if(key == CreativeModeTabs.INGREDIENTS & event.getParentEntries().contains(Items.GOLD_NUGGET.getDefaultInstance())) {
            event.insertAfter(Items.GOLD_NUGGET.getDefaultInstance(),ModItems.DIAMOND_SHARD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (key == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.GRAPHITE_BLOCK);
            event.accept(ModBlocks.GRAPHITE_STAIRS);
            event.accept(ModBlocks.GRAPHITE_SLAB);
            event.accept(ModBlocks.GRAPHITE_WALL);
            event.accept(ModBlocks.POLISHED_GRAPHITE);
            event.accept(ModBlocks.POLISHED_GRAPHITE_STAIRS);
            event.accept(ModBlocks.POLISHED_GRAPHITE_SLAB);
            event.accept(ModBlocks.POLISHED_GRAPHITE_WALL);
            event.accept(ModBlocks.GRAPHITE_BRICKS);
            event.accept(ModBlocks.GRAPHITE_BRICK_STAIRS);
            event.accept(ModBlocks.GRAPHITE_BRICK_SLAB);
            event.accept(ModBlocks.GRAPHITE_BRICK_WALL);
        } else if (key == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.GRAPHITE_BLOCK);
            event.accept(ModBlocks.LACED_GRAPHITE);
            event.accept(ModBlocks.RICH_GRAPHITE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}