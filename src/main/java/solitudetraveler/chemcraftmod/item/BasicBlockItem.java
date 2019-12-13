package solitudetraveler.chemcraftmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.block.BlockList;

public class BasicBlockItem extends BlockItem {
    public BasicBlockItem(ResourceLocation name, Block block) {
        super(block, BlockList.blockItemProperties);

        setRegistryName(name);
    }

    public BasicBlockItem(ResourceLocation name, Block block, Rarity rareLevel) {
        super(block, BlockList.blockItemProperties.rarity(rareLevel));

        setRegistryName(name);
    }
}
