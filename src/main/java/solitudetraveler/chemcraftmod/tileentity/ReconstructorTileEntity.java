package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
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
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.handler.ReconstructorRecipeHandler;
import solitudetraveler.chemcraftmod.item.ElementItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReconstructorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private IItemHandler inventory = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if(slot >= 0 && slot < 9) {
                return stack.getItem() instanceof ElementItem;
            }
            return false;
        }
    };
    private LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> inventory);

    private boolean isReconstructing;
    private int reconstructionTimeLeft;
    private final int RECONSTRUCTION_TIME = 160;

    public ReconstructorTileEntity() {
        super(BlockList.RECONSTRUCTOR_TILE_TYPE);

        this.reconstructionTimeLeft = 0;
        this.isReconstructing = false;
    }


    @Override
    public void tick() {
        ItemStackHandler invHandler = (ItemStackHandler) this.inventory;
        Item[] inputArray = new Item[]{
                invHandler.getStackInSlot(0).getItem(), invHandler.getStackInSlot(1).getItem(), invHandler.getStackInSlot(2).getItem(),
                invHandler.getStackInSlot(3).getItem(), invHandler.getStackInSlot(4).getItem(), invHandler.getStackInSlot(5).getItem(),
                invHandler.getStackInSlot(6).getItem(), invHandler.getStackInSlot(7).getItem(), invHandler.getStackInSlot(8).getItem()
        };
        ItemStack out = ReconstructorRecipeHandler.getResultForInputSet(inputArray);

        if(!isReconstructing) {
            if(out != ItemStack.EMPTY) {
                reconstructionTimeLeft = RECONSTRUCTION_TIME;
                isReconstructing = true;
            }
        }

        if(isReconstructing) {
            if(out == ItemStack.EMPTY) {
                isReconstructing = false;
            }

            if(reconstructionTimeLeft > 0) {
                reconstructionTimeLeft -= 1;
            }
            else if(reconstructionTimeLeft == 0) {
                if(!world.isRemote) {
                    invHandler.setStackInSlot(0, ItemHandlerHelper.copyStackWithSize(invHandler.getStackInSlot(0), invHandler.getStackInSlot(0).getCount() - 1));
                    invHandler.setStackInSlot(1, ItemHandlerHelper.copyStackWithSize(invHandler.getStackInSlot(1), invHandler.getStackInSlot(1).getCount() - 1));
                    invHandler.setStackInSlot(2, ItemHandlerHelper.copyStackWithSize(invHandler.getStackInSlot(2), invHandler.getStackInSlot(2).getCount() - 1));
                    invHandler.setStackInSlot(3, ItemHandlerHelper.copyStackWithSize(invHandler.getStackInSlot(3), invHandler.getStackInSlot(3).getCount() - 1));
                    invHandler.setStackInSlot(4, ItemHandlerHelper.copyStackWithSize(invHandler.getStackInSlot(4), invHandler.getStackInSlot(4).getCount() - 1));
                    invHandler.setStackInSlot(5, ItemHandlerHelper.copyStackWithSize(invHandler.getStackInSlot(5), invHandler.getStackInSlot(5).getCount() - 1));
                    invHandler.setStackInSlot(6, ItemHandlerHelper.copyStackWithSize(invHandler.getStackInSlot(6), invHandler.getStackInSlot(6).getCount() - 1));
                    invHandler.setStackInSlot(7, ItemHandlerHelper.copyStackWithSize(invHandler.getStackInSlot(7), invHandler.getStackInSlot(7).getCount() - 1));
                    invHandler.setStackInSlot(8, ItemHandlerHelper.copyStackWithSize(invHandler.getStackInSlot(8), invHandler.getStackInSlot(8).getCount() - 1));

                    int outSize = invHandler.getStackInSlot(9).getCount();
                    invHandler.setStackInSlot(9, ItemHandlerHelper.copyStackWithSize(out, outSize + 1));
                }
                isReconstructing = false;
            }
        }
    }


    public double getReconstructionTimeScaled() {
        return (RECONSTRUCTION_TIME - reconstructionTimeLeft) * 1.0 / RECONSTRUCTION_TIME;
    }

    public boolean isReconstructing() {
        return isReconstructing;
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT compoundNBT = tag.getCompound("inv");
        inventoryHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(compoundNBT));
        this.reconstructionTimeLeft = tag.getInt("timeLeft");
        this.isReconstructing = (tag.getInt("isReconstructing") == 0);
        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        inventoryHandler.ifPresent(h -> {
            CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compoundNBT);
            tag.put("timeLeft",  new IntNBT(reconstructionTimeLeft));
            tag.put("isReconstructing", new IntNBT(isReconstructing ? 0 : 1));

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

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
        return new ReconstructorContainer(i, world, pos, playerInventory, playerEntity);
    }
}
