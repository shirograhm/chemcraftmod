package solitudetraveler.chemcraftmod.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.block.BlockList;

public class ExperimentBlockItem extends BlockItem {
    public ExperimentBlockItem(ResourceLocation name, Block block) {
        super(block, BlockList.blockItemProperties.rarity(Rarity.EPIC));

        setRegistryName(name);
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return false;
    }
}
