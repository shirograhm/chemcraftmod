package solitudetraveler.chemcraftmod.creativetab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;

import javax.annotation.Nonnull;

public class MaterialsItemGroup extends ItemGroup {
    public MaterialsItemGroup() {
        super("materialsGroup");
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemList.diamond_dust);
    }
}
