package solitudetraveler.chemcraftmod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;
import solitudetraveler.chemcraftmod.effect.EffectList;

import javax.annotation.Nullable;
import java.util.List;

public class ElementItem extends Item {
    private int atomicNumber;

    public ElementItem(ResourceLocation name, int a) {
        super(new Item.Properties().group(CreativeTabList.elementGroup));

        setRegistryName(name);
        this.atomicNumber = a;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if(this.atomicNumber > 0) tooltip.add(new StringTextComponent("Element number " + atomicNumber + "."));
        else tooltip.add(new StringTextComponent("Unknown??"));
    }


    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(atomicNumber < 0 || atomicNumber == 43 || atomicNumber == 61 || atomicNumber >= 84) {
            // Check if entity holding is a player with hazmat gear
            if(entityIn instanceof PlayerEntity) {
                PlayerEntity pe = (PlayerEntity) entityIn;

                if(!playerWearingHazmatGear(pe)) {
                    pe.addPotionEffect(new EffectInstance(EffectList.radiation, 360, 1, false, true));
                }
            }
        }
    }

    private boolean playerWearingHazmatGear(PlayerEntity pe) {
        for(ItemStack stack : pe.getArmorInventoryList()) {
            if(!(stack.getItem() instanceof HazmatArmorItem)) return false;
        }
        return true;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }
}
