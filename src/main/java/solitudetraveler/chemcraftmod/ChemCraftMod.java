package solitudetraveler.chemcraftmod;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
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
import solitudetraveler.chemcraftmod.block.DeconstructorBlock;
import solitudetraveler.chemcraftmod.block.ReconstructorBlock;
import solitudetraveler.chemcraftmod.container.DeconstructorContainer;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.effect.EffectList;
import solitudetraveler.chemcraftmod.effect.RadiationEffect;
import solitudetraveler.chemcraftmod.generation.Config;
import solitudetraveler.chemcraftmod.generation.OreGeneration;
import solitudetraveler.chemcraftmod.handler.ChemCraftEventHandler;
import solitudetraveler.chemcraftmod.item.*;
import solitudetraveler.chemcraftmod.proxy.ClientProxy;
import solitudetraveler.chemcraftmod.proxy.IProxy;
import solitudetraveler.chemcraftmod.proxy.ServerProxy;
import solitudetraveler.chemcraftmod.tileentity.DeconstructorTileEntity;
import solitudetraveler.chemcraftmod.tileentity.ReconstructorTileEntity;

import java.util.Objects;

@Mod("chemcraftmod")
public class ChemCraftMod {
    private static ChemCraftMod instance;
    private static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static final String MOD_ID = "chemcraftmod";
    public static final Logger logger = LogManager.getLogger(MOD_ID);
    public static final String[] ELEMENT_NAMES = new String[] {
            "hydrogen", "helium", "lithium", "beryllium", "boron", "carbon", "nitrogen",
            "oxygen", "fluorine", "neon", "sodium", "magnesium", "aluminium", "silicon",
            "phosphorus", "sulphur", "chlorine", "argon", "potassium", "calcium", "scandium",
            "titanium", "vanadium", "chromium", "manganese", "iron", "cobalt", "nickel",
            "copper", "zinc", "gallium", "germanium", "arsenic", "selenium", "bromine", "krypton",
            "rubidium", "strontium", "yttrium", "zirconium", "niobium", "molybdenum", "technetium", "ruthenium",
            "rhodium", "palladium", "silver", "cadmium", "indium", "tin", "antimony", "tellurium", "iodine",
            "xenon", "cesium", "barium", "lanthanum", "cerium", "praseodymium", "neodymium", "promethium",
            "samarium", "europium", "gadolinium", "terbium", "dysprosium", "holmium", "erbium", "thulium", "ytterbium",
            "lutetium", "hafnium", "tantalum", "tungsten", "rhenium", "osmium", "iridium", "platinum", "gold", "mercury",
            "thallium", "lead", "bismuth", "polonium", "astatine", "radon", "francium", "radium", "actinium", "thorium",
            "protactinium", "uranium", "neptunium", "plutonium", "americium", "curium", "berkelium", "californium",
            "einsteinium", "fermium", "mendelevium", "nobelium", "lawrencium", "rutherfordium", "dubnium", "seaborgium",
            "bohrium", "hassium", "meitnerium", "darmstadtium", "roentgenium", "copernicium", "nihonium", "flerovium",
            "moscovium", "livermorium", "tennessine", "oganesson"
    };

