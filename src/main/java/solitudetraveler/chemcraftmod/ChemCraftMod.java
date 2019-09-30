package solitudetraveler.chemcraftmod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.item.ElementItem;
import solitudetraveler.chemcraftmod.item.ItemList;

@Mod("chemcraftmod")
public class ChemCraftMod {

    public static ChemCraftMod instance;
    public static final String modid = "chemcraftmod";
    private static final Logger logger = LogManager.getLogger(modid);

    public ChemCraftMod() {
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        logger.info("Setup method registered!");
    }

    private void clientRegistries(final FMLClientSetupEvent event) {
        logger.info("Client registries method registered!");
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    // Blocks
                    ItemList.dolomite = new BlockItem(BlockList.dolomite, BlockList.blockProps).setRegistryName(BlockList.dolomite.getRegistryName()),
                    // Items
                    ItemList.hydrogen = new ElementItem(1).setRegistryName(location("hydrogen")),
                    ItemList.helium = new ElementItem(2).setRegistryName(location("helium")),
                    ItemList.lithium = new ElementItem(3).setRegistryName(location("lithium")),
                    ItemList.beryllium = new ElementItem(4).setRegistryName(location("beryllium"))
            );

            logger.info("Items registered!");
        }

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            event.getRegistry().registerAll(
                    BlockList.dolomite = new Block(Block.Properties.create(Material.ROCK)
                            .hardnessAndResistance(2.6f, 4.4f)
                            .lightValue(0)
                            .sound(SoundType.STONE)).setRegistryName(location("dolomite"))
            );

            logger.info("Blocks registered!");
        }

        private static ResourceLocation location(String name) {
            return new ResourceLocation(modid, name);
        }
    }
}
