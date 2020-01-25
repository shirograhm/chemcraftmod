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
import solitudetraveler.chemcraftmod.tileentity.VolcanoTileEntity;

import javax.annotation.Nonnull;

public class VolcanoContainer extends Container {
    public VolcanoTileEntity tileEntity;

    private IItemHandler playerInventory;

    public VolcanoContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockVariables.VOLCANO_CONTAINER, id);

        tileEntity = (VolcanoTileEntity) world.getTileEntity(pos);
        playerInventory = new InvWrapper(playerInv);

        addSlot(new Slot(tileEntity, VolcanoTileEntity.VOLCANO_SLOT_1, 53, 21));
        addSlot(new Slot(tileEntity, VolcanoTileEntity.VOLCANO_SLOT_2, 107, 21));
        layoutPlayerInventorySlots(8, 57);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, BlockList.volcano);
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

            // If clicked slot is in deconstructor
            if(index < VolcanoTileEntity.NUMBER_VOLCANO_SLOTS) {
                // Container to inventory
                if (!this.mergeItemStack(stackInSlot, VolcanoTileEntity.NUMBER_VOLCANO_SLOTS, VolcanoTileEntity.NUMBER_VOLCANO_SLOTS + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Inventory to container
                if(!this.mergeItemStack(stackInSlot, VolcanoTileEntity.VOLCANO_SLOT_1, VolcanoTileEntity.VOLCANO_SLOT_1 + 1, false)) {
                    return ItemStack.EMPTY;
                }
                if(!this.mergeItemStack(stackInSlot, VolcanoTileEntity.VOLCANO_SLOT_2, VolcanoTileEntity.VOLCANO_SLOT_2 + 1, false)) {
                    return ItemStack.EMPTY;
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
