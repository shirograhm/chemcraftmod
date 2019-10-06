package solitudetraveler.chemcraftmod.creativetab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;

import javax.annotation.Nonnull;

public class MineralsItemGroup extends ItemGroup {
    public MineralsItemGroup() {
        super("mineralsGroup");
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemList.calcite);
    }
}
