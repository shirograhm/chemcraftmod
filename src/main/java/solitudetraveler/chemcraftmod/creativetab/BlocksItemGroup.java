package solitudetraveler.chemcraftmod.creativetab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.block.BlockList;

public class BlocksItemGroup extends ItemGroup {
    public BlocksItemGroup() {
        super("blocksGroup");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Item.BLOCK_TO_ITEM.get(BlockList.dolomite));
    }
}
