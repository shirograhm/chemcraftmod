package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.handler.ReconstructorRecipeHandler;
import solitudetraveler.chemcraftmod.item.ElementItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReconstructorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    private static final int RECONSTRUCTOR_INPUT_1 = 0;
    private static final int RECONSTRUCTOR_INPUT_2 = 1;
    private static final int RECONSTRUCTOR_INPUT_3 = 2;
    private static final int RECONSTRUCTOR_INPUT_4 = 3;
    private static final int RECONSTRUCTOR_INPUT_5 = 4;
    private static final int RECONSTRUCTOR_INPUT_6 = 5;
    private static final int RECONSTRUCTOR_INPUT_7 = 6;
    private static final int RECONSTRUCTOR_INPUT_8 = 7;
    private static final int RECONSTRUCTOR_INPUT_9 = 8;
    private static final int RECONSTRUCTOR_OUTPUT = 9;
    public static final int NUMBER_RECONSTRUCTOR_SLOTS = 10;

    private ItemStackHandler inventory;

    private static final int RECONSTRUCTION_TIME = 80;
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
                if(slot >= 0 && slot < 9) {
                    return stack.getItem() instanceof ElementItem;
                }
                return false;
            }
        };
    }

    private Item[] getCurrentInputArray() {
        return new Item[] {
                inventory.getStackInSlot(RECONSTRUCTOR_INPUT_1).getItem(),
                inventory.getStackInSlot(RECONSTRUCTOR_INPUT_2).getItem(),
                inventory.getStackInSlot(RECONSTRUCTOR_INPUT_3).getItem(),
                inventory.getStackInSlot(RECONSTRUCTOR_INPUT_4).getItem(),
                inventory.getStackInSlot(RECONSTRUCTOR_INPUT_5).getItem(),
                inventory.getStackInSlot(RECONSTRUCTOR_INPUT_6).getItem(),
                inventory.getStackInSlot(RECONSTRUCTOR_INPUT_7).getItem(),
                inventory.getStackInSlot(RECONSTRUCTOR_INPUT_8).getItem(),
                inventory.getStackInSlot(RECONSTRUCTOR_INPUT_9).getItem()
        };
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
        Item[] inputArray = getCurrentInputArray();
        ItemStack out = ReconstructorRecipeHandler.getResultForInputSet(inputArray);

        if(isReconstructing) {
            reconstructionTimeLeft--;
            if(out == ItemStack.EMPTY) {
                isReconstructing = false;
                reconstructionTimeLeft = 0;
            }
        } else {
            if(out != ItemStack.EMPTY) {
                reconstructionTimeLeft = RECONSTRUCTION_TIME;
                isReconstructing = true;
            }
        }

        // CLIENT SIDE ONLY
        if (world != null && !world.isRemote) {
            if (isReconstructing && reconstructionTimeLeft == 0) {
                for (int i = 0; i < 9; i++) {
                    int countLeft = inventory.getStackInSlot(i).getCount() - 1;
                    inventory.setStackInSlot(i, ItemHandlerHelper.copyStackWithSize(inventory.getStackInSlot(i), countLeft));
                }
                inventory.setStackInSlot(RECONSTRUCTOR_OUTPUT, out);

                isReconstructing = false;
            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        inventory.deserializeNBT(tag.getCompound("inv"));
        this.reconstructionTimeLeft = tag.getInt("timeLeft");
        this.isReconstructing = (tag.getInt("isReconstructing") == 0);

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", inventory.serializeNBT());
        tag.put("timeLeft",  new IntNBT(reconstructionTimeLeft));
        tag.put("isReconstructing", new IntNBT(isReconstructing ? 0 : 1));

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
        for(int i = 0; i < NUMBER_RECONSTRUCTOR_SLOTS; i++) {
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
