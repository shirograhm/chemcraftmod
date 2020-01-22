package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.handler.ReconstructorRecipeHandler;
import solitudetraveler.chemcraftmod.recipes.ReconstructorRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class ReconstructorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    public static final int RECONSTRUCTOR_INPUT_FIRST = 0;
    public static final int RECONSTRUCTOR_INPUT_LAST = 8;
    public static final int RECONSTRUCTOR_OUTPUT = 9;
    public static final int NUMBER_RECONSTRUCTOR_SLOTS = 10;

    private ItemStackHandler inventory;

    private static final int RECONSTRUCTION_TIME = 60;
    private boolean isReconstructing;
    private int reconstructionTimeLeft;

    public ReconstructorTileEntity() {
        super(BlockList.RECONSTRUCTOR_TILE_TYPE);

        inventory = generateInventory();
        this.reconstructionTimeLeft = 0;
        this.isReconstructing = false;
    }

    private ItemStackHandler generateInventory() {
        return new ItemStackHandler(NUMBER_RECONSTRUCTOR_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(slot >= RECONSTRUCTOR_INPUT_FIRST && slot <= RECONSTRUCTOR_INPUT_LAST) {
                    return true;
                }
                return false;
            }
        };
    }

    private ArrayList<ItemStack> getCurrentInputArray() {
        ArrayList<ItemStack> input = new ArrayList<>();
        for(int i = RECONSTRUCTOR_INPUT_FIRST; i <= RECONSTRUCTOR_INPUT_LAST; i++) {
            ItemStack is = inventory.getStackInSlot(i);
            if (!is.isEmpty() && is.getItem() != Items.AIR) {
                input.add(is);
            }
        }

        return input;
    }

    public double getReconstructionTimeScaled() {
        return (RECONSTRUCTION_TIME - reconstructionTimeLeft) * 1.0 / RECONSTRUCTION_TIME;
    }

    public boolean isReconstructing() {
        return isReconstructing;
    }

    @Override
    public void tick() {
        // CLIENT AND SERVER
        ArrayList<ItemStack> inputArray = getCurrentInputArray();
        ReconstructorRecipe recipe = ReconstructorRecipeHandler.getRecipeForInputs(inputArray);

        // If out stack not empty
        if(inventory.getStackInSlot(RECONSTRUCTOR_OUTPUT) != ItemStack.EMPTY) {
            isReconstructing = false;
            reconstructionTimeLeft = 0;
        }
        else {
            // If input is valid
            if(recipe != null) {
                // If we are already reconstructing
                if(isReconstructing) {
                    reconstructionTimeLeft--;
                }
                // Otherwise, begin reconstruction
                else {
                    isReconstructing = true;
                    reconstructionTimeLeft = RECONSTRUCTION_TIME;
                }
            }
            // Otherwise if the input is invalid
            else {
                // Stop reconstruction
                isReconstructing = false;
                reconstructionTimeLeft = 0;
            }
        }

        // CLIENT SIDE ONLY
        if (world != null && !world.isRemote) {
            if (isReconstructing && reconstructionTimeLeft == 0) {
                // Clear input stacks
                for (int i = RECONSTRUCTOR_INPUT_FIRST; i <= RECONSTRUCTOR_INPUT_LAST; i++) {
                    // Reset input itemstacks
                    inventory.setStackInSlot(i, ItemStack.EMPTY);
                }
                // Set output stack
                inventory.setStackInSlot(RECONSTRUCTOR_OUTPUT, recipe.getOutput());
                // Reset machine
                isReconstructing = false;
            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        inventory.deserializeNBT(tag.getCompound("inv"));
        this.reconstructionTimeLeft = tag.getInt("timeLeft");
        this.isReconstructing = tag.getBoolean("isReconstructing");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", inventory.serializeNBT());
        tag.putInt("timeLeft",  reconstructionTimeLeft);
        tag.putBoolean("isReconstructing", isReconstructing);

        return super.write(tag);
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

    @Override
    public int getSizeInventory() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < inventory.getSlots(); i++) {
            if(inventory.getStackInSlot(i) != ItemStack.EMPTY) {
                return false;
            }
        }
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.getStackInSlot(index);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = inventory.getStackInSlot(index);
        inventory.setStackInSlot(index, ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - count));

        return ItemHandlerHelper.copyStackWithSize(stack, count);
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = inventory.getStackInSlot(index);
        inventory.setStackInSlot(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        inventory.setStackInSlot(index, stack);
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
