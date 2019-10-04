package solitudetraveler.chemcraftmod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.handler.DeconstructorRecipeHandler;

import java.util.Objects;

public class DeconstructorContainer extends Container {

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public DeconstructorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockList.DECONSTRUCTOR_CONTAINER, id);

        tileEntity = world.getTileEntity(pos);
        playerEntity = player;
        playerInventory = new InvWrapper(playerInv);

        if(tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 44, 17));
                addSlot(new SlotItemHandler(h, 1, 44, 53));

                addSlot(new SlotItemHandler(h, 2, 98, 17));
                addSlot(new SlotItemHandler(h, 3, 98, 35));
                addSlot(new SlotItemHandler(h, 4, 98, 53));
                addSlot(new SlotItemHandler(h, 5, 116, 17));
                addSlot(new SlotItemHandler(h, 6, 116, 35));
                addSlot(new SlotItemHandler(h, 7, 116, 53));
            });
        }
        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos()), playerEntity, BlockList.deconstructor);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();
            if(index == 0) {
                if(!this.mergeItemStack(stack, 8, 44, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemStack);
            } else {
                if(DeconstructorRecipeHandler.isValidFuelItem(stack.getItem())) {
                    if(!this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(index < 37) {
                    if(!this.mergeItemStack(stack, 35, 44, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(index < 46 && !this.mergeItemStack(stack, 8, 35, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if(stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if(stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, stack);
        }
        return itemStack;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
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
