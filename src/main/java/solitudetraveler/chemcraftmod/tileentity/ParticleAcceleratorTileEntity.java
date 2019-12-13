package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.ParticleAcceleratorContainer;
import solitudetraveler.chemcraftmod.item.AtomicItem;
import solitudetraveler.chemcraftmod.item.ElementItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ParticleAcceleratorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    public static final int PARTICLE_INPUT_1 = 0;
    public static final int PARTICLE_INPUT_2 = 1;
    public static final int PARTICLE_OUTPUT_1 = 2;
    public static final int PARTICLE_OUTPUT_2 = 3;
    public static final int PARTICLE_OUTPUT_3 = 4;
    public static final int PARTICLE_OUTPUT_4 = 5;
    public static final int PARTICLE_OUTPUT_5 = 6;
    public static final int NUMBER_PARTICLE_SLOTS = 7;

    private ItemStackHandler inventory;

    private static final double BASE_PARTICLE_CHANCE = 6.909;
    private boolean isActive;
    private double multiplier;

    public ParticleAcceleratorTileEntity() {
        super(BlockList.PARTICLE_TILE_TYPE);

        inventory = generateInventory();
        this.isActive = false;
        this.multiplier = 1.0;
    }

    private ItemStackHandler generateInventory() {
        return new ItemStackHandler(NUMBER_PARTICLE_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(slot == PARTICLE_INPUT_1 || slot == PARTICLE_INPUT_2) {
                    return stack.getItem() instanceof ElementItem || stack.getItem() instanceof AtomicItem;
                }
                return false;
            }
        };
    }

    public boolean isActive() {
        return isActive;
    }

    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public void tick() {

    }

    @Override
    public void read(CompoundNBT tag) {
        inventory.deserializeNBT(tag.getCompound("inv"));
        this.isActive = tag.getBoolean("isActive");
        this.multiplier = tag.getDouble("multiplier");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", inventory.serializeNBT());
        tag.putDouble("multiplier", multiplier);
        tag.putBoolean("isActive", isActive);

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
            return new ParticleAcceleratorContainer(i, world, pos, playerInventory, playerEntity);
        }
        return null;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < NUMBER_PARTICLE_SLOTS; i++) {
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
