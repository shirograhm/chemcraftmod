package solitudetraveler.chemcraftmod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ElementItem extends Item {

    private int aNum;

    public ElementItem(int atomicNumber) {
        super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(32).rarity(Rarity.RARE));

        aNum = atomicNumber;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        list.add(new TextComponent() {
            @Override
            public String getUnformattedComponentText() {
                return "Element number " + aNum + ".";
            }

            @Override
            public ITextComponent shallowCopy() {
                return null;
            }
        });
    }
}
