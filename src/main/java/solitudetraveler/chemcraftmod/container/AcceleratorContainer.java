package solitudetraveler.chemcraftmod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.block.BlockVariables;
import solitudetraveler.chemcraftmod.item.ElementItem;
import solitudetraveler.chemcraftmod.item.ItemList;
import solitudetraveler.chemcraftmod.tileentity.AcceleratorTileEntity;

import javax.annotation.Nonnull;

public class AcceleratorContainer extends Container {
    public AcceleratorTileEntity tileEntity;

    private IItemHandler playerInventory;

    public AcceleratorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockVariables.ACCELERATOR_CONTAINER, id);

        tileEntity = (AcceleratorTileEntity) world.getTileEntity(pos);
        playerInventory = new InvWrapper(playerInv);

        addAcceleratorSlot(true, AcceleratorTileEntity.ACCELERATOR_INPUT_1, 17, 22);
        addAcceleratorSlot(true, AcceleratorTileEntity.ACCELERATOR_INPUT_2, 17, 40);
        addAcceleratorSlot(false, AcceleratorTileEntity.ACCELERATOR_OUTPUT_1, 125, 13);
        addAcceleratorSlot(false, AcceleratorTileEntity.ACCELERATOR_OUTPUT_2, 107, 31);
        addAcceleratorSlot(false, AcceleratorTileEntity.ACCELERATOR_OUTPUT_3, 125, 31);
        addAcceleratorSlot(false, AcceleratorTileEntity.ACCELERATOR_OUTPUT_4, 143, 31);
        addAcceleratorSlot(false, AcceleratorTileEntity.ACCELERATOR_OUTPUT_5, 125, 49);
        layoutPlayerInventorySlots(8, 76);
    }

    private void addAcceleratorSlot(boolean isInputSlot, int slotID, int xPos, int yPos) {
        if(isInputSlot) {
            addSlot(new Slot(tileEntity, slotID, xPos, yPos) {
                @Override
                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() instanceof ElementItem && stack.getItem() != ItemList.unknown;
                }

                @Override
                public int getSlotStackLimit() {
                    return 1;
                }
            });
        }
        else {
            addSlot(new Slot(tileEntity, slotID, xPos, yPos) {
                @Override
                public boolean isItemValid(ItemStack stack) {
                    return false;
                }
            });
        }
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack previousStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        // If slot contains some itemstack
        if(slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            previousStack = stackInSlot.copy();

            // If index is in accelerator
            if(index < AcceleratorTileEntity.NUMBER_ACCELERATOR_SLOTS) {
                // Container to inventory
                if(!this.mergeItemStack(stackInSlot, AcceleratorTileEntity.NUMBER_ACCELERATOR_SLOTS, AcceleratorTileEntity.NUMBER_ACCELERATOR_SLOTS + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Inventory to container
                if(inventorySlots.get(AcceleratorTileEntity.ACCELERATOR_INPUT_1).getStack().isEmpty()) {
                    if (!this.mergeItemStack(stackInSlot, AcceleratorTileEntity.ACCELERATOR_INPUT_1, AcceleratorTileEntity.ACCELERATOR_INPUT_1 + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(!this.mergeItemStack(stackInSlot, AcceleratorTileEntity.ACCELERATOR_INPUT_2, AcceleratorTileEntity.ACCELERATOR_INPUT_2 + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if(stackInSlot.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if(stackInSlot.getCount() == previousStack.getCount()) return ItemStack.EMPTY;
        }

        return previousStack;
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, BlockList.accelerator);
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hot bar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }
}
