package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import solitudetraveler.chemcraftmod.container.*;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;
import solitudetraveler.chemcraftmod.tileentity.*;

public class BlockVariables {
    public static Block.Properties rockProperties = Block.Properties.create(Material.ROCK).lightValue(0).sound(SoundType.STONE).hardnessAndResistance(2.6f, 4.4f);
    public static Block.Properties machineProperties = Block.Properties.create(Material.IRON).lightValue(0).sound(SoundType.METAL).hardnessAndResistance(4.5f);

    public static Item.Properties blockItemProperties = new Item.Properties().group(CreativeTabList.blockGroup);

    // Tile Entities and Containers
    @ObjectHolder("chemcraftmod:generator")
    public static TileEntityType<GeneratorTileEntity> GENERATOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:generator")
    public static ContainerType<GeneratorContainer> GENERATOR_CONTAINER;

    @ObjectHolder("chemcraftmod:reconstructor")
    public static TileEntityType<ReconstructorTileEntity> RECONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:reconstructor")
    public static ContainerType<ReconstructorContainer> RECONSTRUCTOR_CONTAINER;

    @ObjectHolder("chemcraftmod:deconstructor")
    public static TileEntityType<DeconstructorTileEntity> DECONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:deconstructor")
    public static ContainerType<DeconstructorContainer> DECONSTRUCTOR_CONTAINER;

    @ObjectHolder("chemcraftmod:volcano")
    public static TileEntityType<VolcanoTileEntity> VOLCANO_TILE_TYPE;
    @ObjectHolder("chemcraftmod:volcano")
    public static ContainerType<VolcanoContainer> VOLCANO_CONTAINER;

    @ObjectHolder("chemcraftmod:accelerator")
    public static TileEntityType<AcceleratorTileEntity> ACCELERATOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:accelerator")
    public static ContainerType<AcceleratorContainer> ACCELERATOR_CONTAINER;
}
