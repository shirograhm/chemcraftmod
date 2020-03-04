package solitudetraveler.chemcraftmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.creativetab.CreativeTabList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChemicalIndexItem extends Item implements INamedContainerProvider {
    public ChemicalIndexItem(ResourceLocation name) {
        super(new Item.Properties().group(CreativeTabList.basicItemGroup).maxStackSize(1));

        setRegistryName(name);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return ActionResultType.PASS;
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull LivingEntity entityLiving) {
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        // Open GUI for Book

        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        return super.updateItemStackNBT(nbt);
    }

    @Nullable
    @Override
    public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
        // Not sure how to use this yet
        return null;
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        ResourceLocation location = getRegistryName();
        return new StringTextComponent(location != null ? location.getPath() : "");
    }
}
