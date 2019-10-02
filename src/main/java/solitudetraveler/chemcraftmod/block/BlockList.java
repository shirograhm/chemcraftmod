package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import solitudetraveler.chemcraftmod.ChemCraftMod;
import solitudetraveler.chemcraftmod.container.ConstructorContainer;
import solitudetraveler.chemcraftmod.tileentity.ConstructorTileEntity;

public class BlockList {
    public static Item.Properties blockItemProps = new Item.Properties().group(ChemCraftMod.blocksGroup);

    public static Block dolomite;
    public static Block constructor;

    @ObjectHolder("chemcraftmod:constructor")
    public static TileEntityType<ConstructorTileEntity> CONSTRUCTOR_TYPE;
    @ObjectHolder("chemcraftmod:constructor_gui")
    public static ContainerType<ConstructorContainer> CONSTRUCTOR_CONTAINER;
}
