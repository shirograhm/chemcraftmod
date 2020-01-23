package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
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
import solitudetraveler.chemcraftmod.recipes.DeconstructorRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class DeconstructorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    public static final int DECONSTRUCTOR_INPUT = 0;
    public static final int DECONSTRUCTOR_OUTPUT_FIRST = 1;
    public static final int DECONSTRUCTOR_OUTPUT_LAST = 6;
    public static final int NUMBER_DECONSTRUCTOR_SLOTS = 7;

    private ItemStackHandler inventory;

    private static final int DECONSTRUCTION_TIME = 320;
    private boolean isDeconstructing;
    private int deconstructionTimeLeft;

    public DeconstructorTileEntity() {
        super(BlockList.DECONSTRUCTOR_TILE_TYPE);

        inventory = generateInventory();
        this.deconstructionTimeLeft = 0;
        this.isDeconstructing = false;
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
                    return true;
                }
                return false;
            }
        };
    }

    private boolean isOutputEmpty() {
        ArrayList<ItemStack> outs = new ArrayList<>();
        for(int i = DECONSTRUCTOR_OUTPUT_FIRST; i <= DECONSTRUCTOR_OUTPUT_LAST; i++) {
            ItemStack is = inventory.getStackInSlot(i);
            if(!is.isEmpty() && is.getItem() != Items.AIR) {
                outs.add(is);
            }
        }

        return outs.size() == 0;
    }

    public double getDeconstructionTimeScaled() {
        return (DECONSTRUCTION_TIME - deconstructionTimeLeft) * 1.0 / DECONSTRUCTION_TIME;
    }

    public boolean isDeconstructing() {
        return isDeconstructing;
    }

    @Override
    public void tick() {
        // If world is null, return
        if(world == null) return;
        // If on client, return
        if(world.isRemote) return;

        // If on server, do recipe computations
        ItemStack input = inventory.getStackInSlot(DECONSTRUCTOR_INPUT);
        DeconstructorRecipe recipe = DeconstructorRecipeHandler.getRecipeForInputs(input);

        // If outputs are not empty
        if(!isOutputEmpty()) {
            isDeconstructing = false;
            deconstructionTimeLeft = 0;
        }
        else {
            // If input is valid
            if(recipe != null) {
                // If we are already deconstructing
                if(isDeconstructing) {
                    deconstructionTimeLeft--;
                }
                // Otherwise, begin deconstruction
                else {
                    isDeconstructing = true;
                    deconstructionTimeLeft = DECONSTRUCTION_TIME;
                }
            }
            // Otherwise, if input is invalid
            else {
                // Stop deconstruction
                isDeconstructing = false;
                deconstructionTimeLeft = 0;
            }
        }
        if (isDeconstructing && deconstructionTimeLeft == 0) {
            // Clear input stack
            inventory.setStackInSlot(DECONSTRUCTOR_INPUT, ItemStack.EMPTY);
            // Set output stacks
            for (int i = 0; i < recipe.getResults().size(); i++) {
                inventory.setStackInSlot(i + 1, recipe.getResults().get(i));
            }
            // Reset machine
            isDeconstructing = false;
        }

        // Send updates to client
        sendUpdates();
    }

    @Override
    public void read(CompoundNBT tag) {
        inventory.deserializeNBT(tag.getCompound("inv"));
        this.deconstructionTimeLeft = tag.getInt("timeLeft");
        this.isDeconstructing = tag.getBoolean("isDeconstructing");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", inventory.serializeNBT());
        tag.putInt("timeLeft", deconstructionTimeLeft);
        tag.putBoolean("isDeconstructing", isDeconstructing);

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
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    private void sendUpdates() {
        world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
        markDirty();
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
