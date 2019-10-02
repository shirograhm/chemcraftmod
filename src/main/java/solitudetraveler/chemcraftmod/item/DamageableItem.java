package solitudetraveler.chemcraftmod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.ChemCraftMod;

import javax.annotation.Nullable;
import java.util.List;

public class DamageableItem extends Item {
    public DamageableItem() {
        super(new Item.Properties().group(ChemCraftMod.toolsGroup).rarity(Rarity.COMMON).maxDamage(150));
    }

    @Override
    public boolean isDamageable() {
        return true;
    }
}
