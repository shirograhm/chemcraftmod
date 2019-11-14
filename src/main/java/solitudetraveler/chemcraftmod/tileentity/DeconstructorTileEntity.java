package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.DeconstructorContainer;
import solitudetraveler.chemcraftmod.handler.DeconstructorRecipeHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DeconstructorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    public static final int DECONSTRUCTOR_INPUT = 0;
    public static final int DECONSTRUCTOR_OUTPUT_1 = 1;
    public static final int DECONSTRUCTOR_OUTPUT_2 = 2;
    public static final int DECONSTRUCTOR_OUTPUT_3 = 3;
    public static final int DECONSTRUCTOR_OUTPUT_4 = 4;
    public static final int DECONSTRUCTOR_OUTPUT_5 = 5;
    public static final int DECONSTRUCTOR_OUTPUT_6 = 6;
    public static final int NUMBER_DECONSTRUCTOR_SLOTS = 7;

    private ItemStackHandler inventory;

    private static final int DECONSTRUCTION_TIME = 320;
    private boolean isDeconstructing;
    private int deconstructionTimeLeft;

    public DeconstructorTileEntity() {
        super(BlockList.DECONSTRUCTOR_TILE_TYPE);

        inventory = generateInventory();
        this.isDeconstructing = false;
        this.deconstructionTimeLeft = 0;
    }

    private ItemStackHandler generateInventory() {
        return new ItemStackHandler(NUMBER_DECONSTRUCTOR_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(slot == DECONSTRUCTOR_INPUT) {
                    return DeconstructorRecipeHandler.outputNotEmpty(DeconstructorRecipeHandler.getResultStacksForInput(stack.getItem()));
                }
                return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(slot == DECONSTRUCTOR_INPUT) {
                    if(stack.getCount() == 1) {
                        return ItemStack.EMPTY;
                    } else {
                        return ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - 1);
                    }
                }
                return stack;
            }
        };
    }

    public double getDeconstructionTimeScaled() {
        return (DECONSTRUCTION_TIME - deconstructionTimeLeft) * 1.0 / DECONSTRUCTION_TIME;
    }

    public boolean isDeconstructing() {
        return isDeconstructing;
    }

    @Override
    public void tick() {
        // CLIENT AND SERVER
        ItemStack input = inventory.getStackInSlot(DECONSTRUCTOR_INPUT);
        ItemStack[] result = DeconstructorRecipeHandler.getResultStacksForInput(input.getItem());

        if(isDeconstructing) {
            deconstructionTimeLeft--;
            if (!DeconstructorRecipeHandler.outputNotEmpty(result)) {
                isDeconstructing = false;
                deconstructionTimeLeft = 0;
            }
        } else {
            if(DeconstructorRecipeHandler.outputNotEmpty(result)) {
                deconstructionTimeLeft = DECONSTRUCTION_TIME;
                isDeconstructing = true;
            }
        }

        // CLIENT SIDE ONLY
        if (world != null && !world.isRemote) {
            if (isDeconstructing && deconstructionTimeLeft == 0) {
                // Remove 1 input item from input stack
                inventory.extractItem(DECONSTRUCTOR_INPUT, 1, false);
                // Populate output stacks
                for (int i = 0; i < 6; i++) {
                    inventory.setStackInSlot(i + 1, result[i]);
                }
                isDeconstructing = false;
            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        inventory.deserializeNBT(tag.getCompound("inv"));
        this.deconstructionTimeLeft = tag.getInt("timeLeft");
        this.isDeconstructing = (tag.getInt("isDeconstructing") == 0);

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", inventory.serializeNBT());
        tag.put("timeLeft", new IntNBT(deconstructionTimeLeft));
        tag.put("isDeconstructing", new IntNBT(isDeconstructing ? 0 : 1));

        return super.write(tag);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        ResourceLocation location = getType().getRegistryName();
        return new StringTextComponent(location != null ? location.getPath() : "");
    }

    @Nullable
    @Override
    public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
        if (world != null) {
            return new DeconstructorContainer(i, world, pos, playerInventory, playerEntity);
        }
        return null;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < NUMBER_DECONSTRUCTOR_SLOTS; i++) {
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
