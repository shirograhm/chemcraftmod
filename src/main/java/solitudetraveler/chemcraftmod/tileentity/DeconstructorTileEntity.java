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
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.DeconstructorContainer;
import solitudetraveler.chemcraftmod.handler.DeconstructorRecipeHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DeconstructorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private IItemHandler inventory = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if(slot == 0) {
                return true;
            }
            if(slot == 1) {
                return DeconstructorRecipeHandler.isValidFuelItem(stack.getItem());
            }
            return false;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }
    };
    private LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> inventory);

    private boolean isDeconstructing;
    private int deconstructionTimeLeft;

    private final int DECONSTRUCTION_TIME = 320;

    public DeconstructorTileEntity() {
        super(BlockList.DECONSTRUCTOR_TILE_TYPE);

        this.deconstructionTimeLeft = 0;
        this.isDeconstructing = false;
    }

    public float getDeconstructionTimeScaled() {
        return 1.0f * deconstructionTimeLeft / DECONSTRUCTION_TIME;
    }

    @Override
    public void tick() {
        if(!world.isRemote) return;

        Item input = this.inventory.extractItem(0, 1, true).getItem();

        if(!isDeconstructing) {
            ItemStack[] out = DeconstructorRecipeHandler.getResultStacksForInput(input);

            if(DeconstructorRecipeHandler.outputNotEmpty(out)) {
                deconstructionTimeLeft = DECONSTRUCTION_TIME;
                isDeconstructing = true;
            } else {
                isDeconstructing = false;
            }
        }

        if(isDeconstructing) {
            if(deconstructionTimeLeft > 0) {
                deconstructionTimeLeft--;
                System.out.println("Time left: " + deconstructionTimeLeft);
            }
            else if(deconstructionTimeLeft == 0) {
                ItemStack[] out = DeconstructorRecipeHandler.getResultStacksForInput(input);

                this.inventory.extractItem(0, 1, false);

                for(int i = 0; i < out.length; i++) {
                    this.inventory.insertItem(i + 2, out[i], false);
                    ((ItemStackHandler) this.inventory).setStackInSlot(i + 2, out[i]);
                }
                isDeconstructing = false;
            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT compoundNBT = tag.getCompound("inv");
        inventoryHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(compoundNBT));
        this.deconstructionTimeLeft = tag.getInt("timeLeft");
        this.isDeconstructing = (tag.getInt("isDeconstructing") == 0);
        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        inventoryHandler.ifPresent(h -> {
            CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compoundNBT);
            tag.put("timeLeft",  new IntNBT(deconstructionTimeLeft));
            tag.put("isDeconstructing", new IntNBT(isDeconstructing ? 0 : 1));
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
        return new DeconstructorContainer(i, world, pos, playerInventory, playerEntity);
    }
}
