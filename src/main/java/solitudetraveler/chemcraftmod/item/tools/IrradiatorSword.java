package solitudetraveler.chemcraftmod.item.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.effect.EffectList;
import solitudetraveler.chemcraftmod.item.ItemVariables;

import javax.annotation.Nullable;
import java.util.List;

public class IrradiatorSword extends SwordItem {
    public IrradiatorSword(ResourceLocation name) {
        super(CustomItemTier.GODLIKE, 6, -1.6F, ItemVariables.equipmentProperty.rarity(Rarity.RARE));

        setRegistryName(name);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return 25;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addPotionEffect(new EffectInstance(EffectList.radiation, 160, 0));

        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("A feat of modern technology, this sword inflicts radiation damage onto anything it touches."));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
