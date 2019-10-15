package solitudetraveler.chemcraftmod.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

public class CompoundItem extends Item {
    public CompoundItem(ResourceLocation name) {
        super(new Item.Properties().group(CreativeTabList.compoundGroup));

        setRegistryName(name);
    }
}
