package solitudetraveler.chemcraftmod.creativetab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.block.BlockList;

import javax.annotation.Nonnull;

public class BlocksItemGroup extends ItemGroup {
    public BlocksItemGroup() {
        super("blocksGroup");
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(Item.BLOCK_TO_ITEM.get(BlockList.dolomite));
    }
}
