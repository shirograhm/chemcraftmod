package solitudetraveler.chemcraftmod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
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
import solitudetraveler.chemcraftmod.block.ConstructorBlock;
import solitudetraveler.chemcraftmod.container.ConstructorContainer;
import solitudetraveler.chemcraftmod.creativetab.BlocksItemGroup;
import solitudetraveler.chemcraftmod.creativetab.ElementItemGroup;
import solitudetraveler.chemcraftmod.generation.Config;
import solitudetraveler.chemcraftmod.generation.OreGeneration;
import solitudetraveler.chemcraftmod.item.ElementItem;
import solitudetraveler.chemcraftmod.item.ItemList;
import solitudetraveler.chemcraftmod.proxy.ClientProxy;
import solitudetraveler.chemcraftmod.proxy.IProxy;
import solitudetraveler.chemcraftmod.proxy.ServerProxy;
import solitudetraveler.chemcraftmod.tileentity.ConstructorTileEntity;

import java.util.Objects;

@Mod("chemcraftmod")
public class ChemCraftMod {

    public static final String MODID = "chemcraftmod";

    private static ChemCraftMod instance;
    private static final Logger logger = LogManager.getLogger(MODID);
    private static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static final ItemGroup elementsGroup = new ElementItemGroup();
    public static final ItemGroup blocksGroup = new BlocksItemGroup();

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
                    ItemList.dolomite = new BlockItem(BlockList.dolomite, BlockList.blockItemProps).setRegistryName(Objects.requireNonNull(BlockList.dolomite.getRegistryName())),
                    ItemList.constructor = new BlockItem(BlockList.constructor, BlockList.blockItemProps).setRegistryName(Objects.requireNonNull(BlockList.constructor.getRegistryName())),
                    // Items
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
                    ItemList.iron = new ElementItem(26).setRegistryName(location("iron")),
                    ItemList.cobalt = new ElementItem(27).setRegistryName(location("cobalt")),
                    ItemList.nickel = new ElementItem(28).setRegistryName(location("nickel")),
                    ItemList.copper = new ElementItem(29).setRegistryName(location("copper")),
                    ItemList.zinc = new ElementItem(30).setRegistryName(location("zinc")),
                    ItemList.gallium = new ElementItem(31).setRegistryName(location("gallium")),
                    ItemList.germanium = new ElementItem(32).setRegistryName(location("germanium")),
                    ItemList.arsenic = new ElementItem(33).setRegistryName(location("arsenic")),
                    ItemList.selenium = new ElementItem(34).setRegistryName(location("selenium")),
                    ItemList.bromine = new ElementItem(35).setRegistryName(location("bromine")),
                    ItemList.krypton = new ElementItem(36).setRegistryName(location("krypton")),
                    ItemList.rubidium = new ElementItem(37).setRegistryName(location("rubidium")),
                    ItemList.strontium = new ElementItem(38).setRegistryName(location("strontium")),
                    ItemList.yttrium = new ElementItem(39).setRegistryName(location("yttrium")),
                    ItemList.zirconium = new ElementItem(40).setRegistryName(location("zirconium")),
                    ItemList.niobium = new ElementItem(41).setRegistryName(location("niobium")),
                    ItemList.molybdenum = new ElementItem(42).setRegistryName(location("molybdenum")),
                    ItemList.technetium = new ElementItem(43).setRegistryName(location("technetium")),
                    ItemList.ruthenium = new ElementItem(44).setRegistryName(location("ruthenium")),
                    ItemList.rhodium = new ElementItem(45).setRegistryName(location("rhodium")),
                    ItemList.palladium = new ElementItem(46).setRegistryName(location("palladium")),
                    ItemList.silver = new ElementItem(47).setRegistryName(location("silver")),
                    ItemList.cadmium = new ElementItem(48).setRegistryName(location("cadmium")),
                    ItemList.indium = new ElementItem(49).setRegistryName(location("indium")),
                    ItemList.tin = new ElementItem(50).setRegistryName(location("tin")),
                    ItemList.antimony = new ElementItem(51).setRegistryName(location("antimony")),
                    ItemList.tellurium = new ElementItem(52).setRegistryName(location("tellurium")),
                    ItemList.iodine = new ElementItem(53).setRegistryName(location("iodine")),
                    ItemList.xenon = new ElementItem(54).setRegistryName(location("xenon")),
                    ItemList.cesium = new ElementItem(55).setRegistryName(location("cesium")),
                    ItemList.barium = new ElementItem(56).setRegistryName(location("barium")),
                    ItemList.lanthanum = new ElementItem(57).setRegistryName(location("lanthanum")),
                    ItemList.cerium = new ElementItem(58).setRegistryName(location("cerium")),
                    ItemList.praseodymium = new ElementItem(59).setRegistryName(location("praseodymium")),
                    ItemList.neodymium = new ElementItem(61).setRegistryName(location("neodymium")),
                    ItemList.promethium = new ElementItem(60).setRegistryName(location("promethium")),
                    ItemList.samarium = new ElementItem(62).setRegistryName(location("samarium")),
                    ItemList.europium = new ElementItem(63).setRegistryName(location("europium")),
                    ItemList.gadolinium = new ElementItem(64).setRegistryName(location("gadolinium")),
                    ItemList.terbium = new ElementItem(65).setRegistryName(location("terbium")),
                    ItemList.dysprosium = new ElementItem(66).setRegistryName(location("dysprosium")),
                    ItemList.holmium = new ElementItem(67).setRegistryName(location("holmium")),
                    ItemList.erbium = new ElementItem(68).setRegistryName(location("erbium")),
                    ItemList.thulium = new ElementItem(69).setRegistryName(location("thulium")),
                    ItemList.ytterbium = new ElementItem(70).setRegistryName(location("ytterbium")),
                    ItemList.lutetium = new ElementItem(71).setRegistryName(location("lutetium")),
                    ItemList.hafnium = new ElementItem(72).setRegistryName(location("hafnium")),
                    ItemList.tantalum = new ElementItem(73).setRegistryName(location("tantalum")),
                    ItemList.tungsten = new ElementItem(74).setRegistryName(location("tungsten")),
                    ItemList.rhenium = new ElementItem(75).setRegistryName(location("rhenium")),
                    ItemList.osmium = new ElementItem(76).setRegistryName(location("osmium")),
                    ItemList.iridium = new ElementItem(77).setRegistryName(location("iridium")),
                    ItemList.platinum = new ElementItem(78).setRegistryName(location("platinum")),
                    ItemList.gold = new ElementItem(79).setRegistryName(location("gold")),
                    ItemList.mercury = new ElementItem(80).setRegistryName(location("mercury")),
                    ItemList.thallium = new ElementItem(81).setRegistryName(location("thallium")),
                    ItemList.lead = new ElementItem(82).setRegistryName(location("lead")),
                    ItemList.bismuth = new ElementItem(83).setRegistryName(location("bismuth")),
                    ItemList.polonium = new ElementItem(84).setRegistryName(location("polonium")),
                    ItemList.astatine = new ElementItem(85).setRegistryName(location("astatine")),
                    ItemList.radon = new ElementItem(86).setRegistryName(location("radon")),
                    ItemList.francium = new ElementItem(87).setRegistryName(location("francium")),
                    ItemList.radium = new ElementItem(88).setRegistryName(location("radium")),
                    ItemList.actinium = new ElementItem(89).setRegistryName(location("actinium")),
                    ItemList.thorium = new ElementItem(90).setRegistryName(location("thorium")),
                    ItemList.protactinium = new ElementItem(91).setRegistryName(location("protactinium")),
                    ItemList.uranium = new ElementItem(92).setRegistryName(location("uranium")),
                    ItemList.neptunium = new ElementItem(93).setRegistryName(location("neptunium")),
                    ItemList.plutonium = new ElementItem(94).setRegistryName(location("plutonium")),
                    ItemList.americium = new ElementItem(95).setRegistryName(location("americium")),
                    ItemList.curium = new ElementItem(96).setRegistryName(location("curium")),
                    ItemList.berkelium = new ElementItem(97).setRegistryName(location("berkelium")),
                    ItemList.californium = new ElementItem(98).setRegistryName(location("californium")),
                    ItemList.einsteinium = new ElementItem(99).setRegistryName(location("einsteinium")),
                    ItemList.fermium = new ElementItem(100).setRegistryName(location("fermium")),
                    ItemList.mendelevium = new ElementItem(101).setRegistryName(location("mendelevium")),
                    ItemList.nobelium = new ElementItem(102).setRegistryName(location("nobelium")),
                    ItemList.lawrencium = new ElementItem(103).setRegistryName(location("lawrencium")),
                    ItemList.rutherfordium = new ElementItem(104).setRegistryName(location("rutherfordium")),
                    ItemList.dubnium = new ElementItem(105).setRegistryName(location("dubnium")),
                    ItemList.seaborgium = new ElementItem(106).setRegistryName(location("seaborgium")),
                    ItemList.bohrium = new ElementItem(107).setRegistryName(location("bohrium")),
                    ItemList.hassium = new ElementItem(108).setRegistryName(location("hassium")),
                    ItemList.meitnerium = new ElementItem(109).setRegistryName(location("meitnerium")),
                    ItemList.darmstadtium = new ElementItem(110).setRegistryName(location("darmstadtium")),
                    ItemList.roentgenium = new ElementItem(111).setRegistryName(location("roentgenium")),
                    ItemList.copernicium = new ElementItem(113).setRegistryName(location("copernicium")),
                    ItemList.nihonium = new ElementItem(112).setRegistryName(location("nihonium")),
                    ItemList.flerovium = new ElementItem(114).setRegistryName(location("flerovium")),
                    ItemList.moscovium = new ElementItem(115).setRegistryName(location("moscovium")),
                    ItemList.livermorium = new ElementItem(116).setRegistryName(location("livermorium")),
                    ItemList.tennessine = new ElementItem(117).setRegistryName(location("tennessine")),
                    ItemList.oganesson = new ElementItem(118).setRegistryName(location("oganesson"))
            );

            logger.info("Items registered!");
        }

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            Block.Properties rockProps = Block.Properties.create(Material.ROCK).lightValue(0).sound(SoundType.STONE);
            Block.Properties machineProps = Block.Properties.create(Material.IRON).lightValue(0).sound(SoundType.METAL).hardnessAndResistance(4.5f);

            event.getRegistry().registerAll(
                    BlockList.dolomite = new Block(rockProps.hardnessAndResistance(2.6f, 4.4f)).setRegistryName(location("dolomite")),
                    BlockList.constructor = new ConstructorBlock(machineProps).setRegistryName(location("constructor"))
            );

            logger.info("Blocks registered!");
        }

        @SubscribeEvent
        public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().registerAll(
                    TileEntityType.Builder.create(ConstructorTileEntity::new, BlockList.constructor).build(null).setRegistryName(location("constructor"))
            );
        }

        @SubscribeEvent
        public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new ConstructorContainer(windowId, proxy.getClientWorld(), pos, inv, proxy.getClientPlayer());
            }).setRegistryName(location("constructor")));
        }


        private static ResourceLocation location(String name) {
            return new ResourceLocation(MODID, name);
        }
    }
}
