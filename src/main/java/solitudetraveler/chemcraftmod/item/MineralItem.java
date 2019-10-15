package solitudetraveler.chemcraftmod.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

public class MineralItem extends Item {
    public MineralItem(ResourceLocation name) {
        super(new Item.Properties().group(CreativeTabList.mineralGroup));

        setRegistryName(name);
    }
}