    public ChemCraftMod() {
        instance = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG, "chemcraftmod-server.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG, "chemcraftmod-client.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);

        Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("chemcraftmod-server.toml").toString());
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("chemcraftmod-client.toml").toString());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.init();
        OreGeneration.setupOreGeneration();
        MinecraftForge.EVENT_BUS.register(ChemCraftEventHandler.class);

        logger.info("Setup method registered!");
    }

    private void clientRegistries(final FMLClientSetupEvent event) {
        logger.info("Client registries method registered!");
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            // Elements
            for(int i = 0; i < ELEMENT_NAMES.length; i++) {
                event.getRegistry().register(
                        ItemList.elementList[i] = new ElementItem(location(ELEMENT_NAMES[i]),i + 1)
                );
            }

            event.getRegistry().registerAll(
                    ItemList.proton = new AtomicItem(location("proton")),
                    ItemList.neutron = new AtomicItem(location("neutron")),
                    ItemList.electron = new AtomicItem(location("electron")),
                    // Blocks
                    ItemList.dolostone = new BlockItem(BlockList.dolostone, BlockList.blockItemProps).setRegistryName(Objects.requireNonNull(BlockList.dolostone.getRegistryName())),
                    ItemList.reconstructor = new BlockItem(BlockList.reconstructor, BlockList.blockItemProps).setRegistryName(Objects.requireNonNull(BlockList.reconstructor.getRegistryName())),
                    ItemList.deconstructor = new BlockItem(BlockList.deconstructor, BlockList.blockItemProps).setRegistryName(Objects.requireNonNull(BlockList.deconstructor.getRegistryName())),
                    // Minerals
                    ItemList.aragonite = new MineralItem(location("aragonite")),
                    ItemList.calcite = new MineralItem(location("calcite")),
                    ItemList.sodalite = new MineralItem(location("sodalite")),
                    ItemList.fluorite = new MineralItem(location("fluorite")),
                    ItemList.andradite = new MineralItem(location("andradite")),
                    ItemList.zircon = new MineralItem(location("zircon")),
                    ItemList.ilmenite = new MineralItem(location("ilmenite")),
                    // Items
                    ItemList.diamond_dust = new MineralItem(location("diamond_dust")),
                    ItemList.gold_dust = new MineralItem(location("gold_dust")),
                    // Covalent Compounds
                    ItemList.sulfate = new CompoundItem(location("sulfate")),
                    ItemList.sulfite = new CompoundItem(location("sulfite")),
                    ItemList.nitrate = new CompoundItem(location("nitrate")),
                    ItemList.nitrite = new CompoundItem(location("nitrite")),
                    ItemList.carbon_dioxide = new CompoundItem(location("carbon_dioxide")),
                    ItemList.carbon_monoxide = new CompoundItem(location("carbon_monoxide")),
                    // Ionic Compounds
                    ItemList.zinc_oxide = new CompoundItem(location("zinc_oxide")),
                    ItemList.sodium_chloride = new CompoundItem(location("sodium_chloride"))
            );

            logger.info("Items registered!");
        }

        @SubscribeEvent
        public static void registerPotionEffects(final RegistryEvent.Register<Effect> event) {
            event.getRegistry().registerAll(
                    EffectList.radiation = new RadiationEffect(location("radiation"))
            );
        }

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {

            event.getRegistry().registerAll(
                    BlockList.dolostone = new Block(BlockList.rockProps).setRegistryName(location("dolostone")),
                    BlockList.reconstructor = new ReconstructorBlock(location("reconstructor"), BlockList.machineProps),
                    BlockList.deconstructor = new DeconstructorBlock(location("deconstructor"), BlockList.machineProps)
            );

            logger.info("Blocks registered!");
        }

        @SubscribeEvent
        public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().registerAll(
                    TileEntityType.Builder.create(ReconstructorTileEntity::new, BlockList.reconstructor).build(null)
                            .setRegistryName(Objects.requireNonNull(BlockList.reconstructor.getRegistryName())),
                    TileEntityType.Builder.create(DeconstructorTileEntity::new, BlockList.deconstructor).build(null)
                            .setRegistryName(Objects.requireNonNull(BlockList.deconstructor.getRegistryName()))
            );

            logger.info("Tile entities registered!");
        }

        @SubscribeEvent
        public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
            ContainerType reconstructor_container = IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new ReconstructorContainer(windowId, proxy.getClientWorld(), pos, inv, proxy.getClientPlayer());
            }).setRegistryName(Objects.requireNonNull(BlockList.reconstructor.getRegistryName()));
            ContainerType deconstructor_container = IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new DeconstructorContainer(windowId, proxy.getClientWorld(), pos, inv, proxy.getClientPlayer());
            }).setRegistryName(Objects.requireNonNull(BlockList.deconstructor.getRegistryName()));

            event.getRegistry().registerAll(reconstructor_container, deconstructor_container);

            logger.info("Containers registered!");
        }


        private static ResourceLocation location(String name) {
            return new ResourceLocation(MOD_ID, name);
        }
    }
}
