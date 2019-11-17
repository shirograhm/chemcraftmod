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
import solitudetraveler.chemcraftmod.container.BeakerContainer;
import solitudetraveler.chemcraftmod.handler.BeakerRecipeHandler;
import solitudetraveler.chemcraftmod.item.CompoundItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class BeakerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    public static final int BEAKER_INPUT_1 = 0;
    public static final int BEAKER_INPUT_2 = 1;
    public static final int BEAKER_INPUT_3 = 2;
    public static final int BEAKER_INPUT_4 = 3;
    public static final int BEAKER_OUTPUT_1 = 4;
    public static final int BEAKER_OUTPUT_2 = 5;
    public static final int BEAKER_OUTPUT_3 = 6;
    public static final int BEAKER_OUTPUT_4 = 7;
    public static final int NUMBER_BEAKER_SLOTS = 8;

    private ItemStackHandler inventory;

    private static final int REACTION_TIME = 99;
    private boolean isInUse;
    private int timeProcessingLeft;

    public BeakerTileEntity() {
        super(BlockList.BEAKER_TILE_TYPE);

        inventory = generateInventory();
        isInUse = false;
        timeProcessingLeft = 0;
    }

    private ItemStackHandler generateInventory() {
        return new ItemStackHandler(NUMBER_BEAKER_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                Item item = stack.getItem();

                if(item instanceof CompoundItem) return true;

                return false;
            }
        };
    }

    public int getProcessStage() {
        return (REACTION_TIME - timeProcessingLeft) % (REACTION_TIME / 3);
    }

    public int getTimeProcessedLeft() {
        return timeProcessingLeft;
    }

    public boolean isInUse() {
        return isInUse;
    }

    @Override
    public void tick() {
        // CLIENT AND SERVER
        ArrayList<Item> inputs = new ArrayList<>();
        for(int i = 0; i < inventory.getSlots(); i++) {
            if(!inventory.getStackInSlot(i).isEmpty()) inputs.add(inventory.getStackInSlot(i).getItem());
        }
        ItemStack[] result = BeakerRecipeHandler.getResultStacksForInputArray(inputs);

        if(isInUse) {
            timeProcessingLeft--;
            if(!BeakerRecipeHandler.outputNotEmpty(result)) {
                isInUse = false;
                timeProcessingLeft = 0;
            }
        } else {
            if(BeakerRecipeHandler.outputNotEmpty(result)) {
                timeProcessingLeft = REACTION_TIME;
                isInUse = true;
            }
        }

        // CLIENT SIDE ONLY
        if(world != null && !world.isRemote) {
            if(isInUse && timeProcessingLeft == 0) {
                inventory.extractItem(BEAKER_INPUT_1, 1, false);
                inventory.extractItem(BEAKER_INPUT_2, 1, false);
                inventory.extractItem(BEAKER_INPUT_3, 1, false);
                inventory.extractItem(BEAKER_INPUT_4, 1, false);
                for(int i = 0; i < 4; i++) {
                    inventory.setStackInSlot(i + 4, result[i]);
                }
                isInUse = false;
            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT compoundNBT = tag.getCompound("inv");
        inventory.deserializeNBT(compoundNBT);
        this.timeProcessingLeft = tag.getInt("timeProcessing");
        this.isInUse = (tag.getInt("isInUse") == 0);

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        CompoundNBT compoundNBT = inventory.serializeNBT();
        tag.put("inv", compoundNBT);
        tag.put("timeProcessing",  new IntNBT(timeProcessingLeft));
        tag.put("isInUse", new IntNBT(isInUse ? 0 : 1));

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
        return new BeakerContainer(i, world, pos, playerInventory, playerEntity);
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < NUMBER_BEAKER_SLOTS; i++) {
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
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.setStackInSlot(index, stack);
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
