package solitudetraveler.chemcraftmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

public class CompoundItem extends Item {
    public CompoundItem(ResourceLocation name) {
        super(new Item.Properties().group(CreativeTabList.compoundGroup));

        setRegistryName(name);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(this.equals(ItemList.hydroxide)) {
            System.out.println("You did it!");
        }
    }
}
