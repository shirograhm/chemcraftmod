package solitudetraveler.chemcraftmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.block.BlockList;

public class MachineBlockItem extends BlockItem {
    public MachineBlockItem(ResourceLocation name, Block block) {
        super(block, BlockList.blockItemProperties.rarity(Rarity.UNCOMMON));

        setRegistryName(name);
    }
}
