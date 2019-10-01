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
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.creativetab.BlocksItemGroup;
import solitudetraveler.chemcraftmod.creativetab.ElementItemGroup;
import solitudetraveler.chemcraftmod.creativetab.ToolsItemGroup;
import solitudetraveler.chemcraftmod.generation.Config;
import solitudetraveler.chemcraftmod.generation.OreGeneration;
import solitudetraveler.chemcraftmod.item.DamageableItem;
import solitudetraveler.chemcraftmod.item.ElementItem;
import solitudetraveler.chemcraftmod.item.ItemList;

import javax.tools.Tool;
import java.util.Objects;

@Mod("chemcraftmod")
public class ChemCraftMod {

    private static ChemCraftMod instance;
    private static final String MODID = "chemcraftmod";
    private static final Logger logger = LogManager.getLogger(MODID);

    public static final ItemGroup elementsGroup = new ElementItemGroup();
    public static final ItemGroup blocksGroup = new BlocksItemGroup();
    public static final ItemGroup toolsGroup = new ToolsItemGroup();

    public ChemCraftMod() {
        instance = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG, "chemcraftmod-server.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG, "chemcraftmod-client.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("chemcraftmod-client.toml").toString());
        Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("chemcraftmod-server.toml").toString());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        OreGeneration.setupOreGeneration();

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
                    ItemList.dolomite = new BlockItem(BlockList.dolomite, BlockList.blockProps).setRegistryName(Objects.requireNonNull(BlockList.dolomite.getRegistryName())),
                    // Items
                    ItemList.deconstruction_token = new DamageableItem().setRegistryName(location("deconstruction_token")),
                    ItemList.construction_token = new DamageableItem().setRegistryName(location("construction_token")),
                    // Elements
                    ItemList.hydrogen = new ElementItem(1).setRegistryName(location("hydrogen")),
                    ItemList.helium = new ElementItem(2).setRegistryName(location("helium")),
                    ItemList.lithium = new ElementItem(3).setRegistryName(location("lithium")),
                    ItemList.beryllium = new ElementItem(4).setRegistryName(location("beryllium")),
                    ItemList.boron = new ElementItem(5).setRegistryName(location("boron")),
                    ItemList.carbon = new ElementItem(6).setRegistryName(location("carbon")),
                    ItemList.nitrogen = new ElementItem(7).setRegistryName(location("nitrogen")),
                    ItemList.oxygen = new ElementItem(8).setRegistryName(location("oxygen")),
                    ItemList.fluorine = new ElementItem(9).setRegistryName(location("fluorine")),
                    ItemList.neon = new ElementItem(10).setRegistryName(location("neon")),
                    ItemList.sodium = new ElementItem(11).setRegistryName(location("sodium")),
                    ItemList.magnesium = new ElementItem(12).setRegistryName(location("magnesium")),
                    ItemList.aluminium = new ElementItem(13).setRegistryName(location("aluminium")),
                    ItemList.silicon = new ElementItem(14).setRegistryName(location("silicon")),
                    ItemList.phosphorus = new ElementItem(15).setRegistryName(location("phosphorus")),
                    ItemList.sulphur = new ElementItem(16).setRegistryName(location("sulphur")),
                    ItemList.chlorine = new ElementItem(17).setRegistryName(location("chlorine")),
                    ItemList.argon = new ElementItem(18).setRegistryName(location("argon")),
                    ItemList.potassium = new ElementItem(19).setRegistryName(location("potassium")),
                    ItemList.calcium = new ElementItem(20).setRegistryName(location("calcium")),
                    ItemList.scandium = new ElementItem(21).setRegistryName(location("scandium")),
                    ItemList.titanium = new ElementItem(22).setRegistryName(location("titanium")),
                    ItemList.vanadium = new ElementItem(23).setRegistryName(location("vanadium")),
                    ItemList.chromium = new ElementItem(24).setRegistryName(location("chromium")),
                    ItemList.manganese = new ElementItem(25).setRegistryName(location("manganese")),
                    ItemList.iron = new ElementItem(26).setRegistryName(location("iron"))
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
            return new ResourceLocation(MODID, name);
        }
    }
}
