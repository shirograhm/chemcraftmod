package solitudetraveler.chemcraftmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.block.BlockVariables;

public class BasicBlockItem extends BlockItem {
    public BasicBlockItem(ResourceLocation name, Block block) {
        super(block, BlockVariables.blockItemProperties);

        setRegistryName(name);
    }

    public BasicBlockItem(ResourceLocation name, Block block, Rarity rareLevel) {
        super(block, BlockVariables.blockItemProperties.rarity(rareLevel));

        setRegistryName(name);
    }
}
