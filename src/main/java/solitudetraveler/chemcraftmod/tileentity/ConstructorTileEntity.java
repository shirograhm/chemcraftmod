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
import solitudetraveler.chemcraftmod.container.ConstructorContainer;
import solitudetraveler.chemcraftmod.handler.ConstructorRecipeHandler;
import solitudetraveler.chemcraftmod.item.ElementItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConstructorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

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

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }
    };
    private LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> inventory);

    private boolean isConstructing;
    private int constructionTimeLeft;

    private final int CONSTRUCTION_TIME = 320;

    public ConstructorTileEntity() {
        super(BlockList.CONSTRUCTOR_TILE_TYPE);

        this.constructionTimeLeft = 0;
        this.isConstructing = false;
    }

    public float getConstructionTimeScaled() {
        return 1.0f * constructionTimeLeft / CONSTRUCTION_TIME;
    }

    public boolean isConstructing() {
        return isConstructing;
    }

    @Override
    public void tick() {
        if(world.isRemote) return;

        Item[] inputArray = new Item[]{
                this.inventory.extractItem(0, 1, true).getItem(),
                this.inventory.extractItem(1, 1, true).getItem(),
                this.inventory.extractItem(2, 1, true).getItem(),
                this.inventory.extractItem(3, 1, true).getItem(),
                this.inventory.extractItem(4, 1, true).getItem(),
                this.inventory.extractItem(5, 1, true).getItem(),
                this.inventory.extractItem(6, 1, true).getItem(),
                this.inventory.extractItem(7, 1, true).getItem(),
                this.inventory.extractItem(8, 1, true).getItem()
        };

        if(!isConstructing) {
            ItemStack out = ConstructorRecipeHandler.getResultForInputSet(inputArray);

            if(out != ItemStack.EMPTY) {
                constructionTimeLeft = CONSTRUCTION_TIME;
                isConstructing = true;
            } else {
                isConstructing = false;
            }
        }

        if(isConstructing) {
            ItemStack out = ConstructorRecipeHandler.getResultForInputSet(inputArray);

            if(constructionTimeLeft > 0) {
                constructionTimeLeft--;
                System.out.println("Time left: " + constructionTimeLeft);
            }
            else if(constructionTimeLeft == 0) {
                this.inventory.extractItem(0, 1, false);
                this.inventory.extractItem(1, 1, false);
                this.inventory.extractItem(2, 1, false);
                this.inventory.extractItem(3, 1, false);
                this.inventory.extractItem(4, 1, false);
                this.inventory.extractItem(5, 1, false);
                this.inventory.extractItem(6, 1, false);
                this.inventory.extractItem(7, 1, false);
                this.inventory.extractItem(8, 1, false);

                ((ItemStackHandler) this.inventory).setStackInSlot(9, out);
                this.inventory.insertItem(9, out, false);

                isConstructing = false;
            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT compoundNBT = tag.getCompound("inv");
        inventoryHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(compoundNBT));
        this.constructionTimeLeft = tag.getInt("timeLeft");
        this.isConstructing = (tag.getInt("isConstructing") == 0);
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        inventoryHandler.ifPresent(h -> {
            CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compoundNBT);
            tag.put("timeLeft",  new IntNBT(constructionTimeLeft));
            tag.put("isConstructing", new IntNBT(isConstructing ? 0 : 1));

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
        return new ConstructorContainer(i, world, pos, playerInventory, playerEntity);
    }
}
