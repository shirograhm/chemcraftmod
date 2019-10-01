package solitudetraveler.chemcraftmod.creativetab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;

public class ElementItemGroup extends ItemGroup {
    public ElementItemGroup() {
        super("elementsGroup");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemList.hydrogen);
    }
}
