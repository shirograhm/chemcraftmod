package solitudetraveler.chemcraftmod.item.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.function.Supplier;

public enum CustomItemTier implements IItemTier {
    GODLIKE(5, 6900, 12.0F, 5.0F, 24, () -> {
        return Ingredient.fromItems(ItemList.getElementNumber(1));
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadBase<Ingredient> repairMaterial;

    private CustomItemTier(int harvest, int max, float eff, float attack, int enchant, Supplier<Ingredient> material) {
        this.harvestLevel = harvest;
        this.maxUses = max;
        this.efficiency = eff;
        this.attackDamage = attack;
        this.enchantability = enchant;
        this.repairMaterial = new LazyLoadBase<>(material);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }


}
