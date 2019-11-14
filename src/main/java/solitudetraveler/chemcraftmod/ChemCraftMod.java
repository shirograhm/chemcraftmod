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
import solitudetraveler.chemcraftmod.block.VolcanoBlock;
import solitudetraveler.chemcraftmod.container.DeconstructorContainer;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.container.VolcanoContainer;
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
import solitudetraveler.chemcraftmod.tileentity.VolcanoTileEntity;

import java.util.Objects;

@Mod("chemcraftmod")
public class ChemCraftMod {
    private static ChemCraftMod instance;
    private static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static final String MOD_ID = "chemcraftmod";
    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    private static final String[] ELEMENT_NAMES = new String[] {
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

        LOGGER.info("Setup method registered!");
    }

    private void clientRegistries(final FMLClientSetupEvent event) {
        LOGGER.info("Client registries method registered!");
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            // Initialize elements
            for(int i = 0; i < ELEMENT_NAMES.length; i++) {
                event.getRegistry().register(
                        ItemList.elementList[i] = new ElementItem(location(ELEMENT_NAMES[i]),i + 1)
                );
            }
            // Register items
            event.getRegistry().registerAll(
                    ItemList.proton = new AtomicItem(location("proton")),
                    ItemList.neutron = new AtomicItem(location("neutron")),
                    ItemList.electron = new AtomicItem(location("electron")),
                    // Blocks
                    ItemList.dolostone = new BlockItem(BlockList.dolostone, BlockList.blockItemProperties).setRegistryName(Objects.requireNonNull(BlockList.dolostone.getRegistryName())),
                    ItemList.reconstructor = new MachineBlockItem(BlockList.reconstructor.getRegistryName(), BlockList.reconstructor),
                    ItemList.deconstructor = new MachineBlockItem(BlockList.deconstructor.getRegistryName(), BlockList.deconstructor),
                    // Experiments
                    ItemList.volcano = new ExperimentBlockItem(BlockList.volcano.getRegistryName(), BlockList.volcano),
                    // Minerals
                    ItemList.aragonite = new MineralItem(location("aragonite")),
                    ItemList.calcite = new MineralItem(location("calcite")),
                    ItemList.sodalite = new MineralItem(location("sodalite")),
                    ItemList.fluorite = new MineralItem(location("fluorite")),
                    ItemList.andradite = new MineralItem(location("andradite")),
                    ItemList.zircon = new MineralItem(location("zircon")),
                    ItemList.ilmenite = new MineralItem(location("ilmenite")),
                    // Items
                    ItemList.diamond_dust = new BasicItem(location("diamond_dust")),
                    ItemList.gold_dust = new BasicItem(location("gold_dust")),
                    ItemList.salt = new BasicItem(location("salt")),
                    ItemList.soap = new BasicItem(location("soap")),
                    ItemList.baking_soda = new BasicItem(location("baking_soda")),
                    ItemList.vinegar = new BasicItem(location("vinegar")),
                    // Covalent Compounds
                    ItemList.sulfate = new CompoundItem(location("sulfate"), "SO4"),
                    ItemList.sulfite = new CompoundItem(location("sulfite"), "SO3"),
                    ItemList.nitrate = new CompoundItem(location("nitrate"), "NO3"),
                    ItemList.nitrite = new CompoundItem(location("nitrite"), "NO2"),
                    ItemList.carbonate = new CompoundItem(location("carbonate"), "CO3"),
                    ItemList.carbonite = new CompoundItem(location("carbonite"), "CO2"),
                    ItemList.bicarbonate = new CompoundItem(location("bicarbonate"), "HCO3"),
                    ItemList.hydroxide = new CompoundItem(location("hydroxide"), "OH"),
                    ItemList.acetate = new CompoundItem(location("acetate"), "C2H3O2"),
                    ItemList.methyl_group = new CompoundItem(location("methyl_group"), "CH3"),
                    ItemList.methylene_group = new CompoundItem(location("methylene_group"), "CH2"),
                    ItemList.alkane_group = new CompoundItem(location("alkane_group"), "C3H7"),
                    ItemList.hydrogen_peroxide = new CompoundItem(location("hydrogen_peroxide"), "H2O2"),
                    ItemList.water = new CompoundItem(location("water"), "H2O"),

                    // Ionic Compounds
                    ItemList.zinc_oxide = new CompoundItem(location("zinc_oxide"), "ZnO"),
                    ItemList.sodium_chloride = new CompoundItem(location("sodium_chloride"), "NaCl"),
                    ItemList.sodium_bicarbonate = new CompoundItem(location("sodium_bicarbonate"), "NaHCO3"),
                    ItemList.acetic_acid = new CompoundItem(location("acetic_acid"), "C2H4O2")
            );

            LOGGER.info("Items registered!");
        }

        @SubscribeEvent
        public static void registerPotionEffects(final RegistryEvent.Register<Effect> event) {
            event.getRegistry().registerAll(
                    EffectList.radiation = new RadiationEffect(location("radiation"))
            );

            LOGGER.info("Potion effects registered!");
        }

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {

            event.getRegistry().registerAll(
                    BlockList.dolostone = new Block(BlockList.rockProperties).setRegistryName(location("dolostone")),
                    BlockList.reconstructor = new ReconstructorBlock(location("reconstructor"), BlockList.machineProperties),
                    BlockList.deconstructor = new DeconstructorBlock(location("deconstructor"), BlockList.machineProperties),
                    BlockList.volcano = new VolcanoBlock(location("volcano"), BlockList.rockProperties)
            );

            LOGGER.info("Blocks registered!");
        }

        @SubscribeEvent
        public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().registerAll(
                    TileEntityType.Builder.create(ReconstructorTileEntity::new, BlockList.reconstructor).build(null)
                            .setRegistryName(Objects.requireNonNull(BlockList.reconstructor.getRegistryName())),
                    TileEntityType.Builder.create(DeconstructorTileEntity::new, BlockList.deconstructor).build(null)
                            .setRegistryName(Objects.requireNonNull(BlockList.deconstructor.getRegistryName())),
                    TileEntityType.Builder.create(VolcanoTileEntity::new, BlockList.volcano).build(null)
                            .setRegistryName(Objects.requireNonNull(BlockList.volcano.getRegistryName()))
            );

            LOGGER.info("Tile entities registered!");
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

            ContainerType volcano_container = IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new VolcanoContainer(windowId, proxy.getClientWorld(), pos, inv, proxy.getClientPlayer());
            })).setRegistryName(Objects.requireNonNull(BlockList.volcano.getRegistryName()));

            event.getRegistry().registerAll(reconstructor_container, deconstructor_container, volcano_container);

            LOGGER.info("Containers registered!");
        }

        private static ResourceLocation location(String name) {
            return new ResourceLocation(MOD_ID, name);
        }
    }
}
