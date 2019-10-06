package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import solitudetraveler.chemcraftmod.ChemCraftMod;
import solitudetraveler.chemcraftmod.container.DeconstructorContainer;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.tileentity.DeconstructorTileEntity;
import solitudetraveler.chemcraftmod.tileentity.ReconstructorTileEntity;

public class BlockList {
    public static Item.Properties blockItemProps = new Item.Properties().group(ChemCraftMod.blocksGroup);

    public static Block dolostone;
    public static Block reconstructor;
    public static Block deconstructor;

    @ObjectHolder("chemcraftmod:reconstructor")
    public static TileEntityType<ReconstructorTileEntity> RECONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:reconstructor")
    public static ContainerType<ReconstructorContainer> RECONSTRUCTOR_CONTAINER;

    @ObjectHolder("chemcraftmod:deconstructor")
    public static TileEntityType<DeconstructorTileEntity> DECONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:deconstructor")
    public static ContainerType<DeconstructorContainer> DECONSTRUCTOR_CONTAINER;
}
