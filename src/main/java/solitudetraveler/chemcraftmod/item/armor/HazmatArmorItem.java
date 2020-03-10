package solitudetraveler.chemcraftmod.item.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

public class HazmatArmorItem extends ArmorItem {
    public HazmatArmorItem(ResourceLocation name, EquipmentSlotType type, int damage) {
        super(CustomArmorMaterials.HAZMAT, type, new Item.Properties().maxDamage(damage).group(CreativeTabList.equipmentGroup));

        setRegistryName(name);
    }

    @Override
    public int getDamageReduceAmount() {
        return 1;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public float getToughness() {
        return super.getToughness() + 0.5F;
    }
}
