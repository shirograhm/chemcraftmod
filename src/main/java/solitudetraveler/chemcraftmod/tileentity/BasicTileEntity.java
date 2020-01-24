package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.GeneratorBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BasicTileEntity extends TileEntity implements ITickableTileEntity, IInventory {
    protected ItemStackHandler inventory;
    protected boolean isActive;

    public BasicTileEntity(TileEntityType type, int numberOfSlots) {
        super(type);

        this.isActive = false;
        inventory = generateInventory(numberOfSlots);
    }

    private ItemStackHandler generateInventory(int numSlots) {
        return new ItemStackHandler(numSlots) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }
        };
    }

    @Override
    public void tick() {
        isActive = checkForPower();

        sendUpdates();
    }

    protected boolean checkForPower() {
        // Check for power
        BlockState[] adjacentStates = {
                world.getBlockState(pos.add(0, 0, 1)),
                world.getBlockState(pos.add(0, 0, -1)),
                world.getBlockState(pos.add(0, 1, 0)),
                world.getBlockState(pos.add(0, -1, 0)),
                world.getBlockState(pos.add(1, 0, 0)),
                world.getBlockState(pos.add(-1, 0, 0)),
        };
        // Check adjacent blocks
        for (BlockState state : adjacentStates) {
            if (state.getBlock() instanceof GeneratorBlock && state.get(BlockStateProperties.POWERED)) {
                return true;
            }
        }
        // False if none of the adjacent blocks are powered generators
        return false;
    }

    @Override
    public void read(CompoundNBT tag) {
        inventory.deserializeNBT(tag.getCompound("inv"));

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", inventory.serializeNBT());

        return super.write(tag);
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

    protected void sendUpdates() {
        world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
        markDirty();
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < inventory.getSlots(); i++) {
            if(!inventory.getStackInSlot(i).isEmpty()) return false;
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
        return inventory.extractItem(index, count, false);
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
