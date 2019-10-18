package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.FlaskContainer;
import solitudetraveler.chemcraftmod.item.CompoundItem;
import solitudetraveler.chemcraftmod.item.ElementItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FlaskTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private IItemHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            Item item = stack.getItem();

            return (item instanceof CompoundItem || item instanceof ElementItem);
        }
    };
    private LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> inventory);

    public FlaskTileEntity() {
        super(BlockList.FLASK_TILE_TYPE);
    }

    @Override
    public void tick() {
        // Check for all possible recipes
    }

    public ItemStack addItemToFlask(ItemStack itemUsed) {
        ItemStackHandler handler = ((ItemStackHandler) inventory);
        int numberOfSlots = 2;

        for(int i = 0; i < numberOfSlots; i++) {
            int alreadyInFlask = -1;

            if(handler.getStackInSlot(i).isEmpty()) {
                alreadyInFlask = 0;
            }
            if(handler.getStackInSlot(i).getItem() == itemUsed.getItem()) {
                alreadyInFlask = handler.getStackInSlot(i).getCount();
            }

            if(alreadyInFlask >= 0 && alreadyInFlask < handler.getSlotLimit(i)) {
                handler.setStackInSlot(i, ItemHandlerHelper.copyStackWithSize(itemUsed, 1 + alreadyInFlask));
                return ItemHandlerHelper.copyStackWithSize(itemUsed, itemUsed.getCount() - 1);
            }

        }

        return itemUsed;
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT compoundNBT = tag.getCompound("inv");
        inventoryHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(compoundNBT));

        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        inventoryHandler.ifPresent(h -> {
            CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compoundNBT);
        });
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventoryHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FlaskContainer(i, world, pos, playerInventory, playerEntity);
    }
}
