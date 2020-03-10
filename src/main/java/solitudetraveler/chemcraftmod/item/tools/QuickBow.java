package solitudetraveler.chemcraftmod.item.tools;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

import javax.annotation.Nonnull;

public class QuickBow extends BowItem {
    private float pullSpeed = 6.0F;

    public QuickBow(ResourceLocation name) {
        super(new Item.Properties().maxStackSize(1).group(CreativeTabList.equipmentGroup).rarity(Rarity.RARE));

        setRegistryName(name);

        this.addPropertyOverride(new ResourceLocation("pull"), (itemStack, world, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return !(livingEntity.getActiveItemStack().getItem() instanceof BowItem) ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / pullSpeed;
            }
        });
    }

    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World world, LivingEntity livingEntity, int timeLeft) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            boolean flag = playerEntity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = playerEntity.findAmmo(stack);

            int chargeAmount = this.getUseDuration(stack) - timeLeft;
            chargeAmount = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, playerEntity, chargeAmount, !itemstack.isEmpty() || flag);
            if (chargeAmount < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getCurrentArrowVelocity(chargeAmount);

                if (f >= 0.1F) {
                    boolean flag1 = playerEntity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerEntity));
                    if (!world.isRemote) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(world, itemstack, playerEntity);
                        abstractarrowentity = customeArrow(abstractarrowentity);
                        abstractarrowentity.shoot(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (k > 0) {
                            abstractarrowentity.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            abstractarrowentity.setFire(100);
                        }

                        stack.damageItem(1, playerEntity, (player) -> {
                            player.sendBreakAnimation(playerEntity.getActiveHand());
                        });
                        if (flag1 || playerEntity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        world.addEntity(abstractarrowentity);
                    }

                    world.playSound(null, playerEntity.posX, playerEntity.posY, playerEntity.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !playerEntity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerEntity.inventory.deleteStack(itemstack);
                        }
                    }

                    playerEntity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public float getCurrentArrowVelocity(int charge) {
        float chargeAdjusted = (float) charge / pullSpeed;
        chargeAdjusted = (chargeAdjusted * (chargeAdjusted + 2.0F)) / 3.0F;

        if (chargeAdjusted > 1.0F) {
            chargeAdjusted = 1.0F;
        }

        return chargeAdjusted;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return (int) pullSpeed * 3600;
    }

    @Override
    public int getItemEnchantability() {
        return 20;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 690;
    }
}
