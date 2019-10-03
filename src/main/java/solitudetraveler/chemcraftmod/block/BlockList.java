package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import solitudetraveler.chemcraftmod.ChemCraftMod;
import solitudetraveler.chemcraftmod.container.ConstructorContainer;
import solitudetraveler.chemcraftmod.container.DeconstructorContainer;
import solitudetraveler.chemcraftmod.tileentity.ConstructorTileEntity;
import solitudetraveler.chemcraftmod.tileentity.DeconstructorTileEntity;

public class BlockList {
    public static Item.Properties blockItemProps = new Item.Properties().group(ChemCraftMod.blocksGroup);

    public static Block dolomite;
    public static Block constructor;
    public static Block deconstructor;

    @ObjectHolder("chemcraftmod:constructor")
    public static TileEntityType<ConstructorTileEntity> CONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:constructor")
    public static ContainerType<ConstructorContainer> CONSTRUCTOR_CONTAINER;

    @ObjectHolder("chemcraftmod:deconstructor")
    public static TileEntityType<DeconstructorTileEntity> DECONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:deconstructor")
    public static ContainerType<DeconstructorContainer> DECONSTRUCTOR_CONTAINER;
}
