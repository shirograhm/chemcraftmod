package solitudetraveler.chemcraftmod.creativetab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;

public class ToolsItemGroup extends ItemGroup {
    public ToolsItemGroup() {
        super("toolsGroup");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemList.deconstruction_token);
    }
}
