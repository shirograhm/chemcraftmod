package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import solitudetraveler.chemcraftmod.container.DeconstructorContainer;
import solitudetraveler.chemcraftmod.container.ErlenmeyerContainer;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;
import solitudetraveler.chemcraftmod.tileentity.DeconstructorTileEntity;
import solitudetraveler.chemcraftmod.tileentity.ErlenmeyerTileEntity;
import solitudetraveler.chemcraftmod.tileentity.ReconstructorTileEntity;

public class BlockList {
    public static Block.Properties rockProps = Block.Properties.create(Material.ROCK).lightValue(0).sound(SoundType.STONE).hardnessAndResistance(2.6f, 4.4f);
    public static Block.Properties machineProps = Block.Properties.create(Material.IRON).lightValue(0).sound(SoundType.METAL).hardnessAndResistance(4.5f);

    public static Item.Properties blockItemProps = new Item.Properties().group(CreativeTabList.blockGroup);

    public static Block dolostone;
    public static Block reconstructor;
    public static Block deconstructor;
    public static Block erlenmeyer;

    @ObjectHolder("chemcraftmod:reconstructor")
    public static TileEntityType<ReconstructorTileEntity> RECONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:reconstructor")
    public static ContainerType<ReconstructorContainer> RECONSTRUCTOR_CONTAINER;

    @ObjectHolder("chemcraftmod:deconstructor")
    public static TileEntityType<DeconstructorTileEntity> DECONSTRUCTOR_TILE_TYPE;
    @ObjectHolder("chemcraftmod:deconstructor")
    public static ContainerType<DeconstructorContainer> DECONSTRUCTOR_CONTAINER;

    @ObjectHolder("chemcraftmod:erlenmeyer")
    public static TileEntityType<ErlenmeyerTileEntity> ERLENMEYER_TILE_TYPE;
    @ObjectHolder("chemcraftmod:erlenmeyer")
    public static ContainerType<ErlenmeyerContainer> ERLENMEYER_CONTAINER;
}
