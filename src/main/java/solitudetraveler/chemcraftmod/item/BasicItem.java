package solitudetraveler.chemcraftmod.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

public class BasicItem extends Item {
    public BasicItem(ResourceLocation name) {
        super(new Item.Properties().group(CreativeTabList.basicItemGroup));

        setRegistryName(name);
    }
}
