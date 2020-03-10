package solitudetraveler.chemcraftmod.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

public class BasicItem extends Item {
    public BasicItem(ResourceLocation name) {
        super(new Item.Properties().group(CreativeTabList.basicItemGroup).maxStackSize(64));

        setRegistryName(name);
    }

    public BasicItem(ResourceLocation name, int maxStackSize) {
        super(new Item.Properties().group(CreativeTabList.basicItemGroup).maxStackSize(maxStackSize));

        setRegistryName(name);
    }
}
