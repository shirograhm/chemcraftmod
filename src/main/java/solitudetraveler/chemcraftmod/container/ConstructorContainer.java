package solitudetraveler.chemcraftmod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.item.ElementItem;

import javax.annotation.Nullable;
import java.util.Objects;

public class ConstructorContainer extends Container {

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public ConstructorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockList.CONSTRUCTOR_CONTAINER, id);

        tileEntity = world.getTileEntity(pos);
        playerEntity = player;
        playerInventory = new InvWrapper(playerInv);

        if(tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 30, 17));
                addSlot(new SlotItemHandler(h, 1, 30, 35));
                addSlot(new SlotItemHandler(h, 2, 30, 53));
                addSlot(new SlotItemHandler(h, 3, 48, 17));
                addSlot(new SlotItemHandler(h, 4, 48, 35));
                addSlot(new SlotItemHandler(h, 5, 48, 53));
                addSlot(new SlotItemHandler(h, 6, 66, 17));
                addSlot(new SlotItemHandler(h, 7, 66, 35));
                addSlot(new SlotItemHandler(h, 8, 66, 53));

                addSlot(new SlotItemHandler(h, 9, 124, 35));
            });
        }
        layoutPlayerInventorySlots(10, 70);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos()), playerEntity, BlockList.constructor);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (stack.getItem() instanceof ElementItem) {
                    if (!this.mergeItemStack(stack, 0, 9, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37) {
                    if (!this.mergeItemStack(stack, 37, 46, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 46 && !this.mergeItemStack(stack, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, stack);
        }
        return itemstack;
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
