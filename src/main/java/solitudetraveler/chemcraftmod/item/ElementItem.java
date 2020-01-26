package solitudetraveler.chemcraftmod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;
import solitudetraveler.chemcraftmod.effect.EffectList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ElementItem extends Item {
    private int atomicNumber;

    public ElementItem(ResourceLocation name, int a) {
        super(new Item.Properties().group(CreativeTabList.elementGroup));

        setRegistryName(name);
        this.atomicNumber = a;
    }

    @Nonnull
    @Override
    public Rarity getRarity(ItemStack stack) {
        if(atomicNumber < 0) return Rarity.EPIC;

        return super.getRarity(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (this.atomicNumber > 0) tooltip.add(new StringTextComponent("Element number " + atomicNumber));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        applyRadiation(entityIn);
    }

    private void applyRadiation(Entity entityIn) {
        int damageAmount;
        int effectStrength;
        Random rand = new Random();

        if(emitsRadiationI()) {
            // [0, 2]
            damageAmount = rand.nextInt(3);
            effectStrength = 1;
        }
        else if(emitsRadiationII()) {
            // [0, 3]
            damageAmount = rand.nextInt(4);
            effectStrength = 2;
        }
        else {
            return;
        }
        // Check is player
        if(!(entityIn instanceof PlayerEntity)) return;
        // If we didn't return, we are being radiated
        // Check for player wearing hazmat gear
        PlayerEntity pe = (PlayerEntity) entityIn;

        if(playerWearingHazmatGear(pe.getArmorInventoryList())) {
            // TODO: Fix damage hazmat suit when experiencing radiation
            damageSuit(pe, damageAmount);
        } else {
            EffectInstance currentInstance = pe.getActivePotionEffect(EffectList.radiation);
            EffectInstance radiationInstance = new EffectInstance(EffectList.radiation, 360, effectStrength);

            // If not being radiated, add effect
            if(currentInstance == null) {
                pe.addPotionEffect(radiationInstance);
            }
            else {
                if(currentInstance.getDuration() == 310) {
                    currentInstance.combine(radiationInstance);
                }
            }
        }
    }

    private boolean playerWearingHazmatGear(Iterable<ItemStack> itr) {
        for(ItemStack stack : itr) {
            if(!(stack.getItem() instanceof HazmatArmorItem)) return false;
        }
        return true;
    }

    private void damageSuit(PlayerEntity pe, int damageAmount) {
        for(ItemStack stack : pe.getArmorInventoryList()) {
            // If radiation is ready to be applied, damage armor
            stack.damageItem(damageAmount, pe, (entity) -> {});
        }
    }

    private boolean emitsRadiationI() {
        return atomicNumber == 43 || atomicNumber == 61;
    }

    private boolean emitsRadiationII() {
        return atomicNumber < 0 || atomicNumber >= 84;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }
}
