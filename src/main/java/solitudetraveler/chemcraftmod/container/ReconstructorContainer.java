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
import solitudetraveler.chemcraftmod.slot.ReconstructorResultSlot;
import solitudetraveler.chemcraftmod.tileentity.ReconstructorTileEntity;

import javax.annotation.Nonnull;

public class ReconstructorContainer extends Container {
    public ReconstructorTileEntity tileEntity;

    private IItemHandler playerInventory;

    public ReconstructorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockVariables.RECONSTRUCTOR_CONTAINER, id);

        tileEntity = (ReconstructorTileEntity) world.getTileEntity(pos);
        playerInventory = new InvWrapper(playerInv);

        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_INPUT_1, 30, 17);
        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_INPUT_2, 48, 17);
        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_INPUT_3, 66, 17);
        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_INPUT_4, 30, 35);
        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_INPUT_5, 48, 35);
        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_INPUT_6, 66, 35);
        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_INPUT_7, 30, 53);
        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_INPUT_8, 48, 53);
        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_INPUT_9, 66, 53);

        addReconstructorSlot(ReconstructorTileEntity.RECONSTRUCTOR_OUTPUT, 124, 35);
        layoutPlayerInventorySlots(8, 84);
    }

    private void addReconstructorSlot(int slotID, int xPos, int yPos) {
        if(slotID == ReconstructorTileEntity.RECONSTRUCTOR_OUTPUT) {
            addSlot(new ReconstructorResultSlot(this, tileEntity, slotID, xPos, yPos));
        }
        else {
            addSlot(new Slot(tileEntity, slotID, xPos, yPos) {
                @Override
                public boolean isItemValid(ItemStack stack) {
                    return true;
                }
            });
        }
    }

    public void removeInputs() {
        tileEntity.removeInputs();
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

            if(index < ReconstructorTileEntity.NUMBER_RECONSTRUCTOR_SLOTS) {
                // Container to inventory
                if (!this.mergeItemStack(stackInSlot, ReconstructorTileEntity.NUMBER_RECONSTRUCTOR_SLOTS, ReconstructorTileEntity.NUMBER_RECONSTRUCTOR_SLOTS + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Do nothing (no shift clicking from inventory to container)
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
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, BlockList.reconstructor);
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
