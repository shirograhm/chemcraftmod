package solitudetraveler.chemcraftmod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

import javax.annotation.Nullable;
import java.util.List;

public class CompoundItem extends Item {
    private String infoLine;

    public CompoundItem(ResourceLocation name, String text) {
        super(new Item.Properties().group(CreativeTabList.compoundGroup));

        infoLine = text;

        setRegistryName(name);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(infoLine != "") {
            tooltip.add(new StringTextComponent(infoLine));
        }
    }
}
