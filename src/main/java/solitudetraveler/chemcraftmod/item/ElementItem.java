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
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;
import solitudetraveler.chemcraftmod.effect.EffectList;
import solitudetraveler.chemcraftmod.item.armor.HazmatArmorItem;

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

        if(emitsRadiationII() || emitsRadiationI()) return Rarity.UNCOMMON;

        return super.getRarity(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        ElementInfo.appendToolTip(tooltip, atomicNumber);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        applyRadiation(entityIn);
    }

    private void applyRadiation(Entity entityIn) {
        int effectStrength;
        Random rand = new Random();

        if(emitsRadiationI()) {
            effectStrength = 0;
        }
        else if(emitsRadiationII()) {
            effectStrength = 1;
        }
        else {
            return;
        }
        // Check is player
        if(!(entityIn instanceof PlayerEntity)) return;
        // If we didn't return, we are being radiated
        // Check for player wearing hazmat gear
        PlayerEntity pe = (PlayerEntity) entityIn;

        if(!playerWearingHazmatGear(pe.getArmorInventoryList())) {
            EffectInstance currentInstance = pe.getActivePotionEffect(EffectList.radiation);
            EffectInstance radiationInstance = new EffectInstance(EffectList.radiation, 300, effectStrength);

            // If not being radiated, add effect
            if(currentInstance == null) {
                pe.addPotionEffect(radiationInstance);
            }
            else {
                if(currentInstance.getDuration() <= 260) {
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
