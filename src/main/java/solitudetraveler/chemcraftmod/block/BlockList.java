package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import solitudetraveler.chemcraftmod.container.DeconstructorContainer;
import solitudetraveler.chemcraftmod.container.FlaskContainer;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;
import solitudetraveler.chemcraftmod.tileentity.DeconstructorTileEntity;
import solitudetraveler.chemcraftmod.tileentity.FlaskTileEntity;
import solitudetraveler.chemcraftmod.tileentity.ReconstructorTileEntity;

public class BlockList {
    public static Block.Properties rockProperties = Block.Properties.create(Material.ROCK).lightValue(0).sound(SoundType.STONE).hardnessAndResistance(2.6f, 4.4f);
    public static Block.Properties machineProperties = Block.Properties.create(Material.IRON).lightValue(0).sound(SoundType.METAL).hardnessAndResistance(4.5f);
    public static Block.Properties glasswareProperties = Block.Properties.create(Material.GLASS).lightValue(0).sound(SoundType.GLASS).hardnessAndResistance(0.5f, 0.0f);

    public static Item.Properties blockItemProperties = new Item.Properties().group(CreativeTabList.blockGroup);

    public static Block dolostone;
    public static Block reconstructor;
    public static Block deconstructor;
    public static Block flask;

    @ObjectHolder("chemcraftmod:reconstructor")
    public static TileEntityType<ReconstructorTileEntity> RECONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:reconstructor")
    public static ContainerType<ReconstructorContainer> RECONSTRUCTOR_CONTAINER;

    @ObjectHolder("chemcraftmod:deconstructor")
    public static TileEntityType<DeconstructorTileEntity> DECONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:deconstructor")
    public static ContainerType<DeconstructorContainer> DECONSTRUCTOR_CONTAINER;

    @ObjectHolder("chemcraftmod:flask")
    public static TileEntityType<FlaskTileEntity> FLASK_TILE_TYPE;
    @ObjectHolder("chemcraftmod:flask")
    public static ContainerType<FlaskContainer> FLASK_CONTAINER;
}
