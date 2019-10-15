package solitudetraveler.chemcraftmod.item;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

public class AtomicItem extends Item {
    public AtomicItem(ResourceLocation name) {
        super(new Item.Properties()
                .group(CreativeTabList.elementGroup)
                .rarity(Rarity.EPIC)
                .maxStackSize(16));

        setRegistryName(name);
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return false;
    }
}
