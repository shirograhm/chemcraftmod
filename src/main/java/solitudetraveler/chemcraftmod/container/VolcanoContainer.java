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
import solitudetraveler.chemcraftmod.item.ItemList;
import solitudetraveler.chemcraftmod.tileentity.VolcanoTileEntity;

import javax.annotation.Nonnull;

public class VolcanoContainer extends Container {
    public VolcanoTileEntity tileEntity;

    private IItemHandler playerInventory;

    public VolcanoContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockList.VOLCANO_CONTAINER, id);

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
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();

            // If the slot clicked is one of the volcano slots
            if(index < VolcanoTileEntity.NUMBER_VOLCANO_SLOTS) {
                if(!this.mergeItemStack(stack, VolcanoTileEntity.NUMBER_VOLCANO_SLOTS, VolcanoTileEntity.NUMBER_VOLCANO_SLOTS + 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (stack.getItem().equals(ItemList.vinegar)) {
                    if (!this.mergeItemStack(stack, 0, 1, true)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (stack.getItem().equals(ItemList.baking_soda)) {
                    if (!this.mergeItemStack(stack, 1, 2, true)) {
                        return ItemStack.EMPTY;
                    }
                }
                else {
                    return ItemStack.EMPTY;
                }
            }
        }
        return itemStack;
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
