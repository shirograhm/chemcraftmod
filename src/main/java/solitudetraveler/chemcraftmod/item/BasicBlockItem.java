package solitudetraveler.chemcraftmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Rarity;
import solitudetraveler.chemcraftmod.block.BlockVariables;

public class BasicBlockItem extends BlockItem {
    public BasicBlockItem(Block block) {
        super(block, BlockVariables.blockItemProperties);

        setRegistryName(block.getRegistryName());
    }

    public BasicBlockItem(Block block, Rarity rareLevel) {
        super(block, BlockVariables.blockItemProperties.rarity(rareLevel));

        setRegistryName(block.getRegistryName());
    }
}
