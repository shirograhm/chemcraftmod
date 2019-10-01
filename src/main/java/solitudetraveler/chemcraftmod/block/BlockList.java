package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import solitudetraveler.chemcraftmod.ChemCraftMod;
import solitudetraveler.chemcraftmod.tileentity.ConstructorTE;

public class BlockList {
    public static Item.Properties blockProps = new Item.Properties().group(ChemCraftMod.blocksGroup);

    public static Block dolomite;

    public static ConstructorBlock constructor;
    public static TileEntityType<ConstructorTE> CONSTRUCTOR_TE;
}
