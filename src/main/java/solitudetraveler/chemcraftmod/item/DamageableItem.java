package solitudetraveler.chemcraftmod.item;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import solitudetraveler.chemcraftmod.ChemCraftMod;

public class DamageableItem extends Item {
    public DamageableItem() {
        super(new Item.Properties().group(ChemCraftMod.toolsGroup).rarity(Rarity.COMMON).maxDamage(150));
    }

    @Override
    public boolean isDamageable() {
        return true;
    }
}
